package com.example.interdisciplinar.mobile;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import DAO.ProdutoMaterialDAO;
import Enum.NegocioStatus;
import model.Materiais;
import model.Negocio;
import model.Produto;
import model.Produto_material;
import util.Dialog;
import util.FuncoesExternas;

public class ProdutoMaterialActivity extends AppCompatActivity {

    private Produto_material prm = new Produto_material();
    private Produto produto  =new Produto();
    private ArrayAdapter<Produto_material> adpMateriais = null;
    public static List<Materiais> lsMateriais = new ArrayList<>();
    private ListView listViewMateial = null;
    private  AlertDialog alertM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produto_material);

        adpMateriais = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        listViewMateial = (ListView)findViewById(R.id.produtoMaterialListView);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.produtoMaterialBtnNovo);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(produto.getNegocio().getNeg_codigo()!=0 && produto.getNegocio().getNeg_cstatus() == NegocioStatus.ABERTO) {
                    Intent i = new Intent(ProdutoMaterialActivity.this, MateriaisProdutoActivity.class);
                    i.putExtra("PRODUTO", produto);
                    startActivity(i);
                }else{
                    Dialog.Show(ProdutoMaterialActivity.this,"Adicionar Materiais", "Não é possível adicionar materiais a este produto, pois este registro esta fechado!");
                }
            }
        });

        Bundle bundle = getIntent().getExtras();
        if(bundle!=null && bundle.containsKey("PRODUTO")){
            produto = (Produto) bundle.getSerializable("PRODUTO");
            prm.setProduto(produto);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        new CarregaMaterial().execute();
        listViewMateial.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EditaValorMaterial(position);
            }
        });

        listViewMateial.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Negocio neg = produto.getNegocio();
                if(neg!=null && neg.getNeg_codigo() != 0 && neg.getNeg_cstatus() == NegocioStatus.ABERTO)
                    OpcaoesMaterial(position);
                return false;
            }
        });
    }

    private class CarregaMaterial extends AsyncTask<Produto_material, String, List<Produto_material>> {
        ProdutoMaterialDAO pmDAO  =new ProdutoMaterialDAO();
        ProgressBar pg = (ProgressBar)findViewById(R.id.produtoMaterialProgressBar);
        @Override
        protected List<Produto_material> doInBackground(Produto_material... params) {
            return pmDAO.Seleciona(produto.getPro_codigo());
        }

        @Override
        protected void onPostExecute(List<Produto_material> lsProdutoMaterial) {
            super.onPostExecute(lsProdutoMaterial);
            if(lsProdutoMaterial!=null) {
                adpMateriais.clear();
                lsMateriais.clear();
                for (Produto_material rg : lsProdutoMaterial) {
                    adpMateriais.add(rg);
                    lsMateriais.add(rg.getMaterial());
                }
                listViewMateial.setAdapter(adpMateriais);

            }
            pg.setVisibility(View.GONE);
        }
    }

    private void OpcaoesMaterial(final int position){

        final Produto_material produtMaterial = adpMateriais.getItem(position);
        Negocio neg = produto.getNegocio();
        AlertDialog.Builder dialog = new AlertDialog.Builder(ProdutoMaterialActivity.this);
        dialog.setTitle(R.string.tituloOpcao)
                .setMessage(R.string.mensagemOpcao);
        if(produtMaterial.getPrm_iunidadeUtilizada()==0)
            dialog.setNegativeButton(R.string.Deletar, null);
        if(neg!=null && neg.getNeg_codigo()!=0 && neg.getNeg_cstatus()== NegocioStatus.ABERTO)
            dialog.setNeutralButton(R.string.Editar,null);

        dialog.setPositiveButton(R.string.Cancelar, null);

        alertM = dialog.create();
        alertM.show();
        if(produtMaterial.getPrm_iunidadeUtilizada()==0)
            alertM.getButton(DialogInterface.BUTTON_NEGATIVE).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Dialog.ShowProgressDialog(ProdutoMaterialActivity.this);
                    new DeleteProdutoMaterial().execute(produtMaterial);
                    Log.i("Deleta","vai deleta!");
                }
            });
        if(neg!=null && neg.getNeg_codigo()!=0 && neg.getNeg_cstatus()== NegocioStatus.ABERTO)
            alertM.getButton(DialogInterface.BUTTON_NEUTRAL).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertM.cancel();
                    AlertDialog.Builder dialog = new AlertDialog.Builder(ProdutoMaterialActivity.this);
                    final EditText txtReserva = new EditText(ProdutoMaterialActivity.this);
                    txtReserva.setInputType(InputType.TYPE_CLASS_NUMBER);
                    txtReserva.setText(produtMaterial.getPrm_iunidade() + "");
                    dialog.setView(txtReserva);
                    dialog.setTitle("Reservas do Material");
                    dialog.setMessage("Informe quantas unidades você deseja reservar  de " + produtMaterial.getMaterial().getMtr_vnome() + "?");
                    dialog.setNegativeButton(R.string.Salvar, null);
                    dialog.setNeutralButton(R.string.Cancelar, null);

                    alertM = dialog.create();
                    alertM.show();
                    alertM.getButton(DialogInterface.BUTTON_NEGATIVE).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (FuncoesExternas.Valida(txtReserva)) {
                                int unidUsadas = Integer.parseInt(txtReserva.getText().toString());
                                if (unidUsadas >= produtMaterial.getPrm_iunidadeUtilizada()) {///&& unidUsadas < prm.getPrm_iunidade()
                                    prm = produtMaterial;
                                    prm.setPrm_iunidade(unidUsadas);
                                    Dialog.ShowProgressDialog(ProdutoMaterialActivity.this);
                                    new EditaProdutoMaterialReserva().execute();
                                } else
                                    txtReserva.setError("A quantidade informada deve ser maior que "+produtMaterial.getPrm_iunidadeUtilizada()+"!");
                            }
                        }
                    });
                }
            });
    }

    private void EditaValorMaterial(int position){
        Negocio neg = produto.getNegocio();
        if(neg!=null && neg.getNeg_codigo() != 0 && neg.getNeg_cstatus() == NegocioStatus.ABERTO) {
            final Produto_material produtMaterial = adpMateriais.getItem(position);
            AlertDialog.Builder dialog = new AlertDialog.Builder(ProdutoMaterialActivity.this);
            final EditText txt = new EditText(ProdutoMaterialActivity.this);
            txt.setInputType(InputType.TYPE_CLASS_NUMBER);
            dialog.setView(txt);
            dialog.setTitle("Unidades do Material");
            dialog.setMessage("Informe quantas unidades você já utilizou de " + produtMaterial.getMaterial().getMtr_vnome()+"?");
            dialog.setNegativeButton(R.string.Salvar, null);
            dialog.setNeutralButton(R.string.Cancelar, null);

            alertM = dialog.create();
            alertM.show();
            alertM.getButton(DialogInterface.BUTTON_NEGATIVE).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // edita produto amrterial
                    if (FuncoesExternas.Valida(txt)) {
                        int unidUsadas = Integer.parseInt(txt.getText().toString());
                        if (unidUsadas > 0) {///&& unidUsadas < prm.getPrm_iunidade()
                            prm = produtMaterial;

                            prm.setPrm_iunidadeUtilizada(unidUsadas);
                            Dialog.ShowProgressDialog(ProdutoMaterialActivity.this);
                            new EditaProdutoMaterial().execute();
                        } else
                            txt.setError("A quantidade informada deve ser maior que 0!");
                    }
                }
            });
        }
    }

    private class EditaProdutoMaterial extends AsyncTask<Produto_material, String, Boolean>{
        ProdutoMaterialDAO prmDAO = new ProdutoMaterialDAO();

        @Override
        protected Boolean doInBackground(Produto_material... params) {
            try {
                Log.i("Edita", prm.getPrm_iunidadeUtilizada() + "");
                return prmDAO.Editar(prm);
            }catch (Exception e){ Log.e("Error",e.toString());}
            return false;
        }

        @Override
        protected void onPostExecute(Boolean salvo) {
            super.onPostExecute(salvo);
            Dialog.CancelProgressDialog();
            if(salvo){
                alertM.cancel();
                ProgressBar pg = (ProgressBar)findViewById(R.id.produtoMaterialProgressBar);
                pg.setVisibility(View.VISIBLE);
                new CarregaMaterial().execute();
                adpMateriais.clear();
                lsMateriais.clear();
            }else
                Dialog.ShowAlertError(ProdutoMaterialActivity.this);
        }
    }

    private class EditaProdutoMaterialReserva extends AsyncTask<Produto_material, String, Boolean>{
        ProdutoMaterialDAO prmDAO = new ProdutoMaterialDAO();

        @Override
        protected Boolean doInBackground(Produto_material... params) {
            try {
                Log.i("Edita", prm.getPrm_iunidade() + "");
                return prmDAO.EditaReserva(prm);
            }catch (Exception e){ Log.e("Error",e.toString());}
            return false;
        }

        @Override
        protected void onPostExecute(Boolean salvo) {
            super.onPostExecute(salvo);
            Dialog.CancelProgressDialog();
            if(salvo){
                alertM.cancel();
                ProgressBar pg = (ProgressBar)findViewById(R.id.produtoMaterialProgressBar);
                pg.setVisibility(View.VISIBLE);
                new CarregaMaterial().execute();
                adpMateriais.clear();
                lsMateriais.clear();
            }else
                Dialog.ShowAlertError(ProdutoMaterialActivity.this);
        }
    }

    private  class DeleteProdutoMaterial extends AsyncTask<Produto_material, String, Boolean>{
        ProdutoMaterialDAO prmDAO = new ProdutoMaterialDAO();
        @Override
        protected Boolean doInBackground(Produto_material... params) {
            return prmDAO.Delete(params[0].getProduto().getPro_codigo(), params[0].getMaterial().getMtr_codigo());
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            Dialog.CancelProgressDialog();
            if(aBoolean){
                alertM.cancel();
                ProgressBar pg = (ProgressBar)findViewById(R.id.produtoMaterialProgressBar);
                pg.setVisibility(View.VISIBLE);
                new CarregaMaterial().execute();
                adpMateriais.clear();
                lsMateriais.clear();
            }else
                Dialog.ShowAlertError(ProdutoMaterialActivity.this);

        }
    }

}

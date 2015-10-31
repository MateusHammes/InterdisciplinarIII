package com.example.interdisciplinar.mobile;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import DAO.ProdutoMaterialDAO;
import DAO.RegistrosDAO;
import Enum.RegistroStatus;
import model.Produto;
import model.Produto_material;
import model.Registros;
import util.Dialog;
import util.FuncoesExternas;

public class ProdutoActivityDetalhes extends AppCompatActivity {

    private Produto produto;
    private Registros registro;
    private RegistrosDAO DAO =new RegistrosDAO();
    private Produto_material produtMaterial = new Produto_material();
    private ArrayAdapter<Produto_material> adpMateriais;
    private ListView listViewMateriais;
    private ArrayAdapter<Registros> adpRegistros;
    private ListView listViewRegistros;
    AlertDialog alert;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produto_detalhes);

        //region Materiais
        adpMateriais = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        listViewMateriais = (ListView) findViewById(R.id.produtoDetalheListViewMaterial);

        listViewMateriais.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Produto_material prm = adpMateriais.getItem(position);
                AlertDialog.Builder dialog = new AlertDialog.Builder(ProdutoActivityDetalhes.this);

                final EditText txt = new EditText(ProdutoActivityDetalhes.this);
                dialog.setView(txt);
                dialog.setTitle("Unidades do Material");
                dialog.setMessage("Informe quantas unidades voce ja de " + prm.getMaterial().getMtr_vnome());
                dialog.setNegativeButton(R.string.Salvar, null);
                dialog.setNeutralButton(R.string.Cancelar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int unidUsadas =Integer.parseInt(txt.getText().toString());
                        if(FuncoesExternas.Valida(txt))
                            if(unidUsadas>0 && unidUsadas < prm.getPrm_iunidade()){
                                produtMaterial =prm;
                                produtMaterial.setPrm_iunidade(prm.getPrm_iunidade()-unidUsadas);
                                produtMaterial.setPrm_iunidadeUtilizada(prm.getPrm_iunidadeUtilizada() + unidUsadas);
                                Dialog.ShowProgressDialog(ProdutoActivityDetalhes.this);
                                new SalvaProdutoMaterial().execute();
                            }else
                                txt.setError("O quantidade deve ser menor que "+prm.getPrm_iunidade()+" e maior que 0");
                    }
                }).show();

                alert = dialog.create();
                alert.show();
                alert.getButton(DialogInterface.BUTTON_NEGATIVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // edita registro

                        Dialog.ShowProgressDialog(ProdutoActivityDetalhes.this);
                    }
                });
            }
        });
        //endregion

        //region Registros

        adpRegistros = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        listViewRegistros = (ListView) findViewById(R.id.produtoDetalheListViewEspecificacao);

        listViewRegistros.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Registros reg = adpRegistros.getItem(position);
                AlertDialog.Builder dialog = new AlertDialog.Builder(ProdutoActivityDetalhes.this);
                if(reg.getRgs_cstatus()== RegistroStatus.aberto) {
                    dialog.setTitle("Deseja completar esta Especificaçao?");
                    dialog.setMessage("Especificaçao: " + reg.getRgs_vdescricao());
                    dialog.setNegativeButton("Completar", null);
                    dialog.setNeutralButton(R.string.Cancelar, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).show();
                }else{
                    // new AlertDialog.Builder(ProdutoActivityDetalhes.this)
                    dialog.setTitle("Deseja abrir esta Especificaçao?");
                    dialog.setMessage("Especificaçao: " + reg.getRgs_vdescricao());
                    dialog.setNegativeButton("abrir", null);
                    dialog.setNeutralButton(R.string.Cancelar, null).show();
                }

                alert = dialog.create();
                alert.show();
                alert.getButton(DialogInterface.BUTTON_NEGATIVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // edita registro

                        Dialog.ShowProgressDialog(ProdutoActivityDetalhes.this);
                    }
                });

            }
        });

        //endregion

        ImageButton btnMaterial = (ImageButton)findViewById(R.id.produtoDetalhesBtnMateriais);
        btnMaterial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ProdutoActivityDetalhes.this,MateriaisProdutoActivity.class);
                i.putExtra("NEGOCIO",produto.getNegocio());
                i.putExtra("PRODUTO",produto);
                startActivity(i);
            }
        });

        Bundle bundle = getIntent().getExtras();
        if(bundle!=null && bundle.containsKey("NEGOCIO")){
            produto =(Produto) bundle.getSerializable("NEGOCIO");
            SetValues(produto);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        new CarregaRegistros().execute();
        new CarregaRegistros().execute();
    }

    private void SetValues(Produto produto){
        TextView nome = (TextView)findViewById(R.id.produtoDetalheNome);
        TextView descricao = (TextView)findViewById(R.id.produtoDetalheDescricao);

        nome.setText(produto.getPro_vnome());
        descricao.setText(produto.getPro_vdescricao());

        if(produto.getLsRegistros()!=null){
            adpRegistros.addAll(produto.getLsRegistros());
            listViewRegistros.setAdapter(adpRegistros);
        }
        if(produto.getLsProdutoMateriais()!=null) {
            adpMateriais.addAll(produto.getLsProdutoMateriais());
            listViewMateriais.setAdapter(adpMateriais);
        }
    }





    public void NovoRegistro(View view){
        registro = new Registros();
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle(R.string.Registros);
        dialog.setMessage(R.string.RegistrosNome);
        final EditText input = new EditText(this);
        dialog.setView(input);
        dialog.setCancelable(false);

        dialog.setNegativeButton(R.string.Salvar, null);
        dialog.setPositiveButton(R.string.Cancelar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ///cancelar
                dialog.cancel();
            }
        });

        alert = dialog.create();
        alert.show();
        alert.getButton(DialogInterface.BUTTON_NEGATIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (FuncoesExternas.Valida(input)) {
                    new SalvaRegistro().execute();
                    Dialog.ShowProgressDialog(ProdutoActivityDetalhes.this);
                }
            }
        });
    }



    private void ListaRegistros(List<Registros>lsRegistros){
        if(lsRegistros!=null) {
            for (Registros rg : lsRegistros) {
                adpRegistros.add(rg);//converte object em Grupo
                listViewRegistros.setAdapter(adpRegistros);
            }
        }
    }

    private class CarregaRegistros extends AsyncTask<Registros, String, List<Registros>>{

        @Override
        protected List<Registros> doInBackground(Registros... params) {
            return DAO.SelecionaRegistros();
        }

        @Override
        protected void onPostExecute(List<Registros> lsRegistros) {
            super.onPostExecute(lsRegistros);
            ListaRegistros(lsRegistros);
        }
    }

    private class SalvaRegistro extends AsyncTask<Registros,String, Boolean> {
        @Override
        protected Boolean doInBackground(Registros... params) {
            return DAO.Salvar(registro);
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if(aBoolean){
                adpRegistros.clear();
                new CarregaRegistros().execute();
            }else {
                Dialog.ShowAlertError(ProdutoActivityDetalhes.this);
            }
            Dialog.CancelProgressDialog();
        }
    }


    private void ListaProdutoMaterial(List<Produto_material> lsItens){
        if(lsItens!=null){
            for (Produto_material prm : lsItens){
                adpMateriais.add(prm);
            }
            listViewMateriais.setAdapter(adpMateriais);
        }
    }

    private class CarregaMaterial extends AsyncTask<Produto_material, String, List<Produto_material>>{
        ProdutoMaterialDAO pmDAO  =new ProdutoMaterialDAO();
        @Override
        protected List<Produto_material> doInBackground(Produto_material... params) {
            return pmDAO.Seleciona(produto.getPro_codigo());
        }

        @Override
        protected void onPostExecute(List<Produto_material> lsProdutoMaterial) {
            super.onPostExecute(lsProdutoMaterial);
            ListaProdutoMaterial(lsProdutoMaterial);
        }
    }

    private class SalvaProdutoMaterial extends AsyncTask<Produto_material, String, Boolean>{
        ProdutoMaterialDAO prmDAO = new ProdutoMaterialDAO();
        @Override
        protected Boolean doInBackground(Produto_material... params) {
            return prmDAO.Salvar(produtMaterial);
        }

        @Override
        protected void onPostExecute(Boolean salvo) {
            super.onPostExecute(salvo);
            if(salvo){
                new CarregaMaterial().execute();
                adpMateriais.clear();
            }else{
                Dialog.ShowAlert(ProdutoActivityDetalhes.this,"Material do Produto","Ops.. Não foi posivel salvar, favor tente novamente!");
            }
            Dialog.CancelProgressDialog();
        }
    }

}










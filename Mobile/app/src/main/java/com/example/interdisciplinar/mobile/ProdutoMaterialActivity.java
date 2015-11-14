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

import java.util.List;

import DAO.ProdutoMaterialDAO;
import model.Produto;
import model.Produto_material;
import util.Dialog;
import util.FuncoesExternas;

public class ProdutoMaterialActivity extends AppCompatActivity {

    private Produto_material prm = new Produto_material();
    private Produto produto  =new Produto();
    private ArrayAdapter<Produto_material> adpMateriais = null;
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
                Intent i = new Intent(ProdutoMaterialActivity.this, MateriaisProdutoActivity.class);
                i.putExtra("PRODUTO", produto);
                startActivity(i);
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
                for (Produto_material rg : lsProdutoMaterial)
                    adpMateriais.add(rg);
                listViewMateial.setAdapter(adpMateriais);
            }
            pg.setVisibility(View.GONE);
        }
    }



    private void EditaValorMaterial(int position){
        final Produto_material produtMaterial = adpMateriais.getItem(position);
        AlertDialog.Builder dialog = new AlertDialog.Builder(ProdutoMaterialActivity.this);
        final EditText txt = new EditText(ProdutoMaterialActivity.this);
        txt.setInputType(InputType.TYPE_CLASS_NUMBER);
        dialog.setView(txt);
        dialog.setTitle("Unidades do Material");
        dialog.setMessage("Informe quantas unidades você já utilizou de " + produtMaterial.getMaterial().getMtr_vnome());
        dialog.setNegativeButton(R.string.Salvar, null);
        dialog.setNeutralButton(R.string.Cancelar,null);

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

                        prm.setPrm_iunidadeUtilizada(produtMaterial.getPrm_iunidadeUtilizada() + unidUsadas);
                        Dialog.ShowProgressDialog(ProdutoMaterialActivity.this);
                        new EditaProdutoMaterial().execute();
                    } else
                        txt.setError("O quantidade deve ser menor que " + produtMaterial.getPrm_iunidade() + " e maior que 0");
                }
            }
        });
    }

    private class EditaProdutoMaterial extends AsyncTask<Produto_material, String, Boolean>{
        ProdutoMaterialDAO prmDAO = new ProdutoMaterialDAO();

        @Override
        protected Boolean doInBackground(Produto_material... params) {
            Log.i("Edita",prm.getPrm_iunidadeUtilizada()+"");
            return prmDAO.Editar(prm);
        }

        @Override
        protected void onPostExecute(Boolean salvo) {
            super.onPostExecute(salvo);
            if(salvo){
                alertM.cancel();
                new CarregaMaterial().execute();
                adpMateriais.clear();
            }else{
                Dialog.ShowAlert(ProdutoMaterialActivity.this,"Material do Produto","Ops.. Não foi posivel Editar, favor tente novamente!");
            }
            Dialog.CancelProgressDialog();
        }
    }


}

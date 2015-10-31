package com.example.interdisciplinar.mobile;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.List;

import DAO.MateriaisDAO;
import DAO.ProdutoMaterialDAO;
import model.Materiais;
import model.Negocio;
import model.Produto;
import model.Produto_material;
import util.Dialog;
import util.FuncoesExternas;

public class MateriaisProdutoActivity extends AppCompatActivity {
    private ListView materialListView;
    private Produto_material prm = new Produto_material();
    private ArrayAdapter<Materiais> adpMaterial;
    private MateriaisDAO DAO = new MateriaisDAO();

    private boolean GoLoad=true;
    private int pageList=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_materiais_produto);


        materialListView = (ListView) findViewById(R.id.produtoMaterialListView);
        adpMaterial = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        materialListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (GoLoad && materialListView.getLastVisiblePosition() == (adpMaterial.getCount() - 1)) {
                    GoLoad = false;
                    new CarregaRegistros().execute();
                }
            }
        });


        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            if(bundle.containsKey("NEGOCIO")){
                Negocio neg =(Negocio) bundle.getSerializable("CODIGO");
                prm.setNegocio(neg);
            }
            if(bundle.containsKey("PRODUTO")){
                Produto pro =(Produto) bundle.getSerializable("PRO_CODIGO");
                prm.setProduto(pro);
            }
        }

        materialListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Materiais mtr = adpMaterial.getItem(position);
                final EditText txt = new EditText(MateriaisProdutoActivity.this);
                txt.setInputType(InputType.TYPE_CLASS_NUMBER);
                new AlertDialog.Builder(MateriaisProdutoActivity.this)
                        .setTitle("Materiais do Produto")
                        .setMessage("Informe a quantidade de "+mtr.getMtr_vnome()+" que voce deseja reservar")
                        .setNegativeButton(R.string.Salvar, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(FuncoesExternas.Valida(txt)) {
                                    prm.setPrm_iunidade(Integer.parseInt(txt.getText().toString()));
                                    prm.setMaterial(mtr);
                                    prm.setPrm_nvalor(mtr.getMtr_nvalor());
                                    Dialog.ShowProgressDialog(MateriaisProdutoActivity.this);
                                    new Salva().execute();
                                }
                            }
                        }).setView(txt).setNeutralButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).show();
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        if(GoLoad) {
            GoLoad=false;
            new CarregaRegistros().execute();
        }
    }

    protected void AtualizaGrid(List<Materiais> lsItens){
    /*    Materiais mtt = new Materiais();
        mtt.setMtr_vnome("TESTE MATERIAL");
        lsItens.add(mtt);*/
        if(lsItens!=null) {
            for (Materiais mt : lsItens) {
                adpMaterial.add(mt);//converte object em Grupo
            }
            if(pageList==0)
                materialListView.setAdapter(adpMaterial);
            else
                adpMaterial.notifyDataSetChanged();

            if(lsItens.size()==15) {
                GoLoad = true;
                pageList++;
            }

        }


    }

    private class CarregaRegistros extends AsyncTask<Materiais, String, List<Materiais>> {
        ProgressBar pg = (ProgressBar) findViewById(R.id.produtoMaterialProgressBar);

        @Override
        protected List<Materiais> doInBackground(Materiais... params) {
            return DAO.SelecionaMateriais();
        }

        @Override
        protected void onPostExecute(List<Materiais> lsMateriais) {
            super.onPostExecute(lsMateriais);
            AtualizaGrid(lsMateriais);
            pg.setVisibility(View.GONE);
        }
    }


    private class Salva extends  AsyncTask<Produto_material, String, Boolean>{
        ProdutoMaterialDAO pmDAO = new ProdutoMaterialDAO();
        @Override
        protected Boolean doInBackground(Produto_material... params) {
            return true;
            //return pmDAO.Salvar(prm);
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if(aBoolean) {//Se Salvo
                finish();
            }else{
                Dialog.ShowAlert(MateriaisProdutoActivity.this,"Material do Produto","Ops.. Não foi posivel salvar, favor tente novamente!");
            }
            Dialog.CancelProgressDialog();
        }
    }






}

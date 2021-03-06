package com.example.interdisciplinar.mobile;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
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
import model.Produto;
import model.Produto_material;
import util.Dialog;
import util.FuncoesExternas;

public class MateriaisProdutoActivity extends AppCompatActivity {
    private ListView materialListView;
    private Produto_material prm = new Produto_material();
    private ArrayAdapter<Materiais> adpMaterial;
    private MateriaisDAO DAO = new MateriaisDAO();
    private Produto produto = new Produto();

    private boolean GoLoad=true;
    private int pageList=0;
    AlertDialog dlg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_materiais_produto);


        materialListView = (ListView) findViewById(R.id.materialProdutoListView);
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

            if(bundle.containsKey("PRODUTO")){
                Produto pro =(Produto) bundle.getSerializable("PRODUTO");
                prm.setProduto(pro);
                produto = pro;
            }
        }

        materialListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Materiais mtr = adpMaterial.getItem(position);
                final EditText txt = new EditText(MateriaisProdutoActivity.this);
                txt.setInputType(InputType.TYPE_CLASS_NUMBER);
                AlertDialog.Builder dialog = new  AlertDialog.Builder(MateriaisProdutoActivity.this)
                        .setTitle("Materiais do Produto")
                        .setMessage("Informe a quantidade de " + mtr.getMtr_vnome() + " que você deseja reservar?")
                        .setView(txt)
                        .setNegativeButton(R.string.Salvar, null)
                        .setNeutralButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });

                dlg = dialog.create();
                dlg.show();
                dlg.getButton(DialogInterface.BUTTON_NEGATIVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(FuncoesExternas.Valida(txt)) {
                            int unidades = Integer.parseInt(txt.getText().toString());
                            if(unidades>0) {
                                prm.setPrm_iunidade(unidades);
                                prm.setMaterial(mtr);
                                prm.setPrm_nvalor(mtr.getMtr_nvalor());
                                prm.setProduto(produto);
                                Log.i("foi salva vo", produto.getPro_codigo()+"");
                                Dialog.ShowProgressDialog(MateriaisProdutoActivity.this);
                                new Salva().execute();
                            }else
                                txt.setError("O número deve ser maior que 0");
                        }
                    }
                });
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
        if(lsItens!=null) {
            for (Materiais mt : lsItens) {
                try{
                    boolean add=true;
                    for (Materiais mtLs : ProdutoMaterialActivity.lsMateriais) {
                        if(mtLs.getMtr_codigo()==mt.getMtr_codigo())
                            add=false;
                    }
                    if(add)
                        adpMaterial.add(mt);
                }catch (Exception e) {
                    adpMaterial.add(mt);
                }
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
            return DAO.SelecionaMateriais(pageList);
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
            if(prm!=null)
                Log.i("Sava mat. numeor",""+prm.toString());
            return pmDAO.Salvar(prm);
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if(aBoolean) {//Se Salvo
                dlg.cancel();
                finish();
            }else{
                Dialog.ShowAlertError(MateriaisProdutoActivity.this);
                //Dialog.ShowAlert(MateriaisProdutoActivity.this,"Material do Produto","Ops.. Não foi posivel salvar, favor tente novamente!");
            }
            Dialog.CancelProgressDialog();
        }
    }






}

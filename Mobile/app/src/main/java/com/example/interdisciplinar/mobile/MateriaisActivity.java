package com.example.interdisciplinar.mobile;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
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
import model.Materiais;
import util.Dialog;

public class MateriaisActivity extends AppCompatActivity {

    private ListView materialListView;
    private Materiais material = new Materiais() ;
    public static ArrayAdapter<Materiais> adpMaterial;
    private MateriaisDAO DAO = new MateriaisDAO();
    public static boolean GoLoad=true;
    public static boolean ClearList=false;
    private int pageList=0;
    AlertDialog dlg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_materiais);

        materialListView = (ListView) findViewById(R.id.materialListView);
        adpMaterial = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.materialBtnNovo);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MateriaisActivity.this, MateriaisActivityForm.class);
                startActivity(i);
            }
        });


        materialListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(MateriaisActivity.this, MateriaisActivityForm.class);
                i.putExtra("MATERIAL", adpMaterial.getItem(position));
                startActivity(i);
            }
        });
        materialListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final Materiais mtr = adpMaterial.getItem(position);
                new AlertDialog.Builder(MateriaisActivity.this)
                        .setTitle(R.string.tituloOpcao)
                        .setMessage(R.string.mensagemOpcao)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton("Excluir", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                new Delete().execute(mtr);
                            }
                        }).setNegativeButton("Editar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //material = mtr;
                        Intent i = new Intent(MateriaisActivity.this, MateriaisActivityForm.class);
                        i.putExtra("MATERIAL", mtr);
                        startActivity(i);
                    }
                }).setNeutralButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).show();
                return false;
            }
        });

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
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("--ACAO--", "onRESUME");
        if(ClearList){
            adpMaterial.clear();
            materialListView.setAdapter(adpMaterial);
            ClearList=false;
        }

        if(GoLoad) {
            GoLoad=false;
            new CarregaRegistros().execute();
        }

        final EditText search = (EditText)findViewById(R.id.materialTxtPesquisar);
        final android.os.Handler handler = new android.os.Handler();
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                handler.removeCallbacksAndMessages(null);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Log.i("iscreveuu -- ", "Inside of the method! ;) value:- " + search.getText().toString());
                        CarregaPesquisa(search.getText().toString());
                    }
                }, 1000);
                Log.i("iscreveuu", "out method");
            }
        });
    }


    public void CarregaPesquisa(String name){
        ProgressBar pg = (ProgressBar) findViewById(R.id.materialProgressBar);
        pg.setVisibility(View.VISIBLE);
        if(name.trim().equals("")){
            pageList=0;
            adpMaterial.clear();
            new CarregaRegistros().execute();
        }else{
            new CarregaPesquisa().execute(name);
        }

    }

    protected void AtualizaGrid(List<Materiais> lsItens){
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
        ProgressBar pg = (ProgressBar) findViewById(R.id.materialProgressBar);

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

    private  class Delete extends  AsyncTask<Materiais, String, Boolean>{

        @Override
        protected Boolean doInBackground(Materiais... params) {
            if(DAO.Deletar(params[0])){
                adpMaterial.remove(params[0]);
                adpMaterial.notifyDataSetChanged();
                return true;
            }else
                Dialog.ShowAlert(MateriaisActivity.this, "Deletar Material", "Ops.. Este material ja esta sendo Utilizado por outros Produtos");
            return false;
        }

        @Override
        protected void onPostExecute(Boolean deleto) {
            super.onPostExecute(deleto);
            Dialog.CancelProgressDialog();
        }
    }

    private class CarregaPesquisa extends AsyncTask<String, String, List<Materiais>>{
        ProgressBar pg = (ProgressBar) findViewById(R.id.materialProgressBar);
        @Override
        protected List<Materiais> doInBackground(String... params) {
            return DAO.SelecionaPesquisa(params[0]);
        }

        @Override
        protected void onPostExecute(List<Materiais> materiaises) {
            super.onPostExecute(materiaises);
            adpMaterial.clear();
            if(materiaises!=null)
                adpMaterial.addAll(materiaises);
            else
                Log.i("retorno", "Lista nula");

            adpMaterial.notifyDataSetChanged();
            pg.setVisibility(View.GONE);
        }
    }
}

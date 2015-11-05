package com.example.interdisciplinar.mobile;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.List;

import DAO.MateriaisDAO;
import model.Materiais;
import util.Dialog;

public class MateriaisActivity extends AppCompatActivity {

    private ListView materialListView;
    private Materiais material = new Materiais() ;
    private ArrayAdapter<Materiais> adpMaterial;
    private MateriaisDAO DAO = new MateriaisDAO();
    public static boolean GoLoad=true;
    private int pageList=0;
    AlertDialog dlg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_materiais);
       /* Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/

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
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);


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
                        .setTitle(R.string.alertTitleOption)
                        .setMessage(R.string.alertMessageOption)
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
        if(GoLoad) {
            GoLoad=false;
            new CarregaRegistros().execute();
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
           /* if(deleto){

            }else{}*/
            Dialog.CancelProgressDialog();
        }
    }
}

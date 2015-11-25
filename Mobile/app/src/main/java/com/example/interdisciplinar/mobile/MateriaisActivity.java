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
    final android.os.Handler handler = new android.os.Handler();

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
                AlertDialog.Builder alert = new AlertDialog.Builder(MateriaisActivity.this);
                alert.setTitle(R.string.tituloOpcao);
                alert.setMessage(R.string.mensagemOpcao);
                alert.setIcon(android.R.drawable.ic_dialog_alert);
                alert.setNeutralButton("Excluir", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alert.setNegativeButton("Editar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText search = (EditText) findViewById(R.id.materialTxtPesquisar);
                        search.setText("");
                        Intent i = new Intent(MateriaisActivity.this, MateriaisActivityForm.class);
                        i.putExtra("MATERIAL", mtr);
                        startActivity(i);
                    }
                });
                alert.setPositiveButton("Cancelar", null);

                dlg = alert.create();
                dlg.show();
                dlg.getButton(DialogInterface.BUTTON_NEUTRAL).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText search = (EditText) findViewById(R.id.materialTxtPesquisar);
                        search.setText("");
                        Dialog.ShowProgressDialog(MateriaisActivity.this);
                        new Delete().execute(mtr);
                    }
                });
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
        final EditText search = (EditText)findViewById(R.id.materialTxtPesquisar);
        search.setText("");
        ProgressBar pgI = (ProgressBar) findViewById(R.id.materialProgressBar);
        pgI.setVisibility(View.VISIBLE);


        if(GoLoad) {
            GoLoad=false;
            new CarregaRegistros().execute();
        }


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
                        if (search.isFocused()) {
                            Log.i("iscreveuu -- ", "Inside of the method! ;) value:- " + search.getText().toString());
                            CarregaPesquisa(search.getText().toString());
                        }
                    }
                }, 1000);
                Log.i("iscreveuu", "out method");
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        pageList=0;
        GoLoad=true;
        adpMaterial.clear();
    }

    public void CarregaPesquisa(String name){
        ProgressBar pg = (ProgressBar) findViewById(R.id.materialProgressBar);
        pg.setVisibility(View.VISIBLE);
        if(name.trim().equals("")){
            pageList=0;
            adpMaterial.clear();
            Log.i("foi","vaziu");
            new CarregaRegistros().execute();
        }else{
            new CarregaPesquisa().execute(name);
        }

    }

    protected void AtualizaGrid(List<Materiais> lsItens){
        if(lsItens!=null) {
            Log.i("Lista com","veio com "+lsItens.size());
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
        }else
            Log.i("Lista","Nulla");
    }

    private class CarregaRegistros extends AsyncTask<Materiais, String, List<Materiais>> {
        ProgressBar pg = (ProgressBar) findViewById(R.id.materialProgressBar);

        @Override
        protected List<Materiais> doInBackground(Materiais... params) {
            try {
                Log.i("foi", "carrega cm pag." + pageList);
                return DAO.SelecionaMateriais(pageList);
            }catch (Exception e){
                Log.e("ERRO", e.toString());
            }
            return null;
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
            return DAO.Deletar(params[0]);
        }

        @Override
        protected void onPostExecute(Boolean deleto) {
            super.onPostExecute(deleto);
            Dialog.CancelProgressDialog();
            if(deleto){
                pageList=0;
                new CarregaRegistros().execute();
                ProgressBar pg = (ProgressBar) findViewById(R.id.materialProgressBar);
                pg.setVisibility(View.VISIBLE);
                adpMaterial.clear();
                dlg.cancel();
            }else{
                Dialog.ShowAlert(MateriaisActivity.this, "Deletar Material", "Ops.. Este material talvez já esteja sendo Utilizado por outros Produtos");
            }
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
            Log.i("retorno", "retornouuuu!!");
            if(materiaises!=null)
                adpMaterial.addAll(materiaises);
            else
                Log.i("retorno", "Lista nula");

            adpMaterial.notifyDataSetChanged();
            pg.setVisibility(View.GONE);
        }
    }
}

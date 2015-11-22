package com.example.interdisciplinar.mobile;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;

import DAO.GrupoDAO;
import model.Grupo;
import util.Dialog;
import util.FuncoesExternas;

public class GrupoActivity extends AppCompatActivity {

    private ListView grupoListView;
    private Grupo grupo = new Grupo() ;
    private ArrayAdapter<Grupo> adpGrupo;
    private GrupoDAO DAO = new GrupoDAO();
    public static String msn =null; //usado pra msn para Toast
    private int pageList=0;
    private boolean GoLoad=true;
    AlertDialog dlg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grupo);

        grupoListView = (ListView) findViewById(R.id.grupoListView);
        adpGrupo = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.grupoBtnNovo);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                grupo = new Grupo();
                GrupoForm();
            }
        });

        grupoListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                grupo = adpGrupo.getItem(position);
                GrupoForm();
            }
        });

        grupoListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                final Grupo gp = adpGrupo.getItem(position);
                AlertDialog.Builder alert =   new AlertDialog.Builder(GrupoActivity.this);
                alert.setTitle(R.string.tituloOpcao);
                alert.setMessage(R.string.mensagemOpcao);
                alert.setIcon(android.R.drawable.ic_dialog_alert);
                alert.setNeutralButton("Excluir", null);
                alert.setNegativeButton("Editar", null);
                alert.setPositiveButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });//.show();
                dlg = alert.create();
                dlg.show();
                dlg.getButton(DialogInterface.BUTTON_NEUTRAL).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        grupo = gp;
                        Dialog.ShowProgressDialog(GrupoActivity.this);
                        new Delete().execute();
                    }
                });
                dlg.getButton(DialogInterface.BUTTON_NEGATIVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        grupo = adpGrupo.getItem(position);
                        dlg.cancel();
                        GrupoForm();
                    }
                });

                return false;
            }
        });


        grupoListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                Log.i("TOTAL!", "Total::: -- - -- " + totalItemCount + "");
                if (GoLoad && grupoListView.getLastVisiblePosition() == (adpGrupo.getCount() - 1)) {
                    Log.e("AKI", "--- FINALZAUM -- da lista -- -");
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
            Log.i("--ACAO--", "onRESUME  -  Goload");
        }
        if(msn!=null&& !msn.isEmpty()){
            Toast t = Toast.makeText(this,msn,Toast.LENGTH_SHORT);
            t.show();
            msn = null;
        }

        final EditText search = (EditText)findViewById(R.id.grupoTxtPesquisar);
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
                        if (!search.getText().toString().trim().equals(""))
                            CarregaPesquisa(search.getText().toString());
                        else {
                            pageList = 0;
                            adpGrupo.clear();
                            //// GoLoad = true;
                            new CarregaRegistros().execute();
                        }
                    }
                }, 1000);
                Log.i("iscreveuu", "out method");
            }
        });
    }

    public void GrupoForm(){
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(R.string.grupoNomeHint);
        final EditText txt = new EditText(this);
        if(grupo!=null)
            txt.setText(grupo.getGru_vdescricao());
        alert.setView(txt);
        alert.setCancelable(false);
        alert.setNegativeButton(R.string.Salvar, null);
        alert.setPositiveButton(R.string.Cancelar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        dlg = alert.create();
        dlg.show();

        dlg.getButton(DialogInterface.BUTTON_NEGATIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (FuncoesExternas.Valida(txt)) {
                    grupo.setGru_vdescricao(txt.getText().toString());
                    new Salvar().execute();
                    Dialog.ShowProgressDialog(GrupoActivity.this);
                }
            }
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_grupo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    protected void AtualizaGrid(List<Grupo> lsGrupos){
        if(lsGrupos!=null) {
            for (Grupo gp : lsGrupos) {
                adpGrupo.add(gp);//converte object em Grupo
            }
            if(pageList==0)
                grupoListView.setAdapter(adpGrupo);
            else
                adpGrupo.notifyDataSetChanged();

            if(lsGrupos.size()==15) {
                GoLoad = true;
                pageList++;
            }
        }
    }

    private class CarregaRegistros extends AsyncTask<String, Integer, List<Grupo>> {
        ProgressBar pg =(ProgressBar) findViewById(R.id.grupoProgressBar);
        @Override
        protected List<Grupo> doInBackground(String... params) {
            try{
                return DAO.SelecionaGrupo(pageList);
            }catch (Exception e){
                Log.e("CARREGA-GRUPOS",e.getMessage(),e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<Grupo> lsGrupo) {
            super.onPostExecute(lsGrupo);

            if(lsGrupo!=null) {
                AtualizaGrid(lsGrupo);
            }else
                Log.i("Carrega", "--- LISTA NULA ---");
            pg.setVisibility(View.GONE);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            pg.setProgress(values[0]);
        }
    }

    private  class Delete extends AsyncTask<Grupo, Integer, Boolean>{
        @Override
        protected Boolean doInBackground (Grupo...params){
            try{
                return DAO.Deletar(grupo);
            }catch (Exception e){
                Log.e("ERROR", e.toString());
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            Dialog.CancelProgressDialog();
            if(aBoolean){
                pageList =0;
                new CarregaRegistros().execute();
                adpGrupo.clear();
                dlg.cancel();
            }else{
                Dialog.ShowAlert(GrupoActivity.this,"Deletar","Opss, Esta Categoria já esta sendo utilizada por outros registros!");
            }
        }
    }

    private class Salvar extends AsyncTask<Grupo,Integer,Boolean>{
        @Override
        protected Boolean doInBackground(Grupo... params) {
            try {
                Log.i("SALVA", "VEIIOOOO");

                return DAO.Salvar(grupo);

            }catch (Exception e){
                Log.e("EROO no SALVAAA", e.toString());
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean salvo) {
            super.onPostExecute(salvo);
            Dialog.CancelProgressDialog();

            if(salvo){
                if(grupo.getGru_codigo()!=0 ){
                    msn = "Registro editado com Sucesso!";
                }else
                    msn="Registro salvo com Sucesso!";
                pageList =0;
                new CarregaRegistros().execute();
                adpGrupo.clear();
                dlg.cancel();
                Toast t = Toast.makeText(GrupoActivity.this,msn,Toast.LENGTH_SHORT);
                t.show();
            } else {
                Dialog.ShowAlert(GrupoActivity.this, "Erro", "Erro ao Inserir registro, Favor tente novamente");
            }
            msn = null;
        }
    }

    public void CarregaPesquisa(String name){
        ProgressBar pg =(ProgressBar) findViewById(R.id.grupoProgressBar);
        if(!name.trim().equals("")){
            pg.setVisibility(View.VISIBLE);
            new CarregaPesquisa().execute(name);
        }
    }

    private class CarregaPesquisa extends AsyncTask<String, String, List<Grupo>>{
        ProgressBar pg =(ProgressBar) findViewById(R.id.grupoProgressBar);
        @Override
        protected List<Grupo> doInBackground(String... params) {
            return DAO.SelecionaPesquisa(params[0]);
        }

        @Override
        protected void onPostExecute(List<Grupo> lsGrupos) {
            super.onPostExecute(lsGrupos);
            adpGrupo.clear();
            if(lsGrupos!=null)
                adpGrupo.addAll(lsGrupos);
            adpGrupo.notifyDataSetChanged();
            pg.setVisibility(View.GONE);
        }
    }


}

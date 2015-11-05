package com.example.interdisciplinar.mobile;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
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
                //  boolean deleta = Dialog.ShowDialog(GrupoActivity.this, "Deletar Grupo", "Você deseja deletar este Grupo?");
                Log.i("Clickou", " -  Passou - ");
                new AlertDialog.Builder(GrupoActivity.this)
                        .setTitle(R.string.alertTitleOption)
                        .setMessage(R.string.alertMessageOption)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton("Excluir", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                grupo=gp;
                                new Delete().execute();
                            }
                        }).setNegativeButton("Editar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        grupo = adpGrupo.getItem(position);
                        GrupoForm();
                    }
                }).setNeutralButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).show();
                return false;
            }
        });


        grupoListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                Log.i("TOTAL!", "Total::: -- - -- " + totalItemCount+"");
                if (GoLoad && grupoListView.getLastVisiblePosition() == (adpGrupo.getCount() - 1)) {
                    Log.e("AKI", "--- FINALZAUM -- da lista -- -");
                    GoLoad = false;
                    new CarregaRegistros().execute();
                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("--ACAO--", "onPAUSEES");

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("--ACAO--", "onRESTART");
        // new CarregaGrupos().execute();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("--ACAO--", "onRESUME");
        if(GoLoad) {
            GoLoad=false;
            new CarregaRegistros().execute();
        }
        if(msn!=null&& !msn.isEmpty()){
            Toast t = Toast.makeText(this,msn,Toast.LENGTH_SHORT);
            t.show();
            msn = null;
        }
    }

    public void GrupoForm(){
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(R.string.GrupoNome);
        alert.setMessage(R.string.grupoNomeHint);

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
            ///   adpGrupo.clear();
            for (Grupo gp : lsGrupos) {
                adpGrupo.add(gp);//converte object em Grupo
                //grupoListView.addView(gp.getGru_vdescricao().tos);
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
        ProgressBar pg = (ProgressBar) findViewById(R.id.grupoProgressBar);
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
                Log.i("Carrega", "RETOURNOU com" + lsGrupo.size());
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
                e.printStackTrace();
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if(aBoolean){
                pageList =0;
                new CarregaRegistros().execute();
                adpGrupo.clear();
            }else{
                Dialog.ShowAlert(GrupoActivity.this,"Deletar","Opss, Esta Categoria ja esta sendo utilizada por outros registros!");
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
            if(salvo){
                if(grupo.getGru_codigo()!=0 ){
                    msn = "Registro editado com Sucesso!";
                }else
                    msn="Registro salvo com Sucesso!";

                pageList =0;
                new CarregaRegistros().execute();
                adpGrupo.clear();
            } else {
                Dialog.ShowAlert(GrupoActivity.this, "Erro", "Erro ao Inserir registro, Favor tente novamente");

            }
            dlg.cancel();
            Dialog.CancelProgressDialog();
            Toast t = Toast.makeText(GrupoActivity.this,msn,Toast.LENGTH_SHORT);
            t.show();
            msn = null;
        }
    }




}

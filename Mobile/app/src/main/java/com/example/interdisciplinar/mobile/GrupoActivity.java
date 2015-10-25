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
    private int ValorListView=1;

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
                        .setTitle("Deletar Grupo")
                        .setMessage("Você deseja deletar este Grupo?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton("Excluir", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                new Delete().execute(gp);
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


        new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                ////carrega mais registros

                Toast t = Toast.makeText(GrupoActivity.this,"Deu Scrolll!!",Toast.LENGTH_SHORT);
                t.show();
               /* if (totalItemCount < currentTotalItems) {
                    currentPage = firstItemPageIndex;
                    currentTotalItems = totalItemCount;
                    if (totalItemCount == 0) { loading = true; }
                }

                if (loading && (totalItemCount > currentTotalItems)) {
                    loading = false;
                    currentTotalItems = totalItemCount;
                    currentPage++;
                }

                if (!loading && (totalItemCount - visibleItemCount) <=
                        (firstVisibleItem + visibleThreshold)) {
                    loadMoreListener.onLoadMore(currentPage + 1, totalItemCount);
                    loading = true;
                }*/
                if(grupoListView.getLastVisiblePosition()==(adpGrupo.getCount()-1)){
                    Log.e("AKI", "--- FINALZAUM -- da lista -- -");
                }


            }
        };
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
        new CarregaRegistros().execute();
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
        AlertDialog dlg = alert.create();
        dlg.show();

        dlg.getButton(DialogInterface.BUTTON_NEGATIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (FuncoesExternas.Valida(txt)) {
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
            adpGrupo.clear();
            for (Grupo gp : lsGrupos) {
                adpGrupo.add(gp);//converte object em Grupo
            }
            grupoListView.setAdapter(adpGrupo);
        }
    }

    private class CarregaRegistros extends AsyncTask<String, Integer, List<Grupo>> {
        ProgressBar pg = (ProgressBar) findViewById(R.id.grupoProgressBar);
        @Override
        protected List<Grupo> doInBackground(String... params) {
            try{
                return DAO.SelecionaGrupo();
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
                return DAO.Deletar(params[0]);
            }catch (Exception e){
                e.printStackTrace();
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if(aBoolean){
                new CarregaRegistros().execute();
            }else{
                Dialog.ShowAlert(GrupoActivity.this,"Deletar","Opss, Esta Categoria ja esta sendo utilizada por outros registros!");
            }
        }
    }

    private class Salvar extends AsyncTask<Grupo,Integer,Boolean>{
        @Override
        protected Boolean doInBackground(Grupo... params) {
            try {
                Log.i("SALVA", "Chamo Mt."+ grupo.toString());
                return DAO.Salvar(grupo);
            }catch (Exception e){
                Log.e("EROO", e.toString());
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean salvo) {
            super.onPostExecute(salvo);
            if(salvo){
                msn = grupo.getGru_codigo()!=0 ? "Registro editado com Sucesso!" : "Registro salvo com Sucesso!";
                finish();
            }
            else {
                Dialog.ShowAlert(GrupoActivity.this, "Erro", "Erro ao Inserir registro, Favor tente novamente");
                Log.e("EROO", "NOA SSALVO");
            }
            Dialog.CancelProgressDialog();
        }
    }




}

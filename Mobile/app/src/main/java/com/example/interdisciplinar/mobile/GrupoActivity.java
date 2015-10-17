package com.example.interdisciplinar.mobile;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;

import DAO.GrupoDAO;
import model.Grupo;

public class GrupoActivity extends AppCompatActivity implements View.OnClickListener {

    private ListView grupoListView;
    static public List<Grupo> lsGrupos;
    private Button btnNovo;
    private ArrayAdapter<Grupo> adpGrupo;
    GrupoDAO DAO = new GrupoDAO();
    //= new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grupo);
        grupoListView = (ListView) findViewById(R.id.grupoListView);
        btnNovo = (Button)findViewById(R.id.grupoBtnNovo);
        btnNovo.setOnClickListener(this);

        adpGrupo = new ArrayAdapter<Grupo>(this, android.R.layout.simple_list_item_1);

        grupoListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Grupo grupo = adpGrupo.getItem(position);
                Intent i = new Intent(GrupoActivity.this, GrupoActivityForm.class);
                i.putExtra("GRUPO", grupo);
                startActivity(i);
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("--ACAO--", "onRESTART");
       // new CarregaGrupos().execute();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("--ACAO--", "onPAUSE");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("--ACAO--", "onDESTROY");

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("--ACAO--", "onRESUME");
        new CarregaGrupos().execute();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("--ACAO--", "onSTOP");

    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        Log.i("--ACAO--", "onFINALIZE");

    }

    @Override
    protected void onStart() {
        super.onStart();
       // new CarregaGrupos().execute();
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


    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, GrupoActivityForm.class);
        startActivity(intent);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Toast ts = Toast.makeText(this,"TEste de result", Toast.LENGTH_SHORT);
        ts.show();
    }



    public void AtualizaGrid(List<Grupo> lsGrupos){
        Log.i("ATUALIZA","VAI CARREGA");
        if(lsGrupos!=null) {
            for (Grupo gp : lsGrupos) {
                adpGrupo.add(gp);//converte object em Grupo
            }
            grupoListView.setAdapter(adpGrupo);
           Log.i("ATUALIZA", " FEIIIITOOO!!!!!!!");
        }
    }

    private class CarregaGrupos extends AsyncTask<String, Integer, List<Grupo>> {
        ProgressBar pg = (ProgressBar) findViewById(R.id.progressBar);

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



}

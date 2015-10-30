package com.example.interdisciplinar.mobile;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import DAO.NegocioDAO;
import model.Negocio;
import Enum.NegocioTipo;

public class NegocioActivity extends AppCompatActivity {

    private NegocioDAO DAO = new NegocioDAO();
    private Negocio negocio;
    private ArrayAdapter<Negocio> adpNegocio;
    private ListView listView;
    public static String msn=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_negocio);
       /* Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.negocioBtnNovo);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NegocioActivity.this, NegocioActivityForm.class);
                startActivity(intent);
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("teste", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast t  = Toast.makeText(NegocioActivity.this,"TEste1",Toast.LENGTH_SHORT);
                                t.show();
                            }
                        }).setAction("Teste 2222", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast t = Toast.makeText(NegocioActivity.this, "TEste222", Toast.LENGTH_SHORT);
                        t.show();
                    }
                }).show();*/
            }
        });
        ///getSupportActionBar().setDisplayHomeAsUpEnabled(true);

       /*
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_negocio);*/
        negocio = new Negocio();
        adpNegocio = new ArrayAdapter<>(this, android.R.layout.simple_list_item_2);
        listView = (ListView) findViewById(R.id.negocioListView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Negocio neg= adpNegocio.getItem(position);
                Intent i = new Intent(NegocioActivity.this, NegocioActivityForm.class);
                i.putExtra("NEGOCIO",neg);
                startActivity(i);
            }
        });

        Bundle bundle = getIntent().getExtras();
        if(bundle!=null && bundle.containsKey("TIPO")){
            char tipo =  bundle.getChar("TIPO");
            if(tipo== NegocioTipo.Orcamento){
                TextView txtTitulo = (TextView)findViewById(R.id.negocioListagemTxtTitulo);
                txtTitulo.setText(R.string.Orcamento);
                negocio.setNeg_ctipo(NegocioTipo.Orcamento);
            }else
                negocio.setNeg_ctipo(NegocioTipo.Negocio);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        new CarregaRegistros().execute();
        if(msn!=null&& !msn.isEmpty()){
            Toast t = Toast.makeText(this,msn,Toast.LENGTH_SHORT);
            t.show();
            msn = null;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_negocio, menu);
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

    public void CallNegocioForm(View view){
        Intent intent = new Intent(this, NegocioActivityForm.class );
        startActivity(intent);
    }

    protected void AtualizaGrid(List<Negocio> lsItens){
        if(lsItens!=null) {
            adpNegocio.clear();
            for (Negocio item : lsItens) {
                adpNegocio.add(item);//converte object em Grupo
            }
            listView.setAdapter(adpNegocio);
        }
    }

    private class CarregaRegistros extends AsyncTask<String, Integer, List<Negocio>>{
        ProgressBar pg = (ProgressBar) findViewById(R.id.negocioProgressBar);
        @Override
        protected List<Negocio> doInBackground(String... params) {
            return DAO.SelecionaNegocios(negocio.getNeg_ctipo());
        }

        @Override
        protected void onPostExecute(List<Negocio> lsNegocios) {
            super.onPostExecute(lsNegocios);
            if(lsNegocios!=null){
                AtualizaGrid(lsNegocios);
            }
            pg.setVisibility(View.GONE);
        }


        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            pg.setProgress(values[0]);
        }
    }



}

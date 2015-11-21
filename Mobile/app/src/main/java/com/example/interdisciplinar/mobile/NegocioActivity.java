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

import DAO.NegocioDAO;
import Enum.NegocioTipo;
import Enum.NegocioStatus;
import model.Negocio;

public class NegocioActivity extends AppCompatActivity {

    private NegocioDAO DAO = new NegocioDAO();
    private Negocio negocio;
    private ArrayAdapter<Negocio> adpNegocio;
    private ListView listView;
    public static String msn=null;
    private int pageList=0;
    public static boolean goLoad=true;
    public static boolean clearList=false;
    private AlertDialog dlg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_negocio);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.negocioBtnNovo);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NegocioActivity.this, NegocioActivityForm.class);
                intent.putExtra("NEGOCIO",negocio);
                startActivity(intent);
            }
        });

        negocio = new Negocio();
        adpNegocio = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        listView = (ListView) findViewById(R.id.negocioListView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,  int position, long id) {
                final Negocio neg = adpNegocio.getItem(position);
                AlertDialog.Builder ldg = new AlertDialog.Builder(NegocioActivity.this);
                ldg.setTitle(R.string.tituloOpcao);
                ldg.setMessage(R.string.mensagemOpcao);
                if(neg.getNeg_cstatus() == NegocioStatus.ABERTO){
                    ldg.setNegativeButton(R.string.Editar, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent i = new Intent(NegocioActivity.this, NegocioActivityForm.class);
                            i.putExtra("NEGOCIO", neg);
                            startActivity(i);
                        }
                    });
                }
                ldg.setNeutralButton("Detalhes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent(NegocioActivity.this, NegocioActivityDetalhes.class);
                        i.putExtra("NEGOCIO", neg);
                        startActivity(i);
                    }
                });
                ldg.setPositiveButton(R.string.Cancelar, null);
                ldg.show();
            }
        });

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if(goLoad && listView.getLastVisiblePosition() == (adpNegocio.getCount() - 1)) {
                    goLoad = false;
                    new CarregaRegistros().execute();
                }
            }
        });

        Bundle bundle = getIntent().getExtras();
        if(bundle!=null && bundle.containsKey("TIPO")){
            int tipo =  bundle.getInt("TIPO");
            if(tipo== NegocioTipo.Orcamento){
                Log.i("TIPO","ORCAMENTO");
                negocio.setNeg_ctipo(NegocioTipo.Orcamento);
            }else {
                setTitle(R.string.Negocio);
                negocio.setNeg_ctipo(NegocioTipo.Negocio);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(clearList) {
            adpNegocio.clear();
            pageList=0;
            goLoad=true;
        }
        if(goLoad) {
            goLoad=false;
            new CarregaRegistros().execute();
        }
        if(msn!=null&& !msn.isEmpty()){
            Toast t = Toast.makeText(this,msn,Toast.LENGTH_SHORT);
            t.show();
            msn = null;
        }

        final EditText search = (EditText)findViewById(R.id.negocioListagemTxtPesquisar);
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
        ProgressBar pg = (ProgressBar) findViewById(R.id.negocioProgressBar);
        pg.setVisibility(View.VISIBLE);
        if(name.trim().equals("")){
            pageList=0;
            new CarregaRegistros().execute();
        }else{
            new CarregaPesquisa().execute(name);
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
            if(pageList==0)
                listView.setAdapter(adpNegocio);
            else
                adpNegocio.notifyDataSetChanged();

            if(lsItens.size()==15) {
                goLoad = true;
                pageList++;
            }
        }
    }

    private class CarregaRegistros extends AsyncTask<String, Integer, List<Negocio>>{
        ProgressBar pg = (ProgressBar) findViewById(R.id.negocioProgressBar);
        @Override
        protected List<Negocio> doInBackground(String... params) {
            return DAO.SelecionaNegocios(negocio.getNeg_ctipo(), pageList);
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

    private class CarregaPesquisa extends AsyncTask<String, String, List<Negocio>>{
        ProgressBar pg = (ProgressBar) findViewById(R.id.negocioProgressBar);
        @Override
        protected List<Negocio> doInBackground(String... params) {
            return DAO.SelecionaPesquisa(negocio.getNeg_ctipo(),params[0]);
        }

        @Override
        protected void onPostExecute(List<Negocio> negocios) {
            super.onPostExecute(negocios);
            adpNegocio.clear();
            if(negocios!=null)
                adpNegocio.addAll(negocios);

            adpNegocio.notifyDataSetChanged();
            pg.setVisibility(View.GONE);
        }
    }

}

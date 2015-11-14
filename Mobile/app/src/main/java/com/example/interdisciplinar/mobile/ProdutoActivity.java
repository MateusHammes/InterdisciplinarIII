package com.example.interdisciplinar.mobile;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.List;

import DAO.ProdutoDAO;
import model.Negocio;
import model.Produto;

public class ProdutoActivity extends AppCompatActivity {

    private Negocio negocio = new Negocio();
    private ListView listView = null;
    private ArrayAdapter<Produto> adpProdutos = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produto);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listView = (ListView)findViewById(R.id.produtoIndexListView);
        adpProdutos = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        listView.setAdapter(adpProdutos);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.produtoBtnNovo);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ProdutoActivity.this, ProdutoActivityForm.class);
                i.putExtra("NEGOCIO", negocio);
                startActivity(i);
                finish();
            }
        });

        Bundle bundle = getIntent().getExtras();
        if(bundle!=null && bundle.containsKey("NEGOCIO")){
            negocio = (Negocio) bundle.getSerializable("NEGOCIO");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        new CarregaProdutos().execute();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final int posicao = position;
                AlertDialog.Builder ldg = new AlertDialog.Builder(ProdutoActivity.this);
                ldg.setTitle(R.string.tituloOpcao);
                ldg.setMessage(R.string.mensagemOpcao);
                ldg.setNegativeButton(R.string.Editar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Produto pro = adpProdutos.getItem(posicao);
                        Intent i = new Intent(ProdutoActivity.this, ProdutoActivityForm.class);
                        i.putExtra("PRODUTO", pro);
                        startActivity(i);
                    }
                });
                ldg.setNeutralButton("Detalhes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Produto pro = adpProdutos.getItem(posicao);
                        Intent i = new Intent(ProdutoActivity.this, ProdutoActivityDetalhes.class);
                        i.putExtra("PRODUTO", pro);
                        startActivity(i);
                        finish();
                    }
                });
                ldg.setPositiveButton(R.string.Cancelar, null);
                ldg.show();

            }
        });
    }

    private class CarregaProdutos extends AsyncTask<Produto, String, List<Produto>> {
        ProdutoDAO produtoDAO = new ProdutoDAO();
        ProgressBar pgProduto = (ProgressBar) findViewById(R.id.produtoIndexProgressBar);
        @Override
        protected List<Produto> doInBackground(Produto... params) {
            return produtoDAO.SelecionaProduto(negocio.getNeg_codigo());
        }

        @Override
        protected void onPostExecute(List<Produto> lsProdutos) {
            super.onPostExecute(lsProdutos);
            adpProdutos.clear();
            if(lsProdutos!=null){
                for(Produto p: lsProdutos){
                    adpProdutos.add(p);
                }
                listView.setAdapter(adpProdutos);
            }
            pgProduto.setVisibility(View.GONE);
        }
    }

}

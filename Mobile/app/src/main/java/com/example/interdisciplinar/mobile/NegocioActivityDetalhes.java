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
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.List;

import DAO.NegocioDAO;
import DAO.ProdutoDAO;
import model.Negocio;
import model.Produto;
import util.DateUtil;

public class NegocioActivityDetalhes extends AppCompatActivity {
    NegocioDAO DAO = new NegocioDAO();
    private Negocio negocio;
    private ArrayAdapter<Produto> adpProdutos;
    private ListView listViewProdutos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_negocio_detalhes);

        adpProdutos = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);

        listViewProdutos = (ListView) findViewById(R.id.negocioDetalhesListViewProdutos);
        listViewProdutos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ///chama detalhes do produto
                Produto pro = adpProdutos.getItem(position);
                Intent i = new Intent(NegocioActivityDetalhes.this, ProdutoActivityDetalhes.class);
                i.putExtra("PRODUTO",pro);
                startActivity(i);
            }
        });


        Bundle bundle = getIntent().getExtras();
        if(bundle!=null && bundle.containsKey("NEGOCIO")){
            Log.i("Vai apresenta","PARAMETROSSSSS");
            negocio =(Negocio) bundle.getSerializable("NEGOCIO");
            Log.i("Vai apresenta","O NEgsds");
            SetValues(negocio);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        new CarregaProdutos().execute();
        new PegaValorTotal().execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_negocio_activity_details, menu);
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


    public void CallProdutoForm(View view){
        Intent i = new Intent(NegocioActivityDetalhes.this, ProdutoActivityForm.class);
        i.putExtra("NEGOCIO", negocio);
        startActivity(i);
    }

    //region Get e Set Values
    private void SetValues(Negocio item){

        TextView cliente = (TextView)findViewById(R.id.negocioDetailCliente);
        TextView nome = (TextView)findViewById(R.id.negocioDetailNome);
        TextView endereco = (TextView)findViewById(R.id.negocioDetailEndereco);
        TextView criacao = (TextView)findViewById(R.id.negocioDetalhesDataCriacao);
        TextView termino = (TextView)findViewById(R.id.negocioDetalhesDataTermino);

        nome.setText(item.getNeg_vnome());
        cliente.setText(item.getNeg_vcliente());
        endereco.setText(item.getNeg_vendereco());
        criacao.setText(DateUtil.dateToString(negocio.getNeg_dcadastro()));
        termino.setText(DateUtil.dateToString(negocio.getNeg_dtermino()));
        Log.e("Tem produt----","???");
        if(item.getLsProdutos()!=null) {   //faz listagem dos produtos do negocio
            Log.e("Tem produtosss","aki");
            adpProdutos.clear();
            for (Produto prd:item.getLsProdutos()) {
                adpProdutos.add(prd);
            }
            listViewProdutos.setAdapter(adpProdutos);
        }
    }



    //endregion

    private class CarregaProdutos extends AsyncTask<Produto, String, List<Produto>>{
        ProdutoDAO produtoDAO = new ProdutoDAO();
        ProgressBar pgProduto = (ProgressBar) findViewById(R.id.negocioDetalhesProgressBarProduto);
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
                listViewProdutos.setAdapter(adpProdutos);
            }
            pgProduto.setVisibility(View.GONE);
        }
    }

    private  class CarregaNegocio extends  AsyncTask<Negocio, String, Negocio>{
        @Override
        protected Negocio doInBackground(Negocio... params) {
            return DAO.SelecionaNegocio(negocio.getNeg_codigo());
        }

        @Override
        protected void onPostExecute(Negocio Negocio) {
            super.onPostExecute(Negocio);
            SetValues(Negocio);
        }
    }


    private class PegaValorTotal extends AsyncTask<String, String, Double >{
        ProdutoDAO produtoDAO = new ProdutoDAO();
        @Override
        protected Double doInBackground(String... params) {
            return produtoDAO.SelecionaValorTotal(negocio.getNeg_codigo());
        }

        @Override
        protected void onPostExecute(Double aDouble) {
            super.onPostExecute(aDouble);
            TextView txt = (TextView)findViewById(R.id.negocioDetailValorTotal);
            NumberFormat mbf = NumberFormat.getCurrencyInstance();
            txt.setText(mbf.format(aDouble));

        }
    }


}

package com.example.interdisciplinar.mobile;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DecimalFormat;

import DAO.NegocioDAO;
import DAO.ProdutoDAO;
import Enum.NegocioStatus;
import Enum.NegocioTipo;
import model.Negocio;
import util.DateUtil;
import util.Dialog;

public class NegocioActivityDetalhes extends AppCompatActivity {
    NegocioDAO DAO = new NegocioDAO();
    private Negocio negocio;
    public static String mensage = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_negocio_detalhes);

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
        new PegaValorTotal().execute(negocio);
        Button btnProduto = (Button)findViewById(R.id.negocioProdutoBtnIndex);
        btnProduto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(NegocioActivityDetalhes.this,ProdutoActivity.class);
                i.putExtra("NEGOCIO",negocio);
                startActivity(i);
            }
        });
        Button btnCriaNegocio = (Button)findViewById(R.id.negocioDetalhesBtnCriarNegocio);
        btnCriaNegocio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CriaNegocioOrcamento().execute(negocio.getNeg_codigo());
            }
        });

        if(mensage != null){
            Dialog.Show(this,"Novo Negócio","");
        }
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

        if(item.getNeg_ctipo() == NegocioTipo.Orcamento){
            TextView header  = (TextView) findViewById(R.id.negocioDetalhesTxtHeader);
            header.setText(R.string.Orcamento);
            if(item.getNeg_cstatus() == NegocioStatus.ABERTO) {
                Button btnCriarNegocio = (Button) findViewById(R.id.negocioDetalhesBtnCriarNegocio);
                btnCriarNegocio.setVisibility(View.VISIBLE);
            }
        }else {
            if (item.getNeg_parent() != null && item.getNeg_parent().getNeg_codigo() != 0) { ///tem orçamento
                new PegaValorTotal().execute(item.getNeg_parent());
                LinearLayout layout = (LinearLayout)findViewById(R.id.negocioDetalhesLayoutComparacao);
                layout.setVisibility(View.VISIBLE);
            }
        }
    }
    //endregion


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


    private class PegaValorTotal extends AsyncTask<Negocio, String, String >{
        ProdutoDAO produtoDAO = new ProdutoDAO();
        boolean isNegocio =true;
        @Override
        protected String doInBackground(Negocio... params) {
//            if(params.length>0) {
                isNegocio = params[0].getNeg_ctipo() == NegocioTipo.Negocio;
                return produtoDAO.SelecionaValorTotal(params[0].getNeg_codigo());
  //          }else
    //            return "0";
        }

        @Override
        protected void onPostExecute(String valor) {
            super.onPostExecute(valor);
            //TextView txt;
           // if(isNegocio)
            TextView  txt = (TextView)findViewById(R.id.negocioDetailValorTotal);
            //else
            //    txt = (TextView)findViewById(R.id.negocioDetalhesTxtValorTotalOrcamento);
            Log.i("Valor", "TOTALLL = " + valor);
            if(!valor.isEmpty())
                txt.setText(DecimalFormat.getInstance().format(Double.parseDouble(valor)));
        }
    }

    /**
     * Metodo responsável por criar o neocio baseado no orçamento
     */
    private class CriaNegocioOrcamento extends  AsyncTask<Integer, String, String>{
        @Override
        protected String doInBackground(Integer... params) {
            return DAO.criaNegocioOrcamento(params[0]);
        }

        @Override
        protected void onPostExecute(String valor) {
            super.onPostExecute(valor);
            Dialog.CancelProgressDialog();

            if(valor.equals("0"))//deu erro
                Dialog.ShowAlertError(NegocioActivityDetalhes.this);
            else{
                Negocio ng = negocio;
                ng.getNeg_parent().setNeg_cstatus(NegocioStatus.CONCLUIDO);
                negocio.setNeg_parent(ng);
                negocio.setNeg_codigo(Integer.parseInt(valor));
                negocio.setNeg_ctipo(NegocioTipo.Negocio);
                //SetValues(negocio);
                Intent i  = new Intent(NegocioActivityDetalhes.this, NegocioActivityDetalhes.class);
                i.putExtra("NEGOCIO",negocio);
                startActivity(i);
                finish();
            }
        }
    }

}

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
import android.widget.EditText;

import DAO.ProdutoDAO;
import model.Negocio;
import model.Produto;
import util.Dialog;
import util.FuncoesExternas;

public class ProdutoActivityForm extends AppCompatActivity {
    private Produto produto = new Produto();
    private ProdutoDAO DAO = new ProdutoDAO();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produto_form);
        produto = new Produto();
        Bundle bundle = getIntent().getExtras();
        try {
            if (bundle != null){
                if(bundle.containsKey("NEGOCIO")) {
                    Log.i("Tem negocio","comneg");
                    Negocio neg =(Negocio) bundle.getSerializable("NEGOCIO");
                    produto.setNegocio(neg);
                }
                if(bundle.containsKey("PRODUTO")){
                    produto= (Produto)bundle.getSerializable("PRODUTO");
                    SetItem(produto);
                }
            }
        }catch (Exception e){
            finish();
        }

        Button btn = (Button)findViewById(R.id.produtoBtnSalvar);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SalvarProduto();
            }
        });
        Button cancel = (Button)findViewById(R.id.produtoBtnCancelar);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_produto_form, menu);
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


    private void SetItem(Produto p){
        EditText nome =(EditText)findViewById(R.id.produtoNome);
        EditText descricao =(EditText)findViewById(R.id.produtoDescricao);
        if(p!=null && p.getPro_codigo() > 0){
            nome.setText(p.getPro_vnome());
            descricao.setText(p.getPro_vdescricao());
        }
    }

    public void GetItem(){
        EditText nome =(EditText)findViewById(R.id.produtoNome);
        EditText descricao =(EditText)findViewById(R.id.produtoDescricao);
        if(produto==null)
            produto = new Produto();

        produto.setPro_vnome(nome.getText().toString());
        produto.setPro_vdescricao(descricao.getText().toString());
    }

    public void SalvarProduto(){
        EditText txtNome = (EditText)findViewById(R.id.produtoNome);
        if(FuncoesExternas.Valida(txtNome)){
            GetItem();
            Log.i("Vai ir", "Serio! com" + produto.getPro_codigo());
            new SalvarProduto().execute();
            Dialog.ShowProgressDialog(ProdutoActivityForm.this);
        }
    }



    //region salvar Assincrono
    private class SalvarProduto extends AsyncTask<Produto, String, Boolean> {
        @Override
        protected Boolean doInBackground(Produto... params) {
            Log.i("Tamo aki","pra salva");
            String id =  DAO.Salvar(produto);
            Log.i("Return ID",id);
            if(!id.equals("0")){
                produto.setPro_codigo(Integer.parseInt(id));
                return true;
            }else
                return false;
        }

        @Override
        protected void onPostExecute(Boolean salvo) {
            super.onPostExecute(salvo);
            if(salvo) {
                Intent i = new Intent(ProdutoActivityForm.this, ProdutoActivityDetalhes.class);
                i.putExtra("PRODUTO",produto);
                startActivity(i);
                finish();
            }else
                Dialog.ShowAlertError(ProdutoActivityForm.this);
            Dialog.CancelProgressDialog();
        }
    }
    //endregion

}

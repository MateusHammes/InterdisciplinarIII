package com.example.interdisciplinar.mobile;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import DAO.ProdutoDAO;
import model.Negocio;
import model.Produto;
import model.Produto_material;
import model.Registros;
import util.Dialog;
import util.FuncoesExternas;

public class ProdutoActivityForm extends AppCompatActivity {
    private Produto produto;
    private ArrayAdapter<Produto_material> adpMateriais;
    private ListView listViewMateriais;
    private ArrayAdapter<Registros> adpRegistros;
    private ListView listViewRegistros;

    private ProdutoDAO DAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produto_form);
        produto = new Produto();

        adpMateriais = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        listViewMateriais = (ListView) findViewById(R.id.produtoDetalheListViewMaterial);

        adpRegistros = new ArrayAdapter<>(this, android.R.layout.simple_list_item_2);
        listViewRegistros = (ListView) findViewById(R.id.produtoDetalheListViewEspecificacao);

        Bundle bundle = getIntent().getExtras();
        try {
            if (bundle != null && bundle.containsKey("NEG_CODIGO")) {
                Negocio neg = new Negocio();
                String id = String.valueOf(bundle.getBundle("NEG_CODIGO"));
                neg.setNeg_codigo(Integer.parseInt(id));
                produto.setNegocio(new Negocio() {
                });
            }
        }catch (Exception e){
            finish();
        }
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



    public void SalvarProduto(View view){
        EditText txtNome = (EditText)findViewById(R.id.produtoNome);
        if(FuncoesExternas.Valida(txtNome)){
            Toast t = Toast.makeText(this,"SALVARIA",Toast.LENGTH_SHORT);
            t.show();
            new Salvar().execute();
        }
    }

    //region salvar Assincrono
    private class Salvar extends AsyncTask<Produto, String, Boolean> {
        @Override
        protected Boolean doInBackground(Produto... params) {
           String id = DAO.Salvar(produto);
            if(!id.isEmpty() && !id.equals("0")){
                produto.setPro_codigo(Integer.parseInt(id));
                return true;
            }else
                return false;
        }

        @Override
        protected void onPostExecute(Boolean salvo) {
            super.onPostExecute(salvo);
            if(salvo) {
                finish();
                Intent i = new Intent(ProdutoActivityForm.this, ProdutoActivityDetalhes.class);
                i.putExtra("PRODUTO",produto);
                startActivity(i);

                //NegocioActivity.msn = negocio.getNeg_codigo()!=0?"Registro editado com Sucesso!":"Registro inserido com Sucesso!";
            }else
                Dialog.ShowAlert(ProdutoActivityForm.this, "Erro", "Ops, houve um imprevisto, favor tente novamente!");
        }
    }
    //endregion


}

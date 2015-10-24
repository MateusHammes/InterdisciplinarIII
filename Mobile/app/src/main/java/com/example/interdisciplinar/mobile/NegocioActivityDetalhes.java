package com.example.interdisciplinar.mobile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.text.NumberFormat;

import model.Negocio;
import model.Produto;

public class NegocioActivityDetalhes extends AppCompatActivity {

    private Negocio negocio;
    private ArrayAdapter<Produto> adpItens;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_negocio_detalhes);

        adpItens = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);

        listView = (ListView) findViewById(R.id.negocioListView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ///chama detalhes do produto
                /*Produto pro = adpItens.getItem(position);
                Intent i = new Intent(NegocioActivityDetahes.this, ProdutoActivity.class);
                i.putExtra("PRODUTO",pro);
                startActivity(i);*/
            }
        });


        Bundle bundle = getIntent().getExtras();
        if(bundle!=null && bundle.containsKey("NEGOCIO")){
            negocio =(Negocio) bundle.getSerializable("NEGOCIO");
            SetValues(negocio);
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
        i.putExtra("NEG_CODIGO", negocio.getNeg_codigo());
        startActivity(i);
    }

    //region Get e Set Values
    private void SetValues(Negocio item){

        NumberFormat number = NumberFormat.getCurrencyInstance();

        TextView cliente = (TextView)findViewById(R.id.negocioDetailCliente);
        TextView nome = (TextView)findViewById(R.id.negocioDetailNome);
        TextView endereco = (TextView)findViewById(R.id.negocioDetailEndereco);
        TextView valorT = (TextView)findViewById(R.id.negocioDetailValorAdquirido);
        TextView valorAd = (TextView)findViewById(R.id.negocioDetailValorTotal);

        nome.setText(item.getNeg_vnome());
        cliente.setText(item.getNeg_vcliente());
        endereco.setText(item.getNeg_vendereco());
        valorT.setText(number.format(item.getNeg_valorTotal()));
        valorAd.setText(number.format(item.getNeg_valorTotal()));

        if(item.getLsProdutos()!=null) {   //faz listagem dos produtos do negocio
            adpItens.clear();
            for (Produto prd:item.getLsProdutos()) {
                adpItens.add(prd);
            }
            listView.setAdapter(adpItens);
        }

    }



    //endregion

}

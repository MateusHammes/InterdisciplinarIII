package com.example.interdisciplinar.mobile;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import DAO.RegistrosDAO;
import model.Produto;
import model.Produto_material;
import model.Registros;
import util.FuncoesExternas;

public class ProdutoActivityDetalhes extends AppCompatActivity {

    private Produto produto;
    private Registros registro;
    private ArrayAdapter<Produto_material> adpMateriais;
    private ListView listViewMateriais;
    private ArrayAdapter<Registros> adpRegistros;
    private ListView listViewRegistros;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produto_detalhes);

        adpMateriais = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        listViewMateriais = (ListView) findViewById(R.id.produtoDetalheListViewMaterial);

        adpRegistros = new ArrayAdapter<>(this, android.R.layout.simple_list_item_2);
        listViewRegistros = (ListView) findViewById(R.id.produtoDetalheListViewEspecificacao);


        Bundle bundle = getIntent().getExtras();
        if(bundle!=null && bundle.containsKey("NEGOCIO")){
            produto =(Produto) bundle.getSerializable("NEGOCIO");
            SetValues(produto);
        }
    }


    private void SetValues(Produto produto){
        TextView nome = (TextView)findViewById(R.id.produtoDetalheNome);
        TextView descricao = (TextView)findViewById(R.id.produtoDetalheDescricao);

        nome.setText(produto.getPro_vnome());
        descricao.setText(produto.getPro_vdescricao());

        if(produto.getLsRegistros()!=null){
            adpRegistros.addAll(produto.getLsRegistros());
            listViewRegistros.setAdapter(adpRegistros);
        }
        if(produto.getLsProdutoMateriais()!=null) {
            adpMateriais.addAll(produto.getLsProdutoMateriais());
            listViewMateriais.setAdapter(adpMateriais);
        }
    }


    public void NovoRegistro(View view){
        registro = new Registros();
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle(R.string.Registros);
        dialog.setMessage(R.string.RegistrosNome);
        final EditText input = new EditText(this);
        dialog.setView(input);
        dialog.setCancelable(false);

        dialog.setNegativeButton(R.string.Salvar, null);
        dialog.setPositiveButton(R.string.Cancelar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ///cancelar
                dialog.cancel();
            }
        });

        AlertDialog alert = dialog.create();
        alert.show();
        alert.getButton(DialogInterface.BUTTON_NEGATIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (FuncoesExternas.Valida(input)) {
                    new SalvaRegistro().execute();
                }
            }
        });


    }



    private class SalvaRegistro extends AsyncTask<Registros,String, Boolean> {
        RegistrosDAO registrosDAO = new RegistrosDAO();
        @Override
        protected Boolean doInBackground(Registros... params) {
            ///show loader desta tela
            String id = registrosDAO.Salvar(registro);
            if (!id.isEmpty() && !id.equals("0")) {
                registro.setRgs_codigo(Integer.parseInt(id));
                return true;
            } else {
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if(aBoolean){
                adpRegistros.clear();
               // adpRegistros =(ArrayAdapter<Registros>)listViewRegistros.getAdapter();
            }else {

                //remove loader desta tela
            }
        }
    }
}










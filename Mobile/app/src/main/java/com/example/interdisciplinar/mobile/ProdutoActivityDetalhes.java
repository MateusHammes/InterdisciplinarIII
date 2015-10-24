package com.example.interdisciplinar.mobile;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import model.Produto;

public class ProdutoActivityDetalhes extends AppCompatActivity {

    private Produto produto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produto_detalhes);

        Bundle bundle = getIntent().getExtras();
        if(bundle!=null && bundle.containsKey("NEGOCIO")){
            produto =(Produto) bundle.getSerializable("NEGOCIO");
            SetValues(produto);
        }
    }


    private void SetValues(Produto produto){

    }







}

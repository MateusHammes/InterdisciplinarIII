package com.example.interdisciplinar.mobile;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.text.DecimalFormat;

import DAO.GrupoDAO;
import DAO.MateriaisDAO;
import model.Grupo;
import model.Materiais;
import util.Dialog;
import util.FuncoesExternas;

public class MateriaisActivityForm extends AppCompatActivity {

    Materiais material = new Materiais();
    MateriaisDAO DAO = new MateriaisDAO();
    Spinner spnGrupos ;
    ArrayAdapter<Grupo>adpSpinnerGrupos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_materiais_form);

        spnGrupos  =(Spinner)findViewById(R.id.materialSpinner);
        adpSpinnerGrupos = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        GrupoDAO grupoDAO = new GrupoDAO();
        adpSpinnerGrupos.addAll(grupoDAO.SelecionaTodosGrupo());
        spnGrupos.setAdapter(adpSpinnerGrupos);

        Bundle bundle = getIntent().getExtras();
        if(bundle!=null && bundle.containsKey("MATERIAL")){
            material =(Materiais) bundle.getSerializable("MATERIAL");
            SetItem(material);
        }
        Button btnSalvar = (Button)findViewById(R.id.materialBtnSalvar);
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetItem();
                new Salvar().execute();
            }
        });
    }


    public void GetItem(){
        EditText txtNome = (EditText)findViewById(R.id.materialTxtNome);
        EditText txtvalor= (EditText)findViewById(R.id.materialTxtValor);
        EditText txtEstoque = (EditText)findViewById(R.id.materialTxtEstoque);
        EditText txtDescricao = (EditText)findViewById(R.id.materialTxtDescricao);

        if(material==null)
            material=new Materiais();

        material.setGrupo((Grupo) spnGrupos.getSelectedItem());
        material.setMtr_vnome(txtNome.getText().toString());
        material.setMtr_iestoque(Integer.parseInt(txtvalor.getText().toString()));
        material.setMtr_vdescricao(txtDescricao.getText().toString());
        material.setMtr_iestoque(Integer.parseInt(txtEstoque.getText().toString()));
    }


    public void SetItem(Materiais mtr){
        spnGrupos.setSelection(adpSpinnerGrupos.getPosition(mtr.getGrupo()));
        EditText txtNome = (EditText)findViewById(R.id.materialTxtNome);
        EditText txtvalor= (EditText)findViewById(R.id.materialTxtValor);
        EditText txtEstoque = (EditText)findViewById(R.id.materialTxtEstoque);
        EditText txtDescricao = (EditText)findViewById(R.id.materialTxtDescricao);
        txtNome.setText(mtr.getMtr_vnome());
        txtDescricao.setText(mtr.getMtr_vdescricao());
        txtEstoque.setText(mtr.getMtr_iestoque());
        txtvalor.setText(DecimalFormat.getInstance().format(mtr.getMtr_nvalor()));
    }

    public void SalvarItem(View view){
        EditText txtNome = (EditText)findViewById(R.id.materialTxtNome);
        EditText txtvalor= (EditText)findViewById(R.id.materialTxtValor);
        EditText txtEstoque = (EditText)findViewById(R.id.materialTxtEstoque);


        if(FuncoesExternas.Valida(txtNome))
            if(FuncoesExternas.Valida(txtvalor))
                if(FuncoesExternas.Valida(txtEstoque)){
                    GetItem();
                    new Salvar().execute();
                    Dialog.ShowProgressDialog(MateriaisActivityForm.this);
                }
    }


    private class Salvar extends AsyncTask<Materiais, String, Boolean>{

        @Override
        protected Boolean doInBackground(Materiais... params) {
            return DAO.Salvar(material);
        }

        @Override
        protected void onPostExecute(Boolean salvo) {
            super.onPostExecute(salvo);
            if(salvo){
                finish();
            }else
                Dialog.ShowAlertError(MateriaisActivityForm.this);
            Dialog.CancelProgressDialog();
        }
    }


}

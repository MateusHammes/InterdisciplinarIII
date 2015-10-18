package com.example.interdisciplinar.mobile;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import DAO.GrupoDAO;
import model.Grupo;
import util.Dialog;

public class GrupoActivityForm extends AppCompatActivity {

    private EditText txtNome;
    private Button btnSalvar;
    private Grupo grupo;
    private GrupoDAO DAO  = new GrupoDAO();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grupo_form);

        txtNome = (EditText) findViewById(R.id.grupoTxtNome);

        btnSalvar = (Button) findViewById(R.id.grupoButtonSalvar);

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvaGrupo();
            }
        });

        grupo = new Grupo();
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null && bundle.containsKey("GRUPO")){
            grupo =(Grupo) bundle.getSerializable("GRUPO");
            SetGrupo(grupo);
        }
    }

    public void salvaGrupo(){
        grupo.setGru_vdescricao(txtNome.getText().toString());
        Log.i("SALVA", "VAI SALVA?");
        new Salvar().execute();
    }

    private void GetGrupo(){
        EditText descricao = (EditText) findViewById(R.id.grupoTxtNome);
        grupo.setGru_vdescricao(descricao.getText().toString());
    }

    private void SetGrupo(Grupo grupo){
        if(grupo!=null) {
            EditText descricao = (EditText) findViewById(R.id.grupoTxtNome);
            descricao.setText(grupo.getGru_vdescricao());
        }
    }


    private class Salvar extends AsyncTask<Grupo,Integer,Boolean>{
        @Override
        protected Boolean doInBackground(Grupo... params) {
            try {
                Log.i("SALVA", "Chamo Mt."+ grupo.toString());

                return DAO.Salvar(grupo);
            }catch (Exception e){
                Log.e("EROO",e.toString());
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean salvo) {
            super.onPostExecute(salvo);
            if(salvo)
                finish();
            else {
                Dialog.ShowAlert(GrupoActivityForm.this, "Erro", "Erro ao Inserir registro, Favor tente novamente");
                Log.e("EROO","NOA SSALVO");
            }

        }
    }
}

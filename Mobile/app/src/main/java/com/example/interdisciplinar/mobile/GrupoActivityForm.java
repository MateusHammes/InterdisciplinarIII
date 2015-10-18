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

      //  DAO.Salvar(grupo);
        new Salvar().execute();
    }

    //region inserir
    /*public void InserirGrupo(final Grupo grupo){
        final String nameSpace = "http://Server/";
        final String metodoWeb = "salvar";
        final String soapAction ="http://Server/salvar";
        final String url = "http://192.168.0.106:8080/WebService/WService?wsdl";

        Thread insertThread  = new Thread(){
            public  void run(){
                try {
                    SoapObject request = new SoapObject(nameSpace, metodoWeb);
                    PropertyInfo pi = new PropertyInfo();
                    pi.name = "Grupo";
                    pi.setValue(new Wrapper(grupo));
                    pi.setType(Wrapper.class);
                    pi.setNamespace(nameSpace);

                    request.addProperty(pi);
                    SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                    envelope.dotNet = true;
                    envelope.setOutputSoapObject(request);

                    envelope.addMapping(nameSpace, "Grupo", Wrapper.class);

                    HttpTransportSE androidHttpTransport = new HttpTransportSE(url);
                    androidHttpTransport.call(soapAction, envelope);
                    envelope.setOutputSoapObject(request);
                    HttpTransportSE http = new HttpTransportSE(url);
                    http.call(soapAction, envelope);

                    final SoapPrimitive response = (SoapPrimitive)envelope.getResponse();

                    if(response.toString().equals("True")){
                        //Objeto Inserido
                    }else{
                        //Erro ao Inserir
                    }

                }catch (Exception e){
                    Log.i("Erro",e.toString());
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
        };
        insertThread.start();



    }*/
///endregion

    private void GetGrupo(){
        EditText descricao = (EditText) findViewById(R.id.grupoTxtNome);
        //Grupo gru = new Grupo();
        grupo.setGru_vdescricao(descricao.getText().toString());
    }

    private void SetGrupo(Grupo grupo){
        if(grupo!=null) {
            EditText descricao = (EditText) findViewById(R.id.grupoTxtNome);
            descricao.setText(grupo.getGru_vdescricao());
        }
    }


    private class Salvar extends AsyncTask<Grupo,String,Boolean>{
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
                Dialog.ShowAlert(GrupoActivityForm.this, "Erro", "Erro ao Inserir");
                Log.e("EROO","NOA SSALVO");
            }

        }
    }
}

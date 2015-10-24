package com.example.interdisciplinar.mobile;

import android.app.DatePickerDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

import DAO.NegocioDAO;
import model.Negocio;
import util.DateUtil;
import util.Dialog;
import util.FuncoesExternas;

public class NegocioActivityForm extends AppCompatActivity {

    private EditText txtDate;
    private Negocio negocio = new Negocio();
    private NegocioDAO DAO = new NegocioDAO();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_negocio_form);

        txtDate =(EditText) findViewById(R.id.negocioTxtDataPrevisao);
        txtDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    ShowCalendar();
            }
        });

        Bundle bundle = getIntent().getExtras();
        if(bundle !=null && bundle.containsKey("NEGOCIO")){
            negocio =(Negocio) bundle.getSerializable("NEGOCIO");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_negocio_form, menu);
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

/*
    public void CallNegocioIndex(View view){
        Intent intent = new Intent(this, NegocioActivity.class);
        startActivity(intent);
    }
*/

    private void ShowCalendar(){
        Calendar cal  = Calendar.getInstance();
        DatePickerDialog dpk = new DatePickerDialog(this, new SelecionaDateListener(), cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
        dpk.show();
    }

    public void Cancelar(View view) {
        finish();
    }

    public void SalvarNegocio(View view){
        EditText txtNome = (EditText)findViewById(R.id.negocioTxtNome);
        EditText txtCliente = (EditText)findViewById(R.id.negocioTxtCliente);
        if(FuncoesExternas.Valida(txtNome))
            if(FuncoesExternas.Valida(txtCliente)){
                Toast t = Toast.makeText(this,"SALVARIA",Toast.LENGTH_SHORT);
                t.show();
            }

    }

    private class SelecionaDateListener implements DatePickerDialog.OnDateSetListener{
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            String dt =  DateUtil.dateToString(year,monthOfYear,dayOfMonth);
            txtDate.setText(dt);
            // negocio.setNeg_dprevisao(date); //passo a data direto pro objeto
        }
    }

    //region /Get e Set Valores
    private void SetNegocio(Negocio negocio){
        EditText txtNome = (EditText)findViewById(R.id.negocioTxtNome);
        EditText txtCliente = (EditText)findViewById(R.id.negocioTxtCliente);
        EditText txtEndereço = (EditText)findViewById(R.id.negocioTxtClienteEndereco);
        EditText txtDataPrevisao = (EditText)findViewById(R.id.negocioTxtDataPrevisao);
        EditText txtdescricao = (EditText)findViewById(R.id.negocioTxtDescricao);

        txtNome.setText(negocio.getNeg_vnome());
        txtCliente.setText(negocio.getNeg_vcliente());
        txtEndereço.setText(negocio.getNeg_vendereco());
        txtDataPrevisao.setText(DateUtil.dateToString(negocio.getNeg_dprevisao()));
        txtdescricao.setText(negocio.getNeg_vdescricao());
    }

    private void GetNegocio(){
        EditText txtNome = (EditText)findViewById(R.id.negocioTxtNome);
        EditText txtCliente = (EditText)findViewById(R.id.negocioTxtCliente);
        EditText txtEndereço = (EditText)findViewById(R.id.negocioTxtClienteEndereco);
        // EditText txtDataPrevisao = (EditText)findViewById(R.id.negocioTxtDataPrevisao);
        EditText txtdescricao = (EditText)findViewById(R.id.negocioTxtDescricao);

        negocio.setNeg_vnome(txtNome.getText().toString());
        negocio.setNeg_vcliente(txtCliente.getText().toString());
        negocio.setNeg_vdescricao(txtdescricao.getText().toString());
        negocio.setNeg_vendereco(txtEndereço.getText().toString());
        //   negocio.setNeg_dprevisao(DateUtil.dateToString());
    }
    //endregion


    //region salvar Assincrono
    private class Salvar extends AsyncTask<Negocio, String, Boolean>{
        @Override
        protected Boolean doInBackground(Negocio... params) {
            return DAO.Salvar(negocio);
        }

        @Override
        protected void onPostExecute(Boolean salvo) {
            super.onPostExecute(salvo);
            if(salvo) {
                finish();
                NegocioActivity.msn = negocio.getNeg_codigo()!=0?"Registro editado com Sucesso!":"Registro inserido com Sucesso!";
            }else
                Dialog.ShowAlert(NegocioActivityForm.this,"Erro","Ops, houve um imprevisto, favor tente novamente!");
        }
    }
//endregion

}


package com.example.interdisciplinar.mobile;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

import model.Negocio;
import util.DateUtil;

public class NegocioActivityForm extends AppCompatActivity {

    private EditText txtDate;
    private Negocio negocio = new Negocio();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_negocio_form);

        txtDate =(EditText) findViewById(R.id.negocioTxtDataPrevisao);
        txtDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus)
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

    public void CallNegocioIndex(View view){


        Intent intent = new Intent(this, NegocioActivity.class);
        startActivity(intent);
    }

    private void ShowCalendar(){
        Calendar cal  = Calendar.getInstance();
        DatePickerDialog dpk = new DatePickerDialog(this, new SelecionaDateListener(), cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
        dpk.show();
    }

    public void Cancelar(View view) {
        finish();
    }


    private class SelecionaDateListener implements DatePickerDialog.OnDateSetListener{
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            Calendar cal  = Calendar.getInstance();
            cal.set(year, monthOfYear, dayOfMonth);
            Date date = cal.getTime();
            DateFormat format = DateFormat.getDateInstance(DateFormat.SHORT);
            String dt = format.format(date);

            txtDate.setText(dt);
            negocio.setNeg_dprevisao(date); //passo a data direto pro objeto

        }
    }

    private void SetNegocio(Negocio negocio){
        TextView txtNome = (TextView)findViewById(R.id.negocioTxtNome);
        TextView txtCliente = (TextView)findViewById(R.id.negocioTxtCliente);
        TextView txtEndereço = (TextView)findViewById(R.id.negocioTxtClienteEndereco);
        TextView txtDataPrevisao = (TextView)findViewById(R.id.negocioTxtDataPrevisao);
        TextView txtdescricao = (TextView)findViewById(R.id.negocioTxtDescricao);

        txtNome.setText(negocio.getNeg_vnome());
        txtCliente.setText(negocio.getNeg_vcliente());
        txtEndereço.setText(negocio.getNeg_vendereco());
        txtDataPrevisao.setText(DateUtil.dateToString(negocio.getNeg_dprevisao()));
        txtdescricao.setText(negocio.getNeg_vdescricao());
    }

    private void GetNegocio(){
        TextView txtNome = (TextView)findViewById(R.id.negocioTxtNome);
        TextView txtCliente = (TextView)findViewById(R.id.negocioTxtCliente);
        TextView txtEndereço = (TextView)findViewById(R.id.negocioTxtClienteEndereco);
        // TextView txtDataPrevisao = (TextView)findViewById(R.id.negocioTxtDataPrevisao);
        TextView txtdescricao = (TextView)findViewById(R.id.negocioTxtDescricao);

        negocio.setNeg_vnome(txtNome.getText().toString());
        negocio.setNeg_vcliente(txtCliente.getText().toString());
        negocio.setNeg_vdescricao(txtdescricao.getText().toString());
        negocio.setNeg_vendereco(txtEndereço.getText().toString());
        //   negocio.setNeg_dprevisao(DateUtil.dateToString());
    }

}


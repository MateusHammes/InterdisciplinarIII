package com.example.mateus.ksoap3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static List<String> lsNamesGrupos= new ArrayList<String>();
    public static boolean keep;
    public  static String palavra;
    private Button btnCall;
    private TextView txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //btnCall.setOnClickListener(this);

        final String nameSpace = "http://Server/";
        final String metodoWeb = "palavra";
        final String soapAction ="http://Server/palavra";
        final String url = "http://192.168.0.106:8080/WebService/WService?wsdl";

        Thread networkThread = new Thread() {
            @Override
            public void run() {
            try {
                SoapObject request = new SoapObject(nameSpace, metodoWeb);
                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.setOutputSoapObject(request);

                HttpTransportSE ht = new HttpTransportSE(url);
                ht.call(soapAction, envelope);
                final SoapPrimitive response = (SoapPrimitive)envelope.getResponse();
                final String str = response.toString();

                runOnUiThread (new Runnable(){
                    public void run() {
                        TextView result;
                        result = (TextView)findViewById(R.id.textView);//Text view id is textView1
                        result.setText(str);
                    }
                });
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
       };
        networkThread.start();
    }

    @Override
    public void onClick(View v) {
   /*     Toast toast = Toast.makeText(this, "Chamando.....", Toast.LENGTH_SHORT);
        toast.show();
        // CallSoup call = new CallSoup();
        txt = (TextView) findViewById(R.id.textView);
        try
        {
            keep=true;
            Caller cal = new Caller();
            cal.join();
            cal.start();

            while (keep){
                try{
                    Thread.sleep(10);
                }catch (Exception e){

                }
            }
            // List<String> lsNomes = call.SelectGrupos();
       *//*     adapter = new CustomAd (getActivity(), R.layout.row, myStringArray1);
            myListView.setAdapter(adapter);*//*
            if(!lsNamesGrupos.isEmpty())
                txt.setText(lsNamesGrupos.get(0));
            else
                txt.setText("Tá nulll");
            //   lsvContatos.setAdapter();

        }catch(Exception ex) {
            txt.setText("Erro denovo!");
            Toast ttsa = Toast.makeText(this, "ERROR na cambanda, aprende a programa inutill...", Toast.LENGTH_SHORT);
            ttsa.show();
        }*/
    }
}

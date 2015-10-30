package com.example.interdisciplinar.mobile;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.net.URL;
import Enum.NegocioTipo;
public class MainActivity extends AppCompatActivity {

    private Button btnTeste;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      /*  btnTeste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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


    public void CallGrupo(View v) {
        Intent intent = new Intent(this, GrupoActivity.class);
        startActivity(intent);
    }
    public void CallNegocios(View view){
        Intent intent = new Intent(this,NegocioActivity.class);
        intent.putExtra("TIPO", NegocioTipo.Negocio);
        startActivity(intent);
    }

    public void CallOrcamentos(View view){
        Intent intent = new Intent(this,NegocioActivity.class);
        intent.putExtra("TIPO", NegocioTipo.Orcamento);
        startActivity(intent);
    }

      public void testaWeb(View view){

        Log.i("MOBILE","Entrou testaWeb");


        URL url = null;
        try {
            //url = new URL("http://192.168.0.102:8080/WebServiceREST/webresources/grupos");
            String sUrl = "http://192.168.0.102:8080/WebServiceREST/webresources/grupos";
             /*   URLConnection con = url.openConnection();
                con.setDoOutput(true);*/
            /*    OutputStreamWriter osw = new OutputStreamWriter(con.getOutputStream());
                osw.write("");
                osw.flush();
                BufferedReader br =new BufferedReader(new InputStreamReader(con.getInputStream()));
                String linha;*/

            RestTemplate rest = new RestTemplate();
            rest.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

            Log.i("____----_ReSuLT__--", "FOI E AGORA???");

            String result = rest.getForObject(sUrl,String.class);

            Log.i("RESULT", result);

            Toast ts = Toast.makeText(null, result, Toast.LENGTH_SHORT);
            ts.show();

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("----ERROOUU__-----",e.toString());
        }
    }


    private class GetString extends AsyncTask<String, String, String>{
        @Override
        protected String doInBackground(String... params) {
            try {
                String sUrl = "http://192.168.0.102:8080/WebServiceREST/webresources/grupos";
                RestTemplate rest = new RestTemplate();
                rest.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

                Log.i("____----_ReSuLT__--", "FOI E AGORA???");

                String result = rest.getForObject(sUrl, String.class);

                Log.i("RESULT", result);

                Toast ts = Toast.makeText(null, result, Toast.LENGTH_SHORT);
                ts.show();
                return result;
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("----ERROOUU__-----",e.toString());
            }
            return "ERROEOO";
        }
    }

    private void MakeText(String txt){
        if(txt.isEmpty())
            txt="SEM TEXTO NENHUM!!";

        Toast tst = Toast.makeText(this, txt, Toast.LENGTH_SHORT);
        tst.show();
        Log.i("MOBILE", "foi o Toast");
    }

// region ASY
   /* private class Load extends AsyncTask<String, String, String>{

        ListView lsView = (ListView) findViewById(R.id.lsRegistros);

        List<String> lsVal = new ArrayList<>();

        final String nameSpace = "http://Server/";
        final String metodoWeb = "salvar";
        final String soapAction ="http://Server/salvar";
        final String url = "http://192.168.0.106:8080/WebService/WService?wsdl";

        @Override
        protected String doInBackground(String... params) {
            try{

                SoapObject request = new SoapObject(nameSpace, metodoWeb);

                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet = true;
                HttpTransportSE androidHttpTransport = new HttpTransportSE(url);
                androidHttpTransport.call(soapAction, envelope);
                final SoapPrimitive response = (SoapPrimitive)envelope.getResponse();
                final String str = response.toString();

             SoapObject  result  = (SoapObject)envelope.getResponse();

                if(result != null){
                    for (int i = 0; i < result.getPropertyCount(); i++) {
                  //      lsVal.add(new Materias((SoapObject)result.getProperty(i)));
                    }
                }



            } catch (Exception e){
                e.printStackTrace();
            }

        }
    }
*/
    //endregion

    public void CallDetalheProduto(View view){
        Intent i = new Intent(this, ProdutoActivityDetalhes.class);
        startActivity(i);
    }

}

package DAO;

import android.util.Log;

import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import model.Grupo;
import util.Connection;

public class GrupoDAO {

    public List<Grupo> SelecionaGrupo(){
        ///"http://192.168.0.102:8080/WebServiceREST/
        String url = Connection.url.concat("grupos");

        RestTemplate rest = new RestTemplate();
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        messageConverters.add(new MappingJackson2HttpMessageConverter());
        // Add the message converters to the restTemplate
        rest.setMessageConverters(messageConverters);

        rest.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        Grupo[] arrayGrupo  = rest.getForObject(url, Grupo[].class); //(url,Grupo.class,));
        return Arrays.asList(arrayGrupo);
    }

    public boolean Salvar(Grupo grupo){
        //try {
        Log.i("Carrega", "Chegou aki!");
        String url = Connection.url.concat("grupos/salvaGrupo"); ////"http://192.168.0.102:8080/WebServiceREST/service/grupos";
        Log.i("Carrega", "Chegou pra salva");
        RestTemplate rest = new RestTemplate();
        //List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
        rest.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        rest.getMessageConverters().add(new StringHttpMessageConverter());
        Log.i("Carrega", "MAndou salva o..");

       return "1".equals(rest.postForObject(url, grupo, String.class));
    }

    public boolean Deletar(Grupo grupo){
        String url = Connection.url.concat("grupos/deleteGrupo");
        RestTemplate rest = new RestTemplate();
        rest.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        rest.getMessageConverters().add(new StringHttpMessageConverter());
        return rest.postForObject(url, grupo, String.class).equals("1");
    }

//region outro tipo
/*    public void invokeWS(RequestParams params){
        // Show Progress Dialog
      //  prgDialog.show();
        // Make RESTful webservice call using AsyncHttpClient object
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://192.168.2.2:9999/useraccount/register/doregister",params ,new AsyncHttpResponseHandler() {
            // When the response returned by REST has Http response code '200'
            @Override
            public void onSuccess(String response) {
                // Hide Progress Dialog
                prgDialog.hide();
                try {
                    // JSON Object
                    JSONObject obj = new JSONObject(response);
                    // When the JSON response has status boolean value assigned with true
                    if(obj.getBoolean("status")){
                        // Set Default Values for Edit View controls
                        setDefaultValues();
                        // Display successfully registered message using Toast
                        Toast.makeText(getApplicationContext(), "You are successfully registered!", Toast.LENGTH_LONG).show();
                    }
                    // Else display error message
                    else{
                        errorMsg.setText(obj.getString("error_msg"));
                        Toast.makeText(getApplicationContext(), obj.getString("error_msg"), Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    Toast.makeText(getApplicationContext(), "Error Occured [Server's JSON response might be invalid]!", Toast.LENGTH_LONG).show();
                    e.printStackTrace();

                }
            }
            // When the response returned by REST has Http response code other than '200'
            @Override
            public void onFailure(int statusCode, Throwable error,
                                  String content) {
                // Hide Progress Dialog
                prgDialog.hide();
                // When Http response code is '404'
                if(statusCode == 404){
                    Toast.makeText(getApplicationContext(), "Requested resource not found", Toast.LENGTH_LONG).show();
                }
                // When Http response code is '500'
                else if(statusCode == 500){
                    Toast.makeText(getApplicationContext(), "Something went wrong at server end", Toast.LENGTH_LONG).show();
                }
                // When Http response code other than 404, 500
                else{
                    Toast.makeText(getApplicationContext(), "Unexpected Error occcured! [Most common Error: Device might not be connected to Internet or remote server is not up and running]", Toast.LENGTH_LONG).show();
                }
            }
        });
    }*/
//endregion
}

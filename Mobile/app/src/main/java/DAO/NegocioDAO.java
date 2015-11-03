package DAO;


import android.util.Log;

import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import model.Negocio;
import util.Connection;

public class NegocioDAO {

    public List<Negocio>SelecionaNegocios(int tipo, int page){
        try{
            String url = Connection.url.concat("negocios/tipo/"+tipo+"/"+page);
            RestTemplate rest = new RestTemplate();
            List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
            messageConverters.add(new MappingJackson2HttpMessageConverter());
            // Add the message converters to the restTemplate
            /*  Map<String, String> params = new HashMap<>();
            params.put("tipo", String.valueOf(tipo));*/

            Log.i("---BUSCO--","TIPO - "+tipo);


            rest.setMessageConverters(messageConverters);
            rest.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

            return Arrays.asList(rest.getForObject(url, Negocio[].class));
            //  return Arrays.asList(rest.getForObject(url, Negocio[].class));
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public Negocio SelecionaNegocio (int neg_id){
        try{
            String url = Connection.url.concat("negocios/"+neg_id);
            RestTemplate rest = new RestTemplate();
            List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
            messageConverters.add(new MappingJackson2HttpMessageConverter());
            Log.i("---BUSCO--","TIPO - "+neg_id);
            rest.setMessageConverters(messageConverters);
            rest.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

            return rest.getForObject(url, Negocio.class);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }



    public String Salvar(Negocio item){
        String url = Connection.url.concat("negocios/salva"); ////"http://192.168.0.102:8080/WebServiceREST/service/grupos";

        RestTemplate rest = new RestTemplate();
        rest.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        rest.getMessageConverters().add(new StringHttpMessageConverter());
        rest.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        Log.i("VAI??", "So falta agora! ->"+item.getNeg_ctipo());
        return rest.postForObject(url, item, String.class);
    }

    public boolean Deletar(Negocio item){
        String url = Connection.url.concat("negocios/delete");
        RestTemplate rest = new RestTemplate();
        rest.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        rest.getMessageConverters().add(new StringHttpMessageConverter());
        return rest.postForObject(url, item, String.class).equals("1");
    }


}

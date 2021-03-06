package DAO;

import android.util.Log;

import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import model.Materiais;
import util.Connection;

public class MateriaisDAO {
    public List<Materiais> SelecionaMateriais(int page){
        try {
            String url = Connection.url.concat("materiais/range/"+page);
            RestTemplate rest = new RestTemplate();
            List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
            messageConverters.add(new MappingJackson2HttpMessageConverter());
            // Add the message converters to the restTemplate
            rest.setMessageConverters(messageConverters);
            rest.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            Materiais[] arrayMateriais = rest.getForObject(url, Materiais[].class); //(url,Materiais.class,));
            if(arrayMateriais!=null)
                return Arrays.asList(arrayMateriais);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public List<Materiais> SelecionaPesquisa(String name){
        try {
            String url = Connection.url.concat("materiais/search/"+name);
            RestTemplate rest = new RestTemplate();
            List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
            messageConverters.add(new MappingJackson2HttpMessageConverter());
            rest.setMessageConverters(messageConverters);
            rest.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            Materiais[] arrayMateriais = rest.getForObject(url, Materiais[].class); //(url,Materiais.class,));
            return Arrays.asList(arrayMateriais);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public boolean Salvar(Materiais Materiais){
        try {
            String url = Connection.url.concat("materiais/salva"); ////"http://192.168.0.102:8080/WebServiceREST/service/Materiais";

            RestTemplate rest = new RestTemplate();
            rest.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            rest.getMessageConverters().add(new StringHttpMessageConverter());
            return "1".equals(rest.postForObject(url, Materiais, String.class));
        }catch (Exception e){
            Log.e("ERRO",e.toString());
        }
        return false;
    }

    public boolean Deletar(Materiais Materiais){
        String url = Connection.url.concat("materiais/delete");
        RestTemplate rest = new RestTemplate();
        rest.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        rest.getMessageConverters().add(new StringHttpMessageConverter());
        return rest.postForObject(url, Materiais, String.class).equals("1");
    }

}

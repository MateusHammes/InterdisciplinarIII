package DAO;

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
        try {
            String url = Connection.url.concat("grupos");
            RestTemplate rest = new RestTemplate();
            List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
            messageConverters.add(new MappingJackson2HttpMessageConverter());
            // Add the message converters to the restTemplate
            rest.setMessageConverters(messageConverters);
            rest.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            Grupo[] arrayGrupo = rest.getForObject(url, Grupo[].class); //(url,Grupo.class,));
            return Arrays.asList(arrayGrupo);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public boolean Salvar(Grupo grupo){
        String url = Connection.url.concat("grupos/salvaGrupo"); ////"http://192.168.0.102:8080/WebServiceREST/service/grupos";
        RestTemplate rest = new RestTemplate();
        rest.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        rest.getMessageConverters().add(new StringHttpMessageConverter());
        return "1".equals(rest.postForObject(url, grupo, String.class));
    }

    public boolean Deletar(Grupo grupo){
        String url = Connection.url.concat("grupos/deleteGrupo");
        RestTemplate rest = new RestTemplate();
        rest.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        rest.getMessageConverters().add(new StringHttpMessageConverter());
        return rest.postForObject(url, grupo, String.class).equals("1");
    }

}

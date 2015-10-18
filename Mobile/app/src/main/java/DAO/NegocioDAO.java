package DAO;


import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import model.Negocio;

public class NegocioDAO {

    public List<Negocio>SelecionaNegocios(){
        try{
            String url = "http://192.168.0.102:44608/WebServiceREST/service/negocios";

            RestTemplate rest = new RestTemplate();
            List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
            messageConverters.add(new MappingJackson2HttpMessageConverter());
            // Add the message converters to the restTemplate
            rest.setMessageConverters(messageConverters);

            rest.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            Negocio[] arrayGrupo  = rest.getForObject(url, Negocio[].class);

            return  Arrays.asList(arrayGrupo);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }



}

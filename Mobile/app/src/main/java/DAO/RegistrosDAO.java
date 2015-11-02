package DAO;

import android.util.Log;

import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import model.Registros;
import util.Connection;

public class RegistrosDAO {

    public List<Registros> SelecionaRegistros( int neg_id, int idProduto){
        try {
            String url = Connection.url.concat("registros/"+neg_id+"/"+idProduto);

            RestTemplate rest = new RestTemplate();
            List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
            messageConverters.add(new MappingJackson2HttpMessageConverter());
            rest.setMessageConverters(messageConverters);
            rest.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

            Registros[] arrayRegistros = rest.getForObject(url, Registros[].class); //(url,Registros.class,));
            return Arrays.asList(arrayRegistros);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


    public Boolean Salvar(Registros Registros){
        try {
            String url = Connection.url.concat("registros/salva"); ////"http://192.168.0.102:8080/WebServiceREST/service/Registross";
            RestTemplate rest = new RestTemplate();
            rest.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            rest.getMessageConverters().add(new StringHttpMessageConverter());
            return rest.postForObject(url, Registros, String.class).equals("1");
        }catch (Exception e){
            Log.e("ERRO",e.toString());
        }
        return false;
    }

    public boolean Deletar(Registros Registros){
        String url = Connection.url.concat("registros/delete");
        RestTemplate rest = new RestTemplate();
        rest.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        rest.getMessageConverters().add(new StringHttpMessageConverter());
        return rest.postForObject(url, Registros, String.class).equals("1");
    }


}

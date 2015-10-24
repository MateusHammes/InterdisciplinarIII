package DAO;

import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import model.Produto;
import util.Connection;

public class ProdutoDAO {

    public List<Produto> SelecionaProduto(){
        try {
            String url = Connection.url.concat("Produtos");
            RestTemplate rest = new RestTemplate();
            List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
            messageConverters.add(new MappingJackson2HttpMessageConverter());
            // Add the message converters to the restTemplate
            rest.setMessageConverters(messageConverters);
            rest.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            Produto[] arrayProduto = rest.getForObject(url, Produto[].class); //(url,Produto.class,));
            return Arrays.asList(arrayProduto);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public boolean Salvar(Produto Produto){
        String url = Connection.url.concat("Produtos/salvaProduto"); ////"http://192.168.0.102:8080/WebServiceREST/service/Produtos";
        RestTemplate rest = new RestTemplate();
        rest.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        rest.getMessageConverters().add(new StringHttpMessageConverter());
        return "1".equals(rest.postForObject(url, Produto, String.class));
    }

    public boolean Deletar(Produto Produto){
        String url = Connection.url.concat("Produtos/deleteProduto");
        RestTemplate rest = new RestTemplate();
        rest.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        rest.getMessageConverters().add(new StringHttpMessageConverter());
        return rest.postForObject(url, Produto, String.class).equals("1");
    }

}

package DAO;

import android.util.Log;

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

    public List<Produto> SelecionaProduto(int neg_id){
        try {
            String url = Connection.url.concat("produtos/"+neg_id);
            RestTemplate rest = new RestTemplate();
            List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
            messageConverters.add(new MappingJackson2HttpMessageConverter());
            rest.setMessageConverters(messageConverters);
            rest.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            Produto[] arrayProduto = rest.getForObject(url, Produto[].class); //(url,Produto.class,));
            return Arrays.asList(arrayProduto);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public List<Produto> SelecionaPesquisa(int neg_id, String name){
        try {
            String url = Connection.url.concat("produtos/search/"+neg_id+"/"+name);
            RestTemplate rest = new RestTemplate();
            List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
            messageConverters.add(new MappingJackson2HttpMessageConverter());
            rest.setMessageConverters(messageConverters);
            rest.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            Produto[] arrayProduto = rest.getForObject(url, Produto[].class); //(url,Produto.class,));
            return Arrays.asList(arrayProduto);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public String Salvar(Produto Produto){
        try {
            Log.i("veio Salva", "Na DAO");
            String url = Connection.url.concat("produtos/salva"); ////"http://192.168.0.102:8080/WebServiceREST/service/Produtos";
            RestTemplate rest = new RestTemplate();

            rest.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            rest.getMessageConverters().add(new StringHttpMessageConverter());
            return rest.postForObject(url, Produto, String.class);
        }catch (Exception e){
            Log.e("ERRORORO", e.toString());
            return "0";
        }
    }

    public boolean Deletar(Produto Produto){
        String url = Connection.url.concat("produtos/delete");
        RestTemplate rest = new RestTemplate();
        rest.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        rest.getMessageConverters().add(new StringHttpMessageConverter());
        return rest.postForObject(url, Produto, String.class).equals("1");
    }

    public String SelecionaValorTotal (int neg_id){
        try{
            String url = Connection.url.concat("produtos/total/"+neg_id);
            RestTemplate rest = new RestTemplate();
            List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
            messageConverters.add(new MappingJackson2HttpMessageConverter());
            Log.i("---BUSCO--","TIPO Neg - "+neg_id);
            rest.setMessageConverters(messageConverters);
            rest.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

            return rest.getForObject(url, Double.class).toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}

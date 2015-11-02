package DAO;

import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import model.Produto_material;
import util.Connection;


public class ProdutoMaterialDAO {

    public boolean Salvar(Produto_material Pmaterial){
        String url = Connection.url.concat("produtoMaterial/salva");
        RestTemplate rest = new RestTemplate();
        rest.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        rest.getMessageConverters().add(new StringHttpMessageConverter());
        return "1".equals(rest.postForObject(url, Pmaterial, String.class));
    }

    public boolean Editar(Produto_material Pmaterial){
        String url = Connection.url.concat("produtoMaterial/edita");
        RestTemplate rest = new RestTemplate();
        rest.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        rest.getMessageConverters().add(new StringHttpMessageConverter());
        return "1".equals(rest.postForObject(url, Pmaterial, String.class));
    }

    public List<Produto_material> Seleciona(int neg_codigo,  int pro_codigo){
        try {
            String url = Connection.url.concat("produtoMaterial/"+neg_codigo+"/"+pro_codigo);
            RestTemplate rest = new RestTemplate();
            List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
            messageConverters.add(new MappingJackson2HttpMessageConverter());
            rest.setMessageConverters(messageConverters);
            rest.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            Produto_material[] arrayMateriais = rest.getForObject(url, Produto_material[].class); //(url,Materiais.class,));
            return Arrays.asList(arrayMateriais);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


}

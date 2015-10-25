package DAO;

import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import model.Produto_material;
import util.Connection;


public class ProdutoMaterialDAO {

    public boolean Salvar(Produto_material Pmaterial){
        String url = Connection.url.concat("produtoMaterial/edita"); ////"http://192.168.0.102:8080/WebServiceREST/service/grupos";
        RestTemplate rest = new RestTemplate();
        rest.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        rest.getMessageConverters().add(new StringHttpMessageConverter());
        return "1".equals(rest.postForObject(url, Pmaterial, String.class));
    }

}

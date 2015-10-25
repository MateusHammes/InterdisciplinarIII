/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package recursos;

import dao.ProdutoMaterialDAO;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import model.Produto;
import model.ProdutoMaterial;

@Path("/produtoMaterial")
public class ProdutoMaterialResource {

    ProdutoMaterialDAO DAO = new ProdutoMaterialDAO();
    
    @POST
    @Path("edita")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public String Update(ProdutoMaterial p) {
        try {
           DAO.update(p);
            return "1";

        } catch (Exception e) {
            return "0";
        }
    }

}

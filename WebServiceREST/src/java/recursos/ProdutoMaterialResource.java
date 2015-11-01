/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package recursos;

import dao.MateriaisDAO;
import dao.ProdutoMaterialDAO;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import model.Materiais;
import model.ProdutoMaterial;

@Path("/produtoMaterial")
public class ProdutoMaterialResource {

    ProdutoMaterialDAO prodMterialDAO = new ProdutoMaterialDAO();
    Materiais materiais = new Materiais();
    MateriaisDAO materiaisDAO = new MateriaisDAO();

    @POST
    @Path("salva")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public String Update(ProdutoMaterial p) {
        try {

            if (p != null) {

                materiais = materiaisDAO.findById(p.getMaterial().getMtr_codigo());
                int autalizaEstoque = materiais.getMtr_iestoque() - p.getMaterial().getMtr_iestoque();
                materiais.setMtr_iestoque(autalizaEstoque);
                materiaisDAO.update(materiais);

                prodMterialDAO.update(p);

                return "1";
            } else {
                return "0";
            }

        } catch (Exception e) {
            return "0";
        }
    }

    @POST
    @Path("edita")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public String insert(ProdutoMaterial p) {
        try {
            prodMterialDAO.update(p);
            return "1";

        } catch (Exception e) {
            return "0";
        }
    }

    
    @GET
    @Path("{id}")///id do Produto
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public ProdutoMaterial findAll(@PathParam("id") Integer id) {
        return prodMterialDAO.findAll(id);

    }

}

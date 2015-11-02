package recursos;

import dao.ProdutoDAO;
import java.util.List;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import model.Produto;

@Path("/produtos")
public class produtosResource {

    ProdutoDAO produtoDAO = new ProdutoDAO();

    public produtosResource() {
    }

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("{id}")//-> id do negocio
    public List<Produto> findAll(@PathParam("id") Integer id) {
        return produtoDAO.findAll(id);
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Path("{id}")
    public Produto findById(@PathParam("id") Integer id) {

        return produtoDAO.findById(id);
    }

    @POST
    @Path("salvaProduto")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public String insert(Produto p) {

        try {

            if (p.getPro_codigo() == 0) {
                produtoDAO.insert(p);
            } else {
                produtoDAO.update(p);
            }
            return "" + p.getPro_codigo();
        } catch (Exception e) {
            return "0";
        }
    }

    @POST
    @Path("deleteProduto")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public String delete(Produto p) {
        try {

            ProdutoMaterialResource PMR = new ProdutoMaterialResource();

            PMR.DevolveMateriais(p.getPro_codigo(), p.getNegocio().getNeg_codigo());

            produtoDAO.delete(p);
            return "1";
        } catch (Exception e) {
            return "0";
        }
    }

}

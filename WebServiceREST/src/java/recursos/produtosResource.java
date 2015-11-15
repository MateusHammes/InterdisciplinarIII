package recursos;

import dao.ProdutoDAO;
import dao.ProdutoMaterialDAO;
import java.util.List;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import model.Produto;
import model.ProdutoMaterial;

@Path("/produtos")
public class produtosResource {

    ProdutoDAO produtoDAO = new ProdutoDAO();
    ProdutoMaterialDAO produtoMaterialDAO = new ProdutoMaterialDAO();

    public produtosResource() {
    }

    @GET
    @Path("{id}")//-> id do negocio
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Produto> findAll(@PathParam("id") Integer id) {
        List<Produto> lsProduto = (List<Produto>) produtoDAO.findAll(id);
        return lsProduto;
    }
//
//    @GET
//    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
//    @Path("{id}")
//    public Produto findById(@PathParam("id") Integer id) {
//
//        return produtoDAO.findById(id);
//    }

    @POST
    @Path("salva")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public String insert(Produto p) {
        System.out.println("Produto: " + p.getPro_codigo());
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
    @Path("delete")
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

    @GET
    @Path("total/{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public String valorTotal(@PathParam("id") Integer id) {

        double valor = 0;
        double total = 0;
        String produto = "";
        if (id > 0) {
            List<Produto> lp = produtoDAO.findAll(id);

           
            if (lp != null && lp.size() != 0) {
                for (Produto p : lp) {
                    produto += p.getPro_codigo() + ",";
                }

                produto = produto.substring(0, produto.length() - 1);
                List<ProdutoMaterial> pm = produtoMaterialDAO.findByProdutos(produto);

                for (ProdutoMaterial l : pm) {
                    if (l.getPrm_iunidade() < l.getPrm_iunidadeUtilizada()) {
                        valor = l.getPrm_iunidadeUtilizada() * l.getPrm_nvalor();
                    } else {
                        valor = l.getPrm_iunidade() * l.getPrm_nvalor();
                    }
                    total += valor;
                }
            }
        }
        return "" + Math.pow(total, 1);
    }
}

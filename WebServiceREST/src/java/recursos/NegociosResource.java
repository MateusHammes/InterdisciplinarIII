
package recursos;

import dao.NegocioDAO;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import model.Negocio;


@Path("negocios")
public class NegociosResource {

    NegocioDAO negocioDAO = new NegocioDAO();
    @Context
    private UriInfo context;

  
    public NegociosResource() {
    }

     @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Negocio> findAll() {
        return negocioDAO.findAll();
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Path("{id}")
    public Negocio findById(@PathParam("id") Integer id) {

        return negocioDAO.findById(id);
    }

    @POST
    @Path("salvaNegocio")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public String insert(Negocio n) {

        try {

            if (true) {
                negocioDAO.insert(n);
            } else {
                negocioDAO.update(n);
            }
            return "1";

        } catch (Exception e) {
            return "0";
        }
    }

    @POST
    @Path("deleteNegocio")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public String delete(Negocio n) {
        try {
            negocioDAO.delete(n);
            return "1";
        } catch (Exception e) {
            return "0";
        }
    }
 
}

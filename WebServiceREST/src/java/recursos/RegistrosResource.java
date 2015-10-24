
package recursos;

import dao.RegistrosDAO;
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
import model.Registros;


@Path("/registros")
public class RegistrosResource {

    RegistrosDAO registrosDAO = new RegistrosDAO();


 
    public RegistrosResource() {
    }

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Registros> findAll() {
        return registrosDAO.findAll();
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Path("{id}")
    public Registros findById(@PathParam("id") Integer id) {

        return registrosDAO.findById(id);
    }

    @POST
    @Path("salvaRegistros")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public String insert(Registros r) {

        try {

            if (r.getRgs_codigo() == 0) {
                registrosDAO.insert(r);
            } else {
                registrosDAO.update(r);
            }
            return "1";

        } catch (Exception e) {
            return "0";
        }
    }

    @POST
    @Path("deleteRegistros")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public String delete(Registros r) {
        try {
            registrosDAO.delete(r);
            return "1";
        } catch (Exception e) {
            return "0";
        }
    }
 
}

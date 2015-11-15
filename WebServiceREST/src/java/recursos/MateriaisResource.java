package recursos;

import dao.MateriaisDAO;
import java.util.List;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import model.Materiais;

@Path("/materiais")
public class MateriaisResource {

    MateriaisDAO materiaisDAO = new MateriaisDAO();

    public MateriaisResource() {
    }

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Materiais> findAll() {
        return materiaisDAO.findAll();
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Path("{id}")
    public Materiais findById(@PathParam("id") Integer id) {

        return materiaisDAO.findById(id);
    }
  

    @POST
    @Path("salva")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public String insert(Materiais m) {

        try {

            if (m.getMtr_codigo() == 0) {
                materiaisDAO.insert(m);
            } else {
                materiaisDAO.update(m);
            }
            return "1";

        } catch (Exception e) {
            return "0";
        }
    }

    @POST
    @Path("delete")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public String delete(Materiais m) {
        try {
            materiaisDAO.delete(m);
            return "1";
        } catch (Exception e) {
            return "0";
        }
    }
    
    
   
    
}

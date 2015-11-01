package recursos;

import dao.GrupoDAO;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import model.Grupo;

@Path("/grupos")
public class GrupoRecurso {

    GrupoDAO grupoDAO = new GrupoDAO();
    @Context
    private UriInfo context;

    public GrupoRecurso() {
    }

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Grupo> findAll() {
        return grupoDAO.findAll();
    }

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("range/{id}")
    public List<Grupo> findRange(@PathParam("id") Integer idRange) {
        return grupoDAO.findRange(idRange);
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Path("{id}")
    public Grupo findById(@PathParam("id") Integer id) {

        return grupoDAO.findById(id);
    }

    @POST
    @Path("salvaGrupo")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public String insert(Grupo g) {
        try {
            if (g.getGru_codigo() == 0) {
                grupoDAO.insert(g);
            } else {
                grupoDAO.update(g);
            }
            return "1";

        } catch (Exception e) {
            return "0";
        }
    }

    @POST
    @Path("deleteGrupo")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public String delete(Grupo g) {
        try {
            grupoDAO.delete(g);
            return "1";
        } catch (Exception e) {
            return "0";
        }
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package recursos;

import dao.PessoaDAO;
import java.util.List;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import model.Pessoa;

@Path("/pessoa")
public class PessoaResource {

    PessoaDAO pessoaDAO = new PessoaDAO();
 

    public PessoaResource() {
    }

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Pessoa> findAll() {
        return pessoaDAO.findAll();
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Path("{id}")
    public Pessoa findById(@PathParam("id") Integer id) {

        return pessoaDAO.findById(id);
    }

    @POST
    @Path("salvaPessoa")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public String insert(Pessoa p) {

        try {

            if (p.getPes_codigo() == 0) {
                pessoaDAO.insert(p);
            } else {
                pessoaDAO.update(p);
            }
            return "1";

        } catch (Exception e) {
            return "0";
        }
    }

    @POST
    @Path("deletePessoa")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public String delete(Pessoa p) {
        try {
            pessoaDAO.delete(p);
            return "1";
        } catch (Exception e) {
            return "0";
        }
    }
}

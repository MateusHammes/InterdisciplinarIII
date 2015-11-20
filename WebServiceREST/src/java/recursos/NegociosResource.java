package recursos;

import Enum.NegocioStatus;
import Enum.NegocioTipo;
import dao.NegocioDAO;
import dao.ProdutoDAO;
import dao.ProdutoMaterialDAO;
import dao.RegistrosDAO;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import model.Negocio;
import model.Produto;
import model.ProdutoMaterial;
import model.Registros;

@Path("/negocios")
public class NegociosResource {

    NegocioDAO negocioDAO = new NegocioDAO();

    public NegociosResource() {
    }

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("tipo/{id}")
    public List<Negocio> findAll(@PathParam("id") String tipo) {
        return negocioDAO.findAll(tipo);
    }

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("tipo/{tipo}/{idRange}")
    public List<Negocio> findRange(@PathParam("tipo") Integer tipo, @PathParam("idRange") Integer idRange) {
        return negocioDAO.findRange(tipo, idRange);
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Path("{id}")
    public Negocio findById(@PathParam("id") Integer id) {

        return negocioDAO.findById(id);
    }

    @POST
    @Path("salva")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public String insert(Negocio n) {

        try {

            if (n.getNeg_codigo() == 0) {
                negocioDAO.insert(n);
            } else {
                negocioDAO.update(n);
            }
            return n.getNeg_codigo() + "";///é necessario o Id pois ele vai pros detahes

        } catch (Exception e) {
            return "0";
        }
    }

    @POST
    @Path("delete")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public String delete(Negocio n) {
        try {
            ProdutoMaterialResource PRM = new ProdutoMaterialResource();
            PRM.DevolveMateriais(0, n.getNeg_codigo());
            negocioDAO.delete(n);
            return "1";
        } catch (Exception e) {
            return "0";
        }
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Path("criaNegocio/{id}")
    public String criaNegocio(@PathParam("id") Integer id) {
        try {
            Negocio orcamento = negocioDAO.findById(id);
            Negocio neg = new Negocio(); //orcamento;
            neg.setNeg_cstatus(NegocioStatus.Aberto);
            neg.setNeg_ctipo(NegocioTipo.Negocio);
            Calendar c = Calendar.getInstance();
            neg.setNeg_dcadastro(c.getTime());
            neg.setNeg_parent(orcamento);
            neg.setNeg_vcliente(orcamento.getNeg_vcliente());
            neg.setNeg_vdescricao(orcamento.getNeg_vdescricao());
            neg.setNeg_vendereco(orcamento.getNeg_vendereco());
            neg.setNeg_vnome(orcamento.getNeg_vnome());
            neg.setNeg_codigo(0);

            ProdutoDAO pDAO = new ProdutoDAO();
            ///pega todos os produtos do negocio
            List<Produto> lsProdutos = pDAO.findAll(neg.getNeg_codigo());

            negocioDAO.insert(neg);
            orcamento.setNeg_cstatus(NegocioStatus.Concluido);
            negocioDAO.update(orcamento);

            /// List<Produto> lsProdutosNew = new ArrayList<>();
            for (Produto p : lsProdutos) {
                CreateProduto(p, neg);
                ///lsProdutosNew.add(p);
            }
            ///seta a lista de produtos no negocio

            return neg.getNeg_codigo() + "";

        } catch (Exception e) {
        }
        return "0";
    }

    private Produto CreateProduto(Produto p, Negocio neg) {
        Produto pNew = new Produto();
        pNew.setPro_vnome(p.getPro_vnome());
        pNew.setPro_ctipo(p.getPro_ctipo());
        pNew.setPro_vdescricao(p.getPro_vdescricao());
        pNew.setNegocio(neg);

        RegistrosDAO rDAO = new RegistrosDAO();
        List<Registros> lsRegistros = rDAO.findByIdProd(p.getPro_codigo());
        List<Registros> lsRegistrosNew = new ArrayList<>();
        for (Registros reg : lsRegistros) {
            Registros rg = new Registros();
            rg.setRgs_cstatus(reg.getRgs_cstatus());
            rg.setRgs_vdescricao(reg.getRgs_vdescricao());
            rg.setProduto(pNew);
            rDAO.insert(rg);
            //lsRegistrosNew.add(rg);
        }
        ProdutoMaterialDAO pmDAO = new ProdutoMaterialDAO();
        List<ProdutoMaterial> lsPM = pmDAO.findAll(p.getPro_codigo(), 0);
        List<ProdutoMaterial> lsPmNew = new ArrayList<>();

        for (ProdutoMaterial pm : lsPM) {
            ProdutoMaterial pmNew = new ProdutoMaterial();
            pmNew.setMaterial(pm.getMaterial());
            pmNew.setPrm_iunidade(pm.getPrm_iunidade());
            pmNew.setPrm_iunidadeUtilizada(pm.getPrm_iunidadeUtilizada());
            pmNew.setPrm_nvalor(pm.getPrm_nvalor());
            pmNew.setProduto(pNew);
            pmDAO.insert(pmNew);
            /// lsPmNew.add(pmNew);
        }

        return pNew;
    }

}

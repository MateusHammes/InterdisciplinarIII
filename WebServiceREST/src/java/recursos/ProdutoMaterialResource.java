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
            System.out.println("Vai salvar o ProdutoMAterial---");
            if (p != null) {
                materiais = materiaisDAO.findById(p.getMaterial().getMtr_codigo());
                int autalizaEstoque = materiais.getMtr_iestoque() - p.getPrm_iunidade();
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
            
            ProdutoMaterial prm = prodMterialDAO.findById(p.getProduto().getPro_codigo(), p.getNegocio().getNeg_codigo(), p.getMaterial().getMtr_codigo());
            
            int unidadesUtilziadas = p.getPrm_iunidadeUtilizada();
            int totalResevado = prm.getPrm_iunidade();
            int totalUnidUtilizadas = p.getPrm_iunidadeUtilizada() + prm.getPrm_iunidadeUtilizada();
            p.setPrm_iunidadeUtilizada(totalUnidUtilizadas);
            
            int diferenca = 0;
            if (totalResevado <= prm.getPrm_iunidadeUtilizada()) { //caso já tenha utilzado mais material do que reservou, apenas dimnui do estoque este valor
                diferenca = unidadesUtilziadas;
            } else if (totalResevado < totalUnidUtilizadas) { ///caso o total utilzado seja maior q o reservado, diminui do estoque
                diferenca = totalUnidUtilizadas - totalResevado;
            }
            
            if (diferenca > 0) {
                materiais = materiaisDAO.findById(p.getMaterial().getMtr_codigo());
                materiais.setMtr_iestoque(materiais.getMtr_iestoque() - diferenca);
                materiaisDAO.update(materiais);
            }
            
            prodMterialDAO.update(p);
            return "1";
            
        } catch (Exception e) {
            return "0";
        }
    }
    
    @GET
    @Path("{neg_id}/{pro_id}")///id do Produto
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<ProdutoMaterial> findAll(@PathParam("neg_id") Integer neg_id, @PathParam("pro_id") Integer pro_id) {
        return prodMterialDAO.findAll(neg_id, pro_id, 0);
    }
    
    public void DevolveMateriais(int pro_id, int neg_id) {
        List<ProdutoMaterial> lsPRM = prodMterialDAO.findAll(pro_id, neg_id, 0);
        if (lsPRM != null) {
            for (ProdutoMaterial prm : lsPRM) {
                if (prm.getPrm_iunidade() > prm.getPrm_iunidadeUtilizada()) {
                    Materiais mat = prm.getMaterial();
                    int resto = prm.getPrm_iunidade() - prm.getPrm_iunidadeUtilizada();
                    mat.setMtr_iestoque(mat.getMtr_iestoque() + resto);
                    materiaisDAO.update(mat);
                }
            }
        }
        
    }
    
}

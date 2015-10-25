package model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;



@Entity
@XmlRootElement
public class ProdutoMaterial implements Serializable {

    @Id
    @ManyToOne
    @JoinColumn(name = "mtr_codigo", referencedColumnName = "mtr_codigo")
    private Materiais material;
    @Id
    @ManyToOne
    @JoinColumn(name = "pro_codigo", referencedColumnName = "pro_codigo")
    private Produto produto;
    @Id
    @ManyToOne
    @JoinColumn(name = "neg_codigo", referencedColumnName = "neg_codigo")
    private Negocio negocio;
    
   private int prm_iunidade;
    private int prm_iunidadeUtilizada;
    private double prm_nvalor;

    public ProdutoMaterial() {
    }

    public Materiais getMaterial() {
        return material;
    }

    public void setMaterial(Materiais material) {
        this.material = material;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Negocio getNegocio() {
        return negocio;
    }

    public void setNegocio(Negocio negocio) {
        this.negocio = negocio;
    }

    public int getPrm_iunidade() {
        return prm_iunidade;
    }

    public void setPrm_iunidade(int prm_iunidade) {
        this.prm_iunidade = prm_iunidade;
    }

    public int getPrm_iunidadeUtilizada() {
        return prm_iunidadeUtilizada;
    }

    public void setPrm_iunidadeUtilizada(int prm_iunidadeUtilizada) {
        this.prm_iunidadeUtilizada = prm_iunidadeUtilizada;
    }

    public double getPrm_nvalor() {
        return prm_nvalor;
    }

    public void setPrm_nvalor(double prm_nvalor) {
        this.prm_nvalor = prm_nvalor;
    }

    
}

package model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
    private int iunidadeUtilizada;
    private int iunidade;
    private double nvalor;

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

    public int getIunidadeUtilizada() {
        return iunidadeUtilizada;
    }

    public void setIunidadeUtilizada(int iunidadeUtilizada) {
        this.iunidadeUtilizada = iunidadeUtilizada;
    }

    public int getIunidade() {
        return iunidade;
    }

    public void setIunidade(int iunidade) {
        this.iunidade = iunidade;
    }

    public double getNvalor() {
        return nvalor;
    }

    public void setNvalor(double nvalor) {
        this.nvalor = nvalor;
    }

}

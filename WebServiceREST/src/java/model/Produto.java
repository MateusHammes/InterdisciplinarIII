/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author mateus
 */
@Entity
@Table(name = "produto")
@XmlRootElement
public class Produto {

    @Id
    @SequenceGenerator(name = "pro_codigo", sequenceName = "seq_pro_codigo")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "pro_codigo")
    private int pro_codigo;
    private String pro_vnome;
    private char pro_ctipo;
    private String pro_vdescricao;

    @ManyToOne
    @JoinColumn(name = "neg_codigo", referencedColumnName = "neg_codigo")
    private Negocio negocio;
   
    @OneToMany(mappedBy = "produto", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<ProdutoMaterial> lsProdutoMaterial;
    
    @OneToMany(mappedBy = "produto", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Registros> lsRegistros;
    
    
    public Produto() {
    }

    public int getPro_codigo() {
        return pro_codigo;
    }

    public void setPro_codigo(int pro_codigo) {
        this.pro_codigo = pro_codigo;
    }

    public String getPro_vnome() {
        return pro_vnome;
    }

    public void setPro_vnome(String pro_vnome) {
        this.pro_vnome = pro_vnome;
    }

    public char getPro_ctipo() {
        return pro_ctipo;
    }

    public void setPro_ctipo(char pro_ctipo) {
        this.pro_ctipo = pro_ctipo;
    }

    public String getPro_vdescricao() {
        return pro_vdescricao;
    }

    public void setPro_vdescricao(String pro_vdescricao) {
        this.pro_vdescricao = pro_vdescricao;
    }

    public Negocio getNegocio() {
        return negocio;
    }

    public void setNegocio(Negocio negocio) {
        this.negocio = negocio;
    }

    @Override
    public String toString() {
        return getPro_vnome();
    }

    public List<ProdutoMaterial> getLsProdutoMaterial() {
        return lsProdutoMaterial;
    }

    public void setLsProdutoMaterial(List<ProdutoMaterial> lsProdutoMaterial) {
        this.lsProdutoMaterial = lsProdutoMaterial;
    }
    
    
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + this.pro_codigo;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Produto other = (Produto) obj;
        if (this.pro_codigo != other.pro_codigo) {
            return false;
        }
        return true;
    }

}

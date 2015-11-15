/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author mateus
 */
@Entity
@Table(name = "registros")
@XmlRootElement
public class Registros implements Serializable{

    @Id
    @SequenceGenerator(name = "rgs_codigo", sequenceName = "seq_rgs_codigo")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "rgs_codigo")
    private int rgs_codigo;
    private String rgs_vdescricao;
    private int rgs_cstatus;

    @ManyToOne
    @JoinColumn(name = "pro_codigo", referencedColumnName = "pro_codigo")
    private Produto produto;

//    @ManyToOne
//    @JoinColumn(name = "neg_codigo", referencedColumnName = "neg_codigo")
//    private Negocio negocio;

    public Registros() {
    }

    public int getRgs_codigo() {
        return rgs_codigo;
    }

    public void setRgs_codigo(int rgs_codigo) {
        this.rgs_codigo = rgs_codigo;
    }

    public String getRgs_vdescricao() {
        return rgs_vdescricao;
    }

    public void setRgs_vdescricao(String rgs_vdescricao) {
        this.rgs_vdescricao = rgs_vdescricao;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

//    public Negocio getNegocio() {
//        return negocio;
//    }
//
//    public void setNegocio(Negocio negocio) {
//        this.negocio = negocio;
//    }

    @Override
    public String toString() {
        return getRgs_vdescricao();
    }

    
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + this.rgs_codigo;
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
        final Registros other = (Registros) obj;
        if (this.rgs_codigo != other.rgs_codigo) {
            return false;
        }
        return true;
    }

    public int getRgs_cstatus() {
        return rgs_cstatus;
    }

    public void setRgs_cstatus(int rgs_cstatus) {
        this.rgs_cstatus = rgs_cstatus;
    }

}

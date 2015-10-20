/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author mateus
 */
@Entity
@XmlRootElement
public class Pessoa {
    @Id
    @SequenceGenerator(name = "pes_codigo", sequenceName = "seq_pes_codigo")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "pes_codigo")
    private int pes_codigo;
    private String pes_vnome;
    private String pes_vlogin;
    private String pes_vsenha;
    private String pes_vsenhahash;
    private Date pes_dcadastro;

    public Pessoa() {
    }

    public int getPes_codigo() {
        return pes_codigo;
    }

    public void setPes_codigo(int pes_codigo) {
        this.pes_codigo = pes_codigo;
    }

    public String getPes_vnome() {
        return pes_vnome;
    }

    public void setPes_vnome(String pes_vnome) {
        this.pes_vnome = pes_vnome;
    }

    public String getPes_vlogin() {
        return pes_vlogin;
    }

    public void setPes_vlogin(String pes_vlogin) {
        this.pes_vlogin = pes_vlogin;
    }

    public String getPes_vsenha() {
        return pes_vsenha;
    }

    public void setPes_vsenha(String pes_vsenha) {
        this.pes_vsenha = pes_vsenha;
    }

    public String getPes_vsenhahash() {
        return pes_vsenhahash;
    }

    public void setPes_vsenhahash(String pes_vsenhahash) {
        this.pes_vsenhahash = pes_vsenhahash;
    }

    public Date getPes_dcadastro() {
        return pes_dcadastro;
    }

    public void setPes_dcadastro(Date pes_dcadastro) {
        this.pes_dcadastro = pes_dcadastro;
    }

    @Override
    public String toString() {
        return getPes_vnome();
    }

    
    

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

/**
 *
 * @author mateus
 */
@Entity
public class Materiais {

    @Id
    @SequenceGenerator(name = "mtr_codigo", sequenceName = "seq_mtr_codigo")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "mtr_codigo")
    private int mtr_codigo;
    private String mtr_vnome;
    private double mtr_nvalor;
    private char mtr_cstatus;
    private String mtr_vdescricao;
    private int mtr_iestoque;

    @ManyToOne
    @JoinColumn(name = "gru_codigo", referencedColumnName = "gru_codigo")
    private Grupo grupo;

    public Materiais() {
    }

    public int getMtr_codigo() {
        return mtr_codigo;
    }

    public void setMtr_codigo(int mtr_codigo) {
        this.mtr_codigo = mtr_codigo;
    }

    public String getMtr_vnome() {
        return mtr_vnome;
    }

    public void setMtr_vnome(String mtr_vnome) {
        this.mtr_vnome = mtr_vnome;
    }

    public double getMtr_nvalor() {
        return mtr_nvalor;
    }

    public void setMtr_nvalor(double mtr_nvalor) {
        this.mtr_nvalor = mtr_nvalor;
    }

    public char getMtr_cstatus() {
        return mtr_cstatus;
    }

    public void setMtr_cstatus(char mtr_cstatus) {
        this.mtr_cstatus = mtr_cstatus;
    }

    public String getMtr_vdescricao() {
        return mtr_vdescricao;
    }

    public void setMtr_vdescricao(String mtr_vdescricao) {
        this.mtr_vdescricao = mtr_vdescricao;
    }

    public int getMtr_iestoque() {
        return mtr_iestoque;
    }

    public void setMtr_iestoque(int mtr_iestoque) {
        this.mtr_iestoque = mtr_iestoque;
    }

    public Grupo getGrupo() {
        return grupo;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    @Override
    public String toString() {
        return getMtr_vnome();
    }
}

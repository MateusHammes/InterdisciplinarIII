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
import javax.persistence.SequenceGenerator;

/**
 *
 * @author mateus
 */
@Entity
public class Grupo implements Serializable{
    @Id
    @SequenceGenerator(name="gru_codigo", sequenceName = "seq_gru_codigo")    
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "gru_codigo")
    private int gru_codigo;
    private String gru_vdescricao;

    public Grupo() {
    }

    public int getGru_codigo() {
        return gru_codigo;
    }

    public void setGru_codigo(int gru_codigo) {
        this.gru_codigo = gru_codigo;
    }

    public String getGru_vdescricao() {
        return gru_vdescricao;
    }

    public void setGru_vdescricao(String gru_vdescricao) {
        this.gru_vdescricao = gru_vdescricao;
    }

    @Override
    public int hashCode() {
        int hash = 5;
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
        final Grupo other = (Grupo) obj;
        if (this.gru_codigo != other.gru_codigo) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getGru_vdescricao();
    }
       
}

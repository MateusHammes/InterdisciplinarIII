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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

/**
 *
 * @author mateus
 */
@Entity
public class Negocio {

    @Id
    @SequenceGenerator(name = "neg_codigo", sequenceName = "seq_neg_codigo")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "neg_codigo")
    private int neg_codigo;
    private String neg_vnome;
    private Date neg_dcadastro;
    private char neg_cstatus;
    private String neg_vcliente;
    private Date neg_dtermino;
    private String neg_vendereco;
    private String neg_vdescricao;

//   @ManyToOne
//    @JoinColumn(name = "pes_codigo", referencedColumnName = "pes_codigo")
//    private Pessoa pessoa;

    public Negocio() {
    }
    
    
    
    
}


package model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
public class Negocio implements Serializable{

    @Id
    @SequenceGenerator(name = "neg_codigo", sequenceName = "seq_neg_codigo")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "neg_codigo")
    private int neg_codigo;
    private int neg_codigoParent;
    private String neg_vnome;
    private Date neg_dcadastro;
    private char neg_cstatus;
    private String neg_vcliente;
    private Date neg_dtermino;
    private String neg_vendereco;
    private String neg_vdescricao;
    private char neg_ctipo;

//   @ManyToOne
//    @JoinColumn(name = "pes_codigo", referencedColumnName = "pes_codigo")
//    private Pessoa pessoa;

    public Negocio() {
    }
    
    
    
    
}

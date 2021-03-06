package model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
public class Negocio implements Serializable {

    @Id
    @SequenceGenerator(name = "neg_codigo", sequenceName = "seq_neg_codigo")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "neg_codigo")
    private int neg_codigo;
 
    @ManyToOne
    @JoinColumn(name = "neg_parent")
    private Negocio neg_parent;
    //private List<Negocio> neg_parent;
    private String neg_vnome;
    @Temporal(TemporalType.DATE)
    private Date neg_dcadastro;
    private int neg_cstatus;
    private String neg_vcliente;
    @Temporal(TemporalType.DATE)
    private Date neg_dtermino;
    private String neg_vendereco;
    private String neg_vdescricao;
    private int neg_ctipo;
    

//   @ManyToOne
//    @JoinColumn(name = "pes_codigo", referencedColumnName = "pes_codigo")
//    private Pessoa pessoa;
    public Negocio() {
    }

    public int getNeg_codigo() {
        return neg_codigo;
    }

    public void setNeg_codigo(int neg_codigo) {
        this.neg_codigo = neg_codigo;
    }

//    public List<Negocio> getNeg_parent() {
//        return neg_parent;
//    }
//
//    public void setNeg_parent(List<Negocio> neg_parent) {
//        this.neg_parent = neg_parent;
//    }

    public Negocio getNeg_parent() {
        return neg_parent;
    }

    public void setNeg_parent(Negocio neg_parent) {
        this.neg_parent = neg_parent;
    }

    

    public String getNeg_vnome() {
        return neg_vnome;
    }

    public void setNeg_vnome(String neg_vnome) {
        this.neg_vnome = neg_vnome;
    }

    public Date getNeg_dcadastro() {
        return neg_dcadastro;
    }

    public void setNeg_dcadastro(Date neg_dcadastro) {
        this.neg_dcadastro = neg_dcadastro;
    }

 

    public String getNeg_vcliente() {
        return neg_vcliente;
    }

    public void setNeg_vcliente(String neg_vcliente) {
        this.neg_vcliente = neg_vcliente;
    }

    public Date getNeg_dtermino() {
        return neg_dtermino;
    }

    public void setNeg_dtermino(Date neg_dtermino) {
        this.neg_dtermino = neg_dtermino;
    }

    public String getNeg_vendereco() {
        return neg_vendereco;
    }

    public void setNeg_vendereco(String neg_vendereco) {
        this.neg_vendereco = neg_vendereco;
    }

    public String getNeg_vdescricao() {
        return neg_vdescricao;
    }

    public void setNeg_vdescricao(String neg_vdescricao) {
        this.neg_vdescricao = neg_vdescricao;
    }

    public int getNeg_cstatus() {
        return neg_cstatus;
    }

    public void setNeg_cstatus(int neg_cstatus) {
        this.neg_cstatus = neg_cstatus;
    }

    public int getNeg_ctipo() {
        return neg_ctipo;
    }

    public void setNeg_ctipo(int neg_ctipo) {
        this.neg_ctipo = neg_ctipo;
    }


 

}


package model;

import java.io.Serializable;
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


@Entity
@Table(name = "produto")
@XmlRootElement
public class Produto implements Serializable{

    @Id
    @SequenceGenerator(name = "pro_codigo", sequenceName = "seq_pro_codigo")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "pro_codigo")
    private int pro_codigo;
    private String pro_vnome;
    private int pro_ctipo;
    private String pro_vdescricao;

    @ManyToOne
    @JoinColumn(name = "neg_codigo", referencedColumnName = "neg_codigo")
    private Negocio negocio;
   
//    @OneToMany(mappedBy = "produto", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
//    private List<ProdutoMaterial> lsProdutoMaterial;
//    
//    @OneToMany(mappedBy = "produto", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
//    private List<Registros> lsRegistros;
//    
    
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


    public int getPro_ctipo() {
        return pro_ctipo;
    }

    public void setPro_ctipo(int pro_ctipo) {
        this.pro_ctipo = pro_ctipo;
    }

 

}

package model;

import java.io.Serializable;
import java.text.DecimalFormat;

public class Produto implements Serializable {

    private int pro_codigo;
    private String pro_vnome;
    private String pro_vdescricao;
    private int pro_cstatus;
    private int pro_ctipo;
    private double valor;

    private Negocio negocio;

  /*  private List<Produto_material> lsProdutoMaterial;
    private List<Registros> lsRegistros;
*/
    public Produto (){}

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

    public int getPro_cstatus() {
        return pro_cstatus;
    }

    public void setPro_cstatus(int pro_cstatus) {
        this.pro_cstatus = pro_cstatus;
    }

    public Negocio getNegocio() {
        return negocio;
    }

    public void setNegocio(Negocio negocio) {
        this.negocio = negocio;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public int getPro_ctipo() {
        return pro_ctipo;
    }

    public void setPro_ctipo(int pro_ctipo) {
        this.pro_ctipo = pro_ctipo;
    }

    @Override
    public String toString() {
        return getPro_vnome()+"  valor:"+ DecimalFormat.getInstance().format(getValor());
    }
}

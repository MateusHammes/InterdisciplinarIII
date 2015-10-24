package model;

import java.io.Serializable;
import java.util.List;

public class Produto implements Serializable {

    private int pro_codigo;
    private String pro_vnome;
    private String pro_vdescricao;
    private char pro_cstatus;

    private Negocio negocio;

    private List<Produto_material> lsProdutoMateriais;
    private List<Registros> lsRegistros;

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

    public char getPro_cstatus() {
        return pro_cstatus;
    }

    public void setPro_cstatus(char pro_cstatus) {
        this.pro_cstatus = pro_cstatus;
    }

    public Negocio getNegocio() {
        return negocio;
    }

    public void setNegocio(Negocio negocio) {
        this.negocio = negocio;
    }

    public List<Produto_material> getLsProdutoMateriais() {
        return lsProdutoMateriais;
    }

    public void setLsProdutoMateriais(List<Produto_material> lsProdutoMateriais) {
        this.lsProdutoMateriais = lsProdutoMateriais;
    }

    public List<Registros> getLsRegistros() {
        return lsRegistros;
    }

    public void setLsRegistros(List<Registros> lsRegistros) {
        this.lsRegistros = lsRegistros;
    }

    @Override
    public String toString() {
        return getPro_vnome();
    }
}

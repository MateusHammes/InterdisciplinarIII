package model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Negocio implements Serializable {

    private int neg_codigo;


    private String neg_vnome;
    private Date neg_dcadastro;
    private int neg_cstatus; // define se o negocio/orçamento esta aberto ou comcluido, so posso editar os abertos
    private String neg_vcliente;
    private Date neg_dtermino;
    private String neg_vendereco;
    private  String neg_vdescricao;
    private int neg_ctipo;///define se é Negocio ou Orçamento
    private double neg_valorTotal;
    private double neg_valorAdquirido;
    private List<Produto>lsProdutos;
    private Negocio neg_parent;

    public Negocio(){}

    public int getNeg_codigo() {
        return neg_codigo;
    }

    public void setNeg_codigo(int neg_codigo) {
        this.neg_codigo = neg_codigo;
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

    public int getNeg_cstatus() {
        return neg_cstatus;
    }

    public void setNeg_cstatus(int neg_cstatus) {
        this.neg_cstatus = neg_cstatus;
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

    public double getNeg_valorTotal() {
        return neg_valorTotal;
    }

    public void setNeg_valorTotal(double neg_valorTotal) {
        this.neg_valorTotal = neg_valorTotal;
    }

    public double getNeg_valorAdquirido() {
        return neg_valorAdquirido;
    }

    public void setNeg_valorAdquirido(double neg_valorAdquirido) {
        this.neg_valorAdquirido = neg_valorAdquirido;
    }

    public List<Produto> getLsProdutos() {
        return lsProdutos;
    }

    public void setLsProdutos(List<Produto> lsProdutos) {
        this.lsProdutos = lsProdutos;
    }

    public Negocio getNeg_parent() {
        return neg_parent;
    }

    public void setNeg_parent(Negocio neg_parent) {
        this.neg_parent = neg_parent;
    }

    public int getNeg_ctipo() {
        return neg_ctipo;
    }

    public void setNeg_ctipo(int neg_ctipo) {
        this.neg_ctipo = neg_ctipo;
    }

    @Override
    public String toString() {
        return getNeg_vnome();
    }
}


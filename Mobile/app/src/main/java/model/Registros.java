package model;


public class Registros {
    private int rgs_codigo;
    private String rgs_vdescricao;
    private int rgs_cstatus;

    private Produto produto;
    private Negocio negocio;

    public Registros(){}

    public int getRgs_codigo() {
        return rgs_codigo;
    }

    public void setRgs_codigo(int rgs_codigo) {
        this.rgs_codigo = rgs_codigo;
    }

    public String getRgs_vdescricao() {
        return rgs_vdescricao;
    }

    public void setRgs_vdescricao(String rgs_vdescricao) {
        this.rgs_vdescricao = rgs_vdescricao;
    }

    public int getRgs_cstatus() {
        return rgs_cstatus;
    }

    public void setRgs_cstatus(int rgs_cstatus) {
        this.rgs_cstatus = rgs_cstatus;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Negocio getNegocio() {
        return negocio;
    }

    public void setNegocio(Negocio negocio) {
        this.negocio = negocio;
    }

    @Override
    public String toString() {
        return getRgs_vdescricao();
    }
}

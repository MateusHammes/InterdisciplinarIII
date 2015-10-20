package model;


public class Registros {
    private int rgs_codigo;
    private String rgs_vdescricao;
    private char rgs_cstatus;

    private Produto produto;

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

    public char getRgs_cstatus() {
        return rgs_cstatus;
    }

    public void setRgs_cstatus(char rgs_cstatus) {
        this.rgs_cstatus = rgs_cstatus;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }
}
package model;

public class Produto_material {

    private int prm_iunidade;
    private int prm_iunidadeUtilizada;
    private double prm_nvalor;

    private Produto produto;
    private Materiais material;
    private Negocio negocio;

    public Produto_material() {
    }

    public int getPrm_iunidade() {
        return prm_iunidade;
    }

    public void setPrm_iunidade(int prm_iunidade) {
        this.prm_iunidade = prm_iunidade;
    }

    public int getPrm_iunidadeUtilizada() {
        return prm_iunidadeUtilizada;
    }

    public void setPrm_iunidadeUtilizada(int prm_iunidadeUtilizada) {
        this.prm_iunidadeUtilizada = prm_iunidadeUtilizada;
    }

    public double getPrm_nvalor() {
        return prm_nvalor;
    }

    public void setPrm_nvalor(double prm_nvalor) {
        this.prm_nvalor = prm_nvalor;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Materiais getMaterial() {
        return material;
    }

    public void setMaterial(Materiais material) {
        this.material = material;
    }

    public Negocio getNegocio() {
        return negocio;
    }

    public void setNegocio(Negocio negocio) {
        this.negocio = negocio;
    }
}

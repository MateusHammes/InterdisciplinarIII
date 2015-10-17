package model;

public class Produto_material {
    private int prm_iunidade;

    private Produto produto;
    private Materiais materiais;

    public Produto_material() {
    }

    public int getPrm_iunidade() {
        return prm_iunidade;
    }

    public void setPrm_iunidade(int prm_iunidade) {
        this.prm_iunidade = prm_iunidade;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Materiais getMateriais() {
        return materiais;
    }

    public void setMateriais(Materiais materiais) {
        this.materiais = materiais;
    }
}

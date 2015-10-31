package model;

import java.io.Serializable;

public class Grupo implements Serializable {

    private int gru_codigo;
    private String gru_vdescricao;

    public Grupo() {}

    public int getGru_codigo() {
        return gru_codigo;
    }

    public void setGru_codigo(int gru_codigo) {
        this.gru_codigo = gru_codigo;
    }

    public String getGru_vdescricao() {
        return gru_vdescricao;
    }

    public void setGru_vdescricao(String gru_vdescricao) {
        this.gru_vdescricao = gru_vdescricao;
    }

    @Override
    public String toString() {
        return getGru_vdescricao()+" id:"+getGru_codigo();
    }
}

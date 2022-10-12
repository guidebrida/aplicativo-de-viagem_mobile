package com.br.rai.projeto.database.models;

import java.util.ArrayList;
import java.util.List;

public class ViagemGastoModel {

    public static final String TABLE = "viagens_gastos";

    public static final String COLUNA_ID = "_id",
            COLUNA_DESCRICAO = "descricao",
            COLUNA_ADD_A_VIAGEM = "adicionarAViagem",
            COLUNA_TOTAL = "total",
            COLUNA_FK_VIAGEM = "i_viagem";

    public static final String CREATE_TABLE =
            " create table " + TABLE + "("
                    + COLUNA_ID + " integer primary key autoincrement,"
                    + COLUNA_DESCRICAO + " text not null, "
                    + COLUNA_TOTAL + " real, "
                    + COLUNA_ADD_A_VIAGEM + " boolean default true, "
                    + COLUNA_FK_VIAGEM + " integer not null, "
                    + "FOREIGN KEY(" + COLUNA_FK_VIAGEM +") REFERENCES " + ViagemModel.TABLE + "(" + ViagemModel.COLUNA_ID + ")"
                    + ");";

    public static final String DROP_TABLE =
            "drop table if exists " + TABLE + ";";

    public ViagemGastoModel() {
    }

    public ViagemGastoModel(Long id, String descricao, Boolean addToViagem, Double total, Long iViagem) {
        this.id = id;
        this.descricao = descricao;
        this.addToViagem = addToViagem;
        this.total = total;
        this.iViagem = iViagem;
    }

    public ViagemGastoModel(String descricao, Boolean addToViagem, Double total, Long iViagem) {
        this.descricao = descricao;
        this.addToViagem = addToViagem;
        this.total = total;
        this.iViagem = iViagem;
    }

    private Long id;
    private String descricao;
    private Boolean addToViagem;
    private Double total;
    private Long iViagem;

    private List<ViagemGastoItemModel> gastosItens = new ArrayList<>();

    public List<ViagemGastoItemModel> getGastosItens() {
        return gastosItens;
    }

    public void setGastosItens(List<ViagemGastoItemModel> gastosItens) {
        this.gastosItens = gastosItens;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Boolean getAddToViagem() {
        return addToViagem;
    }

    public void setAddToViagem(Boolean addToViagem) {
        this.addToViagem = addToViagem;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Long getiViagem() {
        return iViagem;
    }

    public void setiViagem(Long iViagem) {
        this.iViagem = iViagem;
    }

    public void addAoTotal(Double valor) {
        if (valor != null) {
            this.total = this.total + valor;
        }
    }
}

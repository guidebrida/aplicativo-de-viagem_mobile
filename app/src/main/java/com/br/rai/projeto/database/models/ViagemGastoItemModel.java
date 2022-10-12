package com.br.rai.projeto.database.models;

public class ViagemGastoItemModel {

    public static final String TABLE = "viagens_gastos_itens";

    public static final String COLUNA_ID = "_id",
            COLUNA_DESCRICAO = "descricao",
            COLUNA_ADD_AO_TOTAL = "adicionarAoTotal",
            COLUNA_VALOR = "valor",
            COLUNA_TOTAL = "valor",
            COLUNA_FK_VIAGEM_GASTO = "i_viagem";

    public static final String CREATE_TABLE =
            " create table " + TABLE + "("
                    + COLUNA_ID + " integer primary key autoincrement,"
                    + COLUNA_DESCRICAO + " text not null, "
                    + COLUNA_VALOR + " real,"
                    + COLUNA_ADD_AO_TOTAL + " boolean default true, "
                    + COLUNA_FK_VIAGEM_GASTO + " integer not null, "
                    + "FOREIGN KEY(" + COLUNA_FK_VIAGEM_GASTO +") REFERENCES " + ViagemGastoModel.TABLE + "(" + ViagemGastoModel.COLUNA_ID + ")"
                    + ");";

    public static final String DROP_TABLE =
            "drop table if exists " + TABLE + ";";

    public ViagemGastoItemModel() {
    }

    public ViagemGastoItemModel(Long id, String descricao, Boolean addAoTotal, Double valor, Long iViagemGasto) {
        this.id = id;
        this.descricao = descricao;
        this.addAoTotal = addAoTotal;
        this.valor = valor;
        this.iViagemGasto = iViagemGasto;
    }

    public ViagemGastoItemModel(String descricao, Boolean addAoTotal, Double valor, Long iViagemGasto) {
        this.descricao = descricao;
        this.addAoTotal = addAoTotal;
        this.valor = valor;
        this.iViagemGasto = iViagemGasto;
    }

    private Long id;
    private String descricao;
    private Boolean addAoTotal;
    private Double valor;
    private Long iViagemGasto;

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

    public Boolean getAddAoTotal() {
        return addAoTotal;
    }

    public void setAddAoTotal(Boolean addAoTotal) {
        this.addAoTotal = addAoTotal;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Long getiViagemGasto() {
        return iViagemGasto;
    }

    public void setiViagemGasto(Long iViagemGasto) {
        this.iViagemGasto = iViagemGasto;
    }
}

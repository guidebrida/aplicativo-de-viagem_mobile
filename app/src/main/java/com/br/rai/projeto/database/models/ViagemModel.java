package com.br.rai.projeto.database.models;

import com.br.rai.projeto.database.models.UsuarioModel;

import java.util.ArrayList;
import java.util.List;

public class ViagemModel {

    public static final String TABLE = "viagens";

    public static final String COLUNA_ID = "_id",
            COLUNA_DESCRICAO = "descricao",
            COLUNA_TOTAL_VIAJANTES = "totalViajantes",
            COLUNA_TOTAL_DIAS = "totalDias",
            COLUNA_CUSTO_TOTAL = "custoTotal",
            COLUNA_FK_CLIENTE = "i_cliente";

    public static final String CREATE_TABLE =
            " create table " + TABLE + "("
                    + COLUNA_ID + " integer primary key autoincrement,"
                    + COLUNA_DESCRICAO + " text not null, "
                    + COLUNA_TOTAL_DIAS + " integer not null,"
                    + COLUNA_TOTAL_VIAJANTES + " integer not null,"
                    + COLUNA_CUSTO_TOTAL + " REAL,"
                    + COLUNA_FK_CLIENTE + " integer not null,"
                    + "FOREIGN KEY(" + COLUNA_FK_CLIENTE +") REFERENCES " + UsuarioModel.TABLE + "(" + UsuarioModel.COLUNA_ID + ")"
                    + ");";

    public static final String DROP_TABLE =
            "drop table if exists " + TABLE + ";";

    public ViagemModel() {
    }

    public ViagemModel(String descricao, Integer totalDias, Integer totalViajantes, Long iUsuario) {
        this.descricao = descricao;
        this.totalDias = totalDias;
        this.totalViajantes = totalViajantes;
        this.iUsuario = iUsuario;
    }

    public ViagemModel(Long id, String descricao, Integer totalDias, Integer totalViajantes, Double custoTotal, Long iUsuario) {
        this.id = id;
        this.descricao = descricao;
        this.totalDias = totalDias;
        this.totalViajantes = totalViajantes;
        this.custoTotal = custoTotal;
        this.iUsuario = iUsuario;
    }

    private Long id;
    private String descricao;
    private Integer totalDias;
    private Integer totalViajantes;
    private Double custoTotal;
    private Long iUsuario;

    private List<ViagemGastoModel> gastos = new ArrayList<>();

    public List<ViagemGastoModel> getGastos() {
        return gastos;
    }

    public void setGastos(List<ViagemGastoModel> gastos) {
        this.gastos = gastos;
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

    public Integer getTotalDias() {
        return totalDias;
    }

    public void setTotalDias(Integer totalDias) {
        this.totalDias = totalDias;
    }

    public Integer getTotalViajantes() {
        return totalViajantes;
    }

    public void setTotalViajantes(Integer totalViajantes) {
        this.totalViajantes = totalViajantes;
    }

    public Double getCustoTotal() {
        return custoTotal;
    }

    public void setCustoTotal(Double custoTotal) {
        this.custoTotal = custoTotal;
    }

    public Long getiUsuario() {
        return iUsuario;
    }

    public void setiUsuario(Long iUsuario) {
        this.iUsuario = iUsuario;
    }

    public void addAoTotal(Double value) {
        this.custoTotal = this.custoTotal + value;
    }
}

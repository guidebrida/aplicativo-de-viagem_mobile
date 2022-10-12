package com.br.rai.projeto.database.models;

public class UsuarioModel {

    public static final String TABLE = "usuarios";

    public static final String COLUNA_ID = "_id",
    COLUNA_NOME = "nome",
    COLUNA_LOGIN = "login",
    COLUNA_SENHA = "senha";

    public static final String CREATE_TABLE =
            " create table " + TABLE + "("
                    + COLUNA_ID + " integer primary key autoincrement,"
                    + COLUNA_NOME + " text not null, "
                    + COLUNA_LOGIN + " text not null,"
                    + COLUNA_SENHA + " text not null"
                    + ");";

    public static final String DROP_TABLE =
            "drop table if exists " + TABLE + ";";

    public UsuarioModel() {
    }

    public UsuarioModel(String nome, String login, String senha) {
        this.nome = nome;
        this.login = login;
        this.senha = senha;
    }

    public UsuarioModel(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    private Long id;
    private String nome;
    private String login;
    private String senha;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}

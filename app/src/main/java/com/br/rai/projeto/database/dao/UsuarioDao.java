package com.br.rai.projeto.database.dao;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.widget.Toast;

import com.br.rai.projeto.MainActivity;
import com.br.rai.projeto.database.AbstractDao;
import com.br.rai.projeto.database.DBOpenHelper;
import com.br.rai.projeto.database.models.UsuarioModel;

import java.util.Arrays;

public class UsuarioDao extends AbstractDao {

    public UsuarioDao(Context context) {
        this.context = context;
        dbOpenHelper = new DBOpenHelper(context);
    }

    public long register(UsuarioModel usuarioModel) {
        long rowAffected = 0;
        if (usuarioModel.getNome() != null && usuarioModel.getLogin() != null && usuarioModel.getSenha() != null && !usuarioModel.getLogin().isEmpty() && !usuarioModel.getSenha().isEmpty() && !usuarioModel.getNome().isEmpty()) {
            if (!existsLogin(usuarioModel.getLogin())) {
                try {
                    Open();
                    ContentValues values = new ContentValues();
                    values.put(UsuarioModel.COLUNA_NOME, usuarioModel.getNome());
                    values.put(UsuarioModel.COLUNA_LOGIN, usuarioModel.getLogin());
                    values.put(UsuarioModel.COLUNA_SENHA, usuarioModel.getSenha());
                    rowAffected = db.insert(UsuarioModel.TABLE, null, values);
                } finally {
                    Close();
                }
            } else {
                showToast("Login já registrado!");
            }
        } else {
            showToast("Nome, login e senha são obrigatórios!");
        }
        return rowAffected;
    }

    private boolean existsLogin(String login) {
        boolean exists = false;
        try {
            Open();
            String query = String.format("SELECT EXISTS (SELECT * FROM %s WHERE %s = '%s' LIMIT 1);", UsuarioModel.TABLE, UsuarioModel.COLUNA_LOGIN, login);
            Cursor cursor = db.rawQuery(query, null);
            cursor.moveToFirst();
            exists = cursor.getInt(0) == 1;
            cursor.close();
        } finally {
            Close();
        }
        return exists;
    }

    public UsuarioModel login(String login, String senha) {
        UsuarioModel usuarioModel = null;
        if (login != null && !login.isEmpty() && senha != null && !senha.isEmpty()) {
            try {
                Open();
                String query = String.format("SELECT %s, %s FROM %s WHERE %s = '%s' and %s = '%s' LIMIT 1;", UsuarioModel.COLUNA_ID, UsuarioModel.COLUNA_NOME, UsuarioModel.TABLE, UsuarioModel.COLUNA_LOGIN, login, UsuarioModel.COLUNA_SENHA, senha);
                Cursor cursor = db.rawQuery(query, null);
                cursor.moveToFirst();

                if (cursor.getCount() == 0) {
                    showToast("Usuario ou senha incorretos!");
                } else {
                    usuarioModel = new UsuarioModel(cursor.getLong(0), cursor.getString(1));
                }

                cursor.close();
            } finally {
                Close();
            }
        } else {
            this.showToast("Login e senha são obrigatórios!");
        }
        return usuarioModel;
    }
}

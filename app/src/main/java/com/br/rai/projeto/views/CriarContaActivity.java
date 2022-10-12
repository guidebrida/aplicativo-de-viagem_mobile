package com.br.rai.projeto.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.br.rai.projeto.R;
import com.br.rai.projeto.database.dao.UsuarioDao;
import com.br.rai.projeto.database.models.UsuarioModel;

import java.util.ArrayList;

public class CriarContaActivity extends AppCompatActivity {

    private Button botao_cadastrar;
    private EditText editNome;
    private EditText editUsuario;
    private EditText editSenha;
    private TextView editTextVoltar;
    private UsuarioDao usuarioDao;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criarconta);

        usuarioDao = new UsuarioDao(CriarContaActivity.this);
        botao_cadastrar = findViewById(R.id.botao_cadastrar);
        editNome = findViewById(R.id.editNome);
        editUsuario = findViewById(R.id.editUsuario);
        editSenha = findViewById(R.id.editSenha);
        editTextVoltar = findViewById(R.id.editTextVoltar);

        editTextVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CriarContaActivity.this, LoginActivity.class));
            }
        });

        botao_cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nome = editNome.getText().toString();
                String senha = editSenha.getText().toString();
                String usuario = editUsuario.getText().toString();
                if(!nome.isEmpty() && !senha.isEmpty() && !usuario.isEmpty()){
                    long register = usuarioDao.register(new UsuarioModel(nome, usuario, senha));
                    if (register != -1) {
                        Toast.makeText(CriarContaActivity.this,
                                "Cadastrado com sucesso!", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(CriarContaActivity.this, LoginActivity.class));
                    }
                } else {
                    Toast.makeText(CriarContaActivity.this,
                            "Todos os campos são obrigatorios", Toast.LENGTH_LONG).show();
                }
            }
        });
    }



}
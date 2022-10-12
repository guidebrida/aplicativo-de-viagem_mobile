package com.br.rai.projeto.views;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.br.rai.projeto.R;
import com.br.rai.projeto.database.dao.UsuarioDao;
import com.br.rai.projeto.database.models.UsuarioModel;
import com.br.rai.projeto.util.Constants;

public class LoginActivity extends AppCompatActivity {

    private Button entrarButton;
    private TextView criarConta;
    private EditText emailEdit;
    private EditText senhaEdit;
    private SharedPreferences preferences;
    private UsuarioDao usuarioDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.loadDependencies();
        preferences.edit().putLong(Constants.USER, 0L).commit();


        criarConta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, CriarContaActivity.class));
            }
        });

        entrarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String usuarioDigitado = emailEdit.getText().toString();
                final String senhaDigitada = senhaEdit.getText().toString();

                if (usuarioDigitado.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "E-mail obrigatório!", Toast.LENGTH_LONG).show();
                } else if (senhaDigitada.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Senha obrigatória!", Toast.LENGTH_LONG).show();
                } else {
                    UsuarioModel login = usuarioDao.login(usuarioDigitado, senhaDigitada);

                    if (login != null) {
                        preferences.edit().putLong(Constants.USER, login.getId()).commit();
                        startActivity(new Intent(LoginActivity.this, ListaViagensActivity.class));
                    }
                }
            }
        });
    }

    private void loadDependencies() {
        usuarioDao = new UsuarioDao(LoginActivity.this);
        entrarButton = findViewById(R.id.botao_login);
        emailEdit = findViewById(R.id.editTextTextUser);
        senhaEdit = findViewById(R.id.editTextTextPassword);
        criarConta = findViewById(R.id.criarConta);
        preferences = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
    }
}
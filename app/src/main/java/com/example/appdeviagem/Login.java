package com.example.appdeviagem;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appdeviagem.util.Shared;

public class Login extends AppCompatActivity {

    private EditText emailEdit;
    private EditText senhaEdit;
    private Button entrarButton;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emailEdit = findViewById(R.id.editEmail);

        preferences = PreferenceManager.getDefaultSharedPreferences(Login.this);
        if (!preferences.getString(Shared.LOGIN_EMAIL, "").isEmpty()) {
            emailEdit.setText(preferences.getString(Shared.LOGIN_EMAIL, ""));
        }

        senhaEdit = findViewById(R.id.editSenha);
        entrarButton = findViewById(R.id.botao_login);
        entrarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String emailDigitado = emailEdit.getText().toString();
                final String senhaDigitada = senhaEdit.getText().toString();

                if (emailDigitado.isEmpty()) {
                    Toast.makeText(Login.this, "E-mail obrigatório!", Toast.LENGTH_LONG).show();
                }
                else if (senhaDigitada.isEmpty()) {
                    Toast.makeText(Login.this, "Senha obrigatória!", Toast.LENGTH_LONG).show();
                }
                else {

                    SharedPreferences.Editor edit = preferences.edit();
                    edit.putString(Shared.LOGIN_EMAIL, emailDigitado);
                    edit.apply();

                    Intent it = new Intent(Login.this, MainActivity.class);
                    it.putExtra("PASSANDO_EMAIL", emailDigitado);
                    startActivity(it);
                }
            }
        });
    }
}
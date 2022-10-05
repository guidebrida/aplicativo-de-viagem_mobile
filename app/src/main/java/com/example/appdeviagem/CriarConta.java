package com.example.appdeviagem;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class CriarConta extends AppCompatActivity {

    private Button botao_cadastrar;
    private EditText editNome;
    private EditText editEmail;
    private EditText editSenha;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criarconta);

        botao_cadastrar = findViewById(R.id.botao_cadastrar);
        editNome = findViewById(R.id.editNome);
        editEmail = findViewById(R.id.editEmail);
        editSenha = findViewById(R.id.editSenha);

        botao_cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!(editNome.getText().toString().isEmpty()
                        && editEmail.getText().toString().isEmpty()
                        && editSenha.getText().toString().isEmpty())){

                    ArrayList<CharSequence> dados = new ArrayList<CharSequence>();


                    Intent it = new Intent(CriarConta.this, MainActivity.class);
                    it.putCharSequenceArrayListExtra("DADOS", dados);
                    startActivity(it);
                }else{
                    Toast.makeText(CriarConta.this,
                            "Todos os campos são obrigatorios", Toast.LENGTH_LONG);
                }
            }
        });
    }



}
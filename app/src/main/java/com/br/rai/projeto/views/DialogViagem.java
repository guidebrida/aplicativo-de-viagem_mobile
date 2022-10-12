package com.br.rai.projeto.views;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.br.rai.projeto.R;
import com.br.rai.projeto.database.dao.ViagemDao;
import com.br.rai.projeto.database.models.ViagemModel;
import com.br.rai.projeto.util.Constants;

public class DialogViagem extends AppCompatDialogFragment {

    private EditText nDias;
    private EditText qntdPesssoas;
    private EditText editTextDescricao;
    private SharedPreferences preference;
    private ViagemDao viagemDao;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        preference = PreferenceManager.getDefaultSharedPreferences(getContext());
        viagemDao = new ViagemDao(getContext());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add_viagem, null);

        builder.setView(view)
                .setTitle("Adicionar Viagem")
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("Salvar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String nDiasDigitado = nDias.getText().toString();
                        String qntdPessoasDigitado = qntdPesssoas.getText().toString();
                        String descricao = editTextDescricao.getText().toString();
                        if(nDiasDigitado.isEmpty()){
                            Toast.makeText(getActivity(), "Preencha de quantos dias será a viagem!", Toast.LENGTH_LONG).show();

                        } else if(qntdPessoasDigitado.isEmpty()){
                            Toast.makeText(getActivity(), "Preencha quantas pessoas vão na viagem!", Toast.LENGTH_LONG).show();
                        } else if (descricao.isEmpty()) {
                            Toast.makeText(getActivity(), "Preencha a descriçao da viagem!", Toast.LENGTH_LONG).show();
                        } else {
                            int nDiasBanco = Integer.parseInt(nDiasDigitado);
                            int qntdPesssoasBanco = Integer.parseInt(qntdPessoasDigitado);
                            ViagemModel viagemModel = new ViagemModel(descricao, nDiasBanco, qntdPesssoasBanco, preference.getLong(Constants.USER, 0L));
                            ViagemModel saved = viagemDao.save(viagemModel);
                            Intent intent = new Intent(getContext(), CadastrarViagemActivity.class);
                            intent.putExtra(Constants.VIAGEM_ID, saved.getId());
                            startActivity(intent);
                        }
                    }
                });
        editTextDescricao = view.findViewById(R.id.descricao);
        nDias = view.findViewById(R.id.nDias);
        qntdPesssoas = view.findViewById(R.id.qntdPessoas);
        return builder.create();

    }
}

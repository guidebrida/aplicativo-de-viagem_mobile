package com.br.rai.projeto.views;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.br.rai.projeto.R;
import com.br.rai.projeto.adapters.DiversosAdapter;
import com.br.rai.projeto.database.models.ViagemGastoItemModel;
import com.br.rai.projeto.database.models.ViagemGastoModel;

public class DialogDiversos extends AppCompatDialogFragment {

    private EditText nomeEntretenimento;
    private EditText valorEntretenimento;
    private ViagemGastoModel viagemGastoModel;
    private DiversosAdapter adapter;
    private TextView totalDiversos;
    private ListView listView;

    public DialogDiversos(ViagemGastoModel viagemGastoModel, DiversosAdapter adapter, TextView totalDiversos, ListView listView) {
        super();
        this.viagemGastoModel = viagemGastoModel;
        this.listView = listView;
        this.adapter = adapter;
        this.totalDiversos = totalDiversos;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add_diversos, null);
        builder.setView(view)
                .setTitle("Adicionar Gasto Diverso")
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("Salvar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String nomeDiversoDigitado = nomeEntretenimento.getText().toString();
                        String valorDiversoDigitado = valorEntretenimento.getText().toString();
                        double valorDiversoBanco;
                        if(nomeDiversoDigitado.isEmpty()){
                            Toast.makeText(getActivity(), "Preencha o nome do gasto!", Toast.LENGTH_LONG).show();
                        } else if(valorDiversoDigitado.isEmpty()){
                            Toast.makeText(getActivity(), "Preencha o valor do gasto!", Toast.LENGTH_LONG).show();
                        } else {
                            valorDiversoBanco = Double.parseDouble(valorDiversoDigitado);
                            viagemGastoModel.getGastosItens().add(new ViagemGastoItemModel(nomeDiversoDigitado, Boolean.TRUE, valorDiversoBanco, viagemGastoModel.getId()));
                            adapter.notifyDataSetChanged();
                            updateListViewHeight(listView);
                            totalDiversos.setText("0.0");
                        }
                    }
                });
        nomeEntretenimento = view.findViewById(R.id.nomeDiverso);
        valorEntretenimento = view.findViewById(R.id.valorDiverso);
        return builder.create();
    }

    public static void updateListViewHeight(ListView myListView) {
        ListAdapter myListAdapter = myListView.getAdapter();
        if (myListAdapter == null) {
            return;
        }
        int totalHeight = 0;
        int adapterCount = myListAdapter.getCount();
        for (int size = 0; size < adapterCount; size++) {
            View listItem = myListAdapter.getView(size, null, myListView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = myListView.getLayoutParams();
        params.height = (totalHeight
                + (myListView.getDividerHeight() * (adapterCount)));
        myListView.setLayoutParams(params);
    }
}

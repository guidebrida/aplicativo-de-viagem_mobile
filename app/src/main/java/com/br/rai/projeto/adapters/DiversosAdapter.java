package com.br.rai.projeto.adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Switch;
import android.widget.TextView;

import com.br.rai.projeto.R;
import com.br.rai.projeto.database.models.ViagemGastoItemModel;

import java.util.ArrayList;
import java.util.List;

public class DiversosAdapter extends BaseAdapter {
    private List<ViagemGastoItemModel> arlLista;
    private Activity act;
    private TextView totalDiversos;

    public DiversosAdapter(final Activity act, final List<ViagemGastoItemModel> arlLista, TextView totalDiversos) {
        super();
        this.act = act;
        this.arlLista = arlLista;
        this.totalDiversos = totalDiversos;
    }

    @Override
    public int getCount() {
        return arlLista.size();
    }

    @Override
    public Object getItem(int i) {
        return arlLista.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if (view == null) {
            view = act.getLayoutInflater().inflate(R.layout.activity_diversos, viewGroup, false);
        }

        ViagemGastoItemModel d = arlLista.get(i);

        TextView nomeDiverso = view.findViewById(R.id.nomeDiverso);
        nomeDiverso.setText(d.getDescricao());

        TextView valorDiverso = view.findViewById(R.id.valorDiverso);
        valorDiverso.setText(d.getValor().toString());

        Switch addDiverso = view.findViewById(R.id.addDiverso);
        if (d.getAddAoTotal()) {
            if (totalDiversos.getText().toString().isEmpty()) {
                totalDiversos.setText(d.getValor().toString());
            } else {
                totalDiversos.setText(String.valueOf(Double.parseDouble(totalDiversos.getText().toString()) + d.getValor()));
            }
        }
        addDiverso.setChecked(d.getAddAoTotal());
        addDiverso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                d.setAddAoTotal(addDiverso.isChecked());
                if (addDiverso.isChecked()) {
                    if (totalDiversos.getText().toString().isEmpty()) {
                        totalDiversos.setText(d.getValor().toString());
                    } else {
                        totalDiversos.setText(String.valueOf(Double.parseDouble(totalDiversos.getText().toString()) + d.getValor()));
                    }
                } else {
                    totalDiversos.setText(String.valueOf(Double.parseDouble(totalDiversos.getText().toString()) - d.getValor()));
                }
            }
        });

        return view;
    }
}

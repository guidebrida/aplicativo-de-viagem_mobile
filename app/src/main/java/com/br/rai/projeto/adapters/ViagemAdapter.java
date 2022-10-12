package com.br.rai.projeto.adapters;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.br.rai.projeto.R;
import com.br.rai.projeto.database.models.ViagemModel;
import com.br.rai.projeto.util.Constants;
import com.br.rai.projeto.views.CadastrarViagemActivity;

import java.util.List;

public class ViagemAdapter extends BaseAdapter {
    private List<ViagemModel> arlLista;
    private Activity act;

    public ViagemAdapter(final Activity act, final List<ViagemModel> arlLista) {
        this.act = act;
        this.arlLista = arlLista;
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
            view = act.getLayoutInflater().inflate(R.layout.listviagens, viewGroup, false);
        }

        ViagemModel v = arlLista.get(i);

        TextView nomePessoa = view.findViewById(R.id.nomeViagem);
        nomePessoa.setText(v.getDescricao());
        TextView qntPessoas = view.findViewById(R.id.totalPessoas);
        qntPessoas.setText("Pessoas: " + v.getTotalViajantes());

        TextView custoViagem = view.findViewById(R.id.custoViagem);
        custoViagem.setText("Custo da viagem: " + v.getCustoTotal());

        TextView custoPorPessoa = view.findViewById(R.id.custoPessoa);
        custoPorPessoa.setText("Custo por pessoa: " + v.getCustoTotal()/v.getTotalViajantes());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), CadastrarViagemActivity.class);
                intent.putExtra(Constants.VIAGEM_ID, arlLista.get(i).getId());
                view.getContext().startActivity(intent);
            }
        });
        return view;
    }
}

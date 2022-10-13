package com.br.rai.projeto.views;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.br.rai.projeto.R;
import com.br.rai.projeto.adapters.DiversosAdapter;
import com.br.rai.projeto.adapters.ViagemAdapter;
import com.br.rai.projeto.database.dao.ViagemDao;
import com.br.rai.projeto.database.dao.ViagemGastoDao;
import com.br.rai.projeto.database.dao.ViagemGastoItemDao;
import com.br.rai.projeto.database.models.ViagemGastoItemModel;
import com.br.rai.projeto.database.models.ViagemGastoModel;
import com.br.rai.projeto.database.models.ViagemModel;
import com.br.rai.projeto.util.Constants;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class CadastrarViagemActivity extends AppCompatActivity {

    private static final String HOSPEDAGEM = "Hospedagem";
    private static final String HOSPEDAGEM_PERNOITE = "Peronoite";
    private static final String HOSPEDAGEM_TOTAL_NOITES = "TotalDeNoites";
    private static final String HOSPEDAGEM_QUARTOS = "TotalQuartos";
    private static final String REFEICOES = "Refeicoes";
    private static final String REFEICOES_CUSTO = "CustoPorRefeicao";
    private static final String REFEICOES_POR_DIA = "RefeicoesPorDia";
    private static final String TARIFA_AEREA = "TarifaAerea";
    private static final String TARIFA_AEREA_CUSTO_PESSOA = "CustoPorPessoa";
    private static final String TARIFA_AEREA_ALUGUEL_VEICULO = "AluguelDeVeiculo";
    private static final String GASOLNA = "Gasolina";
    private static final String GASOLNA_KM_ESTIMADO = "TotalEstimadoKm";
    private static final String GASOLNA_KM_POR_LITRO = "KmPorLitro";
    private static final String GASOLNA_CUSTO_MEDIO_POR_LITRO = "CustoMedioPorLitro";
    private static final String GASOLNA_TOTAL_VEICULOS = "TotalDeVeiculos";
    private static final String DIVERSOS = "Diversos";

    private ViagemModel viagemModel;
    private EditText totalEstimadoKm;
    private EditText custoMedioLitro;
    private EditText mediaKmLitro;
    private EditText totalVeiculos;
    private TextView totalGasolina;
    private Switch addGasolina;

    private EditText custoEstimadoPessoa;
    private EditText aluguelVeiculo;
    private TextView totalTarifaAerea;
    private Switch addTarifaAerea;

    private EditText custoEstimadoRefeicao;
    private EditText refeicoesDia;
    private TextView totalRefeicoes;
    private Switch addRefeicao;

    private EditText custoEstimadoNoite;
    private EditText totalNoites;
    private EditText totalQuartos;
    private TextView totalHospedagem;
    private Switch addHospedagem;

    private ListView listaDiversos;
    private TextView totalDiversos;
    private DiversosAdapter adapter;
    private Button btnAdiconarDiversos;
    private Button btnSalvarGastos;

    private ViagemDao viagemDao;
    private ViagemGastoItemDao viagemGastoItemDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_viagem);
        viagemDao = new ViagemDao(CadastrarViagemActivity.this);
        viagemGastoItemDao = new ViagemGastoItemDao(CadastrarViagemActivity.this);

        long viagemId = getIntent().getLongExtra(Constants.VIAGEM_ID, 0L);

        if (viagemId == 0L) {
            Toast.makeText(CadastrarViagemActivity.this, "Erro ao abrir a viagem", Toast.LENGTH_LONG).show();
            startActivity(new Intent(CadastrarViagemActivity.this,ListaViagensActivity.class));
        }

        viagemModel = viagemDao.getViagem(viagemId);

        addTarifaAerea = findViewById(R.id.addTarifaEarea);
        addGasolina = findViewById(R.id.addGasolina);
        addRefeicao = findViewById(R.id.addRefeicao);
        addHospedagem = findViewById(R.id.addHospedagem);
        totalEstimadoKm = findViewById(R.id.totalEstimadoKm);
        totalEstimadoKm.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED);
        mediaKmLitro = findViewById(R.id.mediaKmLitro);
        mediaKmLitro.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED);
        custoMedioLitro = findViewById(R.id.custoMedioLitro);
        custoMedioLitro.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED);
        totalVeiculos = findViewById(R.id.totalVeiculos);
        totalVeiculos.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED);
        totalGasolina = findViewById(R.id.totalGasolina);
        custoEstimadoPessoa = findViewById(R.id.custoEstimadoPessoa);
        custoEstimadoPessoa.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED);
        aluguelVeiculo = findViewById(R.id.aluguelVeiculo);
        aluguelVeiculo.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED);
        totalTarifaAerea = findViewById(R.id.totalTarifaAerea);
        custoEstimadoRefeicao = findViewById(R.id.custoEstimadoRefeicao);
        custoEstimadoRefeicao.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED);
        refeicoesDia = findViewById(R.id.refeicoesDia);
        refeicoesDia.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED);
        totalRefeicoes = findViewById(R.id.totalRefeicoes);
        custoEstimadoNoite = findViewById(R.id.custoEstimadoNoite);
        custoEstimadoNoite.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED);
        totalQuartos = findViewById(R.id.totalQuartos);
        totalQuartos.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED);
        totalNoites = findViewById(R.id.totalNoites);
        totalNoites.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED);
        totalHospedagem = findViewById(R.id.totalHospedagem);
        btnAdiconarDiversos = findViewById(R.id.adicionardiversos);
        totalDiversos = findViewById(R.id.totalDiversos);
        listaDiversos = findViewById(R.id.listDiversos);
        btnSalvarGastos = findViewById(R.id.salvar);

        custoEstimadoPessoa.addTextChangedListener(new SomarTarifa(totalTarifaAerea, custoEstimadoPessoa, aluguelVeiculo));
        aluguelVeiculo.addTextChangedListener(new SomarTarifa(totalTarifaAerea, custoEstimadoPessoa, aluguelVeiculo));

        totalEstimadoKm.addTextChangedListener(new SomarGasolina(totalGasolina, totalEstimadoKm, custoMedioLitro, mediaKmLitro, totalVeiculos));
        custoMedioLitro.addTextChangedListener(new SomarGasolina(totalGasolina, totalEstimadoKm, custoMedioLitro, mediaKmLitro, totalVeiculos));
        mediaKmLitro.addTextChangedListener(new SomarGasolina(totalGasolina, totalEstimadoKm, custoMedioLitro, mediaKmLitro, totalVeiculos));
        totalVeiculos.addTextChangedListener(new SomarGasolina(totalGasolina, totalEstimadoKm, custoMedioLitro, mediaKmLitro, totalVeiculos));

        custoEstimadoRefeicao.addTextChangedListener(new SomarRefeicoes(totalRefeicoes, custoEstimadoRefeicao, refeicoesDia));
        refeicoesDia.addTextChangedListener(new SomarRefeicoes(totalRefeicoes, custoEstimadoRefeicao, refeicoesDia));

        totalNoites.addTextChangedListener(new SomarHospedagem(totalHospedagem, custoEstimadoNoite, totalNoites, totalQuartos));
        custoEstimadoNoite.addTextChangedListener(new SomarHospedagem(totalHospedagem, custoEstimadoNoite, totalNoites, totalQuartos));
        totalQuartos.addTextChangedListener(new SomarHospedagem(totalHospedagem, custoEstimadoNoite, totalNoites, totalQuartos));

        registerForContextMenu(listaDiversos);

        btnAdiconarDiversos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });

        btnSalvarGastos.setOnClickListener(new ClickListenerSalvarGastos());

        addHospedagem.setOnClickListener(new AddToViagemClickListener(addHospedagem, getGasto(HOSPEDAGEM), custoEstimadoNoite, totalNoites, totalQuartos));
        addRefeicao.setOnClickListener(new AddToViagemClickListener(addRefeicao, getGasto(REFEICOES), custoEstimadoRefeicao, refeicoesDia));
        addTarifaAerea.setOnClickListener(new AddToViagemClickListener(addTarifaAerea, getGasto(TARIFA_AEREA), custoEstimadoPessoa, aluguelVeiculo));
        addGasolina.setOnClickListener(new AddToViagemClickListener(addGasolina, getGasto(GASOLNA), totalEstimadoKm, custoMedioLitro, mediaKmLitro, totalVeiculos));

        if (viagemModel.getGastos().isEmpty()) {
            viagemModel.setGastos(getGastos());
        } else {
            this.setValores();
        }
        ViagemGastoModel gastosDiversos = getGasto(DIVERSOS);
        adapter = new DiversosAdapter(CadastrarViagemActivity.this, gastosDiversos.getGastosItens(), totalDiversos);
        listaDiversos.setAdapter(adapter);
        updateListViewHeight(listaDiversos);
        totalDiversos.setText("0.0");
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

    public ViagemGastoModel getGasto(String alias) {
        ViagemGastoModel viagemGastoModel = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            viagemGastoModel = viagemModel.getGastos().stream().filter(x -> x.getDescricao().equals(alias)).findFirst().orElse(null);
        }
        return viagemGastoModel;
    }

    public void openDialog(){
        DialogDiversos dialogDiversos = new DialogDiversos(getGasto(DIVERSOS), adapter, totalDiversos, listaDiversos);
        dialogDiversos.show(getSupportFragmentManager(),"Adicionar Diverso");
    }

    private void setValores() {
        for (ViagemGastoModel gasto : viagemModel.getGastos()) {
            switch (gasto.getDescricao()) {
                case GASOLNA:
                    printValoresGasolina(gasto);
                    break;
                case TARIFA_AEREA:
                    printValoresTarifa(gasto);
                    break;
                case HOSPEDAGEM:
                    printValoresHospedagem(gasto);
                    break;
                case REFEICOES:
                    printValoresRefeicoes(gasto);
                    break;
            }
        }
    }

    private void printValoresHospedagem(ViagemGastoModel gasto) {
        for (ViagemGastoItemModel gastosIten : gasto.getGastosItens()) {
            if (gastosIten.getValor() != null && !gastosIten.getValor().equals(0D)) {
                switch (gastosIten.getDescricao()) {
                    case HOSPEDAGEM_PERNOITE:
                        custoEstimadoNoite.setText(String.valueOf(gastosIten.getValor()));
                        break;
                    case HOSPEDAGEM_TOTAL_NOITES:
                        totalNoites.setText(String.valueOf(gastosIten.getValor().intValue()));
                        break;
                    case HOSPEDAGEM_QUARTOS:
                        totalQuartos.setText(String.valueOf(gastosIten.getValor().intValue()));
                }
            }
        }
        addHospedagem.setChecked(gasto.getAddToViagem());
    }

    private void printValoresRefeicoes(ViagemGastoModel gasto) {
        for (ViagemGastoItemModel gastosIten : gasto.getGastosItens()) {
            if (gastosIten.getValor() != null && !gastosIten.getValor().equals(0D)) {
                switch (gastosIten.getDescricao()) {
                    case REFEICOES_CUSTO:
                        custoEstimadoRefeicao.setText(String.valueOf(gastosIten.getValor()));
                        break;
                    case REFEICOES_POR_DIA:
                        refeicoesDia.setText(String.valueOf(gastosIten.getValor().intValue()));
                        break;
                }
            }
        }
        addRefeicao.setChecked(gasto.getAddToViagem());
    }

    private void printValoresGasolina(ViagemGastoModel gasto) {
        for (ViagemGastoItemModel gastosIten : gasto.getGastosItens()) {
            if (gastosIten.getValor() != null && !gastosIten.getValor().equals(0D)) {
                switch (gastosIten.getDescricao()) {
                    case GASOLNA_KM_ESTIMADO:
                        totalEstimadoKm.setText(String.valueOf(gastosIten.getValor()));
                        break;
                    case GASOLNA_KM_POR_LITRO:
                        mediaKmLitro.setText(String.valueOf(gastosIten.getValor()));
                        break;
                    case GASOLNA_CUSTO_MEDIO_POR_LITRO:
                        custoMedioLitro.setText(String.valueOf(gastosIten.getValor()));
                        break;
                    case GASOLNA_TOTAL_VEICULOS:
                        totalVeiculos.setText(String.valueOf(gastosIten.getValor().intValue()));
                        break;
                }
            }
        }
        addGasolina.setChecked(gasto.getAddToViagem());
    }

    private void printValoresTarifa(ViagemGastoModel gasto) {
        for (ViagemGastoItemModel gastosIten : gasto.getGastosItens()) {
            if (gastosIten.getValor() != null && !gastosIten.getValor().equals(0D)) {
                switch (gastosIten.getDescricao()) {
                    case TARIFA_AEREA_ALUGUEL_VEICULO:
                        aluguelVeiculo.setText(String.valueOf(gastosIten.getValor()));
                        break;
                    case TARIFA_AEREA_CUSTO_PESSOA:
                        custoEstimadoPessoa.setText(String.valueOf(gastosIten.getValor()));
                        break;
                }
            }
        }
        addTarifaAerea.setChecked(gasto.getAddToViagem());
    }

    private List<ViagemGastoModel> getGastos() {
        List<ViagemGastoModel> list = new ArrayList<>();
        list.add(createViagemGastoGasolina());
        list.add(createViagemTarifaAerea());
        list.add(createRefeicoes());
        list.add(createHospedagem());
        list.add(createDiversos());
        return list;
    }

    private ViagemGastoModel createHospedagem() {
        ViagemGastoModel viagemGastoModel = new ViagemGastoModel(HOSPEDAGEM, false, 0D, viagemModel.getId());
        List<ViagemGastoItemModel> viagemGastoItemModels = new ArrayList<>();

        viagemGastoItemModels.add(new ViagemGastoItemModel(HOSPEDAGEM_PERNOITE, Boolean.TRUE, 0D, null));
        viagemGastoItemModels.add(new ViagemGastoItemModel(HOSPEDAGEM_TOTAL_NOITES, Boolean.TRUE, 0D, null));
        viagemGastoItemModels.add(new ViagemGastoItemModel(HOSPEDAGEM_QUARTOS, Boolean.TRUE, 0D, null));

        viagemGastoModel.setGastosItens(viagemGastoItemModels);
        return viagemGastoModel;
    }

    private ViagemGastoModel createDiversos() {
        ViagemGastoModel viagemGastoModel = new ViagemGastoModel(DIVERSOS, true, 0D, viagemModel.getId());
        List<ViagemGastoItemModel> viagemGastoItemModels = new ArrayList<>();
        viagemGastoModel.setGastosItens(viagemGastoItemModels);
        return viagemGastoModel;
    }


    private ViagemGastoModel createRefeicoes() {
        ViagemGastoModel viagemGastoModel = new ViagemGastoModel(REFEICOES, false, 0D, viagemModel.getId());
        List<ViagemGastoItemModel> viagemGastoItemModels = new ArrayList<>();

        viagemGastoItemModels.add(new ViagemGastoItemModel(REFEICOES_CUSTO, Boolean.TRUE, 0D, null));
        viagemGastoItemModels.add(new ViagemGastoItemModel(REFEICOES_POR_DIA, Boolean.TRUE, 0D, null));

        viagemGastoModel.setGastosItens(viagemGastoItemModels);
        return viagemGastoModel;
    }

    private ViagemGastoModel createViagemTarifaAerea() {
        ViagemGastoModel viagemGastoModel = new ViagemGastoModel(TARIFA_AEREA, false, 0D, viagemModel.getId());
        List<ViagemGastoItemModel> viagemGastoItemModels = new ArrayList<>();

        viagemGastoItemModels.add(new ViagemGastoItemModel(TARIFA_AEREA_CUSTO_PESSOA, Boolean.TRUE, 0D, null));
        viagemGastoItemModels.add(new ViagemGastoItemModel(TARIFA_AEREA_ALUGUEL_VEICULO, Boolean.TRUE, 0D, null));

        viagemGastoModel.setGastosItens(viagemGastoItemModels);
        return viagemGastoModel;
    }

    private ViagemGastoModel createViagemGastoGasolina() {
        ViagemGastoModel viagemGastoModel = new ViagemGastoModel(GASOLNA, false, 0D, viagemModel.getId());
        List<ViagemGastoItemModel> viagemGastoItemModels = new ArrayList<>();

        viagemGastoItemModels.add(new ViagemGastoItemModel(GASOLNA_KM_ESTIMADO, Boolean.TRUE, 0D, null));
        viagemGastoItemModels.add(new ViagemGastoItemModel(GASOLNA_KM_POR_LITRO, Boolean.TRUE, 0D, null));
        viagemGastoItemModels.add(new ViagemGastoItemModel(GASOLNA_CUSTO_MEDIO_POR_LITRO, Boolean.TRUE, 0D, null));
        viagemGastoItemModels.add(new ViagemGastoItemModel(GASOLNA_TOTAL_VEICULOS, Boolean.TRUE, 0D, null));

        viagemGastoModel.setGastosItens(viagemGastoItemModels);
        return viagemGastoModel;
    }

    private class SomarGasolina implements TextWatcher {

        private EditText[] editTexts;
        private TextView textView;

        public SomarGasolina(TextView textView, EditText ...editTexts) {
            this.textView = textView;
            this.editTexts = editTexts;
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String estimadoKm = totalEstimadoKm.getText().toString();
            String kmPorLitro = mediaKmLitro.getText().toString();
            String custoLitro = custoMedioLitro.getText().toString();
            String qntVeiculos = totalVeiculos.getText().toString();
            if (estimadoKm.isEmpty() || kmPorLitro.isEmpty() || custoLitro.isEmpty() || qntVeiculos.isEmpty()) {
                textView.setText("");
                addGasolina.setChecked(false);
            } else {
                textView.setText(String.valueOf(((Double.parseDouble(estimadoKm)/Double.parseDouble(kmPorLitro)) * Double.parseDouble(custoLitro))/Double.parseDouble(qntVeiculos)));
            }
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

        @Override
        public void afterTextChanged(Editable editable) {}
    }

    private class SomarHospedagem implements TextWatcher {

        private EditText[] editTexts;
        private TextView textView;

        public SomarHospedagem(TextView textView, EditText ...editTexts) {
            this.textView = textView;
            this.editTexts = editTexts;
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String pernoite = custoEstimadoNoite.getText().toString();
            String qntNoites = totalNoites.getText().toString();
            String qntQuartos = totalQuartos.getText().toString();

            if (pernoite.isEmpty() || qntNoites.isEmpty() || qntQuartos.isEmpty()) {
                textView.setText("");
                addHospedagem.setChecked(false);
            } else {
                textView.setText(
                        String.valueOf((Double.valueOf(pernoite) * Double.parseDouble(qntNoites)) * Double.parseDouble(qntQuartos))
                );
            }
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

        @Override
        public void afterTextChanged(Editable editable) {}
    }

    private class SomarRefeicoes implements TextWatcher {

        private EditText[] editTexts;
        private TextView textView;

        public SomarRefeicoes(TextView textView, EditText ...editTexts) {
            this.textView = textView;
            this.editTexts = editTexts;
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String refeicaoPorPessoa = custoEstimadoRefeicao.getText().toString();
            String refeicoesporDIa = refeicoesDia.getText().toString();

            if (refeicaoPorPessoa.isEmpty() || refeicoesporDIa.isEmpty()) {
                textView.setText("");
                addRefeicao.setChecked(false);
            } else {
                textView.setText(String.valueOf(
                        ((Double.parseDouble(refeicoesporDIa) * viagemModel.getTotalViajantes()) * Double.parseDouble(refeicaoPorPessoa)) * viagemModel.getTotalDias()
                ));
            }

        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

        @Override
        public void afterTextChanged(Editable editable) {}
    }

    private class SomarTarifa implements TextWatcher {

        private EditText[] editTexts;
        private TextView textView;

        public SomarTarifa(TextView textView, EditText ...editTexts) {
            this.textView = textView;
            this.editTexts = editTexts;
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String estimadoPessoa = custoEstimadoPessoa.getText().toString();
            String veiculo = aluguelVeiculo.getText().toString();

            if (estimadoPessoa.isEmpty() || veiculo.isEmpty()) {
                textView.setText("");
                addTarifaAerea.setChecked(false);
            } else {
                textView.setText(String.valueOf((Double.parseDouble(estimadoPessoa) * viagemModel.getTotalViajantes()) + Double.parseDouble(veiculo)));
            }

        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

        @Override
        public void afterTextChanged(Editable editable) {}
    }

    private class ClickListenerSalvarGastos implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            viagemModel.setCustoTotal(0D);
            for (ViagemGastoModel gasto : viagemModel.getGastos()) {
                switch (gasto.getDescricao()) {
                    case GASOLNA:
                        setValoresGasolina(gasto);
                        String gasolina = totalGasolina.getText().toString();
                        if (!gasolina.isEmpty()) {
                            gasto.addAoTotal(Double.parseDouble(gasolina));
                        }
                        break;
                    case HOSPEDAGEM:
                        setValoresHospedagem(gasto);
                        String hospedagem = totalHospedagem.getText().toString();
                        if (!hospedagem.isEmpty()) {
                            gasto.addAoTotal(Double.parseDouble(hospedagem));
                        }
                        break;
                    case TARIFA_AEREA:
                        setValoresTarifaAerea(gasto);
                        String tarifa = totalTarifaAerea.getText().toString();
                        if (!tarifa.isEmpty()) {
                            gasto.addAoTotal(Double.parseDouble(tarifa));
                        }
                        break;
                    case REFEICOES:
                        setValoresRefeicoes(gasto);
                        String refeicoes = totalRefeicoes.getText().toString();
                        if (!refeicoes.isEmpty()) {
                            gasto.addAoTotal(Double.parseDouble(refeicoes));
                        }
                        break;
                    case DIVERSOS:
                        String diversos = totalDiversos.getText().toString();
                        if (!diversos.isEmpty()) {
                            gasto.setTotal(Double.parseDouble(diversos));
                        }
                }
                if (gasto.getAddToViagem()) {
                    viagemModel.addAoTotal(gasto.getTotal());
                }
            }
            viagemDao.save(viagemModel);
            startActivity(new Intent(CadastrarViagemActivity.this, ListaViagensActivity.class));
        }

        private void setValoresRefeicoes(ViagemGastoModel gasto) {
            gasto.setAddToViagem(addRefeicao.isChecked());
            for (ViagemGastoItemModel gastosIten : gasto.getGastosItens()) {
                switch (gastosIten.getDescricao()) {
                    case REFEICOES_CUSTO:
                        if (!custoEstimadoRefeicao.getText().toString().isEmpty()) {
                            gastosIten.setValor(Double.parseDouble(custoEstimadoRefeicao.getText().toString()));
                        } else {
                            gastosIten.setValor(0D);
                        }
                        break;
                    case REFEICOES_POR_DIA:
                        if (!refeicoesDia.getText().toString().isEmpty()) {
                            gastosIten.setValor(Double.parseDouble(refeicoesDia.getText().toString()));
                        } else {
                            gastosIten.setValor(0D);
                        }
                        break;
                }
            }
        }

        private void setValoresHospedagem(ViagemGastoModel gasto) {
            gasto.setAddToViagem(addHospedagem.isChecked());
            for (ViagemGastoItemModel gastosIten : gasto.getGastosItens()) {
                switch (gastosIten.getDescricao()) {
                    case HOSPEDAGEM_PERNOITE:
                        if (!custoEstimadoNoite.getText().toString().isEmpty()) {
                            gastosIten.setValor(Double.parseDouble(custoEstimadoNoite.getText().toString()));
                        } else {
                            gastosIten.setValor(0D);
                        }
                        break;
                    case HOSPEDAGEM_TOTAL_NOITES:
                        if (!totalNoites.getText().toString().isEmpty()) {
                            gastosIten.setValor(Double.parseDouble(totalNoites.getText().toString()));
                        } else {
                            gastosIten.setValor(0D);
                        }
                }
            }
        }

        private void setValoresGasolina(ViagemGastoModel gasto) {
            gasto.setAddToViagem(addGasolina.isChecked());
            for (ViagemGastoItemModel gastosIten : gasto.getGastosItens()) {
                switch (gastosIten.getDescricao()) {
                    case GASOLNA_KM_ESTIMADO:
                        if (!totalEstimadoKm.getText().toString().isEmpty()) {
                            gastosIten.setValor(Double.parseDouble(totalEstimadoKm.getText().toString()));
                        } else {
                            gastosIten.setValor(0D);
                        }
                        break;
                    case GASOLNA_KM_POR_LITRO:
                        if (!mediaKmLitro.getText().toString().isEmpty()) {
                            gastosIten.setValor(Double.parseDouble(mediaKmLitro.getText().toString()));
                        } else {
                            gastosIten.setValor(0D);
                        }
                        break;
                    case GASOLNA_CUSTO_MEDIO_POR_LITRO:
                        if (!custoMedioLitro.getText().toString().isEmpty()) {
                            gastosIten.setValor(Double.parseDouble(custoMedioLitro.getText().toString()));
                        } else {
                            gastosIten.setValor(0D);
                        }
                        break;
                    case GASOLNA_TOTAL_VEICULOS:
                        if (!totalVeiculos.getText().toString().isEmpty()) {
                            gastosIten.setValor(Double.parseDouble(totalVeiculos.getText().toString()));
                        } else {
                            gastosIten.setValor(0D);
                        }
                        break;
                }
            }
        }

        private void setValoresTarifaAerea(ViagemGastoModel gasto) {
            gasto.setAddToViagem(addTarifaAerea.isChecked());
            for (ViagemGastoItemModel gastosIten : gasto.getGastosItens()) {
                switch (gastosIten.getDescricao()) {
                    case TARIFA_AEREA_ALUGUEL_VEICULO:
                        if (!aluguelVeiculo.getText().toString().isEmpty()) {
                            gastosIten.setValor(Double.parseDouble(aluguelVeiculo.getText().toString()));
                        } else {
                            gastosIten.setValor(0D);
                        }
                        break;
                    case TARIFA_AEREA_CUSTO_PESSOA:
                        if (!custoEstimadoPessoa.getText().toString().isEmpty()) {
                            gastosIten.setValor(Double.parseDouble(custoEstimadoPessoa.getText().toString()));
                        } else {
                            gastosIten.setValor(0D);
                        }
                        break;
                }
            }
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuItem excluir =  menu.add("Excluir");
        excluir.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
                ViagemGastoItemModel gasto = (ViagemGastoItemModel) listaDiversos.getItemAtPosition(info.position);
                if (gasto.getId() != null) {
                    viagemGastoItemDao.delete(gasto);
                }
                getGasto(DIVERSOS).getGastosItens().remove(info.position);
                totalDiversos.setText("0.0");
                adapter.notifyDataSetChanged();
                Toast.makeText(CadastrarViagemActivity.this, "Gasto excluido", Toast.LENGTH_LONG).show();
                return false;
            }
        });
    }

    private class AddToViagemClickListener implements View.OnClickListener {

        private Switch check;
        private EditText[] editTexts;
        private ViagemGastoModel viagemGastoModel;

        public AddToViagemClickListener(Switch check, ViagemGastoModel viagemGastoModel, EditText ...editTexts) {
            this.check = check;
            this.editTexts = editTexts;
            this.viagemGastoModel = viagemGastoModel;
        }

        @Override
        public void onClick(View view) {
            if (check.isChecked()) {
                EditText first = Arrays.stream(editTexts).filter(x -> x.getText().toString().isEmpty() || x.getText().toString().equals("0.0")).findFirst().orElse(null);
                if (first != null) {
                    Toast.makeText(CadastrarViagemActivity.this, "Preencha todos os campos para adicionar à viagem!", Toast.LENGTH_LONG).show();
                    check.setChecked(false);
                }
            }
        }

    }
}

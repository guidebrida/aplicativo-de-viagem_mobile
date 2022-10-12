package com.br.rai.projeto.views;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.br.rai.projeto.R;
import com.br.rai.projeto.adapters.ViagemAdapter;
import com.br.rai.projeto.database.dao.ViagemDao;
import com.br.rai.projeto.database.models.ViagemModel;
import com.br.rai.projeto.util.Constants;

import java.util.List;

public class ListaViagensActivity extends AppCompatActivity {

    private ListView listaPersonalizada;
    private ViagemAdapter adapter;
    private List<ViagemModel> arl;
    private Button addViagem;
    private ViagemDao viagemDao;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_viagem);
        preferences = PreferenceManager.getDefaultSharedPreferences(ListaViagensActivity.this);
        Long idUser = preferences.getLong(Constants.USER, 0L);

        if (idUser.equals(0L)) {
            Toast.makeText(ListaViagensActivity.this, "Erro ao acessar a tela de viagens", Toast.LENGTH_LONG).show();
            startActivity(new Intent(ListaViagensActivity.this, LoginActivity.class));
        }

        viagemDao = new ViagemDao(ListaViagensActivity.this);
        listaPersonalizada = findViewById(R.id.listviagens);
        addViagem = findViewById(R.id.addViagem);
        registerForContextMenu(listaPersonalizada);
        addViagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });

        arl = viagemDao.listViagem(idUser);

        adapter = new ViagemAdapter(ListaViagensActivity.this, arl);
        listaPersonalizada.setAdapter(adapter);
    }

    public void openDialog(){
        DialogViagem dialogViagem = new DialogViagem();
        dialogViagem.show(getSupportFragmentManager(),"Adicionar Viagem");
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, final ContextMenu.ContextMenuInfo menuInfo) {
        MenuItem excluir =  menu.add("Excluir");
        excluir.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
                ViagemModel viagem = (ViagemModel) listaPersonalizada.getItemAtPosition(info.position);
                viagemDao.delete(viagem.getId());
                arl = viagemDao.listViagem(preferences.getLong(Constants.USER, 0L));
                adapter = new ViagemAdapter(ListaViagensActivity.this, arl);
                listaPersonalizada.setAdapter(adapter);
                return false;
            }
        });
    }

}
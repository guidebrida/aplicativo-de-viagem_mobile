package com.br.rai.projeto;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.br.rai.projeto.database.dao.UsuarioDao;
import com.br.rai.projeto.database.dao.ViagemDao;
import com.br.rai.projeto.database.models.UsuarioModel;
import com.br.rai.projeto.database.models.ViagemGastoItemModel;
import com.br.rai.projeto.database.models.ViagemGastoModel;
import com.br.rai.projeto.database.models.ViagemModel;

import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private UsuarioDao usuarioDao;
    private ViagemDao viagemDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        usuarioDao = new UsuarioDao(MainActivity.this);
        viagemDao = new ViagemDao(MainActivity.this);
//        UsuarioModel usuarioModel = new UsuarioModel("rai", "rai", "rai");
//        usuarioDao.register(usuarioModel);
//        UsuarioModel login = usuarioDao.login("rai", "rai");
//
//        ViagemGastoItemModel viagemGastoItemModel = new ViagemGastoItemModel();
//        viagemGastoItemModel.setDescricao("Total de veiculos");
//        viagemGastoItemModel.setAddAoTotal(Boolean.TRUE);
//        viagemGastoItemModel.setValor(2D);
//
//        ViagemGastoModel viagemGastoModel = new ViagemGastoModel();
//        viagemGastoModel.setAddToViagem(Boolean.TRUE);
//        viagemGastoModel.setDescricao("Gasolina");
//        viagemGastoModel.setTotal(0D);
//        viagemGastoModel.setGastosItens(
//                Collections.singletonList(viagemGastoItemModel)
//        );
//
//        ViagemModel viagemModel = new ViagemModel("Viagem do rai", 20, 2, login.getId());
//        viagemModel.setCustoTotal(2D);
//
//        viagemModel.setGastos(Collections.singletonList(
//                viagemGastoModel
//        ));
//
//        List<ViagemModel> list = viagemDao.listViagem(login.getId());
//        viagemDao.getViagem(list.get(0).getId());
    }
}
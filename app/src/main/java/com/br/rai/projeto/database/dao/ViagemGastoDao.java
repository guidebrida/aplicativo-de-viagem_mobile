package com.br.rai.projeto.database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.br.rai.projeto.database.AbstractDao;
import com.br.rai.projeto.database.DBOpenHelper;
import com.br.rai.projeto.database.models.ViagemGastoItemModel;
import com.br.rai.projeto.database.models.ViagemGastoModel;
import com.br.rai.projeto.database.models.ViagemModel;

import java.util.ArrayList;
import java.util.List;

public class ViagemGastoDao extends AbstractDao {

    private ViagemGastoItemDao viagemGastoItemDao;

    public ViagemGastoDao(Context context) {
        this.context = context;
        dbOpenHelper = new DBOpenHelper(context);
        viagemGastoItemDao = new ViagemGastoItemDao(context);
    }

    public void save(ViagemGastoModel viagemGastoModel, ViagemModel viagemModel) {
        if (viagemGastoModel.getId() != null) {
            this.update(viagemGastoModel);
        } else {
            this.insert(viagemGastoModel, viagemModel);
        }
    }

    public void update(ViagemGastoModel viagemGastoModel) {
        try {
            Open();

            ContentValues values = new ContentValues();
            values.put(ViagemGastoModel.COLUNA_ADD_A_VIAGEM, viagemGastoModel.getAddToViagem());
            values.put(ViagemGastoModel.COLUNA_TOTAL, viagemGastoModel.getTotal());
            db.update(ViagemGastoModel.TABLE, values, String.format("%s = %s;", ViagemGastoModel.COLUNA_ID, viagemGastoModel.getId()), null);

            for (ViagemGastoItemModel gastosIten : viagemGastoModel.getGastosItens()) {
                this.viagemGastoItemDao.save(gastosIten, viagemGastoModel);
            }
        } finally {
            Close();
        }
    }

    public void insert(ViagemGastoModel viagemGastoModel, ViagemModel viagemModel) {
        try {
            Open();

            ContentValues values = new ContentValues();
            values.put(ViagemGastoModel.COLUNA_DESCRICAO, viagemGastoModel.getDescricao());
            values.put(ViagemGastoModel.COLUNA_ADD_A_VIAGEM, viagemGastoModel.getAddToViagem());
            values.put(ViagemGastoModel.COLUNA_TOTAL, viagemGastoModel.getTotal());
            values.put(ViagemGastoModel.COLUNA_FK_VIAGEM, viagemModel.getId());
            long id = db.insert(ViagemGastoModel.TABLE, null, values);
            viagemGastoModel.setId(id);

            for (ViagemGastoItemModel gastosIten : viagemGastoModel.getGastosItens()) {
                this.viagemGastoItemDao.save(gastosIten, viagemGastoModel);
            }
        } finally {
            Close();
        }
    }

    public List<ViagemGastoModel> getGastosByViagem(Long idViagem) {
        List<ViagemGastoModel> list = new ArrayList<>();

        try {
            Open();

            Cursor query = db.query(ViagemGastoModel.TABLE, new String[]{"*"}, String.format("%s = %s;", ViagemGastoModel.COLUNA_FK_VIAGEM, idViagem), null, null, null, null);
            query.moveToFirst();
            while (!query.isAfterLast()) {
                ViagemGastoModel viagemGastoModel = new ViagemGastoModel(query.getLong(0), query.getString(1), query.getInt(3) > 0, query.getDouble(2), query.getLong(4));
                viagemGastoModel.setGastosItens(viagemGastoItemDao.getGastoItemByGasto(viagemGastoModel.getId()));
                list.add(viagemGastoModel);
                query.moveToNext();
            }
        } finally {
            Close();
        }
        return list;
    }
}

package com.br.rai.projeto.database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.br.rai.projeto.database.AbstractDao;
import com.br.rai.projeto.database.DBOpenHelper;
import com.br.rai.projeto.database.models.ViagemGastoItemModel;
import com.br.rai.projeto.database.models.ViagemGastoModel;

import java.util.ArrayList;
import java.util.List;

public class ViagemGastoItemDao extends AbstractDao {

    public ViagemGastoItemDao(Context context) {
        this.context = context;
        dbOpenHelper = new DBOpenHelper(context);
    }

    public void save(ViagemGastoItemModel viagemGastoItemModel, ViagemGastoModel viagemGastoModel) {
        if (viagemGastoItemModel.getId() != null) {
            this.update(viagemGastoItemModel);
        } else {
            this.insert(viagemGastoItemModel, viagemGastoModel);
        }
    }

    public void update(ViagemGastoItemModel viagemGastoItemModel) {
        try {
            Open();

            ContentValues values = new ContentValues();
            values.put(ViagemGastoItemModel.COLUNA_DESCRICAO, viagemGastoItemModel.getDescricao());
            values.put(ViagemGastoItemModel.COLUNA_ADD_AO_TOTAL, viagemGastoItemModel.getAddAoTotal());
            values.put(ViagemGastoItemModel.COLUNA_VALOR, viagemGastoItemModel.getValor());
            db.update(ViagemGastoItemModel.TABLE, values, String.format("%s = %s;", ViagemGastoModel.COLUNA_ID, viagemGastoItemModel.getId()), null);

        } finally {
            Close();
        }
    }

    public void insert(ViagemGastoItemModel viagemGastoItemModel, ViagemGastoModel viagemGastoModel) {
        try {
            Open();

            ContentValues values = new ContentValues();
            values.put(ViagemGastoItemModel.COLUNA_DESCRICAO, viagemGastoItemModel.getDescricao());
            values.put(ViagemGastoItemModel.COLUNA_ADD_AO_TOTAL, viagemGastoItemModel.getAddAoTotal());
            values.put(ViagemGastoItemModel.COLUNA_VALOR, viagemGastoItemModel.getValor());
            values.put(ViagemGastoItemModel.COLUNA_FK_VIAGEM_GASTO, viagemGastoModel.getId());
            db.insert(ViagemGastoItemModel.TABLE, null, values);

        } finally {
            Close();
        }
    }

    public List<ViagemGastoItemModel> getGastoItemByGasto(Long gastoId) {
        List<ViagemGastoItemModel> list = new ArrayList<>();
        try {
            Open();

            Cursor query = db.query(ViagemGastoItemModel.TABLE, new String[]{"*"}, String.format("%s = %s;", ViagemGastoItemModel.COLUNA_FK_VIAGEM_GASTO, gastoId), null, null, null, null);
            query.moveToFirst();
            while (!query.isAfterLast()) {
                ViagemGastoItemModel viagemGastoItemModel = new ViagemGastoItemModel(query.getLong(0), query.getString(1), query.getInt(3) > 0, query.getDouble(2), query.getLong(4));
                list.add(viagemGastoItemModel);
                query.moveToNext();
            }
        } finally {
            Close();
        }
        return list;
    }
}

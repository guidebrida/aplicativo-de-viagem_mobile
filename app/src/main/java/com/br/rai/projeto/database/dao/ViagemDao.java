package com.br.rai.projeto.database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Path;

import com.br.rai.projeto.database.AbstractDao;
import com.br.rai.projeto.database.DBOpenHelper;
import com.br.rai.projeto.database.models.ViagemGastoModel;
import com.br.rai.projeto.database.models.ViagemModel;

import java.util.ArrayList;
import java.util.List;

public class ViagemDao extends AbstractDao {

    private ViagemGastoDao viagemGastoDao;

    public ViagemDao(Context context) {
        this.context = context;
        dbOpenHelper = new DBOpenHelper(context);
        this.viagemGastoDao = new ViagemGastoDao(context);
    }

    public ViagemModel save(ViagemModel viagemModel) {
        if (viagemModel.getId() != null) {
            return this.update(viagemModel);
        }
        return this.persist(viagemModel);
    }

    private ViagemModel update(ViagemModel viagemModel) {
        try {
            Open();

            ContentValues values = new ContentValues();
            values.put(ViagemModel.COLUNA_CUSTO_TOTAL, viagemModel.getCustoTotal());
            db.update(ViagemModel.TABLE, values, String.format("%s = %s;", ViagemModel.COLUNA_ID, viagemModel.getId()), null);

            for (ViagemGastoModel gasto : viagemModel.getGastos()) {
                this.viagemGastoDao.save(gasto, viagemModel);
            }
        } finally {
            Close();
        }
        return viagemModel;
    }

    private ViagemModel persist(ViagemModel viagemModel) {
        try {
            Open();

            ContentValues values = new ContentValues();
            values.put(ViagemModel.COLUNA_DESCRICAO, viagemModel.getDescricao());
            values.put(ViagemModel.COLUNA_TOTAL_VIAJANTES, viagemModel.getTotalViajantes());
            values.put(ViagemModel.COLUNA_TOTAL_DIAS, viagemModel.getTotalDias());
            values.put(ViagemModel.COLUNA_FK_CLIENTE, viagemModel.getiUsuario());
            values.put(ViagemModel.COLUNA_CUSTO_TOTAL, viagemModel.getCustoTotal());
            long id = db.insert(ViagemModel.TABLE, null, values);
            viagemModel.setId(id);

            for (ViagemGastoModel gasto : viagemModel.getGastos()) {
                this.viagemGastoDao.save(gasto, viagemModel);
            }
        } finally {
            Close();
        }
        return viagemModel;
    }

    public List<ViagemModel> listViagem(Long clienteId) {
        List<ViagemModel> list = new ArrayList<>();
        try {
            Open();

            Cursor query = db.query(ViagemModel.TABLE, new String[]{"*"}, String.format("%s = %s;", ViagemModel.COLUNA_FK_CLIENTE, clienteId), null, null, null, null);
            if (query.getCount() > 0) {
                query.moveToFirst();
                while (!query.isAfterLast()) {
                    list.add(
                            new ViagemModel(query.getLong(0), query.getString(1), query.getInt(2), query.getInt(3), query.getDouble(4), query.getLong(5))
                    );
                    query.moveToNext();
                }
            }
        } finally {
            Close();
        }
        return list;
    }

    public ViagemModel getViagem(Long viagemId) {
        ViagemModel viagem = new ViagemModel();
        try {
            Open();
            Cursor query = db.query(ViagemModel.TABLE, new String[]{"*"}, String.format("%s = %s;", ViagemModel.COLUNA_ID, viagemId), null, null, null, null);
            if (query.getCount() > 0) {
                query.moveToFirst();
                viagem = new ViagemModel(query.getLong(0), query.getString(1), query.getInt(2), query.getInt(3), query.getDouble(4), query.getLong(5));
                viagem.setGastos(viagemGastoDao.getGastosByViagem(viagem.getId()));
            }
        } finally {
            Close();
        }
        return viagem;
    }

    public void delete(Long viagemId) {
        try {
            Open();
            db.delete(ViagemModel.TABLE, String.format("%s = %s;", ViagemModel.COLUNA_ID, viagemId), null);
        } finally {
            Close();
        }
    }
}

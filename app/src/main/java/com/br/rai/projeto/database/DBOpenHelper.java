package com.br.rai.projeto.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.br.rai.projeto.database.models.UsuarioModel;
import com.br.rai.projeto.database.models.ViagemGastoItemModel;
import com.br.rai.projeto.database.models.ViagemGastoModel;
import com.br.rai.projeto.database.models.ViagemModel;

public class DBOpenHelper extends SQLiteOpenHelper {

    private static final String DB = "DB";
    private static final int DB_VERSION = 1;
    public DBOpenHelper(Context context) {
        super(context, DB, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(UsuarioModel.CREATE_TABLE);
        sqLiteDatabase.execSQL(ViagemModel.CREATE_TABLE);
        sqLiteDatabase.execSQL(ViagemGastoModel.CREATE_TABLE);;
        sqLiteDatabase.execSQL(ViagemGastoItemModel.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(UsuarioModel.DROP_TABLE);
        sqLiteDatabase.execSQL(UsuarioModel.CREATE_TABLE);
        sqLiteDatabase.execSQL(ViagemModel.DROP_TABLE);
        sqLiteDatabase.execSQL(ViagemModel.CREATE_TABLE);
        sqLiteDatabase.execSQL(ViagemGastoModel.DROP_TABLE);
        sqLiteDatabase.execSQL(ViagemGastoModel.CREATE_TABLE);;
        sqLiteDatabase.execSQL(ViagemGastoItemModel.DROP_TABLE);;
        sqLiteDatabase.execSQL(ViagemGastoItemModel.CREATE_TABLE);
    }
}

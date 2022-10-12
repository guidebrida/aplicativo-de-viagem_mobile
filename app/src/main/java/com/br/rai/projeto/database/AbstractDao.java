package com.br.rai.projeto.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public abstract class AbstractDao {
    protected SQLiteDatabase db;
    protected DBOpenHelper dbOpenHelper;
    protected Context context;

    protected final void Open() {
        db = dbOpenHelper.getWritableDatabase();
    }

    protected final void Close() {
        dbOpenHelper.close();
    }

    protected void showToast(String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
}

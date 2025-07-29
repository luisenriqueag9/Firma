package com.grupo2.firma;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "firmas.db";
    private static final int DB_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE signatures (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "descripcion TEXT, " +
                "firmaDigital BLOB)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS signatures");
        onCreate(db);
    }

    public void insertarFirma(Signature firma) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("descripcion", firma.getDescripcion());
        valores.put("firmaDigital", firma.getFirmaDigital());
        db.insert("signatures", null, valores);
        db.close();
    }

    public List<Signature> obtenerFirmas() {
        List<Signature> lista = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT descripcion, firmaDigital FROM signatures", null);
        if (cursor.moveToFirst()) {
            do {
                String desc = cursor.getString(0);
                byte[] firma = cursor.getBlob(1);
                lista.add(new Signature(desc, firma));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return lista;
    }
}

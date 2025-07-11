package com.example.gastospersonales.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public static final String NOMBRE_DB = "gastos.db";
    public static final int VERSION_DB = 1;

    public DBHelper(Context context) {
        super(context, NOMBRE_DB, null, VERSION_DB);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE transacciones (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "tipo TEXT CHECK(tipo IN ('ingreso', 'egreso')) NOT NULL," +
                        "monto REAL NOT NULL," +
                        "categoria TEXT," +
                        "descripcion TEXT," +
                        "fecha TEXT NOT NULL" +
                        ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS transacciones");
        onCreate(db);
    }
}

package com.example.gastospersonales.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.gastospersonales.models.Transaccion;

import java.util.ArrayList;
import java.util.List;

public class TransaccionRepository {

    private DBHelper dbHelper;

    public TransaccionRepository(Context context) {
        dbHelper = new DBHelper(context);
    }

    public long insertar(Transaccion transaccion) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("tipo", transaccion.getTipo());
        valores.put("monto", transaccion.getMonto());
        valores.put("categoria", transaccion.getCategoria());
        valores.put("descripcion", transaccion.getDescripcion());
        valores.put("fecha", transaccion.getFecha());
        return db.insert("transacciones", null, valores);
    }

    public List<Transaccion> obtenerTodas() {
        List<Transaccion> lista = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM transacciones ORDER BY fecha DESC", null);

        if (cursor.moveToFirst()) {
            do {
                Transaccion t = new Transaccion(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getDouble(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5)
                );
                lista.add(t);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return lista;
    }

    public double calcularSaldo() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        double ingresos = 0;
        double egresos = 0;

        Cursor ingresosCursor = db.rawQuery("SELECT SUM(monto) FROM transacciones WHERE tipo = 'ingreso'", null);
        if (ingresosCursor.moveToFirst()) {
            ingresos = ingresosCursor.getDouble(0);
        }
        ingresosCursor.close();

        Cursor egresosCursor = db.rawQuery("SELECT SUM(monto) FROM transacciones WHERE tipo = 'egreso'", null);
        if (egresosCursor.moveToFirst()) {
            egresos = egresosCursor.getDouble(0);
        }
        egresosCursor.close();

        return ingresos - egresos;
    }
}

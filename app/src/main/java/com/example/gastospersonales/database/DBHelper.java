package com.example.gastospersonales.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.gastospersonales.models.Transaccion;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "expenses_app.db";
    private static final int DATABASE_VERSION = 1;

    // Tabla de Transacciones
    public static final String TABLE_TRANSACTIONS = "transactions";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TYPE = "type"; // Ingreso/Gasto
    public static final String COLUMN_AMOUNT = "amount";
    public static final String COLUMN_CATEGORY = "category";
    public static final String COLUMN_PAYMENT_METHOD = "payment_method";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_TIMESTAMP = "timestamp";

    // Sentencia SQL para crear la tabla de transacciones
    private static final String CREATE_TABLE_TRANSACTIONS = "CREATE TABLE " + TABLE_TRANSACTIONS + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_TYPE + " TEXT NOT NULL,"
            + COLUMN_AMOUNT + " REAL NOT NULL,"
            + COLUMN_CATEGORY + " TEXT,"
            + COLUMN_PAYMENT_METHOD + " TEXT,"
            + COLUMN_DESCRIPTION + " TEXT,"
            + COLUMN_TIMESTAMP + " TEXT DEFAULT CURRENT_TIMESTAMP" + ")";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_TRANSACTIONS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRANSACTIONS);
        onCreate(db);
    }

    // --- Métodos CRUD para Transacciones ---

    // Añadir una nueva transacción
    public long addTransaction(Transaccion transaction) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TYPE, transaction.getType());
        values.put(COLUMN_AMOUNT, transaction.getAmount());
        values.put(COLUMN_CATEGORY, transaction.getCategory());
        values.put(COLUMN_PAYMENT_METHOD, transaction.getPaymentMethod());
        values.put(COLUMN_DESCRIPTION, transaction.getDescription());
        values.put(COLUMN_TIMESTAMP, transaction.getTimestamp()); // Asegúrate de que este valor esté formateado correctamente si lo pasas manualmente

        // Insertar fila
        long id = db.insert(TABLE_TRANSACTIONS, null, values);
        db.close();
        return id;
    }

    // Obtener todas las transacciones
    public List<Transaccion> getAllTransactions() {
        List<Transaccion> transactionList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_TRANSACTIONS + " ORDER BY " + COLUMN_TIMESTAMP + " DESC"; // Ordenar por fecha descendente

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // Iterar sobre todas las filas y añadir a la lista
        if (cursor.moveToFirst()) {
            do {
                Transaccion transaction = new Transaccion(
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TYPE)),
                        cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_AMOUNT)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CATEGORY)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PAYMENT_METHOD)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TIMESTAMP))
                );
                transactionList.add(transaction);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return transactionList;
    }

    // Obtener el balance total (Ingresos - Gastos)
    public double getTotalBalance() {
        double totalIncome = 0;
        double totalExpense = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + COLUMN_TYPE + ", " + COLUMN_AMOUNT + " FROM " + TABLE_TRANSACTIONS, null);

        if (cursor.moveToFirst()) {
            do {
                String type = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TYPE));
                double amount = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_AMOUNT));

                if ("Ingreso".equalsIgnoreCase(type)) {
                    totalIncome += amount;
                } else if ("Gasto".equalsIgnoreCase(type)) {
                    totalExpense += amount;
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return totalIncome - totalExpense;
    }

    // Otros métodos: updateTransaction, deleteTransaction (opcional, pero recomendados)
    // public int updateTransaction(Transaction transaction) { ... }
    // public void deleteTransaction(int id) { ... }
}

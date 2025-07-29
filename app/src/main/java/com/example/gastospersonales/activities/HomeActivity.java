package com.example.gastospersonales.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gastospersonales.R;
import com.example.gastospersonales.database.DBHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.example.gastospersonales.adapters.TransactionAdapter;
import com.example.gastospersonales.models.Transaccion;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class HomeActivity extends AppCompatActivity {

    private static final int ADD_TRANSACTION_REQUEST_CODE = 1;

    private TextView tvTotalBalance;
    private RecyclerView rvTransactions;
    private FloatingActionButton fabAdd;
    private BottomNavigationView bottomNavigationView;

    private DBHelper dbHelper;
    private TransactionAdapter transactionAdapter;
    private List<Transaccion> transactionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Inicializar vistas
        tvTotalBalance = findViewById(R.id.tvTotalBalance);
        rvTransactions = findViewById(R.id.rvTransactions);
        fabAdd = findViewById(R.id.fabAdd);
        bottomNavigationView = findViewById(R.id.bottom_navigation);

        dbHelper = new DBHelper(this);
        transactionList = new ArrayList<>();
        transactionAdapter = new TransactionAdapter(transactionList);

        rvTransactions.setLayoutManager(new LinearLayoutManager(this));
        rvTransactions.setAdapter(transactionAdapter);

        // Configurar FAB para abrir AddTransactionActivity
        fabAdd.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, TransaccionActivity.class);
            startActivityForResult(intent, ADD_TRANSACTION_REQUEST_CODE); // Usar forResult para recargar datos
        });

        // Configurar Bottom Navigation (manejo básico de selección)
        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.nav_home) {
                // Ya estamos en Inicio, puedes recargar datos si es necesario o simplemente no hacer nada
                loadTransactions();
                updateTotalBalance();
                return true;
            } else if (item.getItemId() == R.id.nav_profile) {
                Toast.makeText(HomeActivity.this, "Navegar a Perfil", Toast.LENGTH_SHORT).show();
                // Aquí podrías iniciar una nueva actividad de Perfil
                return true;
            }
            return false;
        });

        // Cargar datos iniciales
        loadTransactions();
        updateTotalBalance();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Recargar datos cada vez que la actividad se vuelve a enfocar (ej. al volver de AddTransactionActivity)
        loadTransactions();
        updateTotalBalance();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_TRANSACTION_REQUEST_CODE && resultCode == RESULT_OK) {
            // Si la transacción se añadió con éxito, recarga la lista
            loadTransactions();
            updateTotalBalance();
        }
    }

    private void loadTransactions() {
        List<Transaccion> transactions = dbHelper.getAllTransactions();
        transactionAdapter.updateTransactions(transactions);
        if (transactions.isEmpty()) {
            // Opcional: Mostrar un mensaje o imagen de "No hay transacciones"
            // Toast.makeText(this, "No hay transacciones aún.", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateTotalBalance() {
        double total = dbHelper.getTotalBalance();
        tvTotalBalance.setText(String.format(Locale.getDefault(), "$%,.2f", total)); // Formato de moneda
        // Puedes cambiar el color del texto si el total es negativo (rojo) o positivo (verde)
        if (total < 0) {
            tvTotalBalance.setTextColor(Color.parseColor("#FF6347")); // Naranja/Rojo
        } else {
            tvTotalBalance.setTextColor(Color.parseColor("#00FFFF")); // Cian de tu imagen
        }
    }
}

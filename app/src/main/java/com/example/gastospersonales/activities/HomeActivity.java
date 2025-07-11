package com.example.gastospersonales.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gastospersonales.R;
import com.example.gastospersonales.database.TransaccionRepository;

public class HomeActivity extends AppCompatActivity {

    private TextView saldoTotal;
    private Button btnAgregar, btnHistorial, btnPresupuesto, btnCategorias;
    private TransaccionRepository repo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        saldoTotal = findViewById(R.id.saldoTotal);
        btnAgregar = findViewById(R.id.btnAgregarMovimiento);
        btnHistorial = findViewById(R.id.btnVerHistorial);
        btnPresupuesto = findViewById(R.id.btnPresupuesto);
        btnCategorias = findViewById(R.id.btnCategorias);

        repo = new TransaccionRepository(this);
        double saldo = repo.calcularSaldo();

        // Aquí eventualmente puedes mostrar el saldo real
        saldoTotal.setText(String.format("Saldo Total: $%.2f", saldo));

        btnAgregar.setOnClickListener(v -> {
            // ir a pantalla de agregar gasto/ingreso
        });

        btnHistorial.setOnClickListener(v -> {
            // ir al historial de movimientos
        });

        btnPresupuesto.setOnClickListener(v -> {
            // ir al módulo de presupuesto mensual
        });

        btnCategorias.setOnClickListener(v -> {
            // ir a categorías
        });
    }
}

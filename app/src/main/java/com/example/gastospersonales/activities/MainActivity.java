package com.example.gastospersonales.activities;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.gastospersonales.R;
import com.example.gastospersonales.auth.PinActivity;
import com.example.gastospersonales.auth.SetPinActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences prefs = getSharedPreferences("gastos_prefs", Context.MODE_PRIVATE);
        String pin = prefs.getString("clave_pin", null);

        if (pin == null) {
            // No hay PIN → ir a configuración
            startActivity(new Intent(this, SetPinActivity.class));
        } else {
            // Hay PIN → ir a validación
            startActivity(new Intent(this, PinActivity.class));
        }

        finish(); // Cerramos esta actividad temporal
    }
}
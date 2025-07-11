package com.example.gastospersonales.auth;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.example.gastospersonales.R;
import com.example.gastospersonales.activities.HomeActivity;
import com.example.gastospersonales.activities.MainActivity;

public class PinActivity extends AppCompatActivity {

    private EditText pinInput;
    private Button ingresarBtn;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin);

        pinInput = findViewById(R.id.pinInput);
        ingresarBtn = findViewById(R.id.ingresarBtn);
        prefs = getSharedPreferences("gastos_prefs", Context.MODE_PRIVATE);

        ingresarBtn.setOnClickListener(v -> validarPin());
    }

    private void validarPin() {
        String pinGuardado = prefs.getString("clave_pin", null);
        String pinIngresado = pinInput.getText().toString();

        if (pinGuardado == null) {
            Toast.makeText(this, "No hay PIN guardado. Debes configurarlo primero.", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, SetPinActivity.class);
            startActivity(intent);
            finish();
        }

        if (pinIngresado.equals(pinGuardado)) {
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "PIN incorrecto", Toast.LENGTH_SHORT).show();
            pinInput.setText("");
        }
    }
}
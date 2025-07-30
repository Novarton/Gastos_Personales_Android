package com.example.gastospersonales.auth;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gastospersonales.R;

public class SetPinActivity extends AppCompatActivity {

    private EditText newPinInput, confirmPinInput;
    private Button guardarPinBtn;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_pin);

        newPinInput = findViewById(R.id.newPinInput);
        confirmPinInput = findViewById(R.id.confirmPinInput);
        guardarPinBtn = findViewById(R.id.ingresarBtn);
        prefs = getSharedPreferences("gastos_prefs", Context.MODE_PRIVATE);

        guardarPinBtn.setOnClickListener(v -> guardarPin());
    }

    private void guardarPin() {
        String nuevoPin = newPinInput.getText().toString();
        String confirmarPin = confirmPinInput.getText().toString();

        if (nuevoPin.length() < 4) {
            Toast.makeText(this, "El PIN debe tener al menos 4 dígitos", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!nuevoPin.equals(confirmarPin)) {
            Toast.makeText(this, "Los PIN no coinciden", Toast.LENGTH_SHORT).show();
            return;
        }

        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("clave_pin", nuevoPin);
        editor.apply();

        Toast.makeText(this, "PIN guardado correctamente", Toast.LENGTH_SHORT).show();

        // Redirigir al login
        startActivity(new Intent(this, PinActivity.class));
        finish();
    }
}
package com.example.gastospersonales.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gastospersonales.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.example.gastospersonales.utils.NavigationHelper;


public class ProfileActivity extends AppCompatActivity {

    private EditText currentPinInput, newPinInput, confirmNewPinInput;
    private Button saveChangesBtn;
    private SharedPreferences prefs;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Inputs
        currentPinInput = findViewById(R.id.currentPinInput);
        newPinInput = findViewById(R.id.newPinInput);
        confirmNewPinInput = findViewById(R.id.confirmNewPinInput);
        saveChangesBtn = findViewById(R.id.saveChangesBtn);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        NavigationHelper.setupBottomNavigation(bottomNavigationView, this);

        // SharedPreferences
        prefs = getSharedPreferences("gastos_prefs", Context.MODE_PRIVATE);

        saveChangesBtn.setOnClickListener(v -> actualizarPin());
    }

    private void actualizarPin() {
        String currentPin = currentPinInput.getText().toString();
        String newPin = newPinInput.getText().toString();
        String confirmNewPin = confirmNewPinInput.getText().toString();

        String pinGuardado = prefs.getString("clave_pin", null);

        if (pinGuardado == null) {
            Toast.makeText(this, "No hay PIN registrado", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!currentPin.equals(pinGuardado)) {
            Toast.makeText(this, "El PIN actual no es correcto", Toast.LENGTH_SHORT).show();
            return;
        }

        if (newPin.length() < 4) {
            Toast.makeText(this, "El nuevo PIN debe tener al menos 4 dígitos", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!newPin.equals(confirmNewPin)) {
            Toast.makeText(this, "Los nuevos PIN no coinciden", Toast.LENGTH_SHORT).show();
            return;
        }

        // Guardar nuevo PIN
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("clave_pin", newPin);
        editor.apply();

        Toast.makeText(this, "PIN actualizado correctamente", Toast.LENGTH_SHORT).show();
        currentPinInput.setText("");
        newPinInput.setText("");
        confirmNewPinInput.setText("");

    }

}

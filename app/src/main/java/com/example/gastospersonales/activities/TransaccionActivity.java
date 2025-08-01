package com.example.gastospersonales.activities;
import android.graphics.Color;
import android.os.Bundle;
import com.example.gastospersonales.R;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.example.gastospersonales.database.DBHelper;
import com.example.gastospersonales.models.Transaccion;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class TransaccionActivity extends AppCompatActivity {
    private Button btnIncome, btnExpense, btnSaveTransaction;
    private TextView tvTransactionDate;
    private Spinner spinnerCategory, spinnerPaymentMethod;
    private EditText etAmount, etDescription;
    private ImageButton btnClose;

    private String transactionType = "Ingreso"; // Por defecto, Ingreso
    private DBHelper dbHelper;
    private Calendar calendar;

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // This line loads the layout from activity_transaction.xml
        setContentView(R.layout.activity_transaccion);
        // Inicializar vistas
        btnIncome = findViewById(R.id.btnIncome);
        btnExpense = findViewById(R.id.btnExpense);
        btnSaveTransaction = findViewById(R.id.btnSaveTransaction);
        tvTransactionDate = findViewById(R.id.tvTransactionDate);
        spinnerCategory = findViewById(R.id.spinnerCategory);
        spinnerPaymentMethod = findViewById(R.id.spinnerPaymentMethod);
        etAmount = findViewById(R.id.etAmount);
        etDescription = findViewById(R.id.etDescription);

        dbHelper = new DBHelper(this);
        calendar = Calendar.getInstance(); // Inicializar el calendario con la fecha y hora actuales

        // Configurar Spinners
        ArrayAdapter<CharSequence> categoryAdapter = ArrayAdapter.createFromResource(this,
                R.array.categories_array, android.R.layout.simple_spinner_item);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(categoryAdapter);

        ArrayAdapter<CharSequence> paymentMethodAdapter = ArrayAdapter.createFromResource(this,
                R.array.payment_methods_array, android.R.layout.simple_spinner_item);
        paymentMethodAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPaymentMethod.setAdapter(paymentMethodAdapter);

        // Establecer la fecha y hora actuales por defecto en el TextView
        updateDateTimeDisplay();

        // Listeners
        btnIncome.setOnClickListener(v -> {
            transactionType = "Ingreso";
            btnIncome.setBackgroundColor(Color.parseColor("#FF6347"));
            btnExpense.setBackgroundColor(Color.parseColor("#FFBB86FC"));

        });

        btnExpense.setOnClickListener(v -> {
            transactionType = "Gasto";
            btnIncome.setBackgroundColor(Color.parseColor("#FFBB86FC"));
            btnExpense.setBackgroundColor(Color.parseColor("#FFAC33"));
        });

        tvTransactionDate.setOnClickListener(v -> showDateTimeDialog());

        btnSaveTransaction.setOnClickListener(v -> saveTransaction());
    }
    private void updateDateTimeDisplay() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm a | dd MMM, yyyy", Locale.getDefault());
        tvTransactionDate.setText(dateFormat.format(calendar.getTime()));
    }

    private void showDateTimeDialog() {
        new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            new TimePickerDialog(this, (timeView, hourOfDay, minute) -> {
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);
                updateDateTimeDisplay();
            }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false).show(); // 'false' para formato 12 horas
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }
    private void saveTransaction() {
        String amountStr = etAmount.getText().toString().trim();
        String category = spinnerCategory.getSelectedItem().toString();
        String paymentMethod = spinnerPaymentMethod.getSelectedItem().toString();
        String description = etDescription.getText().toString().trim();
        String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(calendar.getTime());

        if (amountStr.isEmpty()) {
            etAmount.setError("La cantidad es requerida");
            return;
        }
        if (category.equals("Selecciona una categoría") || category.isEmpty()) {
            Toast.makeText(this, "Por favor, selecciona una categoría", Toast.LENGTH_SHORT).show();
            return;
        }
        if (paymentMethod.equals("Selecciona método de pago") || paymentMethod.isEmpty()) {
            Toast.makeText(this, "Por favor, selecciona un método de pago", Toast.LENGTH_SHORT).show();
            return;
        }


        double amount = Double.parseDouble(amountStr);

        Transaccion newTransaction = new Transaccion(transactionType, amount, category, paymentMethod, description, timestamp);
        long id = dbHelper.addTransaction(newTransaction);

        if (id > 0) {
            Toast.makeText(this, "Transacción guardada con éxito", Toast.LENGTH_SHORT).show();
            // Opcional: Reiniciar campos o cerrar la actividad
            finish(); // Cierra la actividad y regresa a la anterior (MainActivity)
        } else {
            Toast.makeText(this, "Error al guardar la transacción", Toast.LENGTH_SHORT).show();
        }
    }
}

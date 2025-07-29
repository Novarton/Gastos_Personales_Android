package com.example.gastospersonales.adapters;


import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gastospersonales.R;
import com.example.gastospersonales.models.Transaccion;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder> {

    private List<Transaccion> transactionList;

    public TransactionAdapter(List<Transaccion> transactionList) {
        this.transactionList = transactionList;
    }

    public void updateTransactions(List<Transaccion> newTransactions) {
        this.transactionList.clear();
        this.transactionList.addAll(newTransactions);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_transaction, parent, false);
        return new TransactionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionViewHolder holder, int position) {
        Transaccion transaction = transactionList.get(position);

        holder.tvDescription.setText(transaction.getDescription());

        // Formatear la fecha
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        SimpleDateFormat outputFormat = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault());
        try {
            Date date = inputFormat.parse(transaction.getTimestamp());
            holder.tvDate.setText(outputFormat.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
            holder.tvDate.setText(transaction.getTimestamp()); // En caso de error, muestra el original
        }

        // Mostrar cantidad y colorear según el tipo (Ingreso/Gasto)
        if ("Gasto".equalsIgnoreCase(transaction.getType())) {
            holder.tvAmount.setText("-$" + String.format(Locale.getDefault(), "%.2f", transaction.getAmount()));
            holder.tvAmount.setTextColor(Color.parseColor("#FF6347")); // Naranja/Rojo para gastos
            holder.ivIcon.setImageResource(R.drawable.ic_expense); // Icono para gastos
        } else {
            holder.tvAmount.setText("+$" + String.format(Locale.getDefault(), "%.2f", transaction.getAmount()));
            holder.tvAmount.setTextColor(Color.parseColor("#1A936F")); // Verde para ingresos
            holder.ivIcon.setImageResource(R.drawable.ic_income); // Icono para ingresos
        }
        // Puedes agregar lógica para cambiar ivIcon basado en transaction.getCategory()
    }

    @Override
    public int getItemCount() {
        return transactionList.size();
    }

    public static class TransactionViewHolder extends RecyclerView.ViewHolder {
        ImageView ivIcon;
        TextView tvDescription;
        TextView tvDate;
        TextView tvAmount;

        public TransactionViewHolder(@NonNull View itemView) {
            super(itemView);
            ivIcon = itemView.findViewById(R.id.ivTransactionIcon);
            tvDescription = itemView.findViewById(R.id.tvTransactionDescription);
            tvDate = itemView.findViewById(R.id.tvTransactionDateItem);
            tvAmount = itemView.findViewById(R.id.tvTransactionAmount);
        }
    }
}
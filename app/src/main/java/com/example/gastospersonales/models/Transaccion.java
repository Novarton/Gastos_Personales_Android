package com.example.gastospersonales.models;

public class Transaccion {
    private int id;
    private String type; // "Ingreso" o "Gasto"
    private double amount;
    private String category;
    private String paymentMethod;
    private String description;
    private String timestamp; // Formato "YYYY-MM-DD HH:MM:SS"

    public Transaccion(int id, String type, double amount, String category, String paymentMethod, String description, String timestamp) {
        this.id = id;
        this.type = type;
        this.amount = amount;
        this.category = category;
        this.paymentMethod = paymentMethod;
        this.description = description;
        this.timestamp = timestamp;
    }

    // Constructor sin ID (para nuevas transacciones antes de insertarlas)
    public Transaccion(String type, double amount, String category, String paymentMethod, String description, String timestamp) {
        this.type = type;
        this.amount = amount;
        this.category = category;
        this.paymentMethod = paymentMethod;
        this.description = description;
        this.timestamp = timestamp;
    }

    // Getters y Setters (genera todos)
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getTimestamp() { return timestamp; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }
}

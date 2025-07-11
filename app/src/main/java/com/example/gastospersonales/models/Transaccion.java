package com.example.gastospersonales.models;

public class Transaccion {
    private int id;
    private String tipo; // "ingreso" o "egreso"
    private double monto;
    private String categoria;
    private String descripcion;
    private String fecha;

    public Transaccion(int id, String tipo, double monto, String categoria, String descripcion, String fecha) {
        this.id = id;
        this.tipo = tipo;
        this.monto = monto;
        this.categoria = categoria;
        this.descripcion = descripcion;
        this.fecha = fecha;
    }

    public Transaccion(String tipo, double monto, String categoria, String descripcion, String fecha) {
        this(-1, tipo, monto, categoria, descripcion, fecha);
    }

    // Getters y Setters
    public int getId() { return id; }
    public String getTipo() { return tipo; }
    public double getMonto() { return monto; }
    public String getCategoria() { return categoria; }
    public String getDescripcion() { return descripcion; }
    public String getFecha() { return fecha; }

    public void setId(int id) { this.id = id; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public void setMonto(double monto) { this.monto = monto; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public void setFecha(String fecha) { this.fecha = fecha; }
}

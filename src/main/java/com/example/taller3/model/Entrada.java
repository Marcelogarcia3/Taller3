package com.example.taller3.model;

public class Entrada {
    private String tipoEntrada;
    private int precio;
    private String disponibilidad;

    public Entrada(String tipoEntrada, int precio, String disponibilidad) {
        this.tipoEntrada = tipoEntrada;
        this.precio = precio;
        this.disponibilidad = disponibilidad;
    }

    public String getTipoEntrada() {
        return tipoEntrada;
    }

    public void setTipoEntrada(String tipoEntrada) {
        this.tipoEntrada = tipoEntrada;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public String getDisponibilidad() {
        return disponibilidad;
    }

    public void setDisponibilidad(String disponibilidad) {
        this.disponibilidad = disponibilidad;
    }
}

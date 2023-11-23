package com.example.taller3.model;

import java.util.ArrayList;

public class Asistente {
    private ArrayList<String> entradas;
    private String informacionContacto;
    private String preferenciasMusicales;

    public Asistente(ArrayList<String> entradas, String informacionContacto, String preferenciasMusicales) {
        this.entradas = entradas;
        this.informacionContacto = informacionContacto;
        this.preferenciasMusicales = preferenciasMusicales;
    }

    public ArrayList<String> getEntradas() {
        return entradas;
    }

    public void setEntradas(ArrayList<String> entradas) {
        this.entradas = entradas;
    }

    public String getInformacionContacto() {
        return informacionContacto;
    }

    public void setInformacionContacto(String informacionContacto) {
        this.informacionContacto = informacionContacto;
    }

    public String getPreferenciasMusicales() {
        return preferenciasMusicales;
    }

    public void setPreferenciasMusicales(String preferenciasMusicales) {
        this.preferenciasMusicales = preferenciasMusicales;
    }
}

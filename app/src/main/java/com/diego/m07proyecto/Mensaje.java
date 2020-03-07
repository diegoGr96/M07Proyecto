package com.diego.m07proyecto;

import java.util.HashMap;

public class Mensaje {
    private String remitente;
    private String texto;
    private int idMensaje;

    public Mensaje() {
    }

    public Mensaje(String remitente, String texto, int idMensaje) {
        this.remitente = remitente;
        this.texto = texto;
        this.idMensaje = idMensaje;
    }

    public String getRemitente() {
        return remitente;
    }

    public void setRemitente(String remitente) {
        this.remitente = remitente;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public int getIdMensaje() {
        return idMensaje;
    }

    public void setIdMensaje(int idMensaje) {
        this.idMensaje = idMensaje;
    }

    @Override
    public String toString() {
        return "Mensaje{" +
                "remitente='" + remitente + '\'' +
                ", texto='" + texto + '\'' +
                ", idMensaje=" + idMensaje +
                '}';
    }

    public static Mensaje convertMensaje(HashMap<String, Object> mapa){
        String remitente = (String)mapa.get("Remitente");
        String texto  = (String)mapa.get("Texto");
        int idMensaje = ((Long)mapa.get("idMensaje")).intValue();
        return new Mensaje(remitente,texto,idMensaje);
    }
}

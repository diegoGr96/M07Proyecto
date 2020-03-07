package com.diego.m07proyecto;

import java.util.HashMap;

public class Mensaje {
    private String remitente;
    private String texto;
    private long idMensaje;

    public Mensaje() {
    }

    public Mensaje(String remitente, String texto, long idMensaje) {
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

    public long getIdMensaje() {
        return idMensaje;
    }

    public void setIdMensaje(long idMensaje) {
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
        String remitente = (String)mapa.get("remitente");
        String texto  = (String)mapa.get("texto");
        long idMensaje = (Long)mapa.get("idMensaje");
        return new Mensaje(remitente,texto,idMensaje);
    }
}

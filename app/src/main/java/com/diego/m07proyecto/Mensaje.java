package com.diego.m07proyecto;

public class Mensaje {

    private int idMensaje;
    private String remitente;
    private String texto;

    public Mensaje() {
    }

    public Mensaje(int idMensaje, String remitente, String texto) {
        this.idMensaje = idMensaje;
        this.remitente = remitente;
        this.texto = texto;
    }

    public int getIdMensaje() {
        return idMensaje;
    }

    public void setIdMensaje(int idMensaje) {
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

    @Override
    public String toString() {
        return "Mensaje{" +
                "idMensaje=" + idMensaje +
                ", remitente='" + remitente + '\'' +
                ", texto='" + texto + '\'' +
                '}';
    }
}

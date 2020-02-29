package com.diego.m07proyecto;

import java.util.HashMap;

public class Respuesta {
    int idRespuesta;
    String textRespuesta;
    String uidAutor;
    String nickAutor;
    boolean anonimo;
    String tituloAutor;

    public Respuesta() {
    }

    public Respuesta(int idRespuesta, String textRespuesta, String uidAutor, String nickAutor, boolean anonimo) {
        this.idRespuesta = idRespuesta;
        this.textRespuesta = textRespuesta;
        this.uidAutor = uidAutor;
        this.nickAutor = nickAutor;
        this.anonimo = anonimo;
    }

    public Respuesta(String tituloAutor, String nickAutor, String textRespuesta) {
        this.tituloAutor = tituloAutor;
        this.nickAutor = nickAutor;
        this.textRespuesta = textRespuesta;
    }

    public Respuesta(String nickAutor, String textRespuesta, boolean anonimo) {
        this.nickAutor = nickAutor;
        this.textRespuesta = textRespuesta;
        this.anonimo = anonimo;
    }

    public int getIdRespuesta() {
        return idRespuesta;
    }

    public void setIdRespuesta(int idRespuesta) {
        this.idRespuesta = idRespuesta;
    }

    public String getTextRespuesta() {
        return textRespuesta;
    }

    public void setTextRespuesta(String textRespuesta) {
        this.textRespuesta = textRespuesta;
    }

    public String getUidAutor() {
        return uidAutor;
    }

    public void setUidAutor(String uidAutor) {
        this.uidAutor = uidAutor;
    }

    public String getNickAutor() {
        return nickAutor;
    }

    public void setNickAutor(String nickAutor) {
        this.nickAutor = nickAutor;
    }

    public boolean isAnonimo() {
        return anonimo;
    }

    public void setAnonimo(boolean anonimo) {
        this.anonimo = anonimo;
    }

    public String getTituloAutor() {
        return tituloAutor;
    }

    public void setTituloAutor(String tituloAutor) {
        this.tituloAutor = tituloAutor;
    }

    public static Respuesta convertRespuesta(HashMap<String, Object> mapa) {
        String nickAutor = (String) mapa.get("nickAutor");
        String textoRespuesta = (String) mapa.get("textRespuesta");
        boolean isAnonimo = (boolean) mapa.get("anonimo");

        return new Respuesta(nickAutor, textoRespuesta, isAnonimo);
    }

    @Override
    public String toString() {
        return "Respuesta{" +
                "idRespuesta=" + idRespuesta +
                ", textRespuesta='" + textRespuesta + '\'' +
                ", uidAutor='" + uidAutor + '\'' +
                ", nickAutor='" + nickAutor + '\'' +
                ", anonimo=" + anonimo /*+
                ", tituloAutor='" + tituloAutor + '\'' +
                '}'
                */;
    }
}

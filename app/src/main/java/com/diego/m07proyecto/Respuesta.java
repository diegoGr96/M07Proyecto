package com.diego.m07proyecto;

public class Respuesta {
    private int idRespuesta;
    private String textRespuesta;
    private String uidAutor;
    private String nickAutor;
    private boolean anonimo;
    private String tituloAutor;

    public Respuesta(boolean anonimo, int idRespuesta, String nickAutor, String textRespuesta, String uidAutor) {
        this.idRespuesta = idRespuesta;
        this.textRespuesta = textRespuesta;
        this.uidAutor = uidAutor;
        this.nickAutor = nickAutor;
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

    @Override
    public String toString() {
        return "Respuesta{" +
                "idRespuesta=" + idRespuesta +
                ", textRespuesta='" + textRespuesta + '\'' +
                ", uidAutor='" + uidAutor + '\'' +
                ", nickAutor='" + nickAutor + '\'' +
                ", Anonimo=" + anonimo +
                '}';
    }
}

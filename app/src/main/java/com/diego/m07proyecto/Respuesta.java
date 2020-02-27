package com.diego.m07proyecto;

public class Respuesta {
    private int idRespuesta;
    private String textRespuesta;
    private String uidAutor;
    private String nickAutor;
    private boolean anonimo;

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

    public String getTextRespuesta() {
        return textRespuesta;
    }

    public String getUidAutor() {
        return uidAutor;
    }

    public String getNickAutor() {
        return nickAutor;
    }

    public boolean isAnonimo() {
        return anonimo;
    }

    @Override
    public String toString() {
        return "Respuesta{" +
                "idRespuesta=" + idRespuesta +
                ", textRespuesta='" + textRespuesta + '\'' +
                ", uidAutor='" + uidAutor + '\'' +
                ", nickAutor='" + nickAutor + '\'' +
                ", anonimo=" + anonimo +
                '}';
    }
}

package com.diego.m07proyecto;

import java.util.ArrayList;

public class Respuesta {
    private String idRespuesta;
    private String respuesta;
    private String uidAutor;
    private String nickAutor;
    private ArrayList<Respuesta> respuestas;

    public Respuesta(String idRespuesta, String respuesta, String uidAutor, String nickAutor, ArrayList<Respuesta> respuestas) {
        this.idRespuesta = idRespuesta;
        this.respuesta = respuesta;
        this.uidAutor = uidAutor;
        this.nickAutor = nickAutor;
        this.respuestas = respuestas;
    }

    public String getIdRespuesta() {
        return idRespuesta;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public String getUidAutor() {
        return uidAutor;
    }

    public String getNickAutor() {
        return nickAutor;
    }

    public ArrayList<Respuesta> getRespuestas() {
        return respuestas;
    }
}

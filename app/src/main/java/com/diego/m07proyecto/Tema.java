package com.diego.m07proyecto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Tema extends HashMap<String, Object> {
    private int contadorRespuestas;
    private int idTema;
    private String uidTema;
    private boolean anonimato;
    private String uidAutor;
    private String cuerpo;
    private String nickAutor;
    private String titulo;
    private List<Respuesta> respuestas;

    public Tema(boolean anonimato, String uidAutor, String cuerpo, int idTema, String nickAutor, List<Respuesta> respuestas, String titulo) {
        this.idTema = idTema;
        this.anonimato = anonimato;
        this.uidAutor = uidAutor;
        this.cuerpo = cuerpo;
        this.nickAutor = nickAutor;
        this.titulo = titulo;
        this.respuestas = respuestas;
        this.contadorRespuestas = 0;
    }

    public int getContadorRespuestas() {
        return contadorRespuestas;
    }

    public void setContadorRespuestas(int contadorRespuestas) {
        this.contadorRespuestas = contadorRespuestas;
    }

    public int getIdTema() {
        return idTema;
    }

    public String getUidTema() {
        return uidTema;
    }

    public boolean isAnonimato() {
        return anonimato;
    }

    public String getUidAutor() {
        return uidAutor;
    }

    public String getCuerpo() {
        return cuerpo;
    }

    public String getNickAutor() {
        return nickAutor;
    }

    public String getTitulo() {
        return titulo;
    }

    public List<Respuesta> getRespuestas() {
        return respuestas;
    }

    @Override
    public String toString() {
        return "Tema{" +
                "idTema=" + idTema +
                ", uidTema='" + uidTema + '\'' +
                ", anonimato=" + anonimato +
                ", uidAutor='" + uidAutor + '\'' +
                ", cuerpo='" + cuerpo + '\'' +
                ", nickAutor='" + nickAutor + '\'' +
                ", titulo='" + titulo + '\'' +
                ", respuestas=" + respuestas +
                '}';
    }
    public static Tema convertTema(HashMap<String, Object> mapa){
        String uidAutor = (String) mapa.get("uidAutor");
        int idTema = ((Long) mapa.get("idTema")).intValue();
        boolean isAnonimo = (boolean) mapa.get("isAnonimo");
        String titulo = (String) mapa.get("titulo");
        //int contRespuestas = ((Long) mapa.get("contRespuestas")).intValue();
        String nickAutor = (String) mapa.get("nickAutor");
        String cuerpo = (String) mapa.get("cuerpo");
        return new Tema(isAnonimo,uidAutor,cuerpo,idTema,nickAutor,null,titulo);
    }
}

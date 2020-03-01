package com.diego.m07proyecto;

import java.util.HashMap;
import java.util.List;

public class Tema{
     int contRespuestas;
     int idTema;
     String uidTema;
     boolean anonimo;
     String uidAutor;
     String cuerpo;
     String nickAutor;
     String titulo;
     List<Respuesta> respuestas;
     int categoria;

    public Tema(){
    }

    public Tema(boolean anomino, String uidAutor, String cuerpo, int idTema, String nickAutor, List<Respuesta> respuestas, String titulo, int categoria) {
        this.idTema = idTema;
        this.anonimo = anomino;
        this.uidAutor = uidAutor;
        this.cuerpo = cuerpo;
        this.nickAutor = nickAutor;
        this.titulo = titulo;
        this.respuestas = respuestas;
        this.contRespuestas = 0;
        this.categoria = categoria;
    }

    public Tema(boolean anomino, String uidAutor, String cuerpo, int idTema, String nickAutor, String titulo, int categoria) {
        this.idTema = idTema;
        this.anonimo = anomino;
        this.uidAutor = uidAutor;
        this.cuerpo = cuerpo;
        this.nickAutor = nickAutor;
        this.titulo = titulo;
        this.contRespuestas = 0;
        this.categoria = categoria;
    }

    public int getCategoria() {
        return categoria;
    }

    public void setCategoria(int categoria) {
        this.categoria = categoria;
    }

    public int getContRespuestas() {
        return contRespuestas;
    }

    public void setContRespuestas(int contRespuestas) {
        this.contRespuestas = contRespuestas;
    }

    public int getIdTema() {
        return idTema;
    }

    public void setIdTema(int idTema) {
        this.idTema = idTema;
    }

    public String getUidTema() {
        return uidTema;
    }

    public void setUidTema(String uidTema) {
        this.uidTema = uidTema;
    }

    public boolean isAnonimo() {
        return anonimo;
    }

    public void setAnonimo(boolean anonimo) {
        this.anonimo = anonimo;
    }

    public String getUidAutor() {
        return uidAutor;
    }

    public void setUidAutor(String uidAutor) {
        this.uidAutor = uidAutor;
    }

    public String getCuerpo() {
        return cuerpo;
    }

    public void setCuerpo(String cuerpo) {
        this.cuerpo = cuerpo;
    }

    public String getNickAutor() {
        return nickAutor;
    }

    public void setNickAutor(String nickAutor) {
        this.nickAutor = nickAutor;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public List<Respuesta> getRespuestas() {
        return respuestas;
    }

    public void setRespuestas(List<Respuesta> respuestas) {
        this.respuestas = respuestas;
    }

    @Override
    public String toString() {
        return "Tema{" +
                "idTema=" + idTema +
                ", uidTema='" + uidTema + '\'' +
                ", isAnonimo=" + anonimo +
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
        boolean isAnonimo = (boolean) mapa.get("anonimo");
        String titulo = (String) mapa.get("titulo");
        //int contRespuestas = ((Long) mapa.get("contRespuestas")).intValue();
        String nickAutor = (String) mapa.get("nickAutor");
        String cuerpo = (String) mapa.get("cuerpo");
        int categoria = ((Long)mapa.get("categoria")).intValue();
        return new Tema(isAnonimo,uidAutor,cuerpo,idTema,nickAutor,null,titulo, categoria);
    }
}

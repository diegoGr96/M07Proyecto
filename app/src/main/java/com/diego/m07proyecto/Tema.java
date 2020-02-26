package com.diego.m07proyecto;

import java.util.ArrayList;

public class Tema {
    private String uidTema;
    private boolean anonimato;
    private String uidAutor;
    private String cuerpo;
    private String nickAutor;
    private String titulo;
    private ArrayList<Respuesta> respuestas;

    public Tema(String uidTema, boolean anonimato, String uidAutor, String cuerpo, String nickAutor, String titulo, ArrayList<Respuesta> respuestas) {
        this.uidTema = uidTema;
        this.anonimato = anonimato;
        this.uidAutor = uidAutor;
        this.cuerpo = cuerpo;
        this.nickAutor = nickAutor;
        this.titulo = titulo;
        this.respuestas = respuestas;
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

    public ArrayList<Respuesta> getRespuestas() {
        return respuestas;
    }
}

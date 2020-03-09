package com.diego.m07proyecto;

import java.util.HashMap;
import java.util.Map;

public class Usuario {
    private String uid;
    private String email;
    private String fechaNacimiento;
    private String nick;
    private String nombre;
    private String numRespuestas;
    private String numTemas;

    public Usuario() {
    }

    public Usuario(String uid, String email, String fechaNacimiento, String nick, String nombre, String numRespuestas, String numTemas) {
        this.uid = uid;
        this.email = email;
        this.fechaNacimiento = fechaNacimiento;
        this.nick = nick;
        this.nombre = nombre;
        this.numRespuestas = numRespuestas;
        this.numTemas = numTemas;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmial(String email) {
        this.email = email;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNumRespuestas() {
        return numRespuestas;
    }

    public void setNumRespuestas(String numRespuestas) {
        this.numRespuestas = numRespuestas;
    }

    public String getNumTemas() {
        return numTemas;
    }

    public void setNumTemas(String numTemas) {
        this.numTemas = numTemas;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "uid='" + uid + '\'' +
                ", email='" + email + '\'' +
                ", fechaNacimiento='" + fechaNacimiento + '\'' +
                ", nick='" + nick + '\'' +
                ", nombre='" + nombre + '\'' +
                ", numRespuestas='" + numRespuestas + '\'' +
                ", numTemas='" + numTemas + '\'' +
                '}';
    }
}

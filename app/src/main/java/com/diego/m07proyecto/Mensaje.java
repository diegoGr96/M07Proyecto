package com.diego.m07proyecto;

import java.util.HashMap;

public class Mensaje {
    private String correoDestino;
    private String mensaje;

    public Mensaje() {
    }

    public Mensaje(String correoDestino, String mensaje) {
        this.correoDestino = correoDestino;
        this.mensaje = mensaje;
    }

    public String getCorreoDestino() {
        return correoDestino;
    }

    public void setCorreoDestino(String correoDestino) {
        this.correoDestino = correoDestino;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    @Override
    public String toString() {
        return "Mensaje{" +
                "correoDestino='" + correoDestino + '\'' +
                ", mensaje='" + mensaje + '\'' +
                '}';
    }

    public static Mensaje convertConversacion(HashMap<String, Object> mapa){
        String correoDestino = (String)mapa.get("Remitente");
        String mensaje = (String)mapa.get("Texto");
        return new Mensaje(correoDestino,mensaje);
    }
}

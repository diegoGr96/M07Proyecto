package com.diego.m07proyecto;

import java.util.HashMap;
import java.util.List;

public class Chat {
    private int idChat;
    private String correoDestino;
    private String fechaUltimoMensaje;
    private int mensajesSinLeer;
    private String nombreChat;
    private String uidDestino;
    private String nickDestino;
    private String ultimoMensaje;

    public Chat() {
    }

    public Chat(int idChat, String correoDestino, String fechaUltimoMensaje, int mensajesSinLeer, String nombreChat, String uidDestino, String ultimoMensaje) {
        this.idChat = idChat;
        this.correoDestino = correoDestino;
        this.fechaUltimoMensaje = fechaUltimoMensaje;
        this.mensajesSinLeer = mensajesSinLeer;
        this.nombreChat = nombreChat;
        this.uidDestino = uidDestino;
        this.ultimoMensaje = ultimoMensaje;
    }

    public Chat(String correoDestino, String fechaUltimoMensaje, int mensajesSinLeer, String nombreChat, String uidDestino, String nickDestino, String ultimoMensaje) {
        this.correoDestino = correoDestino;
        this.fechaUltimoMensaje = fechaUltimoMensaje;
        this.mensajesSinLeer = mensajesSinLeer;
        this.nombreChat = nombreChat;
        this.uidDestino = uidDestino;
        this.nickDestino = nickDestino;
        this.ultimoMensaje = ultimoMensaje;
    }

    public int getIdChat() {
        return idChat;
    }

    public void setIdChat(int idChat) {
        this.idChat = idChat;
    }

    public String getCorreoDestino() {
        return correoDestino;
    }

    public void setCorreoDestino(String correoDestino) {
        this.correoDestino = correoDestino;
    }

    public String getFechaUltimoMensaje() {
        return fechaUltimoMensaje;
    }

    public void setFechaUltimoMensaje(String fechaUltimoMensaje) {
        this.fechaUltimoMensaje = fechaUltimoMensaje;
    }

    public int getMensajesSinLeer() {
        return mensajesSinLeer;
    }

    public void setMensajesSinLeer(int mensajesSinLeer) {
        this.mensajesSinLeer = mensajesSinLeer;
    }

    public String getNombreChat() {
        return nombreChat;
    }

    public void setNombreChat(String nombreChat) {
        this.nombreChat = nombreChat;
    }

    public String getUidDestino() {
        return uidDestino;
    }

    public void setUidDestino(String uidDestino) {
        this.uidDestino = uidDestino;
    }

    public String getNickDestino() {
        return nickDestino;
    }

    public void setNickDestino(String nickDestino) {
        this.nickDestino = nickDestino;
    }

    public String getUltimoMensaje() {
        return ultimoMensaje;
    }

    public void setUltimoMensaje(String ultimoMensaje) {
        this.ultimoMensaje = ultimoMensaje;
    }

    @Override
    public String toString() {
        return "Chat{" +
                "idChat=" + idChat +
                ", correoDestino='" + correoDestino + '\'' +
                ", fechaUltimoMensaje='" + fechaUltimoMensaje + '\'' +
                ", mensajesSinLeer=" + mensajesSinLeer +
                ", nombreChat='" + nombreChat + '\'' +
                ", uidDestino='" + uidDestino + '\'' +
                ", ultimoMensaje='" + ultimoMensaje + '\'' +
                '}';
    }

    public static Chat convertChat(HashMap<String, Object> mapa) {
        String correoDestino = (String) mapa.get("correoDestino");
        String fechaUltimoMensaje = (String) mapa.get("fechaUltimoMensaje");
        int mensajesSinLeer = 0;
        if (mapa.get("mensajesSinLeer") != null) {
            mensajesSinLeer = ((Long) mapa.get("mensajesSinLeer")).intValue();
        }
        String nombreChat = (String) mapa.get("nombreChat");
        String nickDestino = (String) mapa.get("nickDestino");
        String uidDestino = (String) mapa.get("uidDestino");
        String ultimoMensaje = (String) mapa.get("ultimoMensaje");
        return new Chat(correoDestino, fechaUltimoMensaje, mensajesSinLeer, nombreChat, uidDestino, nickDestino, ultimoMensaje);
    }
}

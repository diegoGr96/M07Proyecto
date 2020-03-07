package com.diego.m07proyecto;

public class SearchUsers {
    private String nickDestino;
    private String correoDestino;
    private String uidDestino;

    public SearchUsers() {
    }

    public SearchUsers(String nickDestino, String correoDestino, String uidDestino) {
        this.nickDestino = nickDestino;
        this.correoDestino = correoDestino;
        this.uidDestino = uidDestino;
    }

    public String getNickDestino() {
        return nickDestino;
    }

    public void setNickDestino(String nickDestino) {
        this.nickDestino = nickDestino;
    }

    public String getCorreoDestino() {
        return correoDestino;
    }

    public void setCorreoDestino(String correoDestino) {
        this.correoDestino = correoDestino;
    }

    public String getUidDestino() {
        return uidDestino;
    }

    public void setUidDestino(String uidDestino) {
        this.uidDestino = uidDestino;
    }

    @Override
    public String toString() {
        return "SearchUsers{" +
                "nickDestino='" + nickDestino + '\'' +
                ", correoDestino='" + correoDestino + '\'' +
                ", uidDestino='" + uidDestino + '\'' +
                '}';
    }
}

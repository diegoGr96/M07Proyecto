package com.diego.m07proyecto;

public class SearchUsers {
    private String nickDestino;
    private String correoDestino;

    public SearchUsers() {
    }

    public SearchUsers(String nickDestino, String correoDestino) {
        this.nickDestino = nickDestino;
        this.correoDestino = correoDestino;
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

    @Override
    public String toString() {
        return "SearchUsers{" +
                "nickDestino='" + nickDestino + '\'' +
                ", correoDestino='" + correoDestino + '\'' +
                '}';
    }


}

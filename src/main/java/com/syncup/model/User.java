package com.syncup.model;

import java.util.LinkedList;
import java.util.Objects;

public class User {
    private String username;
    private String password;
    private String nombre;
    private LinkedList<Cancion> listaFavoritos;

    public User(String username, String password, String nombre) {
        this.username = username;
        this.password = password;
        this.nombre = nombre;
        this.listaFavoritos = new LinkedList<>();
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getNombre() {
        return nombre;
    }

    public LinkedList<Cancion> getListaFavoritos() {
        return listaFavoritos;
    }

    public void agregarFavorito(Cancion cancion) {
        listaFavoritos.add(cancion);
    }

    public void eliminarFavorito(Cancion cancion) {
        listaFavoritos.remove(cancion);
    }

    public int hashCode() {
        return Objects.hash(username);
    }

    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof User)) return false;
        User other = (User) obj;
        return Objects.equals(username, other.username);
    }

    public String toString() {
        return nombre + " (" + username + ")";
    }
}

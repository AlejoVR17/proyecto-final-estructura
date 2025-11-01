package com.syncup.model;

import java.util.Objects;

public class Cancion {
    private int id;
    private String titulo;
    private String artista;
    private String genero;
    private int anio;
    private double duracion;

    public Cancion(int id, String titulo, String artista, String genero, int anio, double duracion) {
        this.id = id;
        this.titulo = titulo;
        this.artista = artista;
        this.genero = genero;
        this.anio = anio;
        this.duracion = duracion;
    }


    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getArtista() {
        return artista;
    }

    public String getGenero() {
        return genero;
    }

    public int getAnio() {
        return anio;
    }

    public double getDuracion() {
        return duracion;
    }


    public int hashCode() {
        return Objects.hash(id);
    }

    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Cancion)) return false;
        Cancion other = (Cancion) obj;
        return id == other.id;
    }

    public String toString() {
        return titulo + " - " + artista;
    }
}

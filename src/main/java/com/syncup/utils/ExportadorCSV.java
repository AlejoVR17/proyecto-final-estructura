package com.syncup.utils;

import com.syncup.model.Cancion;
import com.syncup.model.User;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ExportadorCSV {

    public static boolean exportarFavoritos(User user, String rutaArchivo) {
        try (FileWriter writer = new FileWriter(rutaArchivo)) {
            writer.append("id,titulo,artista,genero,anio,duracion\n");
            for (Cancion c : user.getListaFavoritos()) {
                writer.append(String.valueOf(c.getId())).append(",");
                writer.append(escape(c.getTitulo())).append(",");
                writer.append(escape(c.getArtista())).append(",");
                writer.append(escape(c.getGenero())).append(",");
                writer.append(String.valueOf(c.getAnio())).append(",");
                writer.append(String.valueOf(c.getDuracion())).append("\n");
            }
            writer.flush();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static String escape(String s) {
        return "\"" + s.replace("\"","\"\"") + "\"";
    }
}

package com.syncup.data;

import com.syncup.model.Cancion;
import java.util.Collection;
import java.util.HashMap;

public class CancionRepository {
    private static final HashMap<Integer, Cancion> canciones = new HashMap<>();

    public static boolean agregarCancion(Cancion c) {
        if (canciones.containsKey(c.getId())) return false;
        canciones.put(c.getId(), c);
        return true;
    }

    public static Cancion obtenerCancion(int id) {
        return canciones.get(id);
    }

    public static Collection<Cancion> obtenerTodas() {
        return canciones.values();
    }

    public static boolean eliminarCancion(int id) {
        if (!canciones.containsKey(id)) return false;
        canciones.remove(id);
        return true;
    }

    public static boolean existeCancion(int id) {
        return canciones.containsKey(id);
    }

    public static void limpiar() { canciones.clear(); }
}

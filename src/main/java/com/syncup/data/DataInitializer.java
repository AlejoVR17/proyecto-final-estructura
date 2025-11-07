package com.syncup.data;

import com.syncup.graph.GrafoDeSimilitud;
import com.syncup.graph.GrafoSocial;
import com.syncup.model.Cancion;
import com.syncup.model.User;

public class DataInitializer {

    public static GrafoDeSimilitud initSimilitud() {
        GrafoDeSimilitud g = new GrafoDeSimilitud();
        // cargar canciones demo y nodos
        Cancion c1 = new Cancion(1, "Bohemian Rhapsody", "Queen", "Rock", 1975, 5.55);
        Cancion c2 = new Cancion(2, "Don't Stop Me Now", "Queen", "Rock", 1979, 3.29);
        Cancion c3 = new Cancion(3, "Shape of You", "Ed Sheeran", "Pop", 2017, 4.24);
        CancionRepository.agregarCancion(c1);
        CancionRepository.agregarCancion(c2);
        CancionRepository.agregarCancion(c3);
        g.addNode(c1); g.addNode(c2); g.addNode(c3);
        g.addEdge(c1, c2, 0.5); // weight low = very similar
        g.addEdge(c1, c3, 2.0);
        return g;
    }

    public static GrafoSocial initSocial() {
        GrafoSocial gs = new GrafoSocial();
        // ejemplo de usuarios
        User u1 = new User("ana","123","Ana");
        User u2 = new User("juan","123","Juan");
        User u3 = new User("luis","123","Luis");
        com.syncup.data.UserRepository.addUser(u1);
        com.syncup.data.UserRepository.addUser(u2);
        com.syncup.data.UserRepository.addUser(u3);
        gs.addUser(u1.getUsername()); gs.addUser(u2.getUsername()); gs.addUser(u3.getUsername());
        gs.addEdge(u1.getUsername(), u2.getUsername());
        gs.addEdge(u2.getUsername(), u3.getUsername());
        return gs;
    }
}

package com.syncup.data;

import com.syncup.graph.GrafoDeSimilitud;
import com.syncup.model.Cancion;
import com.syncup.model.User;

import java.util.*;
import java.util.stream.Collectors;

public class PlaylistService {
    private final GrafoDeSimilitud grafo;

    public PlaylistService(GrafoDeSimilitud grafo) {
        this.grafo = grafo;
    }

    public List<Cancion> generarDescubrimientoSemanal(User user) {
        Set<Cancion> recomendadas = new HashSet<>();

        if (user.getListaFavoritos().isEmpty()) {
            return CancionRepository.obtenerTodas().stream()
                    .limit(5)
                    .collect(Collectors.toList());
        }

        for (Cancion fav : user.getListaFavoritos()) {
            Map<Integer, Double> vecinos = grafo.neighbors(fav.getId());
            vecinos.entrySet().stream()
                    .sorted(Map.Entry.comparingByValue())
                    .limit(3)
                    .forEach(entry -> {
                        Cancion candidata = CancionRepository.obtenerCancion(entry.getKey());
                        if (candidata != null && !user.getListaFavoritos().contains(candidata)) {
                            recomendadas.add(candidata);
                        }
                    });
        }

        if (recomendadas.size() < 5) {
            List<Cancion> restantes = CancionRepository.obtenerTodas().stream()
                    .filter(c -> !user.getListaFavoritos().contains(c))
                    .limit(5 - recomendadas.size())
                    .toList();
            recomendadas.addAll(restantes);
        }

        return new ArrayList<>(recomendadas);
    }
}

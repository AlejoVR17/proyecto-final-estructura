package com.syncup.data;

import com.syncup.graph.GrafoDeSimilitud;
import com.syncup.model.Cancion;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class RadioService {

    private final GrafoDeSimilitud grafo;

    public RadioService(GrafoDeSimilitud grafo) {
        this.grafo = grafo;
    }

    public List<Cancion> generarRadio(int idBase) {
        Cancion base = CancionRepository.obtenerCancion(idBase);
        if (base == null) return Collections.emptyList();

        Map<Integer, Double> distancias = grafo.dijkstraDistances(idBase);

        List<Integer> orden = distancias.entrySet().stream()
                .filter(e -> e.getKey() != idBase)
                .sorted(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        return orden.stream()
                .map(CancionRepository::obtenerCancion)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}

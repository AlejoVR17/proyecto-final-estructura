package com.syncup.search;

import com.syncup.model.Cancion;
import com.syncup.data.CancionRepository;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class AdvancedSearch {
    private final ExecutorService executor = Executors.newCachedThreadPool();

    public enum Mode { AND, OR }

    public List<Cancion> search(Optional<String> titulo, Optional<String> artista,
                                Optional<String> genero, Optional<Integer> anio, Mode mode) throws InterruptedException, ExecutionException {
        List<Callable<Set<Cancion>>> tasks = new ArrayList<>();

        Collection<Cancion> all = CancionRepository.obtenerTodas();

        if (titulo.isPresent()) tasks.add(() -> all.stream()
                .filter(c -> c.getTitulo().toLowerCase().contains(titulo.get().toLowerCase()))
                .collect(Collectors.toSet()));
        if (artista.isPresent()) tasks.add(() -> all.stream()
                .filter(c -> c.getArtista().toLowerCase().contains(artista.get().toLowerCase()))
                .collect(Collectors.toSet()));
        if (genero.isPresent()) tasks.add(() -> all.stream()
                .filter(c -> c.getGenero().toLowerCase().contains(genero.get().toLowerCase()))
                .collect(Collectors.toSet()));
        if (anio.isPresent()) tasks.add(() -> all.stream()
                .filter(c -> c.getAnio() == anio.get())
                .collect(Collectors.toSet()));

        if (tasks.isEmpty()) return new ArrayList<>(all);

        List<Future<Set<Cancion>>> futures = executor.invokeAll(tasks);
        List<Set<Cancion>> results = new ArrayList<>();
        for (Future<Set<Cancion>> f : futures) results.add(f.get());

        Set<Cancion> finalSet;
        if (mode == Mode.AND) {
            finalSet = new HashSet<>(results.get(0));
            for (Set<Cancion> s : results) finalSet.retainAll(s);
        } else { // OR
            finalSet = new HashSet<>();
            for (Set<Cancion> s : results) finalSet.addAll(s);
        }
        return new ArrayList<>(finalSet);
    }

    public void shutdown() { executor.shutdownNow(); }
}

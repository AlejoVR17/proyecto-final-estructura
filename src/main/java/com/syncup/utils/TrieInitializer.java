package com.syncup.utils;

import com.syncup.data.CancionRepository;
import com.syncup.model.Cancion;

public class TrieInitializer {

    private static TrieAutocompletado trie = new TrieAutocompletado();

    public static TrieAutocompletado getTrie() {
        if (trie == null) trie = new TrieAutocompletado();
        return trie;
    }

    public static void cargarCanciones() {
        trie = new TrieAutocompletado();
        for (Cancion c : CancionRepository.obtenerTodas()) {
            trie.insert(c.getTitulo());
        }
    }
}

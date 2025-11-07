package com.syncup.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TrieAutocompletado {
    private static class Node {
        Map<Character, Node> children = new HashMap<>();
        boolean isWord = false;
    }

    private final Node root = new Node();

    public void insert(String word) {
        Node cur = root;
        for (char ch : word.toLowerCase().toCharArray()) {
            cur = cur.children.computeIfAbsent(ch, k -> new Node());
        }
        cur.isWord = true;
    }

    public List<String> autocomplete(String prefix) {
        List<String> results = new ArrayList<>();
        Node cur = root;
        for (char ch : prefix.toLowerCase().toCharArray()) {
            cur = cur.children.get(ch);
            if (cur == null) return results;
        }
        collect(prefix.toLowerCase(), cur, results);
        return results;
    }

    private void collect(String prefix, Node node, List<String> results) {
        if (node.isWord) results.add(prefix);
        for (Map.Entry<Character, Node> e : node.children.entrySet()) {
            collect(prefix + e.getKey(), e.getValue(), results);
        }
    }
}

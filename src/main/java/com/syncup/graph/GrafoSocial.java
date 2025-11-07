package com.syncup.graph;

import com.syncup.model.User;
import java.util.*;

public class GrafoSocial {
    private final Map<String, Set<String>> adj = new HashMap<>(); // username -> set of usernames

    public void addUser(String username) {
        adj.putIfAbsent(username.toLowerCase(), new HashSet<>());
    }

    public void addEdge(String u, String v) {
        u = u.toLowerCase(); v = v.toLowerCase();
        adj.putIfAbsent(u, new HashSet<>());
        adj.putIfAbsent(v, new HashSet<>());
        adj.get(u).add(v);
        adj.get(v).add(u);
    }

    public void removeEdge(String u, String v) {
        u = u.toLowerCase(); v = v.toLowerCase();
        if (adj.containsKey(u)) adj.get(u).remove(v);
        if (adj.containsKey(v)) adj.get(v).remove(u);
    }

    public Set<String> neighbors(String u) {
        return adj.getOrDefault(u.toLowerCase(), Collections.emptySet());
    }

    // BFS for friends up to given depth (e.g., depth=2 => friends-of-friends)
    public Set<String> bfs(String start, int depth) {
        start = start.toLowerCase();
        Set<String> seen = new HashSet<>();
        Queue<String> q = new LinkedList<>();
        Map<String, Integer> level = new HashMap<>();
        q.add(start); level.put(start, 0); seen.add(start);
        Set<String> results = new HashSet<>();
        while(!q.isEmpty()) {
            String u = q.poll();
            int lvl = level.get(u);
            if (lvl >= depth) continue;
            for (String v : neighbors(u)) {
                if (!seen.contains(v)) {
                    seen.add(v);
                    level.put(v, lvl + 1);
                    q.add(v);
                    if (lvl + 1 <= depth) results.add(v);
                }
            }
        }
        results.remove(start);
        return results;
    }

    public void clear() { adj.clear(); }
}

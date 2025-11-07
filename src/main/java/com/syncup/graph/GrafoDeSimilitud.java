package com.syncup.graph;

import com.syncup.model.Cancion;

import java.util.*;

public class GrafoDeSimilitud {
    // Nodo: Cancion (usamos id como key)
    private final Map<Integer, Map<Integer, Double>> adj = new HashMap<>();

    public void addNode(Cancion c) {
        adj.putIfAbsent(c.getId(), new HashMap<>());
    }

    public void addEdge(Cancion a, Cancion b, double weight) {
        adj.putIfAbsent(a.getId(), new HashMap<>());
        adj.putIfAbsent(b.getId(), new HashMap<>());
        adj.get(a.getId()).put(b.getId(), weight);
        adj.get(b.getId()).put(a.getId(), weight);
    }

    public boolean hasNode(int id) { return adj.containsKey(id); }

    public Map<Integer, Double> neighbors(int id) {
        return adj.getOrDefault(id, Collections.emptyMap());
    }

    // Dijkstra: retorna mapa de distancias y prev
    public Map<Integer, Double> dijkstraDistances(int sourceId) {
        Map<Integer, Double> dist = new HashMap<>();
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingDouble(a -> a[1]));
        dist.put(sourceId, 0.0);
        pq.add(new int[]{sourceId, 0}); // store id and dummy cost, we will read cost from dist map
        while(!pq.isEmpty()) {
            int u = pq.poll()[0];
            double du = dist.getOrDefault(u, Double.MAX_VALUE);
            for (Map.Entry<Integer, Double> e : neighbors(u).entrySet()) {
                int v = e.getKey();
                double w = e.getValue();
                double nd = du + w;
                if (nd < dist.getOrDefault(v, Double.MAX_VALUE)) {
                    dist.put(v, nd);
                    pq.add(new int[]{v, 0});
                }
            }
        }
        return dist;
    }

    // Dijkstra path from source to target (returns list of node ids)
    public List<Integer> dijkstraPath(int sourceId, int targetId) {
        Map<Integer, Double> dist = new HashMap<>();
        Map<Integer, Integer> prev = new HashMap<>();
        Comparator<int[]> cmp = Comparator.comparingDouble(a -> a[1]);
        PriorityQueue<int[]> pq = new PriorityQueue<>(cmp);
        dist.put(sourceId, 0.0);
        pq.add(new int[]{sourceId, 0});
        while (!pq.isEmpty()) {
            int u = pq.poll()[0];
            double du = dist.getOrDefault(u, Double.MAX_VALUE);
            if (u == targetId) break;
            for (Map.Entry<Integer, Double> e : neighbors(u).entrySet()) {
                int v = e.getKey();
                double w = e.getValue();
                double nd = du + w;
                if (nd < dist.getOrDefault(v, Double.MAX_VALUE)) {
                    dist.put(v, nd);
                    prev.put(v, u);
                    pq.add(new int[]{v, 0});
                }
            }
        }
        List<Integer> path = new LinkedList<>();
        if (!dist.containsKey(targetId)) return path;
        for (Integer at = targetId; at != null; at = prev.get(at)) path.add(0, at);
        return path;
    }

    // utility: clear
    public void clear() { adj.clear(); }
}

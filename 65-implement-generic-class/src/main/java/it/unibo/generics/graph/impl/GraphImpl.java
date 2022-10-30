package it.unibo.generics.graph.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import it.unibo.generics.graph.api.Graph;

public class GraphImpl<N> implements Graph<N> {

    private Map<N, Set<N>> map;

    public GraphImpl() {
        this.map = new HashMap<>();
    }

    @Override
    public void addNode(N node) {
        map.putIfAbsent(node, new HashSet<>());
    }

    @Override
    public void addEdge(N source, N target) {
        if (map.containsKey(source) && map.containsKey(target)) {
            map.get(source).add(target);
        }
    }

    @Override
    public Set<N> nodeSet() {
        return map.keySet();
    }

    @Override
    public Set<N> linkedNodes(N node) {
        return map.get(node);
    }

    @Override
    public List<N> getPath(N source, N target) {
        return Graphs.<N>Bfs_FindPath(map, source, target);
    }
    
}

package it.unibo.generics.graph.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.MissingFormatArgumentException;
import java.util.Objects;
import java.util.Queue;
import java.util.Set;

public final class Graphs<N> {

    private static long time = 0;

    private static <N> void asserGraph(final Map<N, Set<N>> graphMap, final N source, final N dest) {
        Objects.requireNonNull(graphMap);
        Objects.requireNonNull(source);
        Objects.requireNonNull(dest);
        if (!graphMap.containsKey(source) || !graphMap.containsKey(dest)) {
            throw new MissingFormatArgumentException("You are searching for a path in a graph that does not contains " + source.toString() + " and " + dest.toString() + " nodes.");
        }
    }

    public static <N> List<N> Bfs_FindPath(final Map<N, Set<N>> vicinityMap, final N source, final N dest) {
        asserGraph(vicinityMap, source, dest);
        Map<N, Graphs.NodesBfsInfo<N>> map = bfs(vicinityMap, source);
        return bfsReadPath(vicinityMap, source, dest, map);
    }
    public static <N> List<N> Dfs_FindPath(final Map<N, Set<N>> vicinityMap, final N source, final N dest) {
        asserGraph(vicinityMap, source, dest);
        Map<N, Graphs.NodesDfsInfo<N>> map = dfs(vicinityMap, source);
        return dfsReadPath(vicinityMap, source, dest, map);
    }

    private static <N> Map<N, Graphs.NodesBfsInfo<N>> bfs(final Map<N, Set<N>> graphMap, final N source) {
        Queue<N> queue = new LinkedList<>();
        Set<N> set = graphMap.keySet();
        Iterator<N> iterator = set.iterator();
        Map<N, Graphs.NodesBfsInfo<N>> nodesMap = new HashMap<>(set.size(), 1.0f);
        while (iterator.hasNext()) {
            N node = iterator.next();
            if (node != source) {
                nodesMap.put(node, new Graphs.NodesBfsInfo<N>());
            }
        }
        nodesMap.put(source, new Graphs.NodesBfsInfo<N>(NodeColor.GRAY, 0));
        queue.add(source);
        while(!queue.isEmpty()) {
            N head = queue.element();
            iterator = graphMap.get(head).iterator();
            while (iterator.hasNext()) {
                N node = iterator.next();
                Graphs.NodesBfsInfo<N> NodeInfo = nodesMap.get(node);
                if(NodeInfo.getColor() == NodeColor.WHITE) {
                    NodeInfo.setColor(NodeColor.GRAY);
                    NodeInfo.distance = nodesMap.get(head).distance + 1;
                    NodeInfo.setParent(head);
                    queue.add(node);
                }
            }
            Graphs.NodesBfsInfo<N> NodeInfo = nodesMap.get(head);
            queue.remove();
            NodeInfo.setColor(NodeColor.BLACK);
        }
        return (nodesMap);
    }
    private static <N> List<N> bfsReadPath(final Map<N, Set<N>> graphMap, final N source, final N dest, Map<N, Graphs.NodesBfsInfo<N>> map) {
        List<N> list = new LinkedList<>();
        N node = dest;
        NodesBfsInfo<N> nodeInfo = map.get(node);
        if (nodeInfo.distanceAcceptable()) {
            list.add(0, dest);
            while(nodeInfo.getParent() != source) {
                list.add(0, nodeInfo.getParent());
                nodeInfo = map.get(nodeInfo.getParent());
            }
            list.add(0, source);
        }
        return list;
    }
    
    private static <N> Map<N, Graphs.NodesDfsInfo<N>> dfs(final Map<N, Set<N>> graphMap, final N source) {
        Set<N> set = graphMap.keySet();
        Iterator<N> iterator =  set.iterator();
        Map<N, Graphs.NodesDfsInfo<N>> nodesMap = new HashMap<>(set.size(), 1.0f);
        while (iterator.hasNext()) {
            nodesMap.put(iterator.next(), new Graphs.NodesDfsInfo<N>());
        }
        //iterator = set.iterator();
        time = 0;
        //while(iterator.hasNext()) {
            //N node = iterator.next();
            //if (nodesMap.get(node).getColor() == NodeColor.WHITE) {
                dfsVisitRec(graphMap, source, nodesMap);
            //}
        //}
        return (nodesMap);
    }
    private static <N> void dfsVisitRec(final Map<N, Set<N>> graphMap, N tmpSource, Map<N, Graphs.NodesDfsInfo<N>> nodesMap) {
        NodesDfsInfo<N> tmpSourceInfo = nodesMap.get(tmpSource);
        tmpSourceInfo.setColor(NodeColor.GRAY);
        time++;
        tmpSourceInfo.startTime = time;
        Iterator<N> iterator = graphMap.get(tmpSource).iterator();
        while(iterator.hasNext()) {
            N node = iterator.next();
            NodesDfsInfo<N> nodeInfo = nodesMap.get(node);
            if (nodeInfo.getColor() == NodeColor.WHITE) {
                nodeInfo.setParent(tmpSource);
                dfsVisitRec(graphMap, node, nodesMap);
            }
        }
        tmpSourceInfo.setColor(NodeColor.BLACK);
        time++;
        tmpSourceInfo.endTime = time;
    }
    private static <N> List<N> dfsReadPath(final Map<N, Set<N>> graphMap, final N source, final N dest, Map<N, Graphs.NodesDfsInfo<N>> map) {
        List<N> list = new LinkedList<>();
        N node = dest;
        NodesDfsInfo<N> nodeInfo = map.get(node);
        if (nodeInfo.timeAcceptable()) {
            list.add(0, dest);
            while(nodeInfo.getParent() != source) {
                list.add(0, nodeInfo.getParent());
                nodeInfo = map.get(nodeInfo.getParent());
            }
            list.add(0, source);
        }
        return list;
    }

    private static abstract class NodeInfo<N> {
        private NodeColor color;
        private N parent;

        public NodeInfo() {
            this.color = NodeColor.WHITE;
        }
        public NodeInfo(final NodeColor color) {
            this.color = color;
        }
    
        protected NodeColor getColor() {
            return this.color;
        }
        protected void setColor(final NodeColor color) {
            this.color = color;
        }
        protected N getParent() {
            return this.parent;
        }
        protected void setParent(N parent) {
            this.parent = parent;
        }
    }

    private static class NodesBfsInfo<N> extends NodeInfo<N> {
        private long distance;

        public NodesBfsInfo() {
            this.distance = -1;
        }
        public NodesBfsInfo(final NodeColor color, final long distance) {
            super(color);
            this.distance = distance;
        }

        public boolean distanceAcceptable() {
            return (distance >= 0);
        }
    }
    private static class NodesDfsInfo<N> extends NodeInfo<N> {
        private long startTime;
        private long endTime;

        public NodesDfsInfo() {
            startTime = -1;
            endTime = -1;
        }

        public boolean timeAcceptable() {
            return (startTime >= 0) && (endTime >= 0);
        }
    }
    
    private static enum NodeColor {
        WHITE,
        BLACK,
        GRAY;
    }
}

import com.google.gson.*;
import java.io.*;
import java.util.*;

//DATA STRUCTURES

class Edge implements Comparable<Edge> {
    int from, to;
    double weight;

    public Edge(int from, int to, double weight) {
        this.from = from;
        this.to = to;
        this.weight = weight;
    }

    @Override
    public int compareTo(Edge other) {
        return Double.compare(this.weight, other.weight);
    }
}

class Graph {
    int vertices;
    List<Edge> edges;
    List<List<Edge>> adjList;

    public Graph(int vertices) {
        this.vertices = vertices;
        this.edges = new ArrayList<>();
        this.adjList = new ArrayList<>();
        for (int i = 0; i < vertices; i++) {
            adjList.add(new ArrayList<>());
        }
    }

    public void addEdge(int from, int to, double weight) {
        if (from == to) return;
        int a = Math.min(from, to);
        int b = Math.max(from, to);

        for (Edge e : edges) {
            if ((e.from == a && e.to == b) || (e.from == b && e.to == a)) {
                return;
            }
        }

        Edge edge = new Edge(a, b, weight);
        edges.add(edge);
        adjList.get(from).add(new Edge(from, to, weight));
        adjList.get(to).add(new Edge(to, from, weight));
    }

    public int getEdgeCount() {
        return edges.size();
    }
}

class MSTResult {
    String algorithm;
    int vertices;
    int originalEdges;
    List<Edge> mstEdges;
    double totalCost;
    int operations;
    double executionTimeMs;
    boolean isConnected;

    public MSTResult(String algorithm, int vertices, int originalEdges) {
        this.algorithm = algorithm;
        this.vertices = vertices;
        this.originalEdges = originalEdges;
        this.mstEdges = new ArrayList<>();
        this.totalCost = 0.0;
        this.operations = 0;
        this.executionTimeMs = 0.0;
        this.isConnected = true;
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        json.addProperty("algorithm", algorithm);
        json.addProperty("vertices", vertices);
        json.addProperty("original_edges", originalEdges);

        JsonArray edgesArray = new JsonArray();
        for (Edge edge : mstEdges) {
            JsonObject edgeObj = new JsonObject();
            edgeObj.addProperty("from", edge.from);
            edgeObj.addProperty("to", edge.to);
            edgeObj.addProperty("weight", Math.round(edge.weight * 100.0) / 100.0);
            edgesArray.add(edgeObj);
        }
        json.add("mst_edges", edgesArray);

        json.addProperty("mst_edge_count", mstEdges.size());
        json.addProperty("total_cost", Math.round(totalCost * 100.0) / 100.0);
        json.addProperty("operations", operations);
        json.addProperty("execution_time_ms", Math.round(executionTimeMs * 10000.0) / 10000.0);
        json.addProperty("is_connected", isConnected);

        return json;
    }
}

//UNION-FIND

class UnionFind {
    int[] parent;
    int[] rank;
    int operations;

    public UnionFind(int n) {
        parent = new int[n];
        rank = new int[n];
        operations = 0;
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            rank[i] = 0;
        }
    }

    public int find(int x) {
        operations++;
        if (parent[x] != x) {
            parent[x] = find(parent[x]);
        }
        return parent[x];
    }

    public boolean union(int x, int y) {
        operations++;
        int rootX = find(x);
        int rootY = find(y);

        if (rootX == rootY) {
            return false;
        }

        if (rank[rootX] < rank[rootY]) {
            parent[rootX] = rootY;
        } else if (rank[rootX] > rank[rootY]) {
            parent[rootY] = rootX;
        } else {
            parent[rootY] = rootX;
            rank[rootX]++;
        }

        return true;
    }
}

//MST ALGORITHMS

class MSTAlgorithms {

    public static MSTResult primsAlgorithm(Graph graph) {
        MSTResult result = new MSTResult("Prim's Algorithm", graph.vertices, graph.getEdgeCount());
        if (graph.vertices == 0) return result;

        long startTime = System.nanoTime();
        boolean[] visited = new boolean[graph.vertices];
        PriorityQueue<Edge> pq = new PriorityQueue<>();
        int operations = 0;

        visited[0] = true;
        pq.addAll(graph.adjList.get(0));

        while (!pq.isEmpty() && result.mstEdges.size() < graph.vertices - 1) {
            Edge edge = pq.poll();
            operations++;

            if (visited[edge.to]) continue;

            visited[edge.to] = true;
            result.mstEdges.add(edge);
            result.totalCost += edge.weight;

            for (Edge next : graph.adjList.get(edge.to)) {
                if (!visited[next.to]) {
                    pq.offer(next);
                }
            }
        }

        long endTime = System.nanoTime();
        result.operations = operations;
        result.executionTimeMs = (endTime - startTime) / 1_000_000.0;
        result.isConnected = result.mstEdges.size() == graph.vertices - 1;

        return result;
    }



}
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
import java.util.*;

public class Graph<T> implements Iterable<T> {
    private Map<T, Set<T>> verticesMap;

    private int edgesCount;

    public Graph() {
        verticesMap = new HashMap<>();
    }

    public int getNumVertices() {
        return verticesMap.size();
    }

    public int getNumEdges() {
        return edgesCount;
    }


    private void validateVertex(T v) {
        if (!hasVertex(v)) throw new IllegalArgumentException(v.toString() + " is not a vertex");
    }

    public int degree(T v) {
        validateVertex(v);
        return verticesMap.get(v).size();
    }

    public void addEdge(T v, T w) {
        if (!hasVertex(v)) addVertex(v);
        if (!hasVertex(w)) addVertex(w);
        if (!hasEdge(v, w)) edgesCount++;
        verticesMap.get(v).add(w);
        verticesMap.get(w).add(v);
    }

    public void addVertex(T v) {
        if (!hasVertex(v)) verticesMap.put(v, new HashSet<T>());
    }

    public boolean hasEdge(T v, T w) {
        validateVertex(v);
        validateVertex(w);
        return verticesMap.get(v).contains(w);
    }

    public boolean hasVertex(T v) {
        return verticesMap.containsKey(v);
    }

    @Override
    public Iterator<T> iterator() {
        return verticesMap.keySet().iterator();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        for (T v: verticesMap.keySet()) {
            builder.append(v.toString() + ": ");
            for (T w: verticesMap.get(v)) {
                builder.append(w.toString() + " ");
            }
            builder.append("\n");
        }

        return builder.toString();
    }

    public static void main(String[] args) {
        Graph<String> graph = new Graph<>();

        graph.addEdge("A", "B");
        graph.addEdge("A", "C");
        graph.addEdge("C", "D");
        graph.addEdge("D", "E");
        graph.addEdge("D", "G");
        graph.addEdge("E", "G");
        graph.addVertex("H");

        System.out.println(graph);

        System.out.println("Vertices: " + graph.getNumVertices());
        System.out.println("Edges: " + graph.getNumEdges());
    }
}
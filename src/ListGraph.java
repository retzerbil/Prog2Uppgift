import java.util.*;

public class ListGraph<T> implements Iterable<T> {
    private Map<T, Set<T>> nodeMap;

    private int edgeCount;

    public ListGraph() {
        nodeMap = new HashMap<>();
    }

    private void validateNode(T nodeOne) {
        if (!hasNode(nodeOne)) throw new IllegalArgumentException(nodeOne.toString() + " is not a vertex");
    }

    public boolean hasNode(T nodeOne) {
        return nodeMap.containsKey(nodeOne);
    }

    public boolean hasEdge(T nodeOne, T nodeTwo) {
        validateNode(nodeOne);
        validateNode(nodeTwo);
        return nodeMap.get(nodeOne).contains(nodeTwo);
    }

    public void add(T nodeOne){
        if (!hasNode(nodeOne)) nodeMap.put(nodeOne, new HashSet<T>());
    }

    public void remove(T nodeOne){
        if (!hasNode(nodeOne)) nodeMap.remove(nodeOne, new HashSet<T>());
    }

    public void connect(T nodeOne, T nodeTwo, String name, double weight){

        add(nodeOne);
        add(nodeTwo);

        Set<Edge> aEdges = (Set<Edge>) nodeMap.get(nodeOne);
        Set<Edge> bEdges = (Set<Edge>) nodeMap.get(nodeTwo);

        aEdges.add(new Edge(nodeOne, name, weight));
        bEdges.add(new Edge(nodeTwo, name, weight));

//        if (!hasNode(nodeOne)){
//            throw new NoSuchElementException(nodeOne.toString() + "you're stupid");
//        }
//
//        if (!hasNode(nodeTwo)){
//            throw new NoSuchElementException(nodeTwo.toString() + "you're stupid");
//        }
//
//        if (!hasEdge(nodeOne, nodeTwo)){
//            edgeCount++;
//        }
//
//        nodeMap.get(nodeOne).add(nodeTwo);
//        nodeMap.get(nodeTwo).add(nodeOne);
    }

    void disconnect(T nodeOne, T nodeTwo){

    }

    void setConnectionWeight(){

    }

    public int getNodes(){
        return nodeMap.size();
    }

//    public int getEdgesFrom(){
//
//    }

    public Edge<T> getEdgeBetween(T nodeOne, T nodeTwo){
        validateNode(nodeOne);
        validateNode(nodeTwo);
        if(nodeMap.get(nodeOne).contains(nodeTwo)){
            Edge.getDestination();
        } else {
            return null;
        }
    }

    private Edge getEdgeBetween(City next, City current) {
        for (Edge edge : nodes.get(next)) {
            if (edge.getDestination().equals(current)) {
                return edge;
            }
        }

        return null;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        for (T nodeOne: nodeMap.keySet()) {
            builder.append(nodeOne.toString() + ": ");
            for (T nodeTwo: nodeMap.get(nodeOne)) {
                builder.append(nodeTwo.toString() + " ");
            }
            builder.append("\n");
        }

        return builder.toString();
    }

    public boolean pathExists(T nodeOne, T nodeTwo){
        if(!hasNode(nodeOne) || !hasNode(nodeTwo)){
            return false;
        }
        return true;
    }

    void getPath() {

    }

    @Override
    public Iterator<T> iterator() {
        return nodeMap.keySet().iterator();
    }

    public static void main(String[] args) {
        ListGraph<String> graph = new ListGraph<>();

        graph.add("Stockholm");
        graph.add("Malmo");

        graph.connect("Stockholm", "Malmo", "E20", 200);


        System.out.println(graph);

        System.out.println("Nodes: " + graph.getNodes());
        System.out.println("Edges: " + graph.getNodes());
    }

}

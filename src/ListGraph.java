import java.util.*;

public class ListGraph<T> implements Graph<T> {

    private final Map<T,  Set<Edge<T>>>nodeMap = new HashMap<>();

    private void validateNode(T nodeOne) {
        if (!hasNode(nodeOne)) throw new NoSuchElementException(nodeOne.toString() + " node doesn't exist");
    }

    public boolean hasNode(T nodeOne) {
        return nodeMap.containsKey(nodeOne);
    }

    public boolean hasEdge(T nodeOne, T nodeTwo) {
        validateNode(nodeOne);
        validateNode(nodeTwo);

      if(nodeMap.get(nodeOne).contains(nodeTwo)){
            System.out.println("EDGE EXISTS");
            return true;
        } else {
            System.out.println("NO EDGE");
            return false;
        }
    }

    public void add(T nodeOne){
        nodeMap.putIfAbsent(nodeOne, new HashSet<>());
        //if (!hasNode(nodeOne)) nodeMap.put(nodeOne, new HashSet<T>());
    }

    @Override
    public void setConnectionWeight(T node1, T node2, int weight) {

    }

    @Override
    public Set<T> getNodes() {
        return nodeMap.keySet();
    }

    public void remove(T nodeOne){
        if (!hasNode(nodeOne)) nodeMap.remove(nodeOne, new HashSet<T>());
    }

    public void connect(T nodeOne, T nodeTwo, String name, int weight){

        if(!hasNode(nodeOne)){
            throw new NoSuchElementException(nodeOne.toString() + "Node " + nodeOne + " does not exist");
        } else if (!hasNode(nodeTwo)){
            throw new NoSuchElementException(nodeOne.toString() + "Node " + nodeTwo + " does not exist");
        } else if (weight < 0){
            throw new IllegalArgumentException();
        }

        Set<Edge<T>> nodeOneEdges = nodeMap.get(nodeOne);
        Set<Edge<T>> nodeTwoEdges = nodeMap.get(nodeTwo);

        nodeOneEdges.add(new Edge<T>(nodeTwo, name, weight));
        nodeTwoEdges.add(new Edge<T>(nodeOne, name, weight));

    }

    public void disconnect(T nodeOne, T nodeTwo){
        if(hasNode(nodeOne) || hasNode(nodeTwo)) {

            Edge<T> edge1 = getEdgeBetween(nodeOne, nodeTwo);
            Edge<T> edge2 = getEdgeBetween(nodeTwo, nodeOne);


            System.out.println("CHECK");
            this.nodeMap.get(nodeOne).remove(edge1);
            this.nodeMap.get(nodeTwo).remove(edge2);
        }
    }

    void setConnectionWeight(){

    }

    @Override
    public Collection<Edge<T>> getEdgesFrom(T node) {
        return null;
    }

//    public int getEdgesFrom(){
//
//    }

    public Edge<T> getEdgeBetween(T nodeOne, T nodeTwo){
        validateNode(nodeOne);
        validateNode(nodeTwo);

        for (Edge<T> tEdge : nodeMap.get(nodeOne)) {
            if(tEdge.getDestination().equals(nodeTwo)) {
                return tEdge;
            }
        }
    return null;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        for (T nodeOne: nodeMap.keySet()) {
            builder.append(nodeOne.toString() + ": ");
            for (Edge<T> nodeTwo: nodeMap.get(nodeOne)) {
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

    @Override
    public List<Edge<T>> getPath(T from, T to) {
        return null;
    }

    void getPath() {

    }

    public static void main(String[] args) {
        ListGraph<String> graph = new ListGraph<>();

        graph.add("Stockholm");
        graph.add("Malmo");

        System.out.println(graph);

        graph.connect("Stockholm", "Malmo", "E20", 510);

        System.out.println(graph);

        graph.disconnect("Stockholm", "Malmo");

        System.out.println(graph);

    }

}

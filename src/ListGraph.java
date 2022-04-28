import java.util.*;

public class ListGraph<T> implements Iterable<T> {

    private final Map<T, Set<Edge>> nodeMap = new HashMap<>();

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

    public void remove(T nodeOne){
        if (!hasNode(nodeOne)) nodeMap.remove(nodeOne, new HashSet<T>());
    }

    public void connect(T nodeOne, T nodeTwo, String name, double weight){

        if(!hasNode(nodeOne)){
            throw new NoSuchElementException(nodeOne.toString() + "Node " + nodeOne + " does not exist");
        } else if (!hasNode(nodeTwo)){
            throw new NoSuchElementException(nodeOne.toString() + "Node " + nodeTwo + " does not exist");
        } else if (weight < 0){
            throw new IllegalArgumentException();
        }

        Set<Edge> nodeOneEdges = (Set<Edge>) nodeMap.get(nodeOne);
        Set<Edge> nodeTwoEdges = (Set<Edge>) nodeMap.get(nodeTwo);

        nodeOneEdges.add(new Edge(nodeOne, name, weight));
        nodeTwoEdges.add(new Edge(nodeTwo, name, weight));

    }

    void disconnect(T nodeOne, T nodeTwo){
        if(hasNode(nodeOne) || hasNode(nodeTwo)) {
            System.out.println("CHECK");
            this.nodeMap.get(nodeOne).remove(nodeTwo);
            this.nodeMap.get(nodeTwo).remove(nodeOne);
        }
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

            return null;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        for (T nodeOne: nodeMap.keySet()) {
            builder.append(nodeOne.toString() + ": ");
            for (Edge nodeTwo: nodeMap.get(nodeOne)) {
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

        System.out.println(graph);

        graph.connect("Stockholm", "Malmo", "E20", 510);

        System.out.println(graph);

        graph.disconnect("Stockholm", "Malmo");

        System.out.println(graph);
        
    }

}

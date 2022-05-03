import java.util.*;

public class ListGraph<T> implements Graph<T> {

    private final Map<T,  Set<Edge<T>>>nodeMap = new HashMap<>();

    private void validateNode(T nodeOne) {
        if (!hasNode(nodeOne)) throw new NoSuchElementException(nodeOne.toString() + " node doesn't exist");
    }

    public boolean hasNode(T nodeOne) {
        return nodeMap.containsKey(nodeOne);
    }

    public void add(T nodeOne){
        nodeMap.putIfAbsent(nodeOne, new HashSet<>());
    }

    @Override
    public Set<T> getNodes() {
        return nodeMap.keySet();
    }

    public void remove(T nodeOne){
        if(hasNode(nodeOne)) {
            nodeMap.remove(nodeOne);

        } else {
            throw new NoSuchElementException(nodeOne.toString() + "Node " + nodeOne + " does not exist");
        }
    }

    public void connect(T nodeOne, T nodeTwo, String name, int weight){

        if(!hasNode(nodeOne)){
            throw new NoSuchElementException(nodeOne.toString() + "Node " + nodeOne + " does not exist");
        } else if (!hasNode(nodeTwo)){
            throw new NoSuchElementException(nodeOne.toString() + "Node " + nodeTwo + " does not exist");
        } else if (weight < 0){
            throw new IllegalArgumentException("Weight can not be less than 0");
        } else if (pathExists(nodeOne, nodeTwo)) {
            throw new IllegalStateException();
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

            if(edge1 != null && edge2 != null) {
                this.nodeMap.get(nodeOne).remove(edge1);
                this.nodeMap.get(nodeTwo).remove(edge2);
            } else {
                throw new IllegalStateException();
            }
        }
    }

    @Override
    public void setConnectionWeight(T nodeOne, T nodeTwo, int newWeight){
        if(!nodeMap.containsKey(nodeOne) || !nodeMap.containsKey(nodeTwo)){
            throw new NoSuchElementException();
        }else if (newWeight < 0){
            throw new IllegalArgumentException();
        }else{
            getEdgeBetween(nodeOne, nodeTwo).setWeight(newWeight);
            getEdgeBetween(nodeTwo, nodeOne).setWeight(newWeight);
        }
    }


    @Override
    public Collection<Edge<T>> getEdgesFrom(T node) {
        if(!nodeMap.containsKey(node)){
            throw new NoSuchElementException();
        }else{
            Collection<Edge<T>> edgeCollection = nodeMap.get(node);
            return edgeCollection;
        }
    }

    public Edge<T> getEdgeBetween(T nodeOne, T nodeTwo){
        if(!hasNode(nodeOne) || !hasNode(nodeTwo)){
            throw new NoSuchElementException(nodeOne.toString() + " node doesn't exist");
        } else {
            for (Edge<T> tEdge : nodeMap.get(nodeOne)) {
                if(tEdge.getDestination().equals(nodeTwo)) {
                    return tEdge;
                }
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
        if(hasNode(nodeOne) || hasNode(nodeTwo)){
            if (getEdgeBetween(nodeOne, nodeTwo) != null) {
                return true;
            }
        }
        return false;
    }


    @Override
    public List<Edge<T>> getPath(T nodeOne, T nodeTwo) {
        if(hasNode(nodeOne) || hasNode(nodeTwo)) {
            List <Edge<T>> edgeList = new ArrayList<>();


            return edgeList;
        }
        return null;
    }

    public static void main(String[] args) {
        ListGraph<String> graph = new ListGraph<>();

        graph.add("Stockholm");
        graph.add("Malmo");

        System.out.println(graph);

        graph.connect("Stockholm", "Malmo", "E20", 510);

        System.out.println(graph);

        System.out.println(graph.getEdgesFrom("Stockholm"));

    }

}

//Grupp 169, alwa1412, vami4627, anre8319
import java.util.Objects;

public class Edge <T>{

    private T nodeOne;
    private String name;
    private int weight;

    public Edge(T nodeOne, String name, int weight) {
        this.nodeOne = Objects.requireNonNull(nodeOne);
        this.name = Objects.requireNonNull(name);

        if (weight < 0) {
            throw new IllegalArgumentException();
        }
        this.weight = weight;
    }

    public T getDestination() {
        return nodeOne;
    }

    public int getWeight() {
        return weight;
    }

    void setWeight(int newWeight){
        if(newWeight < 0){
            throw new IllegalArgumentException("Vikten är negativ");
        }
        else{
            this.weight = newWeight;
        }
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "till " + nodeOne + " med " + name + " tar " + weight;
    }

}

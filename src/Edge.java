import java.util.*;

public class Edge <T>{



    private T nodeOne;
    private String name;
    private double weight;

    public Edge(String name, double weight, T nodeOne) {
        this.nodeOne = Objects.requireNonNull(nodeOne);
        this.name = Objects.requireNonNull(name);

        if (Double.isNaN(weight)) {
            throw new IllegalArgumentException();
        }
        this.weight = weight;
    }

    T getDestination(){
        return nodeOne;
    }

    public double getWeight() {
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
        return "Edge{" +
                ", name='" + name + '\'' +
                ", weight=" + weight +
                '}';
    }

}

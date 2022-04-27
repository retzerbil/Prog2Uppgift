import java.util.Objects;

public class Edge <T>{

    public T nodeOne;
    public String name;
    public double weight;

    public Edge(T nodeOne, String name, double weight) {
        this.nodeOne = Objects.requireNonNull(nodeOne);
        this.name = Objects.requireNonNull(name);

        if (Double.isNaN(weight)) {
            throw new IllegalArgumentException();
        }
        this.weight = weight;
    }

    public T getDestination() {
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
                ", destination=" + nodeOne +
                ", name='" + name + '\'' +
                ", weight=" + weight +
                '}';
    }

}

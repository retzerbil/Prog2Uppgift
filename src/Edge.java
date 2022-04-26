import java.util.Objects;

public class Edge {

    private final String destination;
    private final String name;
    private final double weight;

    public Edge(String name, double weight) {
        this.name = Objects.requireNonNull(name);

        if (Double.isNaN(weight)) {
            throw new IllegalArgumentException();
        }
        this.weight = weight;
    }

    void getDestination(){

    }

    public double getWeight() {
        return weight;
    }

    void setWeight(){

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

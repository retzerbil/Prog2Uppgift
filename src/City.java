import java.util.Objects;
public class City {

    private final String name;

    public City(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public boolean equals(Object other) {
        if (other instanceof City city) {
            return name.equals(city.name);
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(name);
    }

    public String toString() {
        return name;
    }
}

import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class NewPlace extends BorderPane {

    private Circle circle;
    private String name;
    private boolean selected;

    public NewPlace(String name, double x, double y) {
        circle = new Circle(10, 10, 10);
        circle.setFill(Color.BLUE);
        circle.setStroke(Color.BLACK);
        circle.setCenterX(x);
        circle.setCenterY(y);
        circle.setRadius(15);
        circle.setRadius(15);
        relocate(x, y);
        setTop(circle);
        this.name = name;
        selected = false;
    }

    @Override
    public String toString() {
        return "NewPlace{" +
                "name='" + name + '\'' +
                '}';
    }

    public String getName(){
        return name;
    }

    public void selectPlace(){
        this.selected = true;
        this.circle.setFill(Color.RED);
    }

    public void deselectPlace(){
        this.selected = false;
        this.circle.setFill(Color.BLUE);
    }

    public boolean isSelected(){
        return selected;
    }

}


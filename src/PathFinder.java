import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;


public class PathFinder extends Application {
    ArrayList<NewPlace> selectedPlaces = new ArrayList<>();
    ListGraph<NewPlace> listGraph = new ListGraph<>();

    private Stage stage;
    private BorderPane root;
    private Pane center;
    private boolean changed = false;

    Button newButton = new Button("New Place");

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) {

        this.stage = stage;
        root = new BorderPane();
        VBox vbox = new VBox();
        root.setTop(vbox);

        //TOP

        Menu m = new Menu("File");

        MenuItem newMap = new MenuItem("New Map");
        //newMap.setOnAction(new newMapHandler());
        MenuItem openItem = new MenuItem("Open");
        //openItem.setOnAction(new openItemHandler());
        MenuItem saveItem = new MenuItem("Save");
        //saveItem.setOnAction(new saveItemHandler());
        MenuItem saveImage = new MenuItem("Save Image");
        //saveImage.setOnAction(new SaveImageHandler());
        MenuItem exitProgram = new MenuItem("Exit");
        //exitProgram.setOnAction(new ExitProgramHandler());

        // add menu items to menu
        m.getItems().addAll(newMap, openItem, saveItem, saveImage, exitProgram);

        // create a menubar
        MenuBar menuBar = new MenuBar();

        // add menu to menubar
        menuBar.getMenus().add(m);

        vbox.getChildren().add(menuBar);


        FlowPane controls = new FlowPane();

        controls.setPadding(new Insets(15));
        controls.setHgap(10);
        controls.setVgap(10);
        controls.setAlignment(Pos.CENTER);

        Button findPathButton = new Button("Find Path");
        Button showConnectionButton = new Button("Show Connection");
        //Button newButton = new Button("New Place");
        newButton.setOnAction(new NewButtonHandler());
        Button newConnectionButton = new Button("New Connection");
        Button changeConnectionButton = new Button("Change Connection");

        controls.getChildren().addAll(findPathButton, showConnectionButton, newButton, newConnectionButton, changeConnectionButton);

        vbox.getChildren().add(controls);

        //CENTER

        center = new Pane();
        Image image = new Image("file:D:/europa.gif");
        ImageView imageView = new ImageView(image);
        center.getChildren().add(imageView);
        root.setCenter(center);


        Scene scene = new Scene(root, 600, 800, Color.GRAY);
        stage.setScene(scene);
        stage.setTitle("PathFinder");
        stage.show();
        stage.setResizable(false);

    }

    class NewButtonHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            center.setOnMouseClicked(new ClickHandler());
            center.setCursor(Cursor.CROSSHAIR);
            newButton.setDisable(true);
        }
    }

    class ClickHandler implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent event) {
            double x = event.getX();
            double y = event.getY();
            center.setOnMouseClicked(null);
            center.setCursor(Cursor.DEFAULT);

            TextInputDialog newPlaceDialog = new TextInputDialog();
            newPlaceDialog.setTitle("Name Input");
            newPlaceDialog.setContentText("Name of place:");

            Optional<String> placeName = newPlaceDialog.showAndWait();
            if (placeName.isPresent()) {
                System.out.println("New place created: " + placeName.get());
            }

            NewPlace place = new NewPlace(placeName.get(), x, y);
            place.setOnMouseClicked(new PlaceSelectClickHandler());
            center.getChildren().add(place);
            listGraph.add(place);
            newButton.setDisable(false);
        }
    }

    class PlaceSelectClickHandler implements EventHandler<MouseEvent> {

        @Override
        public void handle(MouseEvent event) {
            NewPlace currentPlace = (NewPlace) event.getSource();

            if(currentPlace.isSelected()){
                currentPlace.deselectPlace();
                selectedPlaces.remove(currentPlace);
                System.out.println(selectedPlaces);
            } else if (selectedPlaces.size() < 2) {
                currentPlace.selectPlace();
                selectedPlaces.add(currentPlace);
                System.out.println(selectedPlaces);
            }

        }
    }

    class ExitProgramHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
        }
    }

    class SaveImageHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            try {
                WritableImage writableImage = root.snapshot(null, null);
                BufferedImage bufferedImage = SwingFXUtils.fromFXImage(writableImage, null);
                ImageIO.write(bufferedImage, "png", new File("capture.png"));
            } catch (IOException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Fel!");
                alert.showAndWait();
            }
        }
    }

    class ExitHandler implements EventHandler<WindowEvent> {
        @Override
        public void handle(WindowEvent event) {
            if (changed) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText("Unsaved changes, exit anyway?");
                alert.setContentText(null);
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() != ButtonType.OK)
                    event.consume();
            }
        }
    }
}

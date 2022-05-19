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
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import javax.imageio.ImageIO;
import javax.swing.*;
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
        newMap.setOnAction(new newMapHandler());
        MenuItem openItem = new MenuItem("Open");
        //openItem.setOnAction(new openItemHandler());
        MenuItem saveItem = new MenuItem("Save");
        //saveItem.setOnAction(new saveItemHandler());
        MenuItem saveImage = new MenuItem("Save Image");
        saveImage.setOnAction(new SaveImageHandler());
        MenuItem exitProgram = new MenuItem("Exit");
        exitProgram.setOnAction(new ExitProgramHandler());

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
        newConnectionButton.setOnAction(new newConnectionHandler());
        Button changeConnectionButton = new Button("Change Connection");
        changeConnectionButton.setOnAction(new changeConnectionHandler());

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
    class ConnectionDialog extends Alert {
        private final TextField nameField = new TextField();
        private final TextField timeField = new TextField();

        ConnectionDialog(String from, String to){
            super(AlertType.CONFIRMATION);
            GridPane grid = new GridPane();
            grid.setAlignment(Pos.CENTER);
            grid.setPadding(new Insets(10));
            grid.setHgap(5);
            grid.setVgap(10);
            grid.addRow(0, new Label("Name: "), nameField);
            grid.addRow(1, new Label("Time: "), timeField);
            setHeaderText("Connection from " + from + " to " + to);
            getDialogPane().setContent(grid);

        }

        public void setTimeField(int time){
            timeField.setText(String.valueOf(time));
        }

        public void setNameField(String name){
            nameField.setText(name);
        }

        public TextField getTimeField(){
            return timeField;
        }

        public TextField getNameField(){
            return nameField;
        }

        public int getTime(){
            return Integer.parseInt(timeField.getText());
        }

        public String getName(){
            return nameField.getText();
        }
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

    class newConnectionHandler implements EventHandler<ActionEvent>{
        @Override public void handle(ActionEvent event){
            if(selectedPlaces.size() < 2){
                Alert alert = new Alert(Alert.AlertType.ERROR, "You have to have two places selected");
                alert.showAndWait();
                return;
            }try{
                NewPlace from = selectedPlaces.get(0);
                NewPlace to = selectedPlaces.get(1);

                if(listGraph.pathExists(from, to)){
                    Alert alert = new Alert(Alert.AlertType.ERROR, from.getName() + " and " + to.getName() + " are already connected");
                    alert.showAndWait();
                    return;
                }

                ConnectionDialog dialog = new ConnectionDialog(from.getName(),to.getName());
                Optional<ButtonType> result = dialog.showAndWait();
                if(result.isPresent() && result.get() != ButtonType.OK)
                    return;

                String name = dialog.getName();
                int time = dialog.getTime();

                if(name.equals("")){
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Name cannot be empty");
                    alert.showAndWait();
                }

                if(time < 0){
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Time cannot be a negative integer");
                    alert.showAndWait();
                }



                listGraph.connect(from,to,name,time);
                Line line = new Line(from.getTranslateX(),from.getTranslateY(),to.getTranslateX(),to.getTranslateY());
                /*
                line.setStartX(from.getTranslateX());
                line.setStartY(from.getTranslateY());
                line.setEndX(to.getTranslateX());
                line.setEndY(to.getTranslateY());
                 */
                center.getChildren().add(line);
            }catch(NumberFormatException e){
                Alert alert = new Alert(Alert.AlertType.ERROR, "Time has to be a number");
                alert.showAndWait();
            }
        }
    }

    class changeConnectionHandler implements EventHandler<ActionEvent>{
        @Override
        public void handle(ActionEvent event){
            if(selectedPlaces.size() < 2){
                Alert alert = new Alert(Alert.AlertType.ERROR, "You have to have two places selected");
                alert.showAndWait();
                return;
            }try{
                NewPlace from = selectedPlaces.get(0);
                NewPlace to = selectedPlaces.get(1);

                if(!listGraph.pathExists(from, to)){
                    Alert alert = new Alert(Alert.AlertType.ERROR, from.getName() + " and " + to.getName() + " are not connected");
                    alert.showAndWait();
                    return;
                }

                ConnectionDialog dialog = new ConnectionDialog(from.getName(),to.getName());
                dialog.nameField.setText(listGraph.getEdgeBetween(from,to).getName());
                dialog.nameField.setEditable(false);
                Optional<ButtonType> result = dialog.showAndWait();
                if(result.isPresent() && result.get() != ButtonType.OK)
                    return;


                int time = dialog.getTime();


                if(time < 0){
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Time cannot be a negative integer");
                    alert.showAndWait();
                }



                listGraph.getEdgeBetween(from,to).setWeight(time);
                listGraph.getEdgeBetween(to,from).setWeight(time);
                System.out.println(listGraph.toString());
                Line line = new Line(from.getTranslateX(),from.getTranslateY(),to.getTranslateX(),to.getTranslateY());
                /*
                line.setStartX(from.getTranslateX());
                line.setStartY(from.getTranslateY());
                line.setEndX(to.getTranslateX());
                line.setEndY(to.getTranslateY());
                 */
                center.getChildren().add(line);
            }catch(NumberFormatException e){
                Alert alert = new Alert(Alert.AlertType.ERROR, "Time has to be a number");
                alert.showAndWait();
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

    class newMapHandler implements EventHandler<ActionEvent>{
        @Override public void handle(ActionEvent event){
            //CENTER
            Pane center = new Pane();
            //path behövs ändras per dator, borde ha en bestämd plats.
            Image image = new Image("E:/Skola/ÅR 2/PROG2/europa.gif");
            ImageView imageView = new ImageView(image);
            center.getChildren().add(imageView);
            root.setCenter(center);
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

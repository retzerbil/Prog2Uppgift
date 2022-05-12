
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

public class PathFinder extends Application {

    private Stage stage;
    private BorderPane root;
    private boolean changed = false;



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

        // create a menu
        Menu m = new Menu("File");

        // create menu items
        MenuItem newMap = new MenuItem("New Map");
        //newMap.setOnAction(new newMapHandler());
        MenuItem openItem = new MenuItem("Open");
        //openItem.setOnAction(new openItemHandler());
        MenuItem saveItem = new MenuItem("Save");
        //saveItem.setOnAction(new saveItemHandler());
        MenuItem saveImage = new MenuItem("Save Image");
        saveImage.setOnAction(new SaveImageHandler());
        MenuItem exitProgram = new MenuItem("Exit");
        exitProgram.setOnAction(new ExitProgramHandler());

        // add menu items to menu
        m.getItems().addAll(newMap,openItem,saveItem,saveImage,exitProgram);

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

        Button button1 = new Button("Find Path");
        Button button2 = new Button("Show Connection");
        Button button3 = new Button("New Place");
        Button button4 = new Button("New Connection");
        Button button5 = new Button("Change Connection");

        controls.getChildren().addAll(button1, button2, button3, button4, button5);

        vbox.getChildren().add(controls);

        //CENTER
        Pane center = new Pane();
        Image image = new Image("file:D:/europa.gif");
        ImageView imageView = new ImageView(image);
        center.getChildren().add(imageView);
        root.setCenter(center);



        Scene scene = new Scene(root, 600, 800, Color.GRAY);
        stage.setScene(scene);
        stage.show();
        stage.setResizable(false);

    }

    class ExitProgramHandler implements EventHandler<ActionEvent>{
        @Override public void handle(ActionEvent event){
            // Skapa samma event som när användaren klickar i stängsningsrutan,
            // tas om hand av ExitHandler
            stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
        }
    }

    class SaveImageHandler implements EventHandler<ActionEvent>{
        @Override public void handle(ActionEvent event){
            try{
                WritableImage writableImage = root.snapshot(null, null);
                BufferedImage bufferedImage = SwingFXUtils.fromFXImage(writableImage,null);
                ImageIO.write(bufferedImage, "png",new File("capture.png"));
            }catch(IOException e){
                Alert alert = new Alert(Alert.AlertType.ERROR, "Fel!");
                alert.showAndWait();
            }
        }
    }

    class ExitHandler implements EventHandler<WindowEvent> {
        @Override public void handle(WindowEvent event){
            if (changed){
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

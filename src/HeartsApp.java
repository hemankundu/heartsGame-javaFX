import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.xml.bind.annotation.XmlAnyAttribute;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class HeartsApp extends Application {

    static Stage stage;
    static Controller controller;
    static Player[] players=new Player[4];
    static Deck deck;
    public HeartsApp(){
        stage=new Stage();

    }

    public static void main(String[] args) {
        launch(args);

    }
    @Override
    public void start(Stage primaryStage) throws Exception{
        stage=primaryStage;
        SetScene1();
        stage.show();
       // SetScene2();

    }
    public void SetScene(String fxmlFile)throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource(fxmlFile));
        String css=getClass().getResource("myStyle.css").toExternalForm();
        Scene scene=new Scene(root,800,800);
        scene.getStylesheets().add(css);
        stage.setScene(scene);
        //stage.show();
    }

    public void SetScene1() throws Exception{
        SetScene("Design.fxml");
    }






}

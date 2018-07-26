import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.util.ArrayList;

public class Control{
    Stage stage;
    @FXML private javafx.scene.control.Button btnExit;
    @FXML private javafx.scene.control.Button btnStart;
    @FXML public ArrayList<TextField> plName;
    public Control(){
        stage=new Stage();
    }
    public void btnStartClicked()throws Exception{
        stage=(Stage) btnStart.getScene().getWindow();
        HeartsApp.controller=new Controller();
        for(int i=0;i<4;i++){
            HeartsApp.players[i]=new Player(i);
            HeartsApp.players[i].name=plName.get(i).getText();
            if(HeartsApp.players[i].name.equals(""))
                HeartsApp.players[i].name=("Player "+Integer.toString(i+1));
        }
        HeartsApp.deck=new Deck();
        SetScene2();
    }
    @FXML
    public void btnExitClicked(){
        stage=(Stage) btnExit.getScene().getWindow();
        stage.close();
    }
    public void SetScene(String fxmlFile)throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource(fxmlFile));
        String css=getClass().getResource("myStyle.css").toExternalForm();
        Scene scene;
        try{
            scene=new Scene(root,root.getScene().getWidth(),root.getScene().getHeight());
        }
        catch(NullPointerException e) {
            scene=new Scene(root,stage.getWidth(),stage.getHeight());
        }
        scene.getStylesheets().add(css);
        stage.setScene(scene);
        //stage.show();
    }
    public void  SetScene2()throws Exception{
        SetScene("Design2.fxml");
    }

}
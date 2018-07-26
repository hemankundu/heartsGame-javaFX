import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.effect.Glow;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Control2 implements Initializable {
    public double SIZE=55;
    public Stage stage;
    public String[] Name;
    public String[] passModes;
    @FXML private javafx.scene.control.Button btnBackStart;
    @FXML private javafx.scene.control.Button btnShuffle;
    @FXML public ArrayList<ImageView> pl1img;
    @FXML public ArrayList<ImageView> pl2img;
    @FXML public ArrayList<ImageView> pl3img;
    @FXML public ArrayList<ImageView> pl4img;
    @FXML public ArrayList<ImageView> swapimg;
    @FXML public ImageView swapimg1;
    @FXML public ImageView dkimg1;
    @FXML public ImageView dkimg2;
    @FXML public ImageView dkimg3;
    @FXML public ImageView dkimg4;
    @FXML public Label lblNotification;
    @FXML public ArrayList<Label> lblPl;
    @FXML public ImageView[] dkimg;
    @FXML public AnchorPane rootGrid;
    @FXML public AnchorPane dkAncPane;
    @FXML public ArrayList<ImageView>[] plimg;
    @FXML public DialogPane dialogPane;
    @FXML public Label dialogHeader;
    public Control2() {
        stage = new Stage();
        plimg = new ArrayList[4];
        dkimg=new ImageView[4];
        passModes=new String[]{"Left","Right","Across","No"};
        Name=new String[4];
        for (int i=0;i<4;i++){
            Name[i]=HeartsApp.players[i].name;
        }
    }
    public void initializeImageViews(){
        plimg[0]=pl1img;
        plimg[1]=pl2img;
        plimg[2]=pl3img;
        plimg[3]=pl4img;
        dkimg[0]=dkimg1;
        dkimg[1]=dkimg2;
        dkimg[2]=dkimg3;
        dkimg[3]=dkimg4;
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeImageViews();
        HeartsApp.controller.NewGame();
        lblNotification.setText("PASS CARD");
        DisplayHidePlayerCard(HeartsApp.controller.curPlayer);
        UpdateScores();
        //DisplayPlayerCard();
    }
    @FXML
    public void btnBackStartClicked() throws Exception {
        stage = (Stage) btnBackStart.getScene().getWindow();
        SetScene1();
    }
    @FXML
    public void btnShuffleClicked() {
        for(int i=0;i<3;i++){
            swapimg.get(i).setImage(null);
            swapimg.get(i).setVisible(false);
        }
        HeartsApp.controller.swapDone[HeartsApp.controller.curPlayer]=true;
        btnShuffle.setVisible(false);
        HeartsApp.controller.curPlayer=(HeartsApp.controller.curPlayer+1)%4;
        DisplayHidePlayerCard(HeartsApp.controller.curPlayer);
        boolean allSwapDone=false;
        int j;
        for(j=0;j<4 && HeartsApp.controller.swapDone[j];j++);
        if(j==4)allSwapDone=true;
        if(allSwapDone){
            HeartsApp.controller.PassCards();
            //DisplayPlayerCard();
            DisplayHidePlayerCard(HeartsApp.controller.curPlayer);
            lblNotification.setText("TURN | "+Name[HeartsApp.controller.curPlayer]);//***********player name here
            FilterCard(HeartsApp.controller.curPlayer);
            UpdateScores();
            HeartsApp.controller.firstGame=false;
        }
    }
    private void EnableDisbleImage(int playerIndex,int i,boolean disable){
        //String oldUrl=plimg[playerIndex].get(i).g

        if(disable) plimg[playerIndex].get(i).setOpacity(0.6);
        else plimg[playerIndex].get(i).setOpacity(1);
        plimg[playerIndex].get(i).setDisable(disable);
    }
    private void FilterCard(int playerIndex) {
        ArrayList<Card> cards=HeartsApp.players[playerIndex].getCards();
        //int cardCount=(HeartsApp.deck.cardCount+1)%4;
        System.out.println(".............DeckCardCount="+HeartsApp.deck.cardCount);
        for(int i=0;i<cards.size();i++){
            //plimg[playerIndex].get(i).setDisable(true);
            EnableDisbleImage(playerIndex,i,true);
        }
        if(HeartsApp.controller.firstGame){
            for(int i=0;i<cards.size();i++){
                System.out.print("\n"+cards.get(i).type+" "+cards.get(i).val+"  ");
                if(cards.get(i).val==2&&cards.get(i).type=='C'){
                    //plimg[playerIndex].get(i).setDisable(false);
                    EnableDisbleImage(playerIndex,i,false);
                    System.out.println(" Enabled");
                }
            }
        }
        else if(HeartsApp.deck.cardCount==0){
            for(int i=0;i<cards.size();i++){
                System.out.print("\n"+cards.get(i).type+" "+cards.get(i).val+"  ");
                System.out.println(" Enabled");
                //plimg[playerIndex].get(i).setDisable(false);
                EnableDisbleImage(playerIndex,i,false);

            }
        }
        else if(HeartsApp.deck.cardCount>=1){
              Card card= HeartsApp.deck.cards.get(0);
              boolean noClr=true;
              for(int i=0;i<cards.size();i++){
                  System.out.print("\n"+cards.get(i).type+" "+cards.get(i).val+"  ");
                  if(cards.get(i).type==card.type){
                      //plimg[playerIndex].get(i).setDisable(false);
                      EnableDisbleImage(playerIndex,i,false);
                      System.out.print("Enabled");
                      noClr=false;
                  }
              }
              if(noClr){
                  for(int i=0;i<cards.size();i++){
                      System.out.print("\n"+cards.get(i).type+" "+cards.get(i).val+"  ");
                      System.out.println(" Enabled");
                      //plimg[playerIndex].get(i).setDisable(false);
                      EnableDisbleImage(playerIndex,i,false);
                  }
              }

        }
    }

    public void SetScene(String fxmlFile) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource(fxmlFile));
        String css = getClass().getResource("myStyle.css").toExternalForm();
        Scene scene;
        try {
            scene = new Scene(root, root.getScene().getWidth(), root.getScene().getHeight());
        } catch (NullPointerException e) {
            scene = new Scene(root, stage.getWidth(), stage.getHeight());
        }
        scene.getStylesheets().add(css);
        stage.setScene(scene);
    }

    public void SetScene1() throws Exception {
        SetScene("Design.fxml");
    }

    public Image PickImage(Card card, int playerIndex) {
        char mode ='v';
        if(playerIndex%2==0) {
            mode = 'h';
        }
        String url = "src/Assets/CardAssets/png-" + mode + "ENB/";
        String val;
        if (card.val == 1) val = "A";
        else if (card.val == 11) val = "J";
        else if (card.val == 12) val = "Q";
        else if (card.val == 13) val = "K";
        else val = Integer.toString(card.val);
        url += val + card.type + ".png";
        Image image;
        try {
            image = new Image(new FileInputStream(url));
            //System.out.println("......found file");
        } catch (FileNotFoundException e) {
            //System.out.println("...........not found file");
            image = null;
        }
        return image;
    }

    public void DisplayPlayerCard(int startIndex){
        for(int i=0;i<4;i++){
            DisplayCard(HeartsApp.players[i],startIndex);
        }
    }
    public void DisplayPlayerCard(){
        for(int i=0;i<4;i++){
            DisplayCard(HeartsApp.players[i],0);
        }
    }
    public void DisplayPlayerCard(Player player,int startIndex){
        DisplayCard(player,startIndex);
    }
    public void DisplayPlayerCard(Player player){
        DisplayCard(player,0);
    }
    private void DisplayCard(Player player,int startIndex){
        ArrayList<Card> cards = player.getCards();
        int i;
        for (i = startIndex; i <player.cardCount; i++) {
                double size=SIZE;
                //System.out.println("size="+size);
                //plimg[player.getIndex()].get(i).setDisable(false);
                plimg[player.getIndex()].get(i).setImage(PickImage(cards.get(i),player.getIndex()));
                if(player.getIndex()%2==0) plimg[player.getIndex()].get(i).setFitHeight(size);
                else plimg[player.getIndex()].get(i).setFitWidth(size);
                plimg[player.getIndex()].get(i).setVisible(true);
                plimg[player.getIndex()].get(i).setOpacity(1);
                plimg[player.getIndex()].get(i).setDisable(false);
        }
        for(;i<13;i++){
            plimg[player.getIndex()].get(i).setImage(null);
            plimg[player.getIndex()].get(i).setVisible(false);
        }
    }
    private void HideCard(Player player){
        char mode ='v';
        if(player.getIndex()%2==0) {
            mode = 'h';
        }
        String url = "src/Assets/CardAssets/png-" + mode + "ENB/red_back.png";
        Image image;
        try {
            image = new Image(new FileInputStream(url));
            //System.out.println("......found file");
        } catch (FileNotFoundException e) {
            //System.out.println("...........not found file");
            image = null;
        }
        int i;
        for (i = 0; i <player.cardCount; i++) {
            double size=SIZE;
            //plimg[player.getIndex()].get(i).setDisable(false);
            plimg[player.getIndex()].get(i).setImage(image);
            if(player.getIndex()%2==0) plimg[player.getIndex()].get(i).setFitHeight(size);
            else plimg[player.getIndex()].get(i).setFitWidth(size);
            plimg[player.getIndex()].get(i).setVisible(true);
            plimg[player.getIndex()].get(i).setOpacity(1);
        }
        for(;i<13;i++){
            plimg[player.getIndex()].get(i).setImage(null);
            plimg[player.getIndex()].get(i).setVisible(false);
        }
    }
    public void DisplayHidePlayerCard(int curPlayer){
        for(int i=0;i<4;i++){
            if(i==curPlayer) DisplayPlayerCard(HeartsApp.players[i]);
            else HideCard(HeartsApp.players[i]);
        }
    }
    @FXML
    public void plimgClicked(MouseEvent mouseEvent) {
        int curPlayer=HeartsApp.controller.curPlayer;
        Player player=HeartsApp.players[curPlayer];
        for(int i=0;i<player.cardCount;i++){
            if(mouseEvent.getSource().equals(plimg[curPlayer].get(i))){
                String IdStr=plimg[curPlayer].get(i).getId();
                int playerIndex=Integer.valueOf(IdStr.charAt(2)-48)-1;
                int cardIndex=Integer.valueOf(IdStr.substring(6))-1;
                if(HeartsApp.controller.swapDone[playerIndex]|| HeartsApp.controller.passMode==3)
                    PlayClick(playerIndex,cardIndex);
                else
                    SwapClick(playerIndex,cardIndex);
            }
        }
    }
    public void PlayClick(int playerIndex,int cardIndex){
        //System.out.println(playerIndex+", "+cardIndex);
        HeartsApp.deck.AddCard(HeartsApp.players[playerIndex].getCards().get(cardIndex),playerIndex);
        dkimg[playerIndex].setImage(plimg[playerIndex].get(cardIndex).getImage());
        HeartsApp.players[playerIndex].RemoveCard(cardIndex);
        DisplayCard(HeartsApp.players[playerIndex],cardIndex);
        if(HeartsApp.deck.cardCount==4){
            boolean newGame=false;
            int[] dialogScores={0,0,0,0};
            if(HeartsApp.players[HeartsApp.controller.curPlayer].getCards().size()==0){
                newGame=true;
                /*dialogScores=new int[4];
                for(int i=0;i<4;i++)
                    dialogScores[i]=HeartsApp.players[i].score;*/
            }
            HeartsApp.controller.CheckCurWinner(HeartsApp.deck.cards,HeartsApp.deck.givenBy);
            HeartsApp.deck.Clear();
            UpdateScores();
            for(int i=0;i<4;i++){
                dkimg[i].setImage(null);
            }
            if(newGame){
                ShowDialog((HeartsApp.controller.lastGame)?1:0);
                lblNotification.setText("PASS CARD");
            }
        }
        DisplayHidePlayerCard(HeartsApp.controller.curPlayer);
        FilterCard(HeartsApp.controller.curPlayer);
        lblNotification.setText("TURN | "+Name[HeartsApp.controller.curPlayer]);//*************player name here
    }
    @FXML public Button dialogBtn;
    @FXML public ArrayList<VBox> plScore;
    @FXML public ArrayList<Label> plHead;
    @FXML public ArrayList<Label> plFinalScore;
    private void ShowDialog(int mode) {
        String head="",btn="OK";
        if(mode==1){
            head="Final ";
            btn="Back to Start";
        }
        int oldScore;
        dialogHeader.setText(head+"Score Board");
        dialogBtn.setText(btn);
        for(int i=0;i<4;i++){
            plHead.get(i).setText(Name[i]); //*******************************palyer name here
            Label label=new Label();
            if(plFinalScore.get(i).getText().equals("")){
                oldScore=0;
            }
            else{
                oldScore=Integer.valueOf(plFinalScore.get(i).getText());
            }
            label.setText(""+(HeartsApp.players[i].finalScore - oldScore));
            plScore.get(i).getChildren().add(label);
            plFinalScore.get(i).setText(""+HeartsApp.players[i].finalScore);

        }
        dialogPane.setVisible(true);
    }
    @FXML
    public void dialogBtnClicked() throws Exception {
        if(dialogBtn.getText().equals("OK")){
            dialogPane.setVisible(false);
            DisplayHidePlayerCard(HeartsApp.controller.curPlayer);
        }
        else{
            btnBackStartClicked();
        }
    }
    public void lblHemanClicked() throws Exception {
        int [] dialogScores=new int[4];
        for(int i=0;i<4;i++)
            dialogScores[i]=HeartsApp.players[i].score;
        ShowDialog(0);
    }
    private void UpdateScores() {
        for(int i=0;i<4;i++){
            lblPl.get(i).setText(Name[i]+" | Score: "+HeartsApp.players[i].score); //***** player name here
        }
    }

    public void SwapClick(int playerIndex,int cardIndex){
        //System.out.println(playerIndex+", "+cardIndex);
        int i;
        for(i=0;i<HeartsApp.controller.swapCards[playerIndex].size();i++){
            if(HeartsApp.controller.swapCards[playerIndex].get(i).equals(HeartsApp.players[playerIndex].getCards().get(cardIndex))){
                return;
            }
        }
        if(HeartsApp.controller.swapCards[playerIndex].size()>=3)
            HeartsApp.controller.swapCards[playerIndex].remove(0);
        HeartsApp.controller.swapCards[playerIndex].add(HeartsApp.players[playerIndex].getCards().get(cardIndex));
        for(i=0;i<HeartsApp.controller.swapCards[playerIndex].size();i++){
            swapimg.get(i).setImage(PickImage(HeartsApp.controller.swapCards[playerIndex].get(i),0));
            swapimg.get(i).setFitWidth(SIZE);
            swapimg.get(i).setVisible(true);
        }
        for(;i<3;i++){
            swapimg.get(i).setImage(null);
            swapimg.get(i).setVisible(false);
        }
        if(HeartsApp.controller.swapCards[playerIndex].size()==3){
            //this.playerIndex=playerIndex;
            btnShuffle.setText(passModes[HeartsApp.controller.passMode]+" Passing");
            btnShuffle.setVisible(true);
        }
    }
}

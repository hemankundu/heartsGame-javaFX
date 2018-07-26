import java.util.ArrayList;
import java.util.Random;
public class Controller {
    private  ArrayList<Card> cards;
    public int curPlayer,passMode;
    public boolean swapDone[],firstGame,lastGame;
    public static ArrayList<Card>[] swapCards;
    public Controller() {
        cards=new ArrayList<>(52);
        swapDone=new boolean[4];
        swapCards=new ArrayList[4];
        passMode=-1;
        lastGame=false;
    }
    public void Initialize(){
        System.out.println("Initialize.");
        char types[]={'S','D','C','H'};
        for(int i=0;i<4;i++) {
            for (int j = 0; j < 13; j++) {
                Card tempCard=new Card(types[i],j + 1);
                cards.add(tempCard);
            }
            swapCards[i]=new ArrayList<>(3);
            swapDone[i]=false;
        }
        curPlayer=0;
        firstGame=true;
    }
    public ArrayList<Card> getCards() {
        return cards;
    }
    public void Shuffle(){
        System.out.println("Shuffle.");
        int i,size=cards.size();
        Random rand=new Random();
        ArrayList <Card> shuffledCards=new ArrayList<>();
        for(i=0;i<size;i++){
            int randomIndex=(rand.nextInt(size-i));
            shuffledCards.add(cards.get(randomIndex));
            cards.remove(randomIndex);
        }
        cards=shuffledCards;
    }
    public void Distribute(Player[] players) {
        System.out.println("Distribute.");
        int i,lasVegasIndex=6;                   // lasVegasIndex here//
        for(i=0;i<=(lasVegasIndex*4)-1;i++){
            players[i%4].AddCard(cards.get(0));
            cards.remove(0);
        }
        for(i=0;i<13-lasVegasIndex;i++){
            boolean reshuffle=false;
            for(int plIndex=0;plIndex<4;plIndex++){
                double average=CalAverage(players[plIndex]);
                if(average<6.1||average>9.9){
                    reshuffle=true;
                    break;
                }
            }
            if(reshuffle) {
                Shuffle();
                System.out.println("Reshuffled..!");
            }
            for(int j=0;j<4;j++) {
                players[j].AddCard(cards.get(0));
                cards.remove(0);
            }
        }
        for(int plIndex=0;plIndex<4;plIndex++) {
            double average = CalAverage(players[plIndex]);
            System.out.println("Player "+(plIndex+1)+" average: "+average);
        }

    }
    private double CalAverage(Player player) {
        int i,sum=0;
        ArrayList<Card> plCards= player.getCards();
        for(i=0;i<player.cardCount;i++){
            if(plCards.get(i).val==1) sum+=14; // A=14
            else sum+=plCards.get(i).val;
        }
        return (double)sum/player.cardCount;
    }
    public void ShowCards(){
        System.out.println();
        for(int i=0;i<52;i++){
            if(i%13==0) System.out.println();
            System.out.print(i+": "+cards.get(i).type+cards.get(i).val+"  ");
        }
    }
    private int HasClubsTWo(){
        for(int i=0;i<4;i++){
            ArrayList<Card> cards=HeartsApp.players[i].getCards();
            for(int j=0;j<13;j++){
                if(cards.get(j).val==2&&cards.get(j).type=='C'){
                    return i;
                }
            }
        }
        return 0;
    }
    public void NewGame(){
        System.out.println("NewGame.");
        boolean endGame=false;
        Initialize();
        passMode=(passMode+1)%4;
        Shuffle();
        Distribute(HeartsApp.players);
        for(int i=0;i<4;i++){
            HeartsApp.players[i].finalScore+=HeartsApp.players[i].score;
            HeartsApp.players[i].score=0;
            if(HeartsApp.players[i].finalScore>=100)
                endGame=true;
        }
        if(endGame){
            lastGame=true;
            EndGame();
        }
    }

    private void EndGame() {

    }

    public void PassCards(){
        if(passMode!=3) {
            //do swaps for all players
            int pair[][]=new int[2][2];
            if(passMode==0){
                pair[0][0]=0; pair[0][1]=1;
                pair[1][0]=2; pair[1][1]=3;
            }
            else if(passMode==1){
                pair[0][0]=0; pair[0][1]=3;
                pair[1][0]=2; pair[1][1]=1;
            }
            else if(passMode==2){
                pair[0][0]=0; pair[0][1]=2;
                pair[1][0]=1; pair[1][1]=3;
            }
            //ArrayList<Card> swap=new ArrayList<>(3);
            for(int i=0;i<2;i++){
                for(int j=0;j<3;j++){
                    for(int k=0;k<13;k++){
                        if(HeartsApp.players[pair[i][0]].getCards().get(k).equals(swapCards[pair[i][0]].get(j))){
                            HeartsApp.players[pair[i][0]].getCards().set(k,swapCards[pair[i][1]].get(j));
                            break;
                        }
                    }
                    for(int k=0;k<13;k++){
                        if(HeartsApp.players[pair[i][1]].getCards().get(k).equals(swapCards[pair[i][1]].get(j))){
                            HeartsApp.players[pair[i][1]].getCards().set(k,swapCards[pair[i][0]].get(j));
                            break;
                        }
                    }
                }
            }
        }
        curPlayer=HasClubsTWo();
    }

    public int CheckCurWinner(ArrayList<Card> cards, ArrayList<Integer> givenBy) {
        System.out.println("CheckCurWinner");
        Card firstCard=cards.get(0);
        int winner=0;
        for(int i=1;i<4;i++){
            if(cards.get(i).type==firstCard.type){
                int curVal=cards.get(i).val;
                if(CheckCardValue(curVal,firstCard.val)){
                    firstCard=cards.get(i);
                    winner=i;
                }
            }
        }
        curPlayer=givenBy.get(winner);
        AdjustScore(curPlayer,cards);
        if(HeartsApp.players[curPlayer].getCards().size()==0){
            HeartsApp.controller.NewGame();
        }
        return curPlayer;
    }

    private boolean CheckCardValue(int big, int small){
        if(big==1) big+=13;
        if(small==1) small+=13;
        return (big>small);

    }

    private void AdjustScore(int playerIndex, ArrayList<Card> cards) {
        int addScore=0;
        for(int i=0;i<4;i++){
            if(cards.get(i).type=='H') addScore++;
            else if(cards.get(i).type=='S'&&cards.get(i).val==12) addScore+=13;
        }
        HeartsApp.players[playerIndex].score+=addScore;
        if(HeartsApp.players[playerIndex].score==26){
            for (int i = 0; i < 4; i++) {
                if (playerIndex != i) {
                    HeartsApp.players[i].score= 26;
                }
            }
            HeartsApp.players[playerIndex].score=0;
        }

    }
}

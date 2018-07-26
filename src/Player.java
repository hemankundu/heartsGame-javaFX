import java.util.ArrayList;

public class Player {
    ArrayList <Card> Cards;
    int index,score,finalScore;
    public int cardCount;
    public String name;
    public Player(int index){
        Cards = new ArrayList<Card>();
        cardCount = 0;
        this.index=index;
        finalScore=score=0;
    }
    public void AddCard(Card card){
        Cards.add(cardCount,card);
        cardCount++;
    }
    public int getIndex(){
        return index;
    }

    public ArrayList<Card> getCards() {
        return Cards;
    }
    public void RemoveCard(int index){
        Cards.remove(index);
        cardCount--;
    }
}

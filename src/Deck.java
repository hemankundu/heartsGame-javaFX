import java.util.ArrayList;

public class Deck {
    public  ArrayList<Card> cards;
    public  ArrayList<Integer> givenBy;
    public  int cardCount;
    public Deck(){
        cardCount=0;
        cards=new ArrayList<>(4);
        givenBy=new ArrayList<>(4);
    }
    public void AddCard(Card card,int playerIndex){
        if(cardCount<4) {
            cards.add(card);
            givenBy.add(playerIndex);
            cardCount++;
            HeartsApp.controller.curPlayer = (playerIndex + 1) % 4;
        }

    }
    public boolean IsFull(){
        return cardCount==4;
    }
    public void Clear(){
        givenBy.clear();
        cards.clear();
        cardCount=0;
    }
}

import java.util.ArrayList;

public class Main {
    public static void main(String args[])
    {
        Controller controller=new Controller();
        Player[] players=new Player[4];
        for(int i=0;i<4;i++)
            players[i]=new Player(i);
        //controller.ShowCards();
        controller.Shuffle();
        //controller.ShowCards();

        controller.Distribute(players);

        for(int i=0;i<4;i++)
        {
            ArrayList<Card> cards=players[i].getCards();
            System.out.println("\nPlayer "+(i+1)+" :");
            for(int j=0;j<13;j++)
                System.out.print("|"+cards.get(j).type+"|"+cards.get(j).val+"|  ");
        }
    }
}

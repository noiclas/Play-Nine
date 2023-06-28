import java.util.ArrayList;
import java.util.Collections;

public class Deck{
	private static ArrayList <Card> deck = new ArrayList<Card>();
	private static int cardNum = 0;

	public Deck(){
		for (int o=0;o<4;o++) {deck.add(new Card(-5));}
		for (int i=0;i<13;i++)
			for (int j=0;j<8;j++)
				deck.add(new Card(i));
	}

	public static void shuffle(){
		Collections.shuffle(deck);
	}

	public static void reshuffle(DiscardPile dp){
		dp.getDiscardPile().remove(dp.getDiscardPile().size()-1);
		deck.addAll(dp.getDiscardPile());
		shuffle();
	}

	public static Card draw(){
		Card top = deck.get(0);
		deck.remove(0);
		return top;
	} 
}

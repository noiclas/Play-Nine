import java.util.ArrayList;

public class DiscardPile{

	private static ArrayList<Card> dp;

	public DiscardPile(){
		dp = new ArrayList<Card>();
	}

	public static void display(){
		System.out.println("DISCARD PILE: "+dp.get(dp.size()-1).getValue());
	}

	public static void discard(Card card){
		dp.add(card);
	}

	public static Card draw(){
		Card card = dp.get(dp.size()-1);
		dp.remove(dp.size()-1);
		return card;
	}

	public static ArrayList<Card> getDiscardPile(){
		return dp;
	}
}
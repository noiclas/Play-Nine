import java.util.*;

public class Hand{
	private ArrayList <Integer> cardsFlipped = new ArrayList<Integer>();
	private Card[] hand = new Card[8];
	private int score=0;
	private String name;

	public Hand(Deck d, String n){
		for (int i=0;i<8;i++)
			hand[i] = d.draw();
		name = n.trim().toUpperCase();
	}

	public void flipAll(){
		for (int i=0;i<8;i++)
			hand[i].flip();
	}

	public void flip(int index){
		cardsFlipped.add(index);
		hand[index].flip();
	}

	public Card swap(Card card, int index){
		Card temp = hand[index];
		card.flip();
		hand[index] = card;
		if (cardsFlipped.indexOf(index)==-1)
			cardsFlipped.add(index);
		return temp;
	}

	public int getScore(){
		ArrayList<Integer> pairs = new ArrayList<Integer>();
		for(int i=0;i<4;i++){
			if (hand[i].getValue()!=hand[i+4].getValue())
				score+=hand[i].getValue()+hand[i+4].getValue();
			else
				pairs.add(hand[i].getValue());
		}
		if(pairs.size()==0)
			return score;
		else{
			int i = 0;
			Collections.sort(pairs);
			if(pairs.get(0)==-5)
				if (pairs.get(1)==-5){
					score+=-30;
					i+=2;
				}
				else{
					score+=-10;
					i++;
				}
			if(pairs.size()>i)
				for(i=i;i<pairs.size();i++){
					if (pairs.indexOf(pairs.get(i))!=pairs.lastIndexOf(pairs.get(i))){
						int index = pairs.lastIndexOf(pairs.get(i)) - pairs.indexOf(pairs.get(i));
						i+=index;
						score+= -5*(index+1);
					}
				}
			}
		return score;
	}

	public String getName(){
		return name;
	}

	public ArrayList<Integer> getFlippedList(){
		return cardsFlipped;
	}

	public Card getCard(int index){
		return hand[index];
	}
}
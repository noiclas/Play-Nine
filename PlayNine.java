import java.util.Arrays;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JFrame;

public class PlayNine{
	private static ArrayList<String> order = new ArrayList<String>(Arrays.asList
			("A","B","C","D","E","F","G","H"));
	private static Scanner keys = new Scanner(System.in);
	private static ArrayList<Hand> players;
	private static Deck deck;
	private static DiscardPile dp;
	private static Display disp;

	public PlayNine(){
		deck = new Deck();
		dp = new DiscardPile();
		players = new ArrayList<Hand>();
		disp = new Display();
		deck.shuffle();	
	}

	public static void gameSetup(){
		int numPlayers;
		System.out.print("\n\tWELCOME TO PLAY NINE.\n\n");
		do{
			System.out.print("HOW MANY PLAYERS? (2-6 PLAYERS): ");
			numPlayers = keys.nextInt();
		} while(numPlayers<2||numPlayers>6);
		for (int i=0;i<numPlayers;i++){
			System.out.print("PLAYER "+(i+1)+"'S NAME: ");
			String name = keys.next().trim().toUpperCase();
			players.add(new Hand(deck,name));
		}
	}

	public static void playGame(){
		boolean final_turn = false;
		String input;
		dp.discard(deck.draw());

		for (int i=0;i<players.size();i++){
			disp.refresh(i, dp, players);
			System.out.println(players.get(i).getName()+", WHICH CARDS WOULD YOU LIKE TO FLIP?");
			System.out.print("FIRST CARD: ");
			players.get(i).flip(checkFlip(players.get(i),keys.next().trim().toUpperCase()));
			disp.refresh(i, dp, players);
			System.out.print("SECOND CARD: ");
			players.get(i).flip(checkFlip(players.get(i),keys.next().trim().toUpperCase()));
		}
		while (!final_turn){
			for (int i=0;i<players.size();i++){
				disp.refresh(i, dp, players);
				System.out.print("WOULD YOU LIKE TO DRAW FROM THE DECK (D) OR DRAW FROM THE DISCARD PILE (DP)?\n: ");
				input = keys.next().trim().toUpperCase();
				disp.refresh(i, dp, players);
				if (checkChoice1(input)==0){
					Card c = deck.draw();
					System.out.println("YOU HAVE DRAWN A: "+c.getValue());
					System.out.print("WOULD YOU LIKE TO DISCARD (D) OR SWAP WITH ANOTHER CARD (S)?\n: ");
					input = keys.next().trim().toUpperCase();
					disp.refresh(i, dp, players);
					if(checkChoice2(input)==0){
						dp.discard(c);
						if (players.get(i).getFlippedList().size()!=7){
							System.out.print("FLIP WHICH CARD?\n: ");
							players.get(i).flip(checkFlip(players.get(i),keys.next().trim().toUpperCase()));
						}
						else if (players.get(i).getFlippedList().size()==7){
							System.out.print("WOULD YOU LIKE TO FLIP YOUR LAST CARD (Y/N)?\n: ");
							input = keys.next().trim().toUpperCase();
							if(checkFinalFlip(input)==0){
								players.get(i).flipAll();
								final_turn = true;
								finalTurn(i);								
								break;
							}
						}
					}
					else if (checkChoice2(input)==1){
						System.out.print("SWAP WITH WHICH CARD?\n: ");
						input = keys.next().trim().toUpperCase();
						dp.discard(players.get(i).swap(c, checkSwap(input)));
						if(players.get(i).getFlippedList().size()==8){
							finalTurn(i);
							final_turn = true;
							break;
						}
					}
				}
				else if(checkChoice1(input)==1){
					Card c = dp.draw();
					System.out.print("SWAP WITH WHICH CARD?\n: ");
					input = keys.next().trim().toUpperCase();
					dp.discard(players.get(i).swap(c, checkSwap(input)));
					if(players.get(i).getFlippedList().size()==8){
						finalTurn(i);
						final_turn = true;
						break;
					}
				}
			}
		}
		disp.refresh(0, dp, players);
		for (int i=0;i<players.size();i++)
			System.out.println(players.get(i).getName()+"'S SCORE: "+players.get(i).getScore());
		System.out.println(winner());
	}

	private static void finalTurn(int firstOut){
		int index = firstOut+1;
		String input;
		
		for (int i=0;i<players.size()-1;i++){
			if (index==players.size())
				index = 0;
			disp.refresh(index, dp, players);
			System.out.print("WOULD YOU LIKE TO DRAW FROM THE DECK (D) OR DRAW FROM THE DISCARD PILE (DP)?\n: ");
			input = keys.next().trim().toUpperCase();
			disp.refresh(index, dp, players);
			if(checkChoice1(input)==0){
				Card c = deck.draw();
				System.out.println("YOU HAVE DRAWN A: "+ c.getValue());
				System.out.print("WOULD YOU LIKE TO DISCARD (D) OR SWAP WITH ANOTHER CARD (S)?\n: ");
				input = keys.next().trim().toUpperCase();
				disp.refresh(i, dp, players);
				if(checkChoice2(input)==0){
					dp.discard(c);
				}
				else if(checkChoice2(input)==1){
					System.out.print("SWAP WITH dWHICH CARD?\n: ");
					players.get(index).swap(c, checkSwap(keys.next().trim().toUpperCase()));
				}
			}
			else if (checkChoice1(input)==1){
				Card c = dp.draw();
				System.out.print("SWAP WITH WHICH CARD?\n: ");
				players.get(index).swap(c, checkSwap(keys.next().trim().toUpperCase()));
			}
			players.get(index).flipAll();
			disp.refresh(i, dp, players);
			index++;
		}
	}

	private static int checkFlip(Hand player, String input){
		String cardIndex=input;
		do{
			while (order.indexOf(cardIndex)==-1){
				System.out.print("POSITION "+cardIndex+" DOESN'T EXIST\nCHOOSE ANOTHER CARD: ");
				cardIndex = keys.next().trim().toUpperCase();
			}
			while (player.getFlippedList().indexOf(order.indexOf(cardIndex))!=-1){
				System.out.print("THE CARD AT POSITION "+cardIndex+" HAS ALREADY BEEN FLIPPED\nCHOOSE ANOTHER CARD: ");
				cardIndex = keys.next().trim().toUpperCase();
			}
		}while(order.indexOf(cardIndex)==-1);
		return order.indexOf(cardIndex);
	}

	private static int checkSwap(String input){
		if (order.indexOf(input)!=-1)
			return order.indexOf(input);
		else{
			System.out.print("POSITION "+input+" DOES NOT EXIST\nCHOOSE ANOTHER CARD: ");
			return checkSwap(keys.next().trim().toUpperCase());
		}
	}

	private static int checkFinalFlip(String input){
		if (input.equals("Y")||input.equals("YES"))
			return 0;
		else if (input.equals("N")||input.equals("NO"))
			return 1;
		else{
			System.out.println("COMMAND NOT FOUND.");
			System.out.print("WOULD YOU LIKE TO FLIP YOUR LAST CARD (Y/N)?\n: ");
			return checkFinalFlip(keys.next().trim().toUpperCase());

		}
	}

	private static int checkChoice1(String input){
		if (input.equals("D")||input.equals("DRAW"))
			return 0;
		else if (input.equals("DP")||input.equals("DISCARD"))
			return 1;
		else{
			System.out.println("COMMAND NOT FOUND.");
			System.out.print("WOULD YOU LIKE TO DRAW FROM THE DECK (D) OR DRAW FROM THE DISCARD PILE (DP)?\n: ");
			return checkChoice1(keys.next().trim().toUpperCase());	
		}
	}

	private static int checkChoice2(String input){
			if (input.equals("D")||input.equals("DISCARD"))
				return 0;
			else if (input.equals("S")||input.equals("SWAP"))
				return 1;
			else{
				System.out.println("COMMAND NOT FOUND.");
				System.out.print("WOULD YOU LIKE TO DISCARD (D) OR SWAP WITH ANOTHER CARD (S)?\n: ");
				return checkChoice2(keys.next().trim().toUpperCase());
			}
	}

	private static String winner(){
		int winner = 0;
		for (int i=1; i<players.size();i++){
			if (players.get(i).getScore()<winner)
				winner = i;
			else if (players.get(i).getScore()==winner)
				return "TIE GAME";
		}
		return players.get(winner).getName()+ " WINS!"; 
	}
}
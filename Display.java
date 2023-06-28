import java.util.ArrayList;

public class Display{
	
	private static String[] order;
	public Display(){
		order = new String[]{"A","B","C","D","E","F","G","H"};
	}

	public static void showGame(ArrayList<Hand> players){
		int counter = 0;
		for(int i=0;i<players.size()-1;i+=2){
			System.out.printf("%-28s%s\n", players.get(i).getName()+"'S HAND: ", players.get(i+1).getName()+"'S HAND: ");
			for (int j=0;j<2;j++){
				System.out.println(" ____  ____  ____  ____      ____  ____  ____  ____ ");
				System.out.println("|    ||    ||    ||    |    |    ||    ||    ||    |");
				for (int h=0;h<2;h++){
					for (int k=0;k<4;k++){
						if(players.get(i+h).getCard(k+4*j).isFaceUp())
							System.out.printf("| %2s |", players.get(i+h).getCard(k+4*j).getValue());
						else 
							System.out.print("|    |");
					}
					System.out.print("    ");
				}
				System.out.println("\n|____||____||____||____|    |____||____||____||____|");	
			}
			System.out.println();
		}
		if (players.size()%2!=0){
			System.out.println(players.get(players.size()-1).getName()+"'S HAND: ");
			for (int i=0;i<5;i+=4){
				System.out.println(" ____  ____  ____  ____      ____  ____  ____  ____ ");
				System.out.println("|    ||    ||    ||    |    |    ||    ||    ||    |");
				for(int j=0;j<4;j++)
					if (players.get(players.size()-1).getCard(j+i).isFaceUp())
						System.out.printf("| %2s |",players.get(players.size()-1).getCard(j+i).getValue());
					else 
						System.out.print("|    |");
				System.out.print("    ");
				for (int k=0;k<4;k++)
					System.out.printf("| %2s |", order[k+i]);
				System.out.println("\n|____||____||____||____|    |____||____||____||____|");
			}
			System.out.println();
		}
		else
			displayLetters();
	}

	public static void refresh(int player, DiscardPile dp, ArrayList<Hand> players){
		System.out.print("\033[H\033[2J");
		showGame(players);
		System.out.println(players.get(player).getName()+"'S TURN");
		dp.display();
	}


	private static void displayLetters(){
		for (int i=0;i<5;i+=4){
			System.out.println(" ____  ____  ____  ____ ");
			System.out.println("|    ||    ||    ||    |");
			for(int j=0;j<4;j++)
				System.out.printf("| %2s |",order[j+i]);
			System.out.println("\n|____||____||____||____|");
		}
		System.out.println();
	}
}
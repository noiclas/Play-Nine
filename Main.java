public class Main{
	public static void main(String[]args){
		System.out.print("\033[H\033[2J");
		PlayNine game = new PlayNine();
		game.gameSetup();
		game.playGame();
	}
}
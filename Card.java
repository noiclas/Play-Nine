public class Card{

	private int value;
	private boolean faceUp;

	public Card(int v){
		this.value = v;
		this.faceUp = false;
	}

	public int getValue(){
		return value;
	}

	public boolean isFaceUp(){
		return faceUp;
	}

	public void flip(){
		if (faceUp==false)
			faceUp = true;
	}
}
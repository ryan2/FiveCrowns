package Game;
import java.util.*;

public class Player {
	
	private String name;
	private List<Cards> hand;
	private int playPosition;
	
	public Player(int play) {
		playPosition = play;
		name = "Player "+Integer.toString(play+1);
		hand = new ArrayList<Cards>();
	}
	
	public Player(int play, String n) {
		playPosition = play;
		name = n;
		hand = new ArrayList<Cards>();
	}
	
	public String getName() {
		return name;
	}
	public List<Cards> getHand(){
		return hand;
	}
	public int getPosition() {
		return playPosition;
	}
	
	public Cards draw(Cards card) {
		hand.add(card);
		return card;
	}
	
	public Cards discard(int pos) {
		return hand.remove(pos);
	}
	
	public List<Cards> returnHand() {
		List<Cards> temp = hand;
		hand.clear();
		return temp;
	}
	
}

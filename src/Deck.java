import java.util.*;

public class Deck {

	
	//Number of Cards and the Cards themselves
	private int num;
	private List<Cards>cards;
	private List<Cards>discard;
	
	//Constructor
	public Deck() {
		//Standard Deck
		num = 116;
		cards = generateGameDeck();
		discard = new ArrayList<Cards>();
	}
	//Special Constructor
	public Deck(int number, List<Cards> cardlist) {
		//Custom Deck
		num=number;
		cards = cardlist;
		discard = new ArrayList<Cards>();
	}
	//Get Size of Deck
	public int size() {
		return num;
	}
	//Get List of Cards
	public List<Cards> cards() {
		return cards;
	}
	//Generate Game ready deck for 5 crowns - two 58-card decks
	private List<Cards> generateGameDeck(){
		List<Cards> deck = new ArrayList<Cards>();
		deck = generateDeck();
		deck.addAll(generateDeck());
		return deck;
	}
	//Generates the standard 5 Crowns 58 Deck: 3 Jokers, 5 suits 3-10, J, Q, K
	private List<Cards> generateDeck(){
		List<Cards> deck = new ArrayList<Cards>();
		for (int i = 1;i<6;i++) {
			deck.addAll(generateSuit(i));
		}
		for (int i = 0;i<3;i++) {
			deck.add(new Cards(0, 14));
		}
		return deck;
	}
	//Assists generateDeck() by generating the cards of a suit 3-10, J, Q, K for 5 Crowns 58 card deck
	private List<Cards> generateSuit(int suit){
		List<Cards> deck = new ArrayList<Cards>();
		for (int i = 3;i<14;i++){
			deck.add(new Cards(suit, i));
		}
		return deck;
		
	}
	
	public Cards showDiscard() {
		return discard.get(0);
	}
	
	public void shuffle() {
		
	}
	
	public Cards deal() {
		return cards.remove(0);
	}
	
	public Cards drawDiscard() {
		return discard.remove(0);
	}
	
	public Cards discard(Cards c) {
		discard.add(0, c);
		return c;
	}
	
	public void reset() {
		cards.addAll(discard);
		discard.clear();
	}
	
	public void cut(int i) {
		if (i<cards.size()) {
			int j = 0;
			Cards card;
			while (j<i) {
				card = cards.remove(0);
				cards.add(card);
				j++;
			}
		}
	}
}

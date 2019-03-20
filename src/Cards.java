public class Cards {

	//Suits 0 = none, 1 = Club, 2 = Diamond, 3 = Heart, 4= Spade; 5 = Stars
	//Value 3 - 10; 11 = J; 12 = Q; 13 = K; 14 = Joker;
	private int suit;
	private int value;
	
	public Cards(int s, int v) {
	suit = s;
	value = v;
	}
	
	public int getSuit() {
		return suit;
	}
	public int getValue() {
			return value;
	}
	
	public String getDisplay() {
		String name="";
		if (joker()) {
			name = "Joker";
			return name;
		}
		name = Integer.toString(value);
		switch (suit) {
		case 1:
			name = name +"\u2663";
			break;
		case 2:
			name = name +"\u2666";
			break;
		case 3:
			name = name +"\u2665";
			break;
		case 4:
			name = name +"\u2660";
			break;
		case 5:
			name = name +"\u2605";
			break;
		}
		return name;
	}
	
	private boolean faceCard() {
		if (value>10&&value<14) {
			return true;
		}
		else return false;
	}
	private boolean joker() {
		if (value == 14) {
			return true;
		}
		else return false;
	}
	public String getName() {
		String name="";
		if (joker()) {
			name = "Joker";
			return name;
		}
		switch(suit) {
		case 1:
			name="Clubs";
			break;
		case 2:
			name="Diamonds";
			break;
		case 3:
			name="Hearts";
			break;
		case 4:
			name = "Spades";
			break;
		case 5: 
			name = "Stars";
			break;
		}
		name = "of "+name;
		if (faceCard()) {
			switch(value) {
			case 11:
				name = "Jack "+name;
				break;
			case 12:
				name = "Queen "+name;
				break;
			case 13:
				name = "King "+name;
				break;
			}
		}
		else {
			name = String.valueOf(value)+" "+name;
		}
		return name;
	}
}

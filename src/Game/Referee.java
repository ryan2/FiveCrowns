package Game;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Referee {

	private int round;
	
	public Referee(int r) {
		round = r;
	}

	/*public boolean out(List<Cards> cards) throws IOException {
		boolean out = false;
		int cardnum;
		List<Cards> unused = cards;
		while (!out) {
			int size = cards.size();
			int testsize;
			List<Cards> used = new ArrayList<Cards>();
			List<Cards> selected = new ArrayList<Cards>();
			List<Cards> unused2 = unused;
			boolean complete=false;
			System.out.println("Enter the cards for the book or run by card number one at a time. Enter 'x' when finished. 'Reset' to reset");
			while (!complete) {
				BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
				String response = reader.readLine();
				try {
					cardnum = Integer.parseInt(response);
					if (cardnum<1||cardnum>cards.size()) {
						System.out.println("Invalid Response. Please enter a number from 1 to "+size);
					}
					else {
						Cards card = unused.get(cardnum-1);
						if (used.contains(card)) {
							System.out.println("That card has already been used");
							break;
						}
						used.add(card);
						selected.add(card);
					}
				}catch (NumberFormatException e) {
					if (response.contentEquals("x")) {
						boolean legal = isLegal(selected);
						if (legal) {
							System.out.println("Accepted");
							if (used.size()==size) {
								out = true;
							}
							for (Cards card :selected) {
								unused.remove(card);
							}
							selected.clear();
							complete = true;
						}
						else {
							System.out.println("Not Accepted. Try Again");
							int temp = selected.size();
							for (int i = 0;i<temp;i++) {
								Cards card = selected.get(0);
								selected.remove(card);
								used.remove(card);
							}
							unused = unused2;
						}
					}
					
					else if (response.contentEquals("Reset")){
						return out;
					}
					else {
					System.out.println("Invalid Response. Please enter a number from 1 to "+size);
					}
					}
			}
		}
		return out;
	}*/
	
	public int score(List<Cards> cards) throws IOException  {
		int i;
		{
		i=1;
		for (Cards card : cards) {
			System.out.println("Card "+Integer.toString(i)+": "+card.getName());
			i++;
		}
		}
		boolean out = false;
		int cardnum;
		List<Cards> unused = cards;
		while (!out) {
			int size = cards.size();
			int testsize;
			List<Cards> used = new ArrayList<Cards>();
			List<Cards> selected = new ArrayList<Cards>();
			List<Cards> unused2 = unused;
			boolean complete=false;
			System.out.println("Enter the cards for the book or run by card number one at a time. Enter 'x' when finished. 'Reset' to reset. 'Done' to score remaining cards.");
			while (!complete) {
				BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
				String response = reader.readLine();
				try {
					cardnum = Integer.parseInt(response);
					if (cardnum<1||cardnum>cards.size()) {
						System.out.println("Invalid Response. Please enter a number from 1 to "+size);
					}
					else {
						Cards card = unused.get(cardnum-1);
						if (used.contains(card)) {
							System.out.println("That card has already been used");
							break;
						}
						used.add(card);
						selected.add(card);
					}
				}catch (NumberFormatException e) {
					if (response.contentEquals("x")) {
						boolean legal = isLegal(selected);
						if (legal) {
							System.out.println("Accepted");
							if (used.size()==size) {
								System.out.println(used.size()+" "+size);
								out = true;
							}
							for (Cards card :selected) {
								unused.remove(card);
							}
							selected.clear();
							complete = true;
						}
						else {
							System.out.println("Not Accepted. Try Again");
							int temp = selected.size();
							for (i = 0;i<temp;i++) {
								Cards card = selected.get(0);
								selected.remove(card);
								used.remove(card);
							}
							unused = unused2;
						}
					}
					
					else if (response.contentEquals("Reset")){
						unused = cards;
						used = new ArrayList<Cards>();
						selected = new ArrayList<Cards>();
						unused2 = unused;
						System.out.println("Reset. Try again");
						break;
					}
					else if (response.contentEquals("Done")) {
						i = 0;
						for (Cards card: unused) {
							i+=card.getValue();
						}
						System.out.println("You add "+i+" points.");
						return i;
					}
					else {
					System.out.println("Invalid Response. Please enter a number from 1 to "+size);
					}
					}
			}
		}

		return 0;
	}
	
	
	public boolean isLegal(List<Cards> cards) {
		if (isRun(cards)||isBook(cards)) {
			return true;
		}
		else return false;
	}
	
	private boolean isRun(List<Cards> cards) {
		boolean result = false;
		
		if (cards.size()<3) {
			return result;
		}
		
		List<Cards> cardList = new ArrayList<Cards>(cards);
		int wilds = 0;
		for (Cards card : cards) {
			if (isWild(card)) {
				cardList.remove(card);
				wilds++;
			}
		}
		
		if (!sameSuit(cardList)) {
			return result;
		}
		List<Cards> sortedCards = sortCards(cardList);
		int i = sortedCards.get(0).getValue();
		for (Cards card : sortedCards) {
			if (card.getValue()-i>1) {
				int diff = card.getValue()-i-1;
				wilds -= diff;
				if (wilds<0) {
					return result;
				}
				else {
					i = card.getValue();
				}
			}
			else {
				i = card.getValue();
			}
		}
		
		result = true;
		
		return result;
	}
	
	private boolean isBook(List<Cards> cards) {
		boolean result = false;
		if (cards.size()<3) {
			return result;
		}
		
		List<Cards> cardList = new ArrayList<Cards>(cards);
		for (Cards card : cards) {
			if (isWild(card)) {
				cardList.remove(card);
			}
		}
		
		if (!sameNumber(cardList)) {
			return result;
		}
		result = true;
		return result;
	}
	
	private boolean isWild(Cards c) {
		if (c.getValue()==14) {
			return true;
		}
		if (c.getValue()==round) {
			return true;
		}
		else {
			return false;
		}
	}
	
	private boolean sameSuit(List<Cards> cards) {
		boolean result = false;
		int suit = cards.get(0).getSuit();
		for (Cards card :cards) {
			if (suit!=card.getSuit()) {
				return result;
			}
		}
		result = true;
		return result;
	}
	
	private boolean sameNumber(List<Cards> cards) {
		boolean result = false;
		
		int value = cards.get(0).getValue();
		for (Cards card : cards){
			if (value!=card.getValue()) {
				return result;
			}
		}
		result = true;
		return result;
	}
	
	private List<Cards> sortCards(List<Cards> cards) {
		List<Cards> result = cards;
		result.sort(Comparator.comparing(Cards::getValue));
		return result;
				}
}

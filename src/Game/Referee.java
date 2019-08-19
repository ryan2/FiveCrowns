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
	
	public boolean isOut2(List<Cards> cards){
		if (cards.size()<6){
			return isLegal(cards);
		}
		boolean result = false;
		List<Cards> cardList = new ArrayList<Cards>(cards);	
		int num = cards.size();
		List<Cards> duplicates = new ArrayList<Cards>(cards);
		List<Cards> wildList = new ArrayList<Cards>();
		int wilds = 0;
		int maxsets = num/3; //7,2 ; 8,2 ;9,3 ;10,3 ;11,3;12,4;13,4
		Cards c= null;
		for (Cards card: cards){
			if (isWild(card)){
				wildList.add(card);
				wilds++;
				num--;
				cardList.remove(card);	
			}
			else {
				if (c!=null) {
					if (c.getValue()==card.getValue()&&c.getSuit()==card.getSuit()) {
						duplicates.add(c);
					}
					c = card;
				}
			}
		}	
		if (wilds==cards.size()) {
			return true;
		}
		List<Cards> sortedCards = sortCards(cardList);
		List<Cards> sortedSuits = sortSuits(cardList);
		int value = sortedCards.get(0).getValue();
		
		List<Cards> curr = new ArrayList<Cards>();

		//all books and semi-books (1-2 cards)
		for (int i = 0;i<sortedCards.size();i++) {
			Cards card = sortedCards.get(i);
			if (card.getValue()!=value) {
					if (curr.size()==1&&wilds>1) {
						Cards tmp = wildList.remove(0);
						Cards tmp1 = wildList.remove(0);
						
						curr.add(tmp);
						curr.add(tmp1);
						wilds-=2;
						cardList.addAll(wildList);
						if (isOut2(cardList)&&isLegal(curr)) {
							return true;
						}
						curr.remove(tmp);
						wildList.add(tmp);
						wilds++;
						curr.remove(tmp1);
						wildList.add(tmp1);
						wilds++;
						cardList.removeAll(wildList);
					}
					else if (curr.size()==2&&wilds>0) {
						Cards tmp = wildList.remove(0);
						curr.add(tmp);
						wilds--;
						cardList.addAll(wildList);
						if (isOut2(cardList)&&isLegal(curr)) {

							return true;
						}
						cardList.removeAll(wildList);
						curr.remove(tmp);
						wildList.add(tmp);
						wilds++;
					}
					else {
						cardList.addAll(wildList);
						if (isOut2(cardList)&&isLegal(curr)) {

							return true;
					}
						cardList.removeAll(wildList);
						}
					for (Cards c1:curr) {
						cardList.add(c1);
					}
					curr.clear();
					cardList.remove(card);
					curr.add(card);
					value = card.getValue();
					if (i == sortedCards.size()-1) {
						if (isLegal(curr)) {
							cardList.addAll(wildList);
							return isOut2(cardList);
						}
					}
				}
			else {

				if (curr.size()>2) {
					if (isOut2(cardList)&&isLegal(curr)) {
						return true;
					}
				}
				cardList.remove(card);
				curr.add(card);

				if (i == sortedCards.size()-1) {
					if (isLegal(curr)) {
						return isOut2(cardList);
					}
				}
			}
		}

		//transition state
		//System.out.println(wilds);
		//System.out.println(wildList.size());
		//System.out.println(cardList.size());
		sortedSuits = sortSuits(sortedSuits);
		for (Cards c1:sortedSuits) {
		//	System.out.println(c1.getName());
		}
		for (Cards c1:wildList) {
		//	System.out.println(c1.getName());
		}
		cardList.addAll(curr);
		curr.clear();
		
		
		int suit = sortedSuits.get(0).getSuit();
		value = sortedSuits.get(0).getValue();
		//all runs and semi-runs (1-2 cards)
		for (int i =0;i<sortedSuits.size();i++) {
			Cards card = sortedSuits.get(i);
			if (card.getSuit()!=suit) {
				if (curr.size()<3) {
					for (int j = curr.size();j<3;j++) {
						if (wilds==0) {
							return false;
						}
						curr.add(wildList.remove(0));
						wilds--;
					}
				}
				if (!isLegal(curr)) {
					return false;
				}
				
				suit = card.getSuit();
				curr.clear();
				curr.add(card);
				cardList.remove(card);
				value = card.getValue();
				if (i==sortedSuits.size()-1) {
					return isLegal(curr);
				}
			}
			else {
				int diff = card.getValue()-value;
				if (diff==0&&i!=0) {
					while (!isLegal(curr)) {
						if (wilds==0) {
							return false;
						}
						curr.add(wildList.remove(0));
						wilds--;
					}
					curr.clear();
				}
				else if (diff>1) {
					for (int j = diff;j>1;j--) {
						if (wilds==0) {
							if (!isLegal(curr)) {
								return false;
							}
							curr.clear();
							break;
						}
						wilds--;
						curr.add(wildList.remove(0));
					}
				}
				curr.add(card);
				cardList.remove(card);
				value = card.getValue();
			}
			if (i==sortedSuits.size()-1) {
				return isLegal(curr);
			}
		}
		
		return result;
		
	}
	public boolean isOut(List<Cards> cards){
		if (cards.size()<6){
			return isLegal(cards);
		}
		boolean result = false;
		List<Cards> cardList = new ArrayList<Cards>(cards);	
		int num = cards.size();
		List<Cards> duplicates = new ArrayList<Cards>(cards);
		List<Cards> wildList = new ArrayList<Cards>();
		int wilds = 0;
		int maxsets = num/3; //7,2 ; 8,2 ;9,3 ;10,3 ;11,3;12,4;13,4
		Cards c= null;
		for (Cards card: cards){
			if (isWild(card)){
				wildList.add(card);
				wilds++;
				num--;
				cardList.remove(card);	
			}
			else {
				if (c!=null) {
					if (c.getValue()==card.getValue()&&c.getSuit()==card.getSuit()) {
						duplicates.add(c);
					}
					c = card;
				}
			}
		}	
	
		List<Cards> sortedCards = sortCards(cardList);
		List<Cards> sortedSuits = sortSuits(cardList);
		int value = -1;
		List<Cards> curr = new ArrayList<Cards>();
		Dictionary books = new Hashtable();
		Dictionary runs = new Hashtable();
		
		//all books and semi-books (1-2 cards)
		for (int i = 0;i<sortedCards.size();i++) {
			Cards card = sortedCards.get(i);
			if (card.getValue()!=value) {
				if (value!=-1) {
					books.put(value, curr);
				}
				value = card.getValue();
				curr.clear();
				curr.add(card);
			}
			else {
				curr.add(card);
				if (i == sortedCards.size()-1) {
					books.put(value, curr);
				}
			}
		}
		
		curr.clear();
		int suit = -1;
		//all runs and semi-runs (1-2 cards)
		for (int i =0;i<sortedSuits.size();i++) {
			Cards card = sortedSuits.get(i);
			if (card.getSuit()!=suit) {
				if (suit!=-1) {
					runs.put(suit, curr);
				}
				suit = card.getSuit();
				curr.clear();
				curr.add(card);
			}
		}
		
		return result;
	}

	public boolean isLegal(List<Cards> cards) {
		if (isRun(cards)||isBook(cards)) {
			return true;
		}
		else return false;
	}

	private boolean isNeighbor(Cards c1, Cards c2){
		if (isWild(c1)||isWild(c2)){
			return true;
		}
		else{
			if (java.lang.Math.abs(c1.getValue()-c2.getValue())>1){
				return false;
			}
			else{
				return true;
			}
		}

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
		
		if (wilds==cards.size()) {
			return true;
		}
		
		if (!sameSuit(cardList)) {
			return result;
		}
		List<Cards> sortedCards = sortCards(cardList);
		int i = 1000;
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
			else if (card.getValue()==i) {

				return false;
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
		int wilds = 0;
		for (Cards card : cards) {
			if (isWild(card)) {
				cardList.remove(card);
				wilds++;
			}
		}
		
		if (wilds==cards.size()) {
			return true;
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
		List<Cards> result = new ArrayList<Cards>(cards);
		result.sort(Comparator.comparing(Cards::getValue));
		return result;
		}
	
	private List<Cards> sortSuits(List<Cards> cards) {
		List<Cards> result = new ArrayList<Cards>(cards);
		result = sortCards(result);
		result.sort(Comparator.comparing(Cards::getSuit));
		return result;
		}
	
}

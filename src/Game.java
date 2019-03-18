import java.util.*;
import java.io.*;

public class Game {

	public static int JACK = 11;
	public static int QUEEN = 12;
	public static int KING = 13;
	public static int JOKER = 50;
	public static int WILD = 20;
	
	
	private List<Player> players; //should be in order by position
	private int playerCount;
	private Deck deck;
	private int round; //3-13
	private List<Integer> score;
	private boolean out;
	private boolean out2;
	
	public Game() throws IOException {
		players = new ArrayList<Player>();
		deck = new Deck();
		round = 3;
		setGame();
}
	
	public void play() throws IOException{
		
		System.out.println("We got to play!");
		score = new ArrayList<Integer>(players.size());
		for (int j=0;j<players.size();j++) {
			score.add(0);
		}
		while (round<14) {
			doRound();
			round++;
			collectCards();
			deal(round);
		}
			}
		
	private void collectCards() {
		for (Player player :players) {
			deck.cards().addAll(player.returnHand());
		}
		deck.reset();
	}
	
	private void doRound() throws IOException{
		out = false;
		out2=false;
		while (!out) {
			for (Player player : players) {
				if (!out) {
					doTurn(player);
				}
				if (!out) {
					doDiscard(player);
					}
				else {
					break;
				}
				}
				}
}
	
	private void doDiscard(Player player) throws IOException{
		int discard;
		System.out.println("Enter the number of the card to discard.");
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		String response = reader.readLine();
		while (true) {
			try {
				discard = Integer.parseInt(response);
				if (discard<1||discard>player.getHand().size()) {
					System.out.println("Invalid Response. Please enter a number from 1 to "+player.getHand().size());
					reader = new BufferedReader(new InputStreamReader(System.in));
					response = reader.readLine();
				}
				else {
					break;
				}
			}catch (NumberFormatException e) {
				System.out.println("Invalid Response. Please enter a number from 1 to "+player.getHand().size());
				reader = new BufferedReader(new InputStreamReader(System.in));
				response = reader.readLine();
				}
		}
		deck.discard(player.discard(discard-1));
	}
	
	private void doTurn(Player player) throws IOException{
		System.out.println(player.getName()+"'s turn. Your Hand:");
		int i =1;
		for (Cards card : player.getHand()) {
			System.out.println("Card "+Integer.toString(i)+": "+card.getName());
			i++;
		}
		boolean play = false;
		while (!play) {
			System.out.println("Discard Pile: "+deck.showDiscard().getName());
			System.out.println("Answer 'Deck' or 'Discard' to draw from the Deck or Discard Pile. Answer 'Out' to go out");
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			String response = reader.readLine();
			switch (response) {
			case "Deck":
				Cards c = player.draw(deck.deal());
				System.out.println("Card "+player.getHand().size()+": "+c.getName());
				play = true;
				break;
			case "Discard":
				c = player.draw(deck.drawDiscard());
				System.out.println("Card "+player.getHand().size()+": "+c.getName());
				play = true;
				break;
			case "Out":
				Referee referee = new Referee(round);
				if (referee.out(player.getHand())) {
					play = true;
					if (!out) {
						out = true;
						System.out.println("You are out. No points added");
						System.out.println("Last hand for the remaining players");
						playOut(player);
					}
					else {
						System.out.println("You are out. No points added");
						out2 = true;
					}
					break;
				}
				else {
					System.out.println("Referee says no. Try Again");
				}
			}
		}
	}
	
	private void playOut(Player p) throws IOException {
		int i = players.indexOf(p)+1;
		Referee referee = new Referee(round);
		for (int j = 1;j<players.size();j++) {
			Player player = players.get(i);
			doTurn(player);
			if (!out2) {
				doDiscard(player);
				System.out.println("Time to score your hand!");
				int temp = score.get(i-1);
				int temp2 = referee.score(player.getHand());
				temp += temp2;
				score.set(i-1, temp);
				System.out.println("Total points: "+temp+" Points added: "+temp2);
				out2=false;
			}
			i++;
			if (i==players.size()) {
				i = 0;
			}
		}
	}
	
	private void checkState() {
		int i =1;
		for (Cards card : deck.cards()) {
			System.out.println(Integer.toString(i)+" "+card.getName());
			i++;}
		System.out.println("Discard: "+deck.showDiscard().getName());	
		for (Player player : players) {
			System.out.println(player.getName());
			for (Cards card: player.getHand()) {
				System.out.println(card.getName());
			}
		}
	}
	
	private void setGame() throws IOException {
		setPlayers();
		deal(round);
	}
	
	private void deal(int i) {
		for (int j=0;j<i;j++) {
			for (Player player : players) {
				player.draw(deck.deal());
			}
		}
		deck.discard(deck.deal());
	}
	
	private void setPlayers() throws IOException {
		boolean ready = false;
		System.out.println("How Many Players? 1 to 7");
		while (!ready) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		String response = reader.readLine();
		try {
			playerCount = Integer.parseInt(response);
			if (playerCount<1||playerCount>7) {
				System.out.println("Invalid Response. Please enter a number from 1 to 7");
			}
			else {
			ready = true;
			}
		}catch (NumberFormatException e) {
			System.out.println("Invalid Response. Please enter a number from 1 to 7");
			}
		}
		System.out.println("Enter a name for each player one by one. Blank names will be assigned default");
		for (int i=0; i < playerCount; i++) {
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			String response = reader.readLine();
			if (response.isEmpty()) {
				Player player = new Player(i+1);
				players.add(player);
			}
			else {
				Player player = new Player(i+1, response);
				players.add(player);
			}
		}
	}
	
	
}
	
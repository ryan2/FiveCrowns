package Game;
import java.util.*;

import Network.Server;

import java.io.*;

public class Game {

	public static int JACK = 11;
	public static int QUEEN = 12;
	public static int KING = 13;
	public static int JOKER = 50;
	public static int WILD = 20;
	
	
	private List<Player> players; //should be in order by position
	private volatile int playerCount;
	private Deck deck;
	public int round; //3-13
	private List<Integer> score;
	private boolean out;
	private boolean out2;
	private Server gameServer;
	
	private Player turn;
	
	public Game(Server server) throws IOException {
		players = new ArrayList<Player>();
		playerCount = 0;
		deck = new Deck();
		round = 3;
		gameServer = server;
		gameServer.setGame(this);
		while (!server.isReady());
		server.startGame();
		setGame();
}
	
	public void play() throws IOException{
		
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
					System.out.println("going once");
					turn = player;
					setTurn(player);
				}
				else {
					break;
				}
				}
				}
}
	
	public void doDiscard(String i) throws IOException{
		int discard = Integer.valueOf(i);
		Player player = turn;
		deck.discard(player.discard(discard));
		synchronized(player) {
			player.notify();
		}
	}
	
	public Cards doTurn(String response) throws IOException{
		Player player = turn;
		//BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		//System.out.println("Response is: "+response);
		switch (response) {
		case "Draw":
			Cards c = player.draw(deck.deal());
			System.out.println("Card "+player.getHand().size()+": "+c.getName());
			return c;
		case "Discard":
			c = player.draw(deck.drawDiscard());
			System.out.println("Card "+player.getHand().size()+": "+c.getName());
			return c;
			}
		return null;
		}
	
	private void doOut() throws IOException {
		Player player = turn;
		Referee referee = new Referee(round);
		if (referee.out(player.getHand())) {
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
		}
		else {
			System.out.println("Referee says no. Try Again");
		}
	}
	
	private void setTurn(Player player) throws IOException{
		gameServer.setTurn(player);
		System.out.println(player.getName()+"'s turn. Your Hand:");
		int i =1;
		for (Cards card : player.getHand()) {
			System.out.println("Card "+Integer.toString(i)+": "+card.getName());
			i++;
		}
		System.out.println("Discard Pile: "+deck.showDiscard().getName());
		System.out.println("Answer 'Deck' or 'Discard' to draw from the Deck or Discard Pile. Answer 'Out' to go out");
		try {
			synchronized(player) {
				player.wait();
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Done waiting");
		
	}
	
	private void playOut(Player p) throws IOException {
		int i = players.indexOf(p)+1;
		if (i==players.size()) {
			i = 0;
		}
		Referee referee = new Referee(round);
		for (int j = 1;j<players.size();j++) {
			Player player = players.get(i);
			setTurn(player);
			if (!out2) {
				doDiscard("change");
				System.out.println("Time to score your hand!");
				i = i-1;
				if (i<0) {
					i = players.size()-1;
				}
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
		System.out.println("******************************CHECKING STATE*************************************");
		System.out.println("******************************Deck*************************************");
		for (Cards card : deck.cards()) {
			System.out.println(Integer.toString(i)+" "+card.getName());
			i++;}
		System.out.println("Discard: "+deck.showDiscard().getName());	
		for (Player player : players) {
			System.out.println("******************************Player*************************************");
			System.out.println(player.getName());
			for (Cards card: player.getHand()) {
				System.out.println(card.getName());
			}
		}
	}
	
	private void setGame() throws IOException {
		System.out.println("Round: "+round);
		deal(round);
	}
	
	private void deal(int i) {
		for (int j=0;j<i;j++) {
			for (Player player : players) {
				player.draw(deck.deal());
			}
		}
		Cards discard = deck.discard(deck.deal());
		for (Player player : players) {
			System.out.println("Players: "+players.size());
			gameServer.setDeal(player.getHand(),discard,player.getPosition());
		}
	}
	
	public int addPlayer(String name){
		if (name.isEmpty()) {
			Player player = new Player(playerCount);
			players.add(player);
			playerCount++;
		}
		else {
			Player player = new Player(playerCount, name);
			players.add(player);
			playerCount++;
		}
		return playerCount;
	}
		
	
}
	
package Game;
import java.util.*;
import java.util.concurrent.TimeUnit;

import Network.Server;

import java.io.*;

public class Game {

	public static int JACK = 11;
	public static int QUEEN = 12;
	public static int KING = 13;
	public static int JOKER = 50;
	public static int WILD = 20;
	
	
	private List<Player> players; //should be in order by original position
	private List<Player> playOrder; //ordered by play order this round
	private volatile int playerCount;
	private Deck deck;
	public int round; //3-13
	private List<Integer> score;
	private boolean out;
	private Server gameServer;
	
	private Player turn;
	
	public Game(Server server) throws IOException {
		players = new ArrayList<Player>();
		playerCount = 0;
		deck = new Deck();
		round = 3;
		gameServer = server;
		gameServer.setGame(this);
		server.startGame(players);
		setGame();
}
	
	public void play() throws IOException{
		
		score = new ArrayList<Integer>(players.size());
		for (int j=0;j<players.size();j++) {
			score.add(0);
		}
		while (round<14) {
			doRound();
			gameServer.updateScoreboard(players, score);
			try {
				TimeUnit.SECONDS.sleep(5);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			round++;
			playOrder.add(playOrder.remove(0));
			collectCards();
			deck.shuffle();
			deal(round);
		}
		int winningScore = 5000;
		int winner = -1;
		for (int i =0;i<players.size();i++) {
			if (score.get(i)<winningScore) {
				winningScore = score.get(i);
				winner = i;
			}
		}
		gameServer.win(winner, players.get(winner).getName());
			}
		
	private void collectCards() {
		for (Player player :players) {
			deck.cards().addAll(player.returnHand());
		}
		deck.reset();
	}
	
	private void doRound() throws IOException{
		out = false;
		while (!out) {
			for (Player player : playOrder) {
				if (!out) {
					turn = player;
					setTurn(player);
				}
				else {
					break;
				}
				}
				}
		playOut(turn);
		
}
	
	public Cards doDiscard(String i) throws IOException{
		int discard = Integer.valueOf(i);
		Player player = turn;
		return deck.discard(player.discard(discard));
	}
	
	public void endTurn() {
		Player player = turn;
		synchronized(player) {
			player.notify();
		}
	}
	
	public Cards doTurn(String response) throws IOException{
		Player player = turn;
		switch (response) {
		case "Draw":
			Cards c = player.draw(deck.deal());
			return c;
		case "Discard":
			c = player.draw(deck.drawDiscard());
			return c;
			}
		return null;
		}
	
	public boolean doOut(List<Integer> cards) throws IOException {
		Player player = turn;
		Referee referee = new Referee(round);
		List<Cards> hand = player.getHand();
		List<Cards> subhand = new ArrayList<Cards>();
		for (Integer i : cards) {
			subhand.add(hand.get(i));
		}
		out = referee.isLegal(subhand);
		return out;
	}
	
	private void setTurn(Player player) throws IOException{
		gameServer.setTurn(player);
		try {
			synchronized(player) {
				player.wait();
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	public int updateScore(List<Integer> cards) {
		int temp = score.get(turn.getPosition());
		Player player = turn;
		List<Cards> hand = player.getHand();
		for (Integer i : cards) {
			temp+=hand.get(i).getValue();
		}
		score.set(turn.getPosition(), temp);
		return temp;
		
	}
	
	private void playOut(Player p) throws IOException {
		int i = playOrder.indexOf(p)+1;
		if (i==playOrder.size()) {
			i = 0;
		}
		for (int j = 1;j<playOrder.size();j++) {
			Player player = playOrder.get(i);
			gameServer.sendMsg("Final", players.indexOf(player));
			turn = player;
			setTurn(player);
			i++;
			if (i==playOrder.size()) {
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
		for (Player player : playOrder) {
			System.out.println("******************************PlayOrder*************************************");
			System.out.println(player.getName());
			for (Cards card: player.getHand()) {
				System.out.println(card.getName());
			}
		}
	}
	
	private void setGame() throws IOException {
		playOrder = new ArrayList<Player>(players);
		deck.shuffle();
		deal(round);
	}
	
	private void deal(int i) {
		for (int j=0;j<i;j++) {
			for (Player player : playOrder) {
				player.draw(deck.deal());
			}
		}
		Cards discard = deck.discard(deck.deal());
		for (Player player : playOrder) {
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
	
package Network;
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import Game.Cards;
import Game.Game;
import Game.Player;
import Login.Login;



public class Server implements Runnable {

	private ServerSocket server;
	private static int players = 0;
	private static Game game;
	private boolean start = false;
	private static List<ClientHandler> clients = new ArrayList<ClientHandler>();
	private Login login;
	

	public Server(Login l) {
		login = l;
	}

	public void run() {
		try {
			start(5000);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setGame(Game g) {
		game = g;
	}
	
	public int getPlayers() {
		return players;
	}
	
	public void win(int client, String name) {
		for (int i = 0;i<clients.size();i++) {
			try {
				ClientHandler c = clients.get(i);
				PrintWriter out = new PrintWriter(new OutputStreamWriter(c.clientSocket.getOutputStream(),StandardCharsets.UTF_8),true);
				if (i==client) {
					out.println("Win!");
				}
				else {
					out.println("Lose!"+name);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void startGame(List<Player> playerList) {
		for (ClientHandler c : clients) {
			try {
				PrintWriter out = new PrintWriter(new OutputStreamWriter(c.clientSocket.getOutputStream(),StandardCharsets.UTF_8),true);
				out.println("Start"+Integer.toString(players));
				for (Player p : playerList) {
					out.println(p.getName());
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void updateScoreboard(List<Player> playerList, List<Integer> score) {
		for (ClientHandler c : clients) {
			try {
				PrintWriter out = new PrintWriter(new OutputStreamWriter(c.clientSocket.getOutputStream(),StandardCharsets.UTF_8),true);
				out.println("Scoreboard"+Integer.toString(players));
				for (int i = 0;i<playerList.size();i++) {
					out.println(playerList.get(i).getName());
					out.println(score.get(i));
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void setTurn(Player player) {
		ClientHandler client = clients.get(player.getPosition());
		try {
			PrintWriter out = new PrintWriter(new OutputStreamWriter(client.clientSocket.getOutputStream(), StandardCharsets.UTF_8),true);
			out.println("Turn?");
			out.println(true);
			for (ClientHandler c : clients) {
				if (!c.equals(client)) {
					out = new PrintWriter(new OutputStreamWriter(c.clientSocket.getOutputStream(),StandardCharsets.UTF_8),true);
					out.println("Turn?");
					out.println(false);
					out.println(player.getName());
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setDeal(List<Cards> hand, Cards discard, int position) {
		ClientHandler client = clients.get(position);
		PrintWriter out;
		try {
			out = new PrintWriter(new OutputStreamWriter(client.clientSocket.getOutputStream(),StandardCharsets.UTF_8),true);
			out.println("Deal:");
			for (int i =0;i<hand.size();i++) {
				out.println(hand.get(i).getDisplay());
			}
			out.println("EndDeal");
			out.println("Discard:");
			out.println(discard.getDisplay());
			out.println("EndDiscard");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void sendMsg(String msg, int position) {
		ClientHandler client = clients.get(position);
		PrintWriter out;
		try {
			out = new PrintWriter(new OutputStreamWriter(client.clientSocket.getOutputStream(),StandardCharsets.UTF_8),true);
			out.println(msg);}
		catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	public static void setDiscard(Cards discard) {
		for (ClientHandler client: clients) {
			PrintWriter out;
			try {
				out = new PrintWriter(new OutputStreamWriter(client.clientSocket.getOutputStream(),StandardCharsets.UTF_8),true);
				out.println("Discard:");
				out.println(discard.getDisplay());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void hostStart() {
		start = true;
	}
	
	public void start(int port) throws IOException {
		server = new ServerSocket(port);
		server.setSoTimeout(3000);
		while (!start) {
			if (players<7) {
				System.out.println(players);
				try {
					new ClientHandler(server.accept()).start();
					System.out.println("Accepted");
					players++;
					login.updatePlayer(players);
				}
				catch (java.net.SocketTimeoutException e) {
				}
			}
		}
	}
	
	public void stop() throws IOException {
		server.close();
	}
	
	public String getInputStream(Player player) {
		BufferedReader in;
		String inputLine = null;
		try {
			in = new BufferedReader(new InputStreamReader(clients.get(player.getPosition()).clientSocket.getInputStream()));
			inputLine = in.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (inputLine!=null ) {
			return inputLine;
		}
		return "";
	}
	
	private static class ClientHandler extends Thread{
		private Socket clientSocket;
		private PrintWriter out;
		private BufferedReader in;
		
		public ClientHandler(Socket socket) {
			this.clientSocket=socket;
		}
		
		public void run() {
			try {
				out = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream(),StandardCharsets.UTF_8),true);
				in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				String inputLine;
				boolean discard = false;
				while ((inputLine = in.readLine())!=null) {
					if (discard) {
						if (!inputLine.isEmpty()) {
							setDiscard(game.doDiscard(inputLine));
							discard = false;
						}
					}
					else if (inputLine.contentEquals("Ready")){
					}
					else if (inputLine.contentEquals("Draw")||inputLine.contentEquals("Discard")) {
						Cards c = game.doTurn(inputLine);
						out.println("Draw:"+c.getDisplay());
						discard = true;
					}
					else if (inputLine.startsWith("Name:")) {
						String name = inputLine.substring(5);
						game.addPlayer(name);
						clients.add(this);
					}
					else if (inputLine.startsWith("Finish")) {
						game.endTurn();
					}
					else if (inputLine.startsWith("Score")) {
						int round  = game.round;

						while (round>0) {
							List<Integer> cards = new ArrayList<Integer>();
							while (!(inputLine = in.readLine()).equals("Submit")) {
								if (inputLine.equals("Done")) {
									break;
								}
								cards.add(Integer.parseInt(inputLine));
								round--;
							}
							if (inputLine.equals("Done")){
								while (round>0) {
									inputLine = in.readLine();
									cards.add(Integer.parseInt(inputLine));
									round--;
								}
								out.println("Score:"+game.updateScore(cards));
								game.endTurn();
								break;
							}
							else if (!game.doOut(cards)) {
								out.println("NOTOUT");
								break;
							}
							if (round==0) {
								game.endTurn();
								out.println("Out");
							}
						}
					}
					else if (inputLine.startsWith("Out")) {
						int round  = game.round;
						while (round>0) {
							List<Integer> cards = new ArrayList<Integer>();
							while (!(inputLine = in.readLine()).equals("Submit")) {
								cards.add(Integer.parseInt(inputLine));
								round--;
							}
							if (!game.doOut(cards)) {
								out.println("NOTOUT");
								round+=1;
								break;
							}
						}
						if (round==0) {
							game.endTurn();
							out.println("Out");
						}
					}
					else {
					}
				}
				in.close();
				out.close();
				clientSocket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
		
	}
	
}

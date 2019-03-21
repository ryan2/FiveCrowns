package Network;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.*;

import Game.Cards;
import Game.Game;
import Game.Player;



public class Server implements Runnable {

	private ServerSocket server;
	private static int players = 0;
	private static int i=0; //users we are waiting on
	private static volatile boolean ready;
	private static Game game;
	private static List<ClientHandler> clients = new ArrayList<ClientHandler>();
	private static ReentrantLock lock = new ReentrantLock();
	
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
		while (!ready||players==0) {
		}
		return players;
	}
	
	public void doTurn(Player player) {
		ClientHandler client = clients.get(player.getPosition());
		try {
			for (ClientHandler c : clients) {
				PrintWriter out = new PrintWriter(client.clientSocket.getOutputStream(),true);
				out.println("Turn?");
				out.println(client.equals(c) ? "true" : "false");
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
			out = new PrintWriter(client.clientSocket.getOutputStream(),true);
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
	
	public boolean isReady() {
		return ready;
	}
	
	public void start(int port) throws IOException {
		server = new ServerSocket(port);
		ready=false;
		server.setSoTimeout(5000);
		while (!ready||players==0) {
			if (players<7) {
				try {
					new ClientHandler(server.accept()).start();
					players++;
					i++;
				}
				catch (java.net.SocketTimeoutException e) {
				}
			}
			if (players>0&&i==0) {
				ready = true;
			}
		}
	}
	
	public void stop() throws IOException {
		server.close();
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
				out = new PrintWriter(clientSocket.getOutputStream(),true);
				in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				
				String inputLine;
				while ((inputLine = in.readLine())!=null) {
					if (inputLine.contentEquals("Ready")){
						i--;
						while (!ready) {
							}
						out.println(Integer.toString(players));
					}
					else if (inputLine.startsWith("Name:")) {
						lock.lock();
						String name = inputLine.substring(5);
						out.println(Integer.toString(game.addPlayer(name)));
						clients.add(this);
						lock.unlock();
					}
					else {
						System.out.println("Incorrect");
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

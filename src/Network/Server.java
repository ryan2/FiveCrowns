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
	private static volatile boolean start = false;
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
	
	public void startGame() {
		if (clients.size()==players) {
			start = true;
		}
		while (!start);
		for (ClientHandler c : clients) {
			try {
				PrintWriter out = new PrintWriter(c.clientSocket.getOutputStream(),true);
				System.out.println(c.clientSocket.toString());
				out.println("Start"+Integer.toString(players));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public void setTurn(Player player) {
		System.out.println("PLAYER POSITION: "+player.getPosition());
		ClientHandler client = clients.get(player.getPosition());
		try {
			for (ClientHandler c : clients) {
				System.out.println("SOCKET INFORMATION: "+clients.size()+c.getId());
				PrintWriter out = new PrintWriter(client.clientSocket.getOutputStream(),true);
				System.out.println("Turn? "+client.equals(c));
				out.println("Turn?");
				String bool = String.valueOf(client.equals(c));
				System.out.print("BOOL: "+bool);
				out.println(bool);
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
	
	public void setDiscard(Cards discard) {
		for (ClientHandler client: clients) {
			PrintWriter out;
			try {
				out = new PrintWriter(client.clientSocket.getOutputStream(),true);
				out.println("Discard:");
				out.println(discard.getDisplay());
				out.println("EndDiscard");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public boolean isReady() {
		return ready;
	}
	
	public void start(int port) throws IOException {
		server = new ServerSocket(port);
		ready=false;
		server.setSoTimeout(3000);
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
		System.out.println("InputStreamFailed");
		return "";
	}
	
	private static class ClientHandler extends Thread{
		private Socket clientSocket;
		private PrintWriter out;
		private BufferedReader in;
		
		public ClientHandler(Socket socket) {
			this.clientSocket=socket;
			System.out.println(socket.toString());
		}
		
		public void run() {
			try {
				out = new PrintWriter(clientSocket.getOutputStream(),true);
				in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				String inputLine;
				Boolean discard = false;
				while ((inputLine = in.readLine())!=null) {
					System.out.println(inputLine);
					if (discard) {
						if (!inputLine.isEmpty()) {
							game.doDiscard(inputLine);
							discard = false;
						}
					}
					if (inputLine.contentEquals("Ready")){
						i--;
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
						if (clients.size()==players) {
							start= true;
						}
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

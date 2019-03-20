package Network;
import java.io.*;
import java.net.*;
import java.util.List;

import Game.Game;



public class Server implements Runnable {

	private ServerSocket server;
	private static int players = 0;
	private static int i=0;
	private static volatile boolean ready;
	private static Game game;
	
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
		System.out.println("Getting players");
		while (!ready||players==0) {
		}
		return players;
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
					e.printStackTrace();
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
		private int player;
		
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
						String name = inputLine.substring(5);
						out.println(Integer.toString(game.addPlayer(name)));
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

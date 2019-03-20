package Network;
import java.io.*;
import java.net.*;
import java.util.List;



public class Server implements Runnable {

	private ServerSocket server;
	private static int players = 0;
	private static int i=0;
	private static boolean ready;
	
	public void run() {
		try {
			start(5000);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public int getPlayers() {
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
				System.out.println("Setting to True");
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
						int k = 0;
						while (!ready) {
							System.out.println("");
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

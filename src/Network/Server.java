package Network;
import java.io.*;
import java.net.*;



public class Server implements Runnable {

	private ServerSocket server;
	
	public void run() {
		try {
			start(5000);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void start(int port) throws IOException {
		server = new ServerSocket(port);
		while (true) {
			System.out.println("Friend!");
			new ClientHandler(server.accept()).start();
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
					out.println("Copy");
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

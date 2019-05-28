package Network;
import Graphics.Window;
import java.io.*;

import java.net.*;

public class Client implements Runnable{

	private Socket clientSocket;
	private PrintWriter out;
	private static Window window;
	
	public void run() {
		try {
			startConnection("127.0.0.1",5000);
			setWindow(new Window(this));
			new ServerHandler(clientSocket).start();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void startConnection(String ip, int port) throws UnknownHostException, IOException {
		clientSocket = new Socket(ip,port);
		out = new PrintWriter(clientSocket.getOutputStream(),true);
	}
	
	
	public String sendMessage(String msg) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		out.println(msg);
		String resp=in.readLine();
		return resp;
	}
	
	public void sendMessageVoid(String msg) {
		out.println(msg);
	}
	
	public void setWindow(Window w) {
		window = w;
	}
	
	public void stopConnection() throws IOException {
		out.close();
		clientSocket.close();
	}
	
	private static class ServerHandler extends Thread{
		private Socket mainSocket;
		
		public ServerHandler(Socket socket) {
			this.mainSocket = socket;
		}
		
		public void run() {
			System.out.println("Starting Client");
			boolean deal = false;
			try {
				String inputLine;
				System.out.println("CLient: "+mainSocket.toString());
				BufferedReader in = new BufferedReader(new InputStreamReader(mainSocket.getInputStream()));
				System.out.println("Starting Inside Client");
				while ((inputLine = in.readLine())!=null) {
					System.out.println("THIS IS THE INPUTLINE: "+inputLine);
					if (inputLine.startsWith("Deal:")) {
						deal = true;
					}
					else if (inputLine.endsWith("EndDeal")) {
						deal = false;
					}
					else if (inputLine.startsWith("Start")) {
						window.setPlayer(Integer.parseInt(inputLine.substring(5)));
					}
					else if (inputLine.startsWith("Discard:")) {
						String temp = in.readLine();
						window.showDiscard(temp);
					}
					else if (inputLine.startsWith("Draw:")) {
						window.draw(inputLine.substring(5));
					}
					else if (inputLine.startsWith("Discard")){
						
					}
					else if (inputLine.startsWith("Out")) {
						
					}
					else if (inputLine.startsWith("Turn?")) {
						System.out.println("INTURN");
						String temp = in.readLine();
						boolean turn = temp.contentEquals("true") ? true : false;
						if (turn) {
							window.setHeaderTurn();
							window.doTurn();
						}
						else {
							window.setHeaderNotTurn();
						}
					}
					else if (deal == true) {
						window.deal(inputLine);
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				}
			}
		}
}
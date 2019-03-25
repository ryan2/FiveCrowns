package Network;
import Graphics.Window;
import java.io.*;

import java.net.*;

public class Client implements Runnable{

	private Socket clientSocket;
	private PrintWriter out;
	private static BufferedReader in;
	private static Window window;
	
	public void run() {
		try {
			startConnection("127.0.0.1",5000);
			setWindow(new Window(this));
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
		in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
	}
	
	public static void getDeal() {
		new ServerHandler().start();		
	}
	
	
	public String sendMessage(String msg) throws IOException {
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
		in.close();
		out.close();
		clientSocket.close();
	}
	
	private static class ServerHandler extends Thread{
		
		public ServerHandler() {
		}
		
		public void run() {
			boolean deal = false;
			try {
				String inputLine;
				while ((inputLine = in.readLine())!=null) {
					System.out.println("THIS IS THE INPUTLINE: "+inputLine);
					if (inputLine.startsWith("Deal:")) {
						deal = true;
					}
					else if (inputLine.endsWith("EndDeal")) {
						deal = false;
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
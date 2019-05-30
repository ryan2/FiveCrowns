package Network;
import Graphics.Window;
import java.io.*;

import java.net.*;

public class Client implements Runnable{

	private Socket clientSocket;
	private PrintWriter out;
	private Window window;
	
	public void run() {
		try {
			startConnection("127.0.0.1",5000);
			setWindow(new Window(this));
			new ServerHandler(clientSocket, window).start();
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
		private Window window;
		
		public ServerHandler(Socket socket, Window w) {
			this.mainSocket = socket;
			window = w;
		}
		
		public void run() {
			boolean deal = false;
			try {
				String inputLine;
				BufferedReader in = new BufferedReader(new InputStreamReader(mainSocket.getInputStream()));
				while ((inputLine = in.readLine())!=null) {
					System.out.println("THIS IS THE INPUTLINE: "+inputLine+mainSocket.toString());
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
					else if (inputLine.startsWith("NOTOUT")) {
						window.setFinish();
					}
					else if (inputLine.startsWith("Turn?")) {
						String temp = in.readLine();
						boolean turn = temp.contentEquals("true") ? true : false;
						if (turn) {
							System.out.println("Turn is True "+ window.name.getText());
							window.setHeaderTurn();
							window.doTurn();
						}
						else {
							System.out.println("Turn is False "+ window.name.getText());
							temp = in.readLine();
							window.setHeaderNotTurn(temp);
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
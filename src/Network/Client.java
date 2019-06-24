package Network;
import Graphics.Window;
import java.io.*;

import java.net.*;
import java.nio.charset.StandardCharsets;

public class Client implements Runnable{

	private Socket clientSocket;
	private PrintWriter out;
	private Window window;
	
	public void run() {
		try {
			startConnection("69.138.11.135",5000);
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
		BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(),StandardCharsets.UTF_8));
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
				BufferedReader in = new BufferedReader(new InputStreamReader(mainSocket.getInputStream(),StandardCharsets.UTF_8));
				while ((inputLine = in.readLine())!=null) {
					if (inputLine.startsWith("Deal:")) {
						window.clearCards();
						deal = true;
					}
					else if (inputLine.endsWith("EndDeal")) {
						deal = false;
					}
					else if (inputLine.startsWith("Start")) {
						int count = Integer.parseInt(inputLine.substring(5));
						String[][] names = new String[count][2];
						window.setPlayer(count);
						for (int i = 0;i<count;i++) {
							inputLine = in.readLine();
							String[] name = {inputLine, "0"};
							names[i] = name;
						}
						window.setScoreboard(names);
					}
					else if (inputLine.startsWith("Scoreboard")) {
						int count = Integer.parseInt(inputLine.substring(10));
						String[][] names = new String[count][2];
						window.setPlayer(count);
						for (int i = 0;i<count;i++) {
							inputLine = in.readLine();
							String s = in.readLine();
							String[] name = {inputLine, s};
							names[i] = name;
						}
						window.setScoreboard(names);
					}
					else if (inputLine.startsWith("Discard:")) {
						String temp = in.readLine();
						window.showDiscard(temp);
					}
					else if (inputLine.startsWith("Draw:")) {
						window.draw(inputLine.substring(5));
					}
					else if (inputLine.startsWith("Final")){
						window.finalTurn();
					}
					else if (inputLine.startsWith("Win!")) {
						window.Winner(true, "");
					}
					else if (inputLine.startsWith("Lose!")) {
						window.Winner(false, inputLine.substring(5));
					}
					else if (inputLine.startsWith("Out")) {
						window.setOut();
					}
					else if (inputLine.startsWith("NOTOUT")) {
						window.Reset();
					}
					else if (inputLine.startsWith("Score:")) {
						window.setScore(inputLine.substring(6));
					}
					else if (inputLine.startsWith("Turn?")) {
						String temp = in.readLine();
						boolean turn = temp.contentEquals("true") ? true : false;
						if (turn) {
							window.setHeaderTurn();
							window.doTurn();
						}
						else {
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
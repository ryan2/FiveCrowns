package Network;
import java.io.*;
import java.net.*;

public class Client implements Runnable{

	private Socket clientSocket;
	private PrintWriter out;
	private BufferedReader in;
	
	public void run() {
		try {
			startConnection("127.0.0.1",5000);
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
	
	public String sendMessage(String msg) throws IOException {
		out.println(msg);
		String resp=in.readLine();
		return resp;
	}
	
	public void stopConnection() throws IOException {
		in.close();
		out.close();
		clientSocket.close();
	}
}

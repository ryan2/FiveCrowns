import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import Game.Game;
import Network.*;
import Graphics.Window;

public class PiChallenge {
/*     
	public static void main(String[] args){
		// TODO Auto-generated method stub PiDay2019 Challenge
		System.out.println("Working R 2019");
		boolean play = true;
		while (play) {
			try {
				Game game = new Game();
				game.play();
				System.out.println("Would you like to play again? y or n");
				BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
				String response = reader.readLine();
				if (response.contentEquals("y")||response.contentEquals("yes")||response.contentEquals("Y")||response.contentEquals("Yes")) {
					play = true;
				}
				else {
					System.out.println("Thanks for playing!");
					play = false;
				}
			}
			catch (IOException e) {
				System.out.println(e);
				play = false;
			}
		}
	}
*/
	//test runs
	public static void main(String[] args) {
		Server server = new Server();
		(new Thread(server)).start();
		Client client = new Client();
		(new Thread(client)).start();
		Client client2 = new Client();
		(new Thread(client2)).start();
		try {
			Game game = new Game(server);
			game.play();	
			}			
		catch (IOException e) {
			System.out.println(e);
		}
	}
	
	/*
	//client runs
	public static void main (String[] args) {
		Client client = new Client();
		(new Thread(client)).start();
	}
	*/
	/*
	public static void main(String[] args) {
		Server server = new Server();
		try {
			(new Thread(server)).start();
			Client client = new Client();
			(new Thread(client)).start();
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			String response = reader.readLine();
			String response2 = client.sendMessage(response);
			System.out.println(response2);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	*/
/*	public static void main(String[] args) {
		Cards card = new Cards(0,3);
		Cards card1 = new Cards(1,3);
		Cards card2 = new Cards(2,3);
		Cards card3 = new Cards(3,3);
		Cards card4 = new Cards(4,3);
		Cards card5 = new Cards(5,3);
		
		System.out.println(card.getDisplay());
		System.out.println(card1.getDisplay());
		System.out.println(card2.getDisplay());
		System.out.println(card3.getDisplay());
		System.out.println(card4.getDisplay());
		System.out.println(card5.getDisplay());
		
		Window window = new Window();
		
	}*/
}
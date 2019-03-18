import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class PiChallenge {

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
}

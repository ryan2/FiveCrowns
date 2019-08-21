package Test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import Game.Cards;
import Game.Deck;
import Game.Referee;

public class Test {
	public Test() {
		start();
	}
	
	private void start(){
	for (int k = 0; k<1;k++) {
		int x = 6;
		Referee ref = new Referee(x);
		Deck deck = new Deck();
		deck.shuffle();
		List<Cards> card = new ArrayList<Cards>();
		card.add(new Cards(1,3));
		card.add(new Cards(2,4));
		card.add(new Cards(3,4));
		card.add(new Cards(4,5));
		card.add(new Cards(5,5));
		card.add(new Cards(4,5));
		//card.addAll((deck.cards().subList(0, x)));
		int y = 0;
		for (Cards c: card) {
			y++;
			System.out.println("Card "+y+": "+c.getName());
		}
		System.out.println("Out: " + ref.isOut2(card) +" (false)");
		
	}
	
	for (int k = 0; k<1;k++) {
		int x = 6;
		Referee ref = new Referee(x);
		Deck deck = new Deck();
		deck.shuffle();
		List<Cards> card = new ArrayList<Cards>();
		card.add(new Cards(1,4));
		card.add(new Cards(2,4));
		card.add(new Cards(3,4));
		card.add(new Cards(4,5));
		card.add(new Cards(5,5));
		card.add(new Cards(4,5));
		//card.addAll((deck.cards().subList(0, x)));
		int y = 0;
		for (Cards c: card) {
			y++;
			System.out.println("Card "+y+": "+c.getName());
		}
		System.out.println("Out: " + ref.isOut2(card)+" (true)");
		
	}
	
	for (int k = 0; k<1;k++) {
		int x = 6;
		Referee ref = new Referee(x);
		Deck deck = new Deck();
		deck.shuffle();
		List<Cards> card = new ArrayList<Cards>();
		card.add(new Cards(3,4));
		card.add(new Cards(3,4));
		card.add(new Cards(3,4));
		card.add(new Cards(4,5));
		card.add(new Cards(4,5));
		card.add(new Cards(4,5));
		//card.addAll((deck.cards().subList(0, x)));
		int y = 0;
		for (Cards c: card) {
			y++;
			System.out.println("Card "+y+": "+c.getName());
		}
		System.out.println("Out: " + ref.isOut2(card)+" (true)");
		
	}
	
	for (int k = 0; k<1;k++) {
		int x = 6;
		Referee ref = new Referee(x);
		Deck deck = new Deck();
		deck.shuffle();
		List<Cards> card = new ArrayList<Cards>();
		card.add(new Cards(3,4));
		card.add(new Cards(3,4));
		card.add(new Cards(3,4));
		card.add(new Cards(3,4));
		card.add(new Cards(3,4));
		card.add(new Cards(3,4));
		//card.addAll((deck.cards().subList(0, x)));
		int y = 0;
		for (Cards c: card) {
			y++;
			System.out.println("Card "+y+": "+c.getName());
		}
		System.out.println("Out: " + ref.isOut2(card)+" (true)");
		
	}
	
	for (int k = 0; k<1;k++) {
		int x = 6;
		Referee ref = new Referee(x);
		Deck deck = new Deck();
		deck.shuffle();
		List<Cards> card = new ArrayList<Cards>();
		card.add(new Cards(3,3));
		card.add(new Cards(3,3));
		card.add(new Cards(3,4));
		card.add(new Cards(3,4));
		card.add(new Cards(3,7));
		card.add(new Cards(3,7));
		//card.addAll((deck.cards().subList(0, x)));
		int y = 0;
		for (Cards c: card) {
			y++;
			System.out.println("Card "+y+": "+c.getName());
		}
		System.out.println("Out: " + ref.isOut2(card)+" (false)");
		
	}
	
	for (int k = 0; k<1;k++) {
		int x = 7;
		Referee ref = new Referee(x);
		Deck deck = new Deck();
		deck.shuffle();
		List<Cards> card = new ArrayList<Cards>();
		card.add(new Cards(3,4));
		card.add(new Cards(3,4));
		card.add(new Cards(3,4));
		card.add(new Cards(3,4));
		card.add(new Cards(3,5));
		card.add(new Cards(3,5));
		card.add(new Cards(3,5));
		//card.addAll((deck.cards().subList(0, x)));
		int y = 0;
		for (Cards c: card) {
			y++;
			System.out.println("Card "+y+": "+c.getName());
		}
		System.out.println("Out: " + ref.isOut2(card)+" (true)");
		
	}
	
	for (int k = 0; k<1;k++) {
		int x = 7;
		Referee ref = new Referee(x);
		Deck deck = new Deck();
		deck.shuffle();
		List<Cards> card = new ArrayList<Cards>();
		card.add(new Cards(3,4));
		card.add(new Cards(3,5));
		card.add(new Cards(3,6));
		card.add(new Cards(3,8));
		card.add(new Cards(3,6));
		card.add(new Cards(3,7));
		card.add(new Cards(3,9));
		//card.addAll((deck.cards().subList(0, x)));
		int y = 0;
		for (Cards c: card) {
			y++;
			System.out.println("Card "+y+": "+c.getName());
		}
		System.out.println("Out: " + ref.isOut2(card)+" (true)");
		
	}
	
	for (int k = 0; k<1;k++) {
		int x = 7;
		Referee ref = new Referee(x);
		Deck deck = new Deck();
		deck.shuffle();
		List<Cards> card = new ArrayList<Cards>();
		card.add(new Cards(3,5));
		card.add(new Cards(3,5));
		card.add(new Cards(3,5));
		card.add(new Cards(3,8));
		card.add(new Cards(3,6));
		card.add(new Cards(3,7));
		card.add(new Cards(3,9));
		//card.addAll((deck.cards().subList(0, x)));
		int y = 0;
		for (Cards c: card) {
			y++;
			System.out.println("Card "+y+": "+c.getName());
		}
		System.out.println("Out: " + ref.isOut2(card)+" (true)");
		
	}
	
	for (int k = 0; k<1;k++) {
		int x = 3;
		Referee ref = new Referee(x);
		Deck deck = new Deck();
		deck.shuffle();
		List<Cards> card = new ArrayList<Cards>();
		card.add(new Cards(3,3));
		card.add(new Cards(3,3));
		card.add(new Cards(3,3));

		//card.addAll((deck.cards().subList(0, x)));
		int y = 0;
		for (Cards c: card) {
			y++;
			System.out.println("Card "+y+": "+c.getName());
		}
		System.out.println("Out: " + ref.isOut2(card)+" (true)");
		
	}
	
	for (int k = 1; k<6;k++) {
		for (int j = 3;j<14;j++) {
			int x = 3;
			Referee ref = new Referee(x);
			//Deck deck = new Deck();
			//deck.shuffle();
			List<Cards> card = new ArrayList<Cards>();
			card.add(new Cards(k,j));
			card.add(new Cards(k,j));
			card.add(new Cards(k,j));
			//card.addAll((deck.cards().subList(0, x)));
			int y = 0;
			for (Cards c: card) {
				y++;
				System.out.println("Card "+y+": "+c.getName());
			}
			System.out.println("Out: " + ref.isOut2(card)+" (true)");
		}
	}
	ArrayList<Integer> list = new ArrayList<Integer>();
	int t = 0;
	int f = 0;
	long startTime = System.nanoTime();
			for (int k = 0; k<1;k++) {
				int x = 13;
				Referee ref = new Referee(x);
				Deck deck = new Deck();
				deck.shuffle();
				List<Cards> card = new ArrayList<Cards>();
				card.addAll((deck.cards().subList(0, x)));
				int y = 0;
				for (Cards c: card) {
					y++;
					System.out.println("Card "+y+": "+c.getName());
				}
		
				boolean result = ref.isOut2(card);
				if (result) {
					t++;
				}
				else {
					f++;
				}
				System.out.println(k+" - Out: " + result);
				assert result;
				}
			long endTime = System.nanoTime();
			System.out.println("True: "+t);
			System.out.println("False: "+f);
			System.out.println((float)((float)t/(float)(t+f))*(float)100+"%");
			float time = (float)((endTime-startTime)/(float)1000000000);
			System.out.println(time+"s");
			System.out.println("Checks per second: "+(float)10000/time);
		//System.out.println("True: "+t);
		//System.out.println("False: "+f);
		//System.out.println((float)((float)t/(float)(t+f))*(float)100+"%");
		
	for (int k = 1; k<2;k++) {
			int x = 13;
			Referee ref = new Referee(x);
			//Deck deck = new Deck();
			//deck.shuffle();
			List<Cards> card = new ArrayList<Cards>();
			card.add(new Cards(k,3));
			card.add(new Cards(k,3));
			card.add(new Cards(k,3));
			card.add(new Cards(k,4));
			card.add(new Cards(k,4));
			card.add(new Cards(k,4));
			card.add(new Cards(k,5));
			card.add(new Cards(k,5));
			card.add(new Cards(k,5));
			card.add(new Cards(k,6));
			card.add(new Cards(k,6));
			card.add(new Cards(k,6));
			card.add(new Cards(k,6));
//card.addAll((deck.cards().subList(0, x)));
			int y = 0;
			for (Cards c: card) {
				y++;
				System.out.println("Card "+y+": "+c.getName());
			}
			System.out.println("Out: " + ref.isOut2(card)+" (true)");
		}
	}
}

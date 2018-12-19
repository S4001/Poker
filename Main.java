package poker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Main{
	
	public ArrayList<Card> cards;
	public ArrayList<Hand> hands;
	public ArrayList<Card> board;
	
	public int numPlayers;
	
	public String[] suits = {"s", "d", "h", "c"};
	
	public void play() {
		cards = new ArrayList<>();
		hands = new ArrayList<>();
		board = new ArrayList<>();
		
		numPlayers = 6;      //how many are playing
		
		for(int rank = 1; rank <= 13; rank++) {
			for(int suit = 0; suit <= 3; suit++) {
				cards.add(new Card(rank, suits[suit]));     //creates the deck
			}
		}
		
		Collections.shuffle(cards);  //shuffles
		deal(numPlayers);            //deals to x amount of players
		
		for(Hand h: hands) {
			h.check(board);          //checks what each hand has
			System.out.println("player " + h.getPlayer() + ": " + h.getCard1().getRank() + h.getCard1().getSuit() + "  " + 
					   h.getCard2().getRank() + h.getCard2().getSuit());
		}
		
		Collections.sort(hands, new SortByValue()); //sorts the hands based on value
		
		System.out.println("");
		System.out.println(board.get(0).getRank() + board.get(0).getSuit() + ", " + 
						   board.get(1).getRank() + board.get(1).getSuit() + ", " + 
						   board.get(2).getRank() + board.get(2).getSuit() + ", " + 
						   board.get(3).getRank() + board.get(3).getSuit() + ", " + 
						   board.get(4).getRank() + board.get(4).getSuit());
		
		System.out.println("");
		System.out.println("player " + hands.get(numPlayers - 1).getPlayer() + " wins");
	}
	
	public void deal(int numPlayers) {
		for(int player = 0; player < numPlayers; player++) {
			hands.add(new Hand(player + 1, cards.get(player), cards.get(player + numPlayers)));
		}
		
		for(int i = 0; i < 7; i++) {
			if(i == 0) i++;
			if(i == 4) i++;
			if(i == 6) i++;
			board.add(cards.get(numPlayers * 2 + i));
		}
	}
	
	class SortByValue implements Comparator<Hand> {
		public int compare(Hand a, Hand b) {
			return a.getValue() - b.getValue();
		}
	}
	
	public static void main(String[] args) {
		Main main = new Main();
		main.play();
	}
}
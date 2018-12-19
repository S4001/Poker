package poker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Hand {
	
	private Card card1, card2;
	private int player, value;
	
	private ArrayList<Card> hand;
	
	public Hand(int player, Card card1, Card card2) {
		this.card1 = card1;
		this.card2 = card2;
		this.player = player;
		
		hand = new ArrayList<>();
		hand.add(card1);
		hand.add(card2);
	}
	
	class SortByRank implements Comparator<Card> {
		public int compare(Card a, Card b) {
			return a.getRank() - b.getRank();
		}
	}
	
	int pair = 0;
	
	public void check(ArrayList<Card> board) {
		for(Card c: board) hand.add(c);
		
		Collections.sort(hand, new SortByRank());
		
		if(pair()) value = 1;
		if(straight()) value = 5;
		
		//for(Card c: hand) System.out.println("...." + c.getRank());
		/*
		if(pair == -1 || pair > 1) {
			hand.remove(1);
			hand.remove(0);
		} else if (pair == 1) {
			hand.remove(0);
			hand.remove(2);
		} else if (pair == 0) {
			hand.remove(3);
			hand.remove(2);
		}
		*/
		//for(Card c: hand) System.out.println("    " + c.getRank());
		
		//System.out.println(value);
	}
	
	private boolean straight() {
		for(int i = 0; i < 3; i++) {
			for(int j = 1; j < 4; j++) {
				for(int k = 2; k < 5; k++) {
					for(int l = 3; l < 6; l++) {
						for(int m = 4; m < 7; m++) {
							if(i < j && j < k && k < l && l < m && 
									hand.get(i).getRank() - hand.get(j).getRank() == -1 && 
									hand.get(j).getRank() - hand.get(k).getRank() == -1 && 
									hand.get(k).getRank() - hand.get(l).getRank() == -1 && 
									(hand.get(l).getRank() - hand.get(m).getRank() == -1 ||
									hand.get(l).getRank() - hand.get(m).getRank() == -12)) {
								return true;
							}
						}
					}
				}
			}
		}
		
		return false;
	}
	
	private boolean twoPair() {
		return false;
	}
	
	private boolean pair() {
		for(int i = 0; i < hand.size(); i++) {
			for(int j = 0; j < hand.size(); j++) {
				if(i < j && hand.get(i).getRank() == hand.get(j).getRank()) {
					pair = i;
					
					return true;
				}
			}
		}
		
		pair = -1;
		
		return false;
	}
	
	public Card getCard1() {
		return card1;
	}
	
	public Card getCard2() {
		return card2;
	}
	
	public int getPlayer() {
		return player;
	}
	
	public int getValue() {
		return value;
	}
}
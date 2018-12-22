package poker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

public class Main {
	
	private ArrayList<Card> deck, board;
	private ArrayList<Hand> hands, tied, winner;
	
	private final int[] RANKS = {2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14};
	private final int [] SUITS = {0, 1, 2, 3};
	
	private int sims = 100000;
	private int wins = 0;
	
	private int high = 0;
	private int pair = 0;
	private int twop = 0;
	private int trip = 0;
	private int stra = 0;
	private int flus = 0;
	private int full = 0;
	private int quad = 0;
	private int stfl = 0;
	private int rofl = 0;
	
	private int numPlayers = 5;
	
	int pairWins = 0;
	int overWins = 0;
	int ties = 0;
	
	public void start() {
		for(int i = 0; i < sims; i++) {
			deck = new ArrayList<>();
			board = new ArrayList<>();
			hands = new ArrayList<>();
			tied = new ArrayList<>();
			winner = new ArrayList<>();
			
			for(int rank: RANKS) {
				for(int suit: SUITS) {
					deck.add(new Card(rank, suit));   //creates the deck
				}
			}
			
			Collections.shuffle(deck);    //shuffles
			deal(numPlayers);
			
			for(Hand h: hands) {
				//System.out.print("player " + h.player + ": " + h.a.face + "   " + h.b.face + "\n"); //prints all the hands
				
				//h.check(); //checks all the hands
			}
			
			//System.out.println("");
			for(Card c: board) {
				//System.out.print(c.face + "   ");  //prints the board
			}
			
			for(Hand h: hands) h.check();
			
			//who wins
			findWinner();
			
			//for(Hand h: winner) System.out.println("\n" + "player " + h.player + " wins with " + h.finalHand);	//print who wins and with what hand
			
			if(winner.size() == 1 && winner.get(0).player == numPlayers + 1) {
				wins++;
				if(winner.get(0).value == 0) high++;
				if(winner.get(0).value == 1) pair++;
				if(winner.get(0).value == 2) twop++;
				if(winner.get(0).value == 3) trip++;
				if(winner.get(0).value == 4) stra++;
				if(winner.get(0).value == 5) flus++;
				if(winner.get(0).value == 6) full++;
				if(winner.get(0).value == 7) quad++;
				if(winner.get(0).value == 8) stfl++;
				if(winner.get(0).value == 9) rofl++;
			}
			
			//if(winner.size() == 1 && winner.get(0).a.rank == 14) overWins++;
			//if(winner.size() == 1 && winner.get(0).a.rank == 12) pairWins++;
			//if(winner.size() > 1) ties++;
		}
		
		System.out.println(wins + " wins");
		System.out.println(high + " high card");
		System.out.println(pair + " pairs");
		System.out.println(twop + " two pairs");
		System.out.println(trip + " three of a kinds");
		System.out.println(stra + " straights");
		System.out.println(flus + " flushes");
		System.out.println(full + " full houses");
		System.out.println(quad + " four of a kinds");
		System.out.println(stfl + " straight flushes");
		System.out.println(rofl + " royal flushes");
		
		//System.out.println(overWins + " overcard wins, " + pairWins + " pair wins, " + ties + " ties");
	}
	
	public void deal(int numPlayers) {
		//Random rand = new Random();
		
		int a = 0;
		int b = 0;
		
		for(int i = 0; i < deck.size(); i++) {
			if(deck.get(i).rank == 11 && deck.get(i).suit == 0) a = i;
			if(deck.get(i).rank == 13 && deck.get(i).suit == 1) b = i;
		}
		
		hands.add(new Hand(numPlayers + 1, deck.get(a), deck.get(b)));
		
		if(b > a) b--;
		
		deck.remove(a);
		deck.remove(b);
		
		/*int c = 0;
		int d = 0;
		
		for(int i = 0; i < deck.size(); i++) {
			if(deck.get(i).rank == 14 && deck.get(i).suit == 2) c = i;
			if(deck.get(i).rank == 13 && deck.get(i).suit == 3) d = i;
		}
		
		hands.add(new Hand(numPlayers + 1, deck.get(c), deck.get(d)));
		
		if(d > c) d--;
		
		deck.remove(c);
		deck.remove(d);*/
		
		for(int i = 0; i < numPlayers; i++) {
			hands.add(new Hand(i + 1, deck.get(i), deck.get(i + numPlayers)));
		}
		
		for(int i = 1; i <= 7; i++) {
			if(i == 4) i++;
			if(i == 6) i++;
			board.add(deck.get(numPlayers * 2 + i));
		}
	}
	
	public void findWinner() {
		Collections.sort(hands, new SortByValue());
		
		int target = hands.get(0).value;
		
		int[] x = new int[numPlayers + 2]; 			  //if adding in a hand manually, make sure there is a plus one here
		for(int i = 0; i < x.length; i++) x[i] = 0;
		
		for(int i = 0; i < hands.size(); i++) {
			if(hands.get(i).value == target) {
				x[i] = 1;                             //finds and stores the location of any hands with the same value as the top hand
			}
		}
		
		for(int i = 0; i < x.length; i++) {
			if(x[i] == 1) tied.add(hands.get(i));     //adds all the hands with the same highest value to a new ArrayList for tied hands
		}
		
		Collections.sort(tied, new SortByHand());
		
		int target2 = tied.get(0).hand;				  //sets the new target, the highest hand out of the tied hand
		
		for(int i = 0; i < x.length; i++) x[i] = 0;   //resets x
		
		for(int i = 0; i < tied.size(); i++) {
			if(tied.get(i).hand == target2) {
				x[i] = 1;                             //stores the location of any tied hands
			}
		}
		
		for(int i = 0; i < x.length; i++) {
			if(x[i] == 1) winner.add(tied.get(i));    //adds all tied hands or the single winner to a new ArrayList for winnin hands
		}
	}
	
	public static void main(String[] args) {
		Main main = new Main();
		main.start();
	}
	
	class Card {
		
		public int rank;
		public int suit;
		public String face;
		public String[] suits = {"s", "d", "h", "c"};
		
		
		public Card(int rank, int suit) {
			this.rank = rank;
			this.suit = suit;
			
			if(rank < 10) face = rank + suits[suit];
			if(rank == 10) face = "T" + suits[suit];
			if(rank == 11) face = "J" + suits[suit];
			if(rank == 12) face = "Q" + suits[suit];
			if(rank == 13) face = "K" + suits[suit];
			if(rank == 14) face = "A" + suits[suit];
		}
	}
	
	class Hand {
		
		public int player;
		public Card a, b;
		
		public int value;
		public int hand;
		public String finalHand;
		
		public Hand(int player, Card a, Card b) {
			this.player = player;
			this.a = a;
			this.b = b;
			
			value = 0;
			hand = 0x0;
		}
		
		private int high = -1;
		private int loc = -1;
		private int loc2 = -1;
		private ArrayList<Card> temp = new ArrayList<>();
		private ArrayList<Card> similar = new ArrayList<>();
		private ArrayList<Card> suited = new ArrayList<>();
		private ArrayList<Card> connected = new ArrayList<>();
		
		private void random() {
			temp.add(a);
			temp.add(b);
			for(Card c: board) temp.add(c);
		}
		
		private void manual() {
			temp.add(new Card(14, 1));
			temp.add(new Card(2, 1));
			temp.add(new Card(3, 1));
			temp.add(new Card(4, 1));
			temp.add(new Card(5, 1));
			temp.add(new Card(9, 2));
			temp.add(new Card(10, 0));
		}
		
		public void check() {
			random();
			//manual();
			
			Collections.sort(temp, new SortByRank());
			
			numSimilar();
			
			if(similar.size() == 2) {
				value = 1;
				finalHand = "pair";
			} else if(similar.size() == 4) {
				value = 2;
				finalHand = "two pair";
			} else if(similar.size() == 5) {
				value = 3;
				finalHand = "three of a kind";
			} else if(similar.size() == 6) {
				value = 2;
				finalHand = "two pair";
			}
			
			if(straight()) {
				value = 4;
				finalHand = "straight";
			}
			
			if(flush()) {
				value = 5;
				finalHand = "flush";
			}
			
			if(similar.size() == 7 || similar.size() == 9 || similar.size() == 10) {
				value = 6;
				finalHand = "full house";
			} else if(similar.size() == 11 || similar.size() == 13 || similar.size() == 16) {
				value = 7;
				finalHand = "four of a kind";
			}
			
			if(straightFlush()) {
				value = 8;
				finalHand = "straight flush";
				
				if(hand >> 16 == 14) {
					value = 9;
					finalHand = "royal flush";
				}
			}
			
			if(value == 0) {
				finalHand = "high card";
				hand = temp.get(0).rank * 0x10000 + temp.get(1).rank * 0x1000 + temp.get(2).rank * 0x100 + temp.get(3).rank * 0x10 + temp.get(4).rank;
			}
			
			//System.out.println(hand);
			//System.out.println(finalHand);
		}
		
		/*
		Index of Values:
		high card - 0
		pair - 1
		two pair - 2
		trips - 3
		straight - 4
		flush - 5
		full house - 6
		quads - 7
		straight flush - 8
		royal flush - 9
		
		Results of numSimilar() Function:
		one pair - 2
		two pair - 4
		three pair - 6
		three of a kind - 5
		full house - 7
		three of a kind and two pair - 9
		two three of a kind - 10
		four of a kind - 11
		four of a kind and a pair - 13
		four of a kind and three of a kind - 16
		*/
		
		public void numSimilar() {
			for(int i = 0; i < temp.size(); i++) {
				if(i != temp.size() - 1 && temp.get(i).rank == temp.get(i + 1).rank) {
					if(i < temp.size() - 2 && temp.get(i + 1).rank == temp.get(i + 2).rank) {
						if(i < temp.size() - 3 && temp.get(i + 2).rank == temp.get(i + 3).rank) {
							similar.add(temp.get(i + 3));
							similar.add(temp.get(i + 3));
							similar.add(temp.get(i + 3));
						}
						
						similar.add(temp.get(i + 2));
					}
					
					similar.add(temp.get(i));
					similar.add(temp.get(i + 1));
				}
				
				if(similar.size() == 2 && loc == -1) {  //one pair
					high = i;
					loc = i;
					
					if(loc == 0) hand = temp.get(0).rank * 0x10000 + temp.get(1).rank * 0x1000 + temp.get(2).rank * 0x100 + temp.get(3).rank * 0x10 + temp.get(4).rank;
					if(loc == 1) hand = temp.get(1).rank * 0x10000 + temp.get(2).rank * 0x1000 + temp.get(0).rank * 0x100 + temp.get(3).rank * 0x10 + temp.get(4).rank;
					if(loc == 2) hand = temp.get(2).rank * 0x10000 + temp.get(3).rank * 0x1000 + temp.get(0).rank * 0x100 + temp.get(1).rank * 0x10 + temp.get(4).rank;
					if(loc >= 3) hand = temp.get(loc).rank * 0x10000 + temp.get(loc + 1).rank * 0x1000 + temp.get(0).rank * 0x100 + temp.get(1).rank * 0x10 + temp.get(2).rank;
				}
				
				if(similar.size() == 4 && loc2 == -1) {  //two pair
					loc2 = i;
					
					int x = 0;
					if(loc2 == 2) x = 4;
					if(loc2 > 2) x = 2;
					if(loc > 0) x = 0;
					
					hand = temp.get(loc).rank * 0x10000 + temp.get(loc + 1).rank * 0x1000 + temp.get(loc2).rank * 0x100 + temp.get(loc2).rank * 0x10 + temp.get(x).rank;
				}
				
				if(similar.size() == 5 && loc == -1) {   //three of a kind
					high = i - 1;
					loc = i - 1;
					
					if(loc == 0) hand = temp.get(loc).rank * 0x10000 + temp.get(loc + 1).rank * 0x1000 + temp.get(loc + 2).rank * 0x100 + temp.get(3).rank * 0x10 + temp.get(4).rank;
					if(loc == 1) hand = temp.get(loc).rank * 0x10000 + temp.get(loc + 1).rank * 0x1000 + temp.get(loc + 2).rank * 0x100 + temp.get(0).rank * 0x10 + temp.get(4).rank;
					if(loc >= 2) hand = temp.get(loc).rank * 0x10000 + temp.get(loc + 1).rank * 0x1000 + temp.get(loc + 2).rank * 0x100 + temp.get(0).rank * 0x10 + temp.get(1).rank;
				}
				
				if(similar.size() == 7 || similar.size() == 10 && loc2 == -1) {  //full house or two three of a kind
					loc2 = i - 2;
					
					if(high != -1) {  //loc is location of trips, loc2 is location of the pair making it a full house
						loc = loc2;
						loc2 = high;
					}
					
					hand = temp.get(loc).rank * 0x10000 + temp.get(loc + 1).rank * 0x1000 + temp.get(loc + 2).rank * 0x100 + temp.get(loc2).rank * 0x10 + temp.get(loc2 + 1).rank;
				}
				
				if(similar.size() == 11 && loc == -1) {  //four of a kind
					loc = i - 2;
					
					int x = 0;
					if(loc == 0) x = 4;
					if(loc > 0) x = 0;
					
					hand = temp.get(loc).rank * 0x10000 + temp.get(loc + 1).rank * 0x1000 + temp.get(loc + 2).rank * 0x100 + temp.get(loc + 3).rank * 0x10 + temp.get(x).rank;
				}
				
				if(similar.size() == 13 || similar.size() == 16 && high != -1) {  //four of a kind and a pair
					high = -1;
					loc = i - 3;
					
					int x = 0;
					if(loc == 0) x = 4;
					if(loc > 0) x = 0;
					
					hand = temp.get(loc).rank * 0x10000 + temp.get(loc + 1).rank * 0x1000 + temp.get(loc + 2).rank * 0x100 + temp.get(loc + 3).rank * 0x10 + temp.get(x).rank;
				}
			}
		}
		
		public boolean straight() {
			connected.clear();
			int start = 0;
			
			for(int i = 0; i < temp.size(); i++) {
				if(i != temp.size() - 1 && temp.get(i).rank - temp.get(i + 1).rank == 1) {
					connected.add(temp.get(i));
				}
				
				if(connected.size() == 1 && start == 0) {
					start = i;
				}
				
				if(connected.size() == 4 && temp.get(i).rank - temp.get(i + 1).rank == 1) {
					connected.add(temp.get(i + 1));
				}
				
				if(connected.size() == 5 && connected.get(0).rank - connected.get(4).rank == 4) {
					if(start < temp.size() - 3) hand = temp.get(start).rank * 0x10000 + temp.get(start + 1).rank * 0x1000 + temp.get(start + 2).rank * 0x100 + temp.get(start + 3).rank * 0x10 + temp.get(start + 4).rank;
					
					return true;
				}
			}
			
			return false;
		}
		
		public boolean flush() {
			suited.clear();
			int tempHigh = 0;
			int target = 0;
			
			Collections.sort(temp, new SortBySuit());
			
			for(Card c: temp) {
				target = c.suit;
				
				suited.clear();
				suited.add(c);
				for(Card d: temp) {
					if(c != d && target == d.suit) {
						suited.add(d);
					}
					
					if(suited.size() == 1) {
						tempHigh = c.rank;
					}
					
					if(suited.size() == 5) {
						high = tempHigh;
						
						hand = suited.get(0).rank * 0x10000 + suited.get(1).rank * 0x1000 + suited.get(2).rank * 0x100 + suited.get(3).rank * 0x10 + suited.get(4).rank;
						
						Collections.sort(temp, new SortByRank());
						
						return true;
					}
				}
			}
			
			Collections.sort(temp, new SortByRank());
			
			return false;
		}
		
		public boolean straightFlush() {
			int similar = 0;
			
			if(straight() && flush()) {
				for(int i = 0; i < connected.size(); i++) {
					if(connected.get(i) == suited.get(i)) similar++;
					
					if(similar == 5) return true;
				}
			}
			
			return false;
		}
	}
	
	class SortByRank implements Comparator<Card> {
		public int compare(Card a, Card b) {
			return b.rank - a.rank;
		}
	}
	
	class SortBySuit implements Comparator<Card> {
		public int compare(Card a, Card b) {
			return a.suit - b.suit;
		}
	}
	
	class SortByValue implements Comparator<Hand> {
		public int compare(Hand a, Hand b) {
			return b.value - a.value;
		}
	}
	
	class SortByHand implements Comparator<Hand> {
		public int compare(Hand a, Hand b) {
			return b.hand - a.hand;
		}
	}
}

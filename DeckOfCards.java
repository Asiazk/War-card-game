// Extension of DeckOfCards class taken from course textbook.
// Arrays changed to Array List.
// DeckOfCards class represents a deck of playing cards.
import java.security.SecureRandom;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class DeckOfCards {
   // random number generator
   private static final SecureRandom randomNumbers = new SecureRandom();
   private static final int NUMBER_OF_CARDS = 52; // constant # of Cards
   private static final int MINIMAL_WAR_CARDS = 3;

   // Card references
   private ArrayList<Card> deck = new ArrayList<Card>(NUMBER_OF_CARDS); 
   private int currentCard = 0; // index of next Card to be dealt (0-51)

   // constructor fills deck of Cards
   public DeckOfCards() {
      String[] faces = {"Ace", "Deuce", "Three", "Four", "Five", "Six",
         "Seven", "Eight", "Nine", "Ten", "Jack", "Queen", "King"};    
      String[] suits = {"Hearts♥", "Diamonds♦", "Clubs♣", "Spades♠"};      

      // populate deck with Card objects                   
      for (int count = 0; count < NUMBER_OF_CARDS; count++) {  
         deck.add(count, new Card(faces[count % 13], suits[count / 13]));
      }                                                    
   } 

   // shuffle deck of Cards with one-pass algorithm
   public void shuffle() {
      // next call to method dealCard should start at deck[0] again
      currentCard = 0; 

      // for each Card, pick another random Card (0-51) and swap them
      for (int first = 0; first < deck.size(); first++) {
         // select a random number between 0 and the size of the deck 
         int second = randomNumbers.nextInt(deck.size());

         // swap current Card with randomly selected Card
         Card temp = deck.get(first);
         deck.set(first, deck.get(second));
         deck.set(second, temp);
            
      } 
   } 

   // deal one Card
   public Card dealCard() {
      // determine whether Cards remain to be dealt
      if (deck.size() > 0) {
         return deck.get(currentCard); // return current Card in array
      } 
      else {
         return null; // return null to indicate that all Cards were dealt
      } 
   } 
   
   /**
    * Clearing the deck from cards
    */
   public void emptyDeckOfCards() {
	   // Each player will need max 52 cards in his deck
	   deck = new ArrayList<Card>(NUMBER_OF_CARDS);
   }
   
/**
 * A method to split a deck of cards in half
 * @param mainDeck the deck we would like to split
 * @param player1 deck of the first player
 * @param player2 deck of the second player
 */
   public static void splitDeck(DeckOfCards mainDeck, DeckOfCards player1,
		   						DeckOfCards player2) {
	   // Declare 2 new Array Lists. Each represents a half of deck.
	   player1.deck = new ArrayList<Card>(NUMBER_OF_CARDS);
	   player2.deck = new ArrayList<Card>(NUMBER_OF_CARDS);
	   
	   // Filling the first half
	   for (int i=0; i<NUMBER_OF_CARDS/2; i++) {
		   player1.deck.add(mainDeck.deck.get(i));
	   }
	   
	   // Filling the second half
	   for (int i=NUMBER_OF_CARDS/2; i<NUMBER_OF_CARDS; i++) {
		   player2.deck.add(mainDeck.deck.get(i));
	   }
   }
   
   /**
    * A method the adds a chosen card to the deck
    * @param card the name of the card we want to add to the deck
    */
   public void addCard(Card card) {
	   deck.add(card);
   }
   
   /**
    * A method that removes a chosen card from deck
    * @param i the index of the card we would like to remove from the deck
    */
	public void removeCard(int i) {
		deck.remove(i);
	}
   
	/**
	 * A method that calculates how many cards are left in the deck
	 * @return number of cards left in the deck
	 */
	public int remainingCards() {
		return deck.size();
	}
	
	/**
	 * A method that executes a war in case both drawn
	 * cards have the same face value
	 * @param player1 first player
	 * @param player2 second player
	 * @param curCard1 current card of the first player
	 * @param curCard2 current card of the second player
	 * @return 0 if it's a tie, 1 if player1 won, 2 if player2 won
	 */
	public static int war(DeckOfCards player1, DeckOfCards player2, Card curCard1,
						   Card curCard2, ArrayList<Card> cardsFromWar) {
		
		// If both players are left with an empty deck - the game ends with a tie
		if (player1 == null && player2 == null) {
			JOptionPane.showMessageDialog(null, "It's a tie!" +
												"\n" + "Good game :)");
			return 0;
		}
	
		// If both decks left with 3 or less cards, the game ends with a tie
		if (player1.remainingCards() <= MINIMAL_WAR_CARDS &&
			player2.remainingCards() <= MINIMAL_WAR_CARDS) {
			JOptionPane.showMessageDialog(null, "It's a tie!" +
										"\n" + "Good game :)");
			return 0;
		}
		
		// If someone is left with less than 3 cards in their deck - he loses
		if (player1.remainingCards() < MINIMAL_WAR_CARDS) {
			JOptionPane.showMessageDialog(null,
			"Player 2 is the winner!!!" + "\n" + "Thank you for playing :)");
			return 2;
		}
		if (player2.remainingCards() < MINIMAL_WAR_CARDS) {
			JOptionPane.showMessageDialog(null,
			"Player 1 is the winner!!!" + "\n" + "Thank you for playing :)");
			return 1;
		}
		
		// If both decks are empty, i.e. drawn cards are null, the game ends
		// with a tie
		if (Card.getValue(curCard1) == 0) {
			JOptionPane.showMessageDialog(null, "It's a tie!" +
												"\n" + "Good game :)");
			 return 0;
		}
		
		//Both players enter a war turn only if they have more than 3 cards
		//in the deck
		if (player1.remainingCards() > MINIMAL_WAR_CARDS &&
			player2.remainingCards() > MINIMAL_WAR_CARDS) {
			JOptionPane.showMessageDialog(null,
					"Player 1: " + curCard1.toString()
					+ "(" + player1.remainingCards() + ")" +
					"\n" + "Player 2: " + curCard2.toString() 
					+ "(" + player2.remainingCards() + ")" +
					"\n" +"A war is declared!");
		
			// Adding the current cards of each player to the new array list
			// and removing the current cards from both decks
			cardsFromWar.add(curCard1);
			player1.removeCard(0);
			cardsFromWar.add(curCard2);
			player2.removeCard(0);
			
			// Each player draws additional 3 cards
			Card num1p1 = player1.dealCard();
			player1.removeCard(0);
			cardsFromWar.add(num1p1);
			
			Card num2p1 = player1.dealCard();
			player1.removeCard(0);
			cardsFromWar.add(num2p1);
			
			Card num3p1 = player1.dealCard();
			player1.removeCard(0);
			cardsFromWar.add(num3p1);
			
			Card num1p2 = player2.dealCard();
			player2.removeCard(0);
			cardsFromWar.add(num1p2);
			
			Card num2p2 = player2.dealCard();
			player2.removeCard(0);
			cardsFromWar.add(num2p2);
			
			Card num3p2 = player2.dealCard();
			player2.removeCard(0);
			cardsFromWar.add(num3p2);
			
			// Comparing the 3rd card
			if (Card.getValue(num3p1) > Card.getValue(num3p2)) {
				JOptionPane.showMessageDialog(null,
				"Player 1: " + num3p1.toString() + "(" + player1.remainingCards() 
				+ ")" + "\n" +
				"Player 2: " + num3p2.toString() +"(" + player2.remainingCards() 
				+ ")" + "\n" +
				"Player 1 wins this war");
				// Adding all drawn cards to winner
				player1.deck.addAll(cardsFromWar);
				player1.shuffle();
				return 1;
			}
			if (Card.getValue(num3p1) < Card.getValue(num3p2)) {
				JOptionPane.showMessageDialog(null,
				"Player 1: " + num3p1.toString() + "(" + player1.remainingCards() 
				+ ")" + "\n" +
				"Player 2: " + num3p2.toString() + "(" + player2.remainingCards() 
				+ ")" + "\n" +
				"Player 2 wins this war");
				// Adding all drawn cards to the winner
				player2.deck.addAll(cardsFromWar);
				player2.shuffle();
				return 1;
			}
			
			
			// In case both players once again drew a card with the
			// same value, another war is declared
			if (Card.getValue(num3p1) == Card.getValue(num3p2))
				war(player1, player2, num3p1, num3p2, cardsFromWar);
		}
	return 0;
	}
} 
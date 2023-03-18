/**
 * This class represents a game called "War"
 * @author Asia Zhivov 
 *
 */

import java.util.ArrayList;
import javax.swing.JOptionPane;


public class Game {
	private static final int NUMBER_OF_CARDS = 52; // Number of cards in a deck
	private static int turns = 1;// A turns counter is necessary to end the game

	public static void main(String args[]) {
		
		// Setting a deck for the game
		DeckOfCards mainDeck = new DeckOfCards();
		mainDeck.shuffle();
		
		// Each player gets an empty deck
		DeckOfCards player1 = new DeckOfCards();
		player1.emptyDeckOfCards();
		DeckOfCards player2 = new DeckOfCards();
		player2.emptyDeckOfCards();
		
		// Each player gets half a deck
		DeckOfCards.splitDeck(mainDeck, player1, player2);
		
		/**
		 * A turn in the game: Each player draws 1 card.
		 * The player with the higher card value wins the turn and takes
		 * all the drawn cards.
		 * If the drawn cards have the same value - a war is declared and both 
		 * players draw additional 3 cards. The higher value card wins.
		 * If there is another tie, another war is declared.
		 * The winner of the war takes all last drawn cards.
		 * The first player who reaches an empty deck - loses.
		 * If a war turn starts with both players having 3 or less cards
		 * in the deck, the game ends with a tie.
		 * If both players stay with empty decks, the game ends with a tie.
		 * In every turn, the message pane shows how many cards are
		 * currently in each player's deck in "()".
		 */
		
		// A turn begins only if both players have cards in their deck
		// The game won't end until someone wins!
		while (true) {
			
			// When we reach 100 turns in the game, the player with the larger
			// deck wins.
			// In case both decks are the same size, the game ends with a tie
			if (turns == 100) {
				if (player1.remainingCards() > player2.remainingCards()) 
					JOptionPane.showMessageDialog(null, "Player 1"
					 + " is the winner!!! thank you for playing :)");
				
				if (player2.remainingCards() > player1.remainingCards())
					JOptionPane.showMessageDialog(null, "Player 2"
							 + " is the winner!!! thank you for playing :)");
				
				if (player2.remainingCards() == player1.remainingCards())
					JOptionPane.showMessageDialog(null, "It's a tie!" + "\n" + "Good game :)");
				
				// An indicator that the game ended within 100 turns
				System.out.println(turns);
				break;
			}
			
			// First player draws a card
			Card curCard1 = player1.dealCard();
			//If the drawn card is empty,the first player loses.
			// else - continue to 2nd player
			if (curCard1 == null) {
				JOptionPane.showMessageDialog(null,
				"Player 2 is the winner!!!" + "\n" + "Thank you for playing :)");
				break;
			}
			
			// Seconds player draws a card
			Card curCard2 = player2.dealCard();
			//If the drawn card is empty, the second player loses. else - continue.
			if (curCard2 == null) {
				JOptionPane.showMessageDialog(null,
				"Player 1 is the winner!!!" + "\n" + "Thank you for playing :)");
				break;
			}

			// After we checked both cards are not null we can get their value
			int valueCard1 = Card.getValue(curCard1);
			int valueCard2 = Card.getValue(curCard2);
				
			// Checking which player drew the card with higher value
			if (valueCard1 > valueCard2) {
				JOptionPane.showMessageDialog(null,
				"Player 1: " + curCard1.toString() + "(" + player1.remainingCards() +
				")" + "\n" +
				"Player 2: " + curCard2.toString() + "(" + player2.remainingCards() +
				")" + "\n" +
				"Player 1 wins this turn");
				player2.removeCard(0); // Remove current card from the loser's deck
				// The card drew by the winner stays in his deck
				player1.addCard(curCard2); // Add the loser's card to winner's deck
				player1.shuffle(); // Shuffle winner's deck
				turns++;
			}	
			if (valueCard1 < valueCard2) {
				JOptionPane.showMessageDialog(null,
				"Player 1: " + curCard1.toString() + "(" + player1.remainingCards() + ")" 
				+ "\n" +
				"Player 2: " + curCard2.toString() + "(" + player2.remainingCards() + ")"
				+ "\n" +
				"Player 2 wins this turn");
				player1.removeCard(0); // Remove current card from the loser's deck
				// The card drew by the winner stays in his deck
				player2.addCard(curCard1); // Add the loser's card to winner's deck
				player2.shuffle(); // Shuffle winner's deck
				turns++;
			}
			
			// In case both drawn cards have the same value
			if (valueCard1 == valueCard2) {
				turns++;
				// An array list that stores all the drawn cards in current war turn
				ArrayList<Card> cardsFromWar = new ArrayList<Card>(NUMBER_OF_CARDS);
				DeckOfCards.war(player1, player2, curCard1, curCard2, cardsFromWar);
			}
		}
	}	 
}

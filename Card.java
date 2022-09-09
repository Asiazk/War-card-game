// Extension of Card class taken from course textbook.
// Card class represents a playing card.

public class Card {
   private final String face; // face of card ("Ace", "Deuce", ...)
   private final String suit; // suit of card ("Hearts", "Diamonds", ...)

   // two-argument constructor initializes card's face and suit
   public Card(String cardFace, String cardSuit) {
      this.face = cardFace; // initialize face of card
      this.suit = cardSuit; // initialize suit of card
   } 

   // return String representation of Card
   public String toString() {             
      return face + " of " + suit;                        
   
   }
   
   /**
    * A method that returns the face value of a card
    * @param card the card we want to check its value
    * @return the value of the chosen card, 0 if card is null
    */
   public static int getValue(Card card) {
	   String[] faces = {"Ace", "Deuce", "Three", "Four", "Five", "Six","Seven",
			   			 "Eight", "Nine", "Ten", "Jack", "Queen", "King"}; 
	   
	   int i = 0;
	   int value = 0;
	   while (i < faces.length && card != null) {
		   if (faces[i].equals(card.face)) {
			   value = i+1;
			   break;
		   }

		   else {
			   i++;
		   }
	   }
	   return value;
   }
}
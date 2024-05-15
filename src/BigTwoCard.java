/**
 * This class is a subclass of the Card class and is used to model a card used in a Big Two card game. 
 * 
 * @author Wang Keran
 *
 */
public class BigTwoCard extends Card {

	/**
	 * a constructor for building a card with the specified suit and rank.
	 * 
	 * @param suit the suit of card
	 * @param rank the rank of card
	 */
	public BigTwoCard(int suit, int rank) {
		super(suit, rank);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * comparing the order of this card with the specified card. 
	 * 
	 * @return a negative integer, zero, or a positive integer when this card is less than, equal to, or greater than the specified card.
	 * 
	 */
	public int compareTo(Card card) {
		// one of the card ranks 2 and the other doesn't
		if(this.rank==1&&card.rank!=1) {
			return 1;
		}
		if(card.rank==1&&this.rank!=1) {
			return -1;
		}
		
		// thus the circumstance exclude only one of them ranks 2
		// one of the card ranks 1 and the other doesn't
		if(this.rank==0&&card.rank!=0) {
			return 1;
		}
		if(card.rank==0&&this.rank!=0) {
			return -1;
		}
		
		// thus the circumstance exclude only one of them ranks 1
		if (this.rank > card.rank) {
			return 1;
		} else if (this.rank < card.rank) {
			return -1;
		} 
			
		// when the ranks equal, compare the suit
		else if (this.suit > card.suit) {
			return 1;
		} else if (this.suit < card.suit) {
			return -1;
		} else {
			return 0;
		}
	}
	
	
	

}

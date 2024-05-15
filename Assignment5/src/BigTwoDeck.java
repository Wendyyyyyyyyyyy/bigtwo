/**
 * This class is a subclass of the Deck class and is used to model a deck of cards used in a Big Two card game.
 * 
 * @author Wang Keran
 *
 */
public class BigTwoDeck extends Deck {
	/**
	 * Initializing a deck of Big Two cards.
	 */	
	public void initialize() {
		removeAllCards();
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 13; j++) {
				BigTwoCard card = new BigTwoCard(i, j);
				addCard(card);
			}
		}
	}

}

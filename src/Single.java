
/**
 * This class is a subclass of the Hand class and are used to model a hand of Single.
 * 
 * @author Wang Keran
 *
 */
public class Single extends Hand {
	/**
	 * a constructor for building a Single with the specified player and list of cards.
	 * 
	 * @param player the player of Single
	 * @param cards list of cards
	 */

	public Single(CardGamePlayer player, CardList cards) {
		super(player, cards);
		// TODO Auto-generated constructor stub
	}
	/** 
	 * a method for checking if this is a valid Single.
	 *
	 */
	public boolean isValid() {
		if(this.size()==1) {
			return true;
		}
		else {return false;}
	}
	/** 
	 * a method for retrieving the top card of this Single.
	 *
	 */
	public Card getTopCard() {
		this.topcard=this.getCard(0);
		return topcard;
		
	}
	/** 
	 * a method for returning a string specifying the type of this Single.
	 *
	 */
	public String getType() {
		return "Single";
	}
	/**
	 *  a method for returning a integer specifying the type of this Single.
	 *
	 */
	public int getTypeNumber() {
		return 1;
	}
}

/**
 * This class is a subclass of the Hand class and are used to model a hand of Pair.
 * 
 * @author Wang Keran
 *
 */
public class Pair extends Hand {
	/**
	 * a constructor for building a pair with the specified player and list of cards.
	 * 
	 * @param player the player of hand
	 * @param cards list of cards
	 */
	public Pair(CardGamePlayer player, CardList cards) {
		super(player, cards);
		// TODO Auto-generated constructor stub
	}
	
	/** 
	 * a method for checking if this is a valid Pair.
	 *
	 */
	public boolean isValid() {
		if(this.size()==2 && this.getCard(0).getRank()==this.getCard(1).getRank()) {
			return true;
		}
		else {return false;}
	}
	
	/** 
	 * a method for retrieving the top card of this Pair.
	 *
	 */
	public Card getTopCard() {
		this.sort();
		this.topcard=this.getCard(0);
		return topcard;		
	}
	/** 
	 * a method for returning a string specifying the type of this Pair.
	 *
	 */
	public String getType() {
		return "Pair";
	}
	/**
	 *  a method for returning a integer specifying the type of this Pair.
	 *
	 */
	public int getTypeNumber() {
		return 2;
	}
}

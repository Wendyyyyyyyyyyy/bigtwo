/**
 * This class is a subclass of the Hand class and are used to model a hand of Triple.
 * 
 * @author Wang Keran
 *
 */
public class Triple extends Hand {
	/**
	 * a constructor for building a triple with the specified player and list of cards.
	 * 
	 * @param player the player of hand
	 * @param cards list of cards
	 */
	public Triple(CardGamePlayer player, CardList cards) {
		super(player, cards);
		// TODO Auto-generated constructor stub
	}
	/** 
	 * a method for checking if this is a valid  Triple .
	 *
	 */
	public boolean isValid() {
		
		if(this.size()==3) { 
				if(this.getCard(0).getRank()==this.getCard(1).getRank()&&
				this.getCard(0).getRank()==this.getCard(2).getRank()
				) {
			return true;
		}
		else {return false;}
	}
	else {return false;}}
	/** 
	 * a method for retrieving the top card of this  Triple .
	 *
	 */
	public Card getTopCard() {
		this.sort();
		this.topcard=this.getCard(2);
		return topcard;		
	}
	/** 
	 * a method for returning a string specifying the type of this  Triple .
	 *
	 */	
	public String getType() {
		return "Triple";
	}
	/**
	 *  a method for returning a integer specifying the type of this  Triple .
	 *
	 */
	public int getTypeNumber() {
		return 3;
	}
}

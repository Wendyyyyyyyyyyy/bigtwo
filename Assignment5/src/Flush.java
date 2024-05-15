/**
 * This class is a subclass of the Hand class and are used to model a hand of Flush.
 * 
 * @author Wang Keran
 *
 */
public class Flush extends Hand {
	/**
	 * a constructor for building a Flush with the specified player and list of cards.
	 * 
	 * @param player the player of Flush
	 * @param cards list of cards
	 */
	public Flush(CardGamePlayer player, CardList cards) {
		super(player, cards);
		// TODO Auto-generated constructor stub
	}
	/** 
	 * a method for checking if this is a valid Flush.
	 *
	 */
	
	public boolean isValid() {
		if(this.size()==5) {
			this.sort();

			int[] suit= new int[5];
			for (int i=0;i<5;i++) {
				suit[i]=this.getCard(i).getSuit();
			}
			// 5 cards in same suit
			if((suit[0]==suit[1] && suit[0]==suit[2])&& 
				(suit[0]==suit[3] && suit[0]==suit[4])) {
							   return true;}
			
			
		}
		return false;
	}
	/** 
	 * a method for retrieving the top card of this Flush.
	 *
	 */
	public Card getTopCard() {
		this.sort();
		topcard=this.getCard(4);
		return topcard;		
	}
	/** 
	 * a method for returning a string specifying the type of this Flush.
	 *
	 */
	public String getType() {
		return "Flush";
	}	
	/**
	 *  a method for returning a integer specifying the type of this Flush.
	 *
	 */
	public int getTypeNumber() {
		return 5;
	}
}

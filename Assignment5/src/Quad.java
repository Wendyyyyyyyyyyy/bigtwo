/**
 * This class is a subclass of the Hand class and are used to model a hand of Quad.
 * 
 * @author Wang Keran
 *
 */
public class Quad extends Hand {
	/**
	 * a constructor for building a Quad with the specified player and list of cards.
	 * 
	 * @param player the player of Quad
	 * @param cards list of cards
	 */
	public Quad(CardGamePlayer player, CardList cards) {
		super(player, cards);
		// TODO Auto-generated constructor stub
	}
	/** 
	 * a method for checking if this is a valid Quad.
	 *
	 */
	public boolean isValid() {
		// hand consists of five cards
		if(this.size()==5) {
			this.sort();
			int[] rank= new int[5];
			for (int i=0;i<5;i++) {
				rank[i]=this.getCard(i).getRank();
			}
			
			// with first 4 having the same rank
			if((rank[0]==rank[1] && rank[0]==rank[2])&&
			   (rank[0]==rank[3] )
				) {
			   return true;}
			
			//with last 4 having the same rank
			if((rank[1]==rank[2] && rank[1]==rank[3])&&
					   (rank[1]==rank[4] )
						) {
					   return true;}
			
			
		}
	return false;
	}
	/** 
	 * a method for retrieving the top card of this Quad.
	 *
	 */
	public Card getTopCard() {
		this.sort();
		
		int[] rank= new int[5];
		for (int i=0;i<5;i++) {
			rank[i]=this.getCard(i).getRank();
		}
		
		// with first 4 having the same rank
		if((rank[0]==rank[1] && rank[0]==rank[2])&&
		   (rank[0]==rank[3] )
			) {
			topcard=this.getCard(3);
			return topcard;	}
		
		//with last 4 having the same rank
		if((rank[1]==rank[2] && rank[1]==rank[3])&&
				   (rank[1]==rank[4] )
					) {
			topcard=this.getCard(4);
			return topcard;	}
		return topcard;
		
	}
	/** 
	 * a method for returning a string specifying the type of this Quad.
	 *
	 */
	public String getType() {
		return "Quad";
	}
	
	/**
	 *  a method for returning a integer specifying the type of this Quad.
	 *
	 */
	public int getTypeNumber() {
		return 7;
	}
}

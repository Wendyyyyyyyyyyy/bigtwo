/**
 * This class is a subclass of the Hand class and are used to model a hand of FullHouse.
 * 
 * @author Wang Keran
 *
 */
public class FullHouse extends Hand {
	/**
	 * a constructor for building a FullHouse with the specified player and list of cards.
	 * 
	 * @param player the player of hand
	 * @param cards list of cards
	 */
	public FullHouse(CardGamePlayer player, CardList cards) {
		super(player, cards);
		// TODO Auto-generated constructor stub
	}
	/** 
	 * a method for checking if this is a valid FullHouse.
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
			//System.out.print("15 loop \n");
			// with two having the same rank and three having another same rank
			if((rank[0]==rank[1] && rank[2]==rank[3])&&
			   (rank[2]==rank[4] )
				) {
				//System.out.print("16 loop \n");
			   return true;}
			// with three having the same rank and two having another same rank
			if((rank[0]==rank[1] && rank[0]==rank[2])&&
					   (rank[3]==rank[4] )
						) {
					   return true;}
			
		}
		return false;
	}
	/** 
	 * a method for retrieving the top card of this FullHouse.
	 *
	 */
	public Card getTopCard() {
		this.sort();
		int[] rank= new int[5];
		for (int i=0;i<5;i++) {
			rank[i]=this.getCard(i).getRank();
		}
		// with two having the same rank and three having another same rank
		if((rank[0]==rank[1] && rank[2]==rank[3])&&
		   (rank[2]==rank[4] )
			) {
			topcard=this.getCard(4);
			return topcard;}
		
		// with three having the same rank and two having another same rank
		if((rank[0]==rank[1] && rank[0]==rank[2])&&
				   (rank[3]==rank[4] )
					) {
			topcard=this.getCard(2);
			return topcard;}
		return topcard;	
	}
	/** 
	 * a method for returning a string specifying the type of this FullHouse.
	 *
	 */
	public String getType() {
		return "FullHouse";
	}	
	/**
	 *  a method for returning a integer specifying the type of this FullHouse.
	 *
	 */
	public int getTypeNumber() {
		return 6;
	}
}

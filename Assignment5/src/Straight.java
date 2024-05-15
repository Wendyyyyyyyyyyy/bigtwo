/**
 * This class is a subclass of the Hand class and are used to model a hand of Straight.
 * 
 * @author Wang Keran
 *
 */
public class Straight extends Hand {
	/**
	 * a constructor for building a straight with the specified player and list of cards.
	 * 
	 * @param player the player of Straight
	 * @param cards list of cards
	 */
	public Straight(CardGamePlayer player, CardList cards) {
		super(player, cards);
		// TODO Auto-generated constructor stub
	}
	
	/** 
	 * a method for checking if this is a valid Straight.
	 *
	 */
	public boolean isValid() {
		//System.out.print("9 loop \n");
		if(this.size()==5) {
			//System.out.print("8 loop \n");
			this.sort();
			int[] rank= new int[5];
			for (int i=0;i<5;i++) {
				rank[i]=this.getCard(i).getRank();
			}
			// consists of five cards with consecutive ranks
			if((rank[0]==rank[1]-1 && rank[0]==rank[2]-2)&&
			   (rank[0]==rank[3]-3 && rank[0]==rank[4]-4)
				) {
				//System.out.print("9  loop \n");
			   return true;}
			// consists of  ranks 1,10,11,12,13
			if(((rank[0]==9 && rank[1]==10)  &&
			   (rank[2]==11 && rank[3]==12))   &&
			   (rank[4]==0 )
						) {
					   return true;}
			// consists of  ranks 1,2,11,12,13
			if(((rank[0]==10 && rank[1]==11)  &&
					   (rank[2]==12 && rank[3]==0))   &&
					   (rank[4]==1 )
								) {
							   return true;}
			
			
		}
		return false;
	}
	/** 
	 * a method for retrieving the top card of this Straight.
	 *
	 */
	public Card getTopCard() {
		this.sort();
		topcard=this.getCard(4);
		return topcard;		
	}
	/** 
	 * a method for returning a string specifying the type of this Straight.
	 *
	 */
	public String getType() {
		return "Straight";
	}
	/**
	 *  a method for returning a integer specifying the type of this Straight.
	 *
	 */
	public int getTypeNumber() {
		return 4;
	}
	
}

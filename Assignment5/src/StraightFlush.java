
/**
 * This class is a subclass of the Hand class and are used to model a hand of StraightFlush.
 * @author Wang Keran
 *
 */
public class StraightFlush extends Hand {
	/**
	 * a constructor for building a StraightFlush with the specified player and list of cards.
	 * 
	 * @param player the player of hand
	 * @param cards list of cards
	 */
	public StraightFlush(CardGamePlayer player, CardList cards) {
		super(player, cards);
		// TODO Auto-generated constructor stub
	}
	

	/** 
	 * a method for checking if this is a valid StraightFlush.
	 *
	 */
	public boolean isValid() {
		if(this.size()==5) {
			this.sort();
			// get ranks[]
			int[] rank= new int[5];
			for (int i=0;i<5;i++) {
				rank[i]=this.getCard(i).getRank();
			}
			// get suits[]
			int[] suit= new int[5];
			for (int i=0;i<5;i++) {
				suit[i]=this.getCard(i).getSuit();
			}
			// 5 cards in same suit
			if((suit[0]==suit[1] && suit[0]==suit[2])&& 
				(suit[0]==suit[3] && suit[0]==suit[4])) {
				
				// consists of five cards with consecutive ranks
				if((rank[0]==rank[1]-1 && rank[0]==rank[2]-2)&&
						(rank[0]==rank[3]-3 && rank[0]==rank[4]-4)
						) {
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
								   return true;}}
			
		}
		
		return false;
	}
	/** 
	 * a method for retrieving the top card of this StraightFlush.
	 *
	 */
	public Card getTopCard() {
		
		this.sort();
		topcard=this.getCard(4);
		return topcard;		
	}
	/** 
	 * a method for returning a string specifying the type of this StraightFlush.
	 *
	 */
	public String getType() {
		return "StraightFlush";
	}
	/**
	 *  a method for returning a integer specifying the type of this StraightFlush.
	 *
	 */
	public int getTypeNumber() {
		return 8;
	}
}

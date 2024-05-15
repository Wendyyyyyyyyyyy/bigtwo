/**
 * This class is a subclass of the CardList class and is used to model a hand of cards. It has a private instance variable for storing the player who plays this hand. 
 * 
 * @author Wang Keran
 *
 */
public abstract class Hand extends CardList{
	/**
	 * a constructor for building a hand with the specified player and list of cards.
	 * 
	 * @param player the player of hand
	 * @param cards list of cards
	 */
	public Hand(CardGamePlayer player, CardList cards) {
		this.player = player;
		for (int i = 0; i < cards.size(); i++) {
			this.addCard(cards.getCard(i));
		}
//		this.cards=cards;
		// TODO Auto-generated constructor stub	
	}
	
	/**
	 * a constructor for building a hand without player and list of cards.
	 * 
	 */
	public Hand() {
		// TODO Auto-generated constructor stub
	}
	
	
	/**
	 * @return the player
	 */
	public CardGamePlayer getPlayer() {
		return player;
	}

	/**
	 * @param player the player to set
	 */
	public void setPlayer(CardGamePlayer player) {
		this.player = player;
	}
	/**
	 * a CardGamePlayer object specifying the player.
	 */
	private CardGamePlayer player;
//	public CardList cards = new CardList();
	/**
	 * a Card object specifying the topcard.
	 */
	public Card topcard;
	/**
	 * a integer value specifying the typenumber.
	 */
	public int typenumber;
	
	/** 
	 * a method for retrieving the top card of this hand.
	 * @return the top card of this hand.
	 */
	public abstract Card getTopCard() ;
	
	/**
	 *  a method for returning a integer specifying the type of this hand.
	 *@return a integer specifying the type of this hand.
	 */
	public abstract int getTypeNumber() ;
	
	/**
	 *  a method for checking if this hand beats a specified hand.
	 * 
	 * @param hand the specified hand
	 * @return a boolean value of if this hand beats a specified hand.
	 */
	boolean beats(Hand hand) {
		
		// if compare with pass, every hands beats pass
		if (hand==null) {
			return true;
		}
		
		// if it is not pass
		// if it is single or pair or Triple, then it must be compare by the topcard
		// if the number of cards is {1,2,3}
		if (hand.size()<=3) {
			//System.out.print("loop 0");
			if (this.getType()==hand.getType()) {
				//System.out.print("loop 1");
				if(this.getTopCard().compareTo(hand.getTopCard())==1) {
				return true;
			     }
		    }
		
	    }
		
		if (hand.size()==5) {
			// stronger type, beats
			if (this.getTypeNumber()>hand.getTypeNumber()) {
				return true;
			}
			// weaker type, can't beat
			if (this.getTypeNumber()<hand.getTypeNumber()){
				return false;
			}
			// same type
			if (this.getTypeNumber()==hand.getTypeNumber()){
				//compare topcard if it not flush
				if (this.getTypeNumber()!=5) {
					if(this.getTopCard().compareTo(hand.getTopCard())==1) {
						return true;
						}	   
				}
				//compare suit first and then topcard if it is flush
				if (this.getTypeNumber()==5) {
					if((this.getCard(0).getSuit())>(hand.getCard(0).getSuit())) {
						return true;
						}	
					if((this.getCard(0).getSuit())==(hand.getCard(0).getSuit())) {
						if(this.getTopCard().compareTo(hand.getTopCard())==1) {
							return true;
							}
						}	
				}
				   
		    }
		
	    }
		return false;
	}
	
	
	
	/** 
	 * a method for checking if this is a valid hand.
	 * @return boolean indicating whether isValid.
	 */
	public abstract boolean isValid();
	/** 
	 * a method for returning a string specifying the type of this hand.
	 *@return a string specifying the type of this hand.
	 */
	public abstract String getType();
	

}

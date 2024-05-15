import java.util.ArrayList;

import javax.swing.JOptionPane;


/**
 * This class is a subclass of the Card class and is used to model a card used in a Big Two card game.
 * 
 * @author Wang Keran
 *
 */
/**
 * @author apple
 *
 */
public class BigTwo {
	
	
	private Deck deck;
	//= new BigTwoDeck();
	private ArrayList<CardGamePlayer> playerList;
	//= new ArrayList<CardGamePLayerd>();
	private ArrayList<Hand> handsOnTable;
	private int currentPlayerIdx=-1;
	//private BigTwoUI ui;
	private BigTwoGUI gui;
	private BigTwoClient client;
	/**
     * an int of the nth turn of the game
     */
    public int turn = 0;
	/**
     * an int specifying the number of players.
     */

    private int numOfPlayer;

	/**
	 * a constructor for creating a Big Two card game. 
	 * 
	 */
	public BigTwo() {
		// TODO Auto-generated constructor stub
		
		ArrayList <CardGamePlayer> plist = new ArrayList <CardGamePlayer>();
		for (int i=0;i<4;i++) {
				plist.add (new CardGamePlayer());
		}
		this.handsOnTable = new ArrayList<Hand>();
		this.playerList = plist;
		//this.ui= new BigTwoUI(this);
		BigTwoGUI gui1=new BigTwoGUI(this);
		this.gui= gui1;	
		/*BigTwoClient client1 = new BigTwoClient(this,gui1);
		this.client=client1; */
	
		
		this.setNumOfPlayer(4);
		
	}


	
	/**
	 * @return the client
	 */
	public BigTwoClient getClient() {
		return client;
	}


	/**
	 * @param client the client to set
	 */
	public void setClient(BigTwoClient client) {
		this.client = client;
	}


	/**
	 * @return the gui
	 */
	public BigTwoGUI getGui() {
		return gui;
	}


	/**
	 * @param gui the gui to set
	 */
	public void setGui(BigTwoGUI gui) {
		this.gui = gui;
	}


	/**
	 * a method for retrieving the index of the current player.
	 * @return the currentPlayerIdx
	 */
	public int getCurrentPlayerIdx() {
		return currentPlayerIdx;
	}
	

	/**
	 * set the index of the current player.
	 * @param currentPlayerIdx the currentPlayerIdx to set
	 */
	public void setCurrentPlayerIdx(int currentPlayerIdx) {
		this.currentPlayerIdx = currentPlayerIdx;
	}
	
	/**
     * reset the game status
     */
    
    public void reset(){
        for (CardGamePlayer i: this.playerList){
            i.removeAllCards();
        }
        this.handsOnTable = new ArrayList<>();// modified
        this.turn = 1;// added
    }

	
	
	/**
	 * a method for starting/restarting the game with a given shuffled deck of cards. 
	 * @param deck deck of cards
	 */
	void start(Deck deck) {
		// remove all the cards from the players as well as from the ui; 
		for (int i=0; i<playerList.size();i++) {
			playerList.get(i).removeAllCards();
		}
		if(handsOnTable!=null) {
		handsOnTable.clear();
		}
		reset();
		// distribute the cards to the players;
		
		deck.shuffle();
		//deck.sort();
		//deck.print();
		for (int i=0; i<playerList.size();i++) {
			for (int j=0; j<13;j++) {
			playerList.get(i).addCard(deck.getCard(j*4+i));
			}
			playerList.get(i).sortCardsInHand();
		}
		//identify the player who holds the Three of Diamonds;
		BigTwoCard Diamonds3= new BigTwoCard(0,2);
		int D3Holder=-1;
		for (int i=0; i<playerList.size();i++) {
			if(playerList.get(i).getCardsInHand().contains(Diamonds3)) {
				D3Holder=i;	
				}
			}
		//set both the currentPlayerIdx of the BigTwo object and the activePlayer of the 
		//BigTwoUI object to the index of the player who holds the Three of Diamonds; 
		currentPlayerIdx=D3Holder;
		this.setCurrentPlayerIdx(D3Holder);
		this.getGui().setActivePlayer(currentPlayerIdx);
		//gui.printMsg(  "\nGame ends\n" );
		
		/*gui.printMsg("All players are ready. Game starts.");
		gui.printMsg(getPlayerList().get(currentPlayerIdx).getName() + "'s turn");
		*/
		///////while (endOfGame()!=true) {
		
		// call the repaint() method of the BigTwoUI object to show the cards on the ui;
		//this.getGui().repaint();
		
		//call the promptActivePlayer() method of the BigTwoUI object to 
		//prompt user to select cards and make his/her move.
		this.getGui().promptActivePlayer();
		if (this.client.getPlayerID() == getCurrentPlayerIdx()){ // enable interface of the first player
            this.gui.enable();
        }
		}
		
			
	
	
	/**
	 * a method for making a move by a player with the specified index 
	 * @param playerIdx specified index of player
	 * @param cardIdx the list of indices of cards
	 */
	
	//a method for making a move by a player with the specified index 
	//using the cards specified by the list of indices.
	void makeMove(int playerIdx, int[] cardIdx) {
		//initialize
		CardGameMessage move = new CardGameMessage(CardGameMessage.MOVE, this.client.getPlayerID(), cardIdx);
		 this.client.sendMessage(move);

		//checkMove( playerIdx, cardIdx);
		
		
	}
	/**
     * A method to help to update the game
     * @param p current player
     * @param q hand will be played
     */

    private void moveplayer(CardGamePlayer p,Hand q){
        //modified
        if(q != null){
            handsOnTable.add(q);
            this.gui.printMsg("{" + q.getType() + "} ");
            for (int i = 0; i < q.size(); i++){
                this.gui.printMsg("[" + q.getCard(i).toString() + "]");
            }
            this.gui.printMsg("\n");
            p.removeCards(q);
        }
        //modified
        if(!endOfGame()){
            if(this.currentPlayerIdx == 3){
                this.currentPlayerIdx = 0;
            }
            else {
                this.currentPlayerIdx++;
            }
        }
        // this.ui.setActivePlayer(this.currentPlayerIdx);
        this.gui.setActivePlayer(this.currentPlayerIdx);
        this.turn++;
    }

    /**
     * A method for checking a move made by a player.
     *
     * @param playerIdx the index of the player who makes the move
     * @param cardIdx   the list of the indices of the cards selected by the player
     */

    
    public void checkMove(int playerIdx, int[] cardIdx) {
        CardGamePlayer m = this.playerList.get(playerIdx);
        CardList a = m.play(cardIdx);
        Hand n = composeHand(m,a);
        int t = this.turn;

        // at the beginning of the game
        if(this.turn == 1){
            if(a != null && a.contains(new Card(0,2))  && n != null){
                moveplayer(m,n);

            }
            else {
                //System.out.println("Not a legal move!!!");
                // modified
                // only notice the current player if the move is illegal
                if(client.getPlayerID() == currentPlayerIdx){
                    this.gui.printMsg("Not a legal move!!!\n");
                }
            }
        }
        // during the game
        else{
            Hand lst = handsOnTable.get(handsOnTable.size()-1);

            // pass
            if(a == null && !lst.getPlayer().getName().equals(m.getName())){
                moveplayer(m,n);
                //System.out.println("{Pass}");
                this.gui.printMsg("{Pass}\n");

            }
            // current player beats the last or played the last hand
            else if (n != null && (n.beats(lst) || lst.getPlayer().getName().equals(m.getName()))){
                moveplayer(m,n);

            }
            else{
                //System.out.println("Not a legal move!!!");
                // modified
                // only notice the current player if the move is illegal
                if(client.getPlayerID() == currentPlayerIdx){
                    this.gui.printMsg("Not a legal move!!!\n");
                }
            }
        }
        int o = this.turn;
        // added
        if(o != t){ // if go to the next player
            this.gui.promptActivePlayer();
        }
        this.gui.repaint();
    }
	
			
		
		
		
	/**
	 * a method for checking if the game ends.
	 */
	
	//a method for checking if the game ends.
	boolean endOfGame() {
		for (int i=0; i<playerList.size();i++) {
			if(playerList.get(i).getNumOfCards()==0) {
				return true;
			}
		}
		return false;
	}

	/**
	 * a method for starting a Big Two card game. 
	 * @param args not in use 
	 */
	public static void main(String[] args) {
		BigTwo game = new BigTwo();
        BigTwoClient client= new BigTwoClient(game, game.gui);
        client.go();
		
		
		
		// TODO Auto-generated method stub
		
	}
	
	
	
	/**
	 * a method for returning a valid hand from the specified list of cards of the player.
	 * @param playerIdx specified index of player
	 * @param cardIdx the list of indices of cards
	 */
	Hand composeHand(CardGamePlayer player, CardList cards) {
		
		
		if (cards==null) {
			return null;
		}
		
		if (cards.size()==5) {
			Hand result= new StraightFlush( player,  cards);
			if (result.isValid()) {
				return result;
			}
		}
		if (cards.size()==5) {
			Hand result= new Quad( player,  cards);
			if (result.isValid()) {
				return result;
			}
		}
		if (cards.size()==5) {
			Hand result= new FullHouse( player,  cards);
			if (result.isValid()) {
				return result;
			}
		}
		if (cards.size()==5) {
			Hand result= new Flush( player,  cards);
			if (result.isValid()) {
				return result;
			}
		}
		if (cards.size()==5) {
			Hand result= new Straight( player,  cards);
			if (result.isValid()) {
				return result;
			}
		}
		if (cards.size()==3) {
			Hand result= new Triple( player,  cards);
			if (result.isValid()) {
				return result;
			}
		}
		if (cards.size()==2) {
			Hand result= new Pair( player,  cards);
			if (result.isValid()) {
				return result;
			}
		}
		if (cards.size()==1) {
			Hand result= new Single( player,  cards);
				return result;	
		}
		return null;
	}
	
	private int numOfPlayers;
	/**
	 * a method for getting the number of players.
	 * @return the numOfPlayers
	 */
	public int getNumOfPlayers() {
		return numOfPlayers;
	}

	/**
	 * set the numOfPlayers
	 * @param numOfPlayers the numOfPlayers to set
	 */
	public void setNumOfPlayers(int numOfPlayers) {
		this.numOfPlayers = numOfPlayers;
	}

	/**
	 * a method for retrieving the deck of cards being used.
	 * @return the deck
	 */
	public Deck getDeck() {
		return deck;
	}

	/**
	 * set the deck
	 * @param deck the deck to set
	 */
	public void setDeck(Deck deck) {
		this.deck = deck;
	}

	/**
	 * a method for retrieving the list of players.
	 * @return the playerList
	 */
	public ArrayList<CardGamePlayer> getPlayerList() {
		return playerList;
	}

	/**
	 * set the playerList 
	 * @param playerList the playerList to set
	 */
	public void setPlayerList(ArrayList<CardGamePlayer> playerList) {
		this.playerList = playerList;
	}

	/**
	 * a method for retrieving the list of hands played on the ui.
	 * @return the handsOnTable
	 */
	public ArrayList<Hand> getHandsOnTable() {
		return handsOnTable;
	}

	/**
	 * set the handsOnTable
	 * @param handsOnTable the handsOnTable to set
	 */
	public void sethandsOnTable(ArrayList<Hand> handsOnTable) {
		this.handsOnTable = handsOnTable;
	}


	/**
	 * @return the numOfPlayer
	 */
	public int getNumOfPlayer() {
		return numOfPlayer;
	}


	/**
	 * @param numOfPlayer the numOfPlayer to set
	 */
	public void setNumOfPlayer(int numOfPlayer) {
		this.numOfPlayer = numOfPlayer;
	}



}

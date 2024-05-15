
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.DefaultCaret;

/**
 * The BigTwoGUI class implements the CardGameTable interface. 
 * It is used to build a GUI for the Big Two card game and handle all user actions.
 * 
 * @author apple
 */
public class BigTwoGUI implements CardGameUI{
	
	
	
	/*
	 * A card game associated with this table.
	 */
	private BigTwo game;
	
	private BigTwoClient client;
	
	/*
	 * A boolean array indicating which cards are being selected.
	 */
	private boolean[] selected;
	
	/*
	 * An integer specifying the index of the active player.
	 */
	private int activePlayer;
	
	/*
	 * The main window of the application.
	 */
	private JFrame frame= new JFrame();
	//private JMenuBar M1 = new JMenuBar();
	
	/*
	 * A panel for showing the cards of each player and the cards played on the table.
	 */
	private JPanel bigTwoPanel;
	
	/*
	 * A “Play” button for the active player to play the selected cards.
	 */
	private JButton playButton;
	
	/*
	 * A “Pass” button for the active player to pass his/her turn to the next player.
	 */
	private JButton passButton;
	
	/*
	 * A text area for showing the current game status as well as end of game messages.
	 */
	private JTextArea msgArea;
	
	/*
	 * A text area for showing the chat messages between the players.
	 */
	private JTextArea chatArea;
	
	/*
	 * A text field to type out messages to send to other players.
	 */
	private JTextField chatTypeArea;
	
	/*
	 * A 2D array storing the images for the faces of the cards.
	 */
	private Image[][] cardImages;
	
	/*
	 * An image for the backs of the cards.
	 */
	private Image cardBackImage;
	
	/*
	 * An array storing the images for the avatars.
	 */
	private Image[] avatars;
	
	/**
	 * getter to getActivePlayer.
	 * @return index of getActivePlayer
	 */
	public int getActivePlayer(){
		return activePlayer;
	}
	/**
	 * A private method to loadImages.
	 */
	private void loadImages() {
		avatars = new Image[4];
		avatars[0] = new ImageIcon("v5.png").getImage();
		avatars[1] = new ImageIcon("v7.png").getImage();
		avatars[2] = new ImageIcon("v6.png").getImage();
		avatars[3] = new ImageIcon("v1.png").getImage();
		
		cardBackImage = new ImageIcon("cards/b.gif").getImage();
		
		
		
		char[] rank = {'a', '2', '3', '4', '5', '6', '7', '8', '9', 't', 'j', 'q', 'k'};
		char[] suit = {'d','c','h','s'};
		
		
		cardImages = new Image[4][13];
		for(int i=0;i<4;i++) {
		for(int j=0;j<13;j++){
			
				String location = new String();
				location="cards/" + rank[j] + suit[i] + ".gif";
				cardImages[i][j] = new ImageIcon(location).getImage();
			}
		}
	}
	
	/**
	 * A private method to setup the GUI.
	 */
	public void go() {
		
		//initializing the frame
		//frame = new JFrame();
		
		frame.setResizable(false);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Big Two Game");
		
		// menu setting with game message
		JMenuBar M1 = new JMenuBar();
		frame.setJMenuBar(M1); 
		JMenu m1 = new JMenu("Game");
		M1.add(m1);
		JMenu m2= new JMenu("Message");
		M1.add(m2);
		
		
		JMenuItem quit = new JMenuItem("Quit");
		quit.addActionListener(new QuitMenuItemListener());
		m1.add(quit);
		
		JMenuItem Connect = new JMenuItem("Connect");
		Connect.addActionListener(new RestartMenuItemListener());
		m1.add(Connect);
		
		
		//add menu option for message bar
		JMenuItem emptyInfo = new JMenuItem("Clear Information Board");
		emptyInfo.addActionListener(new ClearInfoBoardListener());
		m2.add(emptyInfo);
		
		JMenuItem emptyChat = new JMenuItem("Clear Chat Screen");
		emptyChat.addActionListener(new ClearChatListener());
		m2.add(emptyChat);
		
	    // create a panel for game messages and chat
	    JPanel mesChat = new JPanel();
	    mesChat.setLayout(new BoxLayout(mesChat, BoxLayout.PAGE_AXIS));
	    
	    	//create message area
	    msgArea = new JTextArea(30,40);
	    msgArea.setEnabled(false);
	    DefaultCaret caret = (DefaultCaret) msgArea.getCaret();
	    caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
	    JScrollPane bar = new JScrollPane(msgArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

	    	//create chat area
	    chatArea = new JTextArea(25,40);
	    chatArea.setEnabled(false);
	    DefaultCaret caretChat = (DefaultCaret) chatArea.getCaret();
	    caretChat.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
	    JScrollPane barChat = new JScrollPane(chatArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	    	
	    	//---------------------------------
	    	JPanel chat = new JPanel();
	    	chat.setLayout(new FlowLayout());
	    	chat.add(new JLabel("Message:"));
	    
	    	chatTypeArea = new TextField(30);
	    	chatTypeArea.getDocument().putProperty("filterNewlines", Boolean.TRUE);
	    	chatTypeArea.setPreferredSize(new Dimension(200, 30));
	    	chat.add(chatTypeArea);
	    
	    
	    mesChat.add(bar);
	    mesChat.add(barChat);
	    mesChat.add(chat);
	   
	    
	    // making a panel for "Play" and "Pass"
	    JPanel buttons = new JPanel();
	    playButton = new JButton("Play");
	    playButton.addActionListener(new PlayButtonListener());
	    passButton = new JButton("Pass");
	    passButton.addActionListener(new PassButtonListener());
	    buttons.add(playButton);
	    buttons.add(passButton);
	    
	    
	    buttons.setEnabled(true);
		playButton.setEnabled(true);
		passButton.setEnabled(true);
	    
	    //A panel for showing cards.
	    bigTwoPanel = new BigTwoPanel();
		bigTwoPanel.setPreferredSize(new Dimension(800,800));
		
		frame.add(mesChat, BorderLayout.EAST);
	    frame.add(bigTwoPanel,BorderLayout.WEST);
	    frame.add(buttons, BorderLayout.SOUTH);
	    
		frame.setSize(1250,800);
	    frame.setVisible(true);
	    
	   
	}
	
	/**
	 * A constructor for creating a BigTwoTable. The parameter game is a reference to a card game associates with this table.
	 * 
	 * @param game A Card Game of BigTwo type to play through this GUI.
	 */
	public BigTwoGUI(BigTwo game) {
		
		this.game = game;
		this.selected = new boolean[13];
		//setActivePlayer(game.getCurrentPlayerIdx()); 
		loadImages();
		//this.go();	
	}
	
	/**
	 * A setter method that sets the index of the active player (i.e., the current player).
	 * 
	 * @param activePlayer an int value representing the index of the active player
	 */
	public void setActivePlayer(int activePlayer) {
		this.activePlayer = activePlayer;
	}
	
	/**
	 * Returns an array of indices of the cards selected.
	 * 
	 * @return an array of indices of the cards selected by the user.
	 */
	public int[] getSelected() {
		
		int len = 0;
		int[] result;
		int index = 0;
		
		for(int i=0;i<selected.length;i++)
		{
			if(selected[i]==true) {
				len++;
			}
		}
		result = new int[len];
		
		for(int i=0;i<selected.length;i++) {
			if(selected[i]==true) {
				result[index] = i;
				index+=1;
			}
		}
		if(len==0) {
			return null;
		}
		return result;	
	}
	
	/**
	 * A method that resets the list of selected cards to an empty list.
	 */
	public void resetSelected() {
		for(int i=0;i<selected.length;i++) {
			selected[i] = false;
		}
	}
	
	/**
	 * A method that repaints the GUI.
	 */
	public void repaint() {
		//resetSelected();
		frame.repaint();
		//resetSelected();
	}
	
	/**
	 * A method that prints the specified string to the message area of the card game table.
	 * 
	 * @param msg the string to be printed to the message area of the card game table.
	 */
	public void printMsg(String msg) {
		
		//msgArea.append(game.getPlayerList().get(getActivePlayer()).getName()+": ");
		msgArea.append(msg+"\n");
	}
	
	/**
	 * A method that clears the message area of the card game table.
	 */
	public void clearMsgArea() {
		msgArea.setText("");
	}
	/**
	 * A method that prints the specified string to the chat message area of the card game table.
	 * 
	 * @param msg the string to be printed to the chat message area of the card game table.
	 */
	public void printChatMsg(String msg) {
		chatArea.append(game.getPlayerList().get(getActivePlayer()).getName()+": ");
		chatArea.append(msg+"\n");
	}
	
	/**
	 * Clears the message area of the card game table.
	 */
	public void clearChatMsgArea() {
		chatArea.setText("");
	}

	/**
	 * A method that resets the GUI.
	 */
	public void reset() {
		this.resetSelected();
		this.clearMsgArea();
		this.enable();
	}
	
	/**
	 * A method that enables user interactions.
	 */
	public void enable() {
		playButton.setEnabled(true);
		passButton.setEnabled(true);
		bigTwoPanel.setEnabled(true);
	}
	
	/**
	 * A method that disables user interactions.
	 */
	public void disable() {
		playButton.setEnabled(false);
		passButton.setEnabled(false);
		bigTwoPanel.setEnabled(false);
	}
	/**
     * a method to show the game result
     */

    public void endgame(){
		
		//gui.setActivePlayer(-1);
		//gui.repaint();
    	disable();
		String message = "Game ends.\n";
		
		//check who wins and how many cards the other players have
		for(int i = 0; i < game.getPlayerList().size();i++)
		{
				if(game.getPlayerList().get(i).getCardsInHand().size() == 0)
				{
					message+="Player " + i + " wins the game.\n"; 
				}
			
			else
			{
				message+="Player " + i + " has " + game.getPlayerList().get(i).getCardsInHand().size() + " cards in hand.\n"; // list the number of cards left in the other players' hand
			}
		}
		
		for (int i=0; i<4; ++i)
        {
			game.getPlayerList().get(i).removeAllCards();
        }
		JOptionPane.showMessageDialog(null, message);
		CardGameMessage ready = new CardGameMessage(CardGameMessage.READY,-1,null);
		this.getClient().sendMessage(ready);
	}
	
	/**
	 * An inner class that extends the JPanel class and implements the MouseListener interface. 
	 * Overrides the paintComponent() method inherited from the JPanel class to draw the card game table. 
	 * Implements the mouseClicked() method from the MouseListener interface to handle mouse click events.
	 * 
	 * @author apple
	 */
	class BigTwoPanel extends JPanel implements MouseListener{
		
		// private variables for storing variables to assign positions and for checking clicks as well
		
		private int namex = 20;
		private int namey = 20;
		private int avatarx = 20;
		private int avatary = 25;
		private int downCardy = 43;
		private int upCardY = 23;
		private int cardx = 155;
		private int cardw = 39;
		private int verticalplus = 140;
		
		/**
		 * A private method to get the suit of a particular card of a particular player
		 */
		private int getSuitofPlayer(int Player, int Suit) {
			return game.getPlayerList().get(Player).getCardsInHand().getCard(Suit).getSuit();
		}
		
		/*
		 *  A private method to get the rank of a particular card of a particular player
		 */
		private int getRankofPlayer(int Player, int Rank) {
			return game.getPlayerList().get(Player).getCardsInHand().getCard(Rank).getRank();
		}
		
		/**
		 * BigTwoPanel default constructor which adds the Mouse Listener and sets background of the card table.
		 */
		public BigTwoPanel() {
			this.addMouseListener(this);
		}
		
		/**
		 * paint name, avatar, cards
		 * 
		 * @param g Provided by system to allow drawing.
		 */
		public void paintComponent(Graphics g)
		{
			// setting background color and text color
			super.paintComponent(g);
			this.setBackground(Color.GREEN.darker());
			g.setColor(Color.BLACK);
			
			//paint cards and avatars
				for (int plyIdx=0; plyIdx<4;plyIdx++) {
				//------------------------
				g.setColor(Color.WHITE);
				g.drawString(game.getPlayerList().get(plyIdx).getName(), namex , namey + 140*plyIdx); 
				
				//paint avatars
				Image img=avatars[plyIdx];
				int x=avatarx;
				int y=avatary+ 135*plyIdx;
				g.drawImage(img, x,y, this);
			    

			    //show card for active player
			    if (game.getClient().getPlayerID() == plyIdx) 
			    {
			    	for (int i = 0; i < game.getPlayerList().get(plyIdx).getNumOfCards(); i++) 
			    	{
			    		int suit = getSuitofPlayer(plyIdx, i);
			    		int rank = getRankofPlayer(plyIdx, i);
			    		Image Cardimg=cardImages[suit][rank];
						int Cardx=cardx+cardw*i;
						int CardUpy=upCardY+verticalplus*plyIdx;
						int CardDowny=downCardy+verticalplus*plyIdx;
						
			    		//paint card upward if selected
			    		if (selected[i])
			    		{
			    			//printChatMsg("selected: " + i);
			    			
			    			g.drawImage(Cardimg, Cardx, CardUpy , this);
			    		}	
			    		// else paint downwards
			    		else
			    		{
			    			g.drawImage(Cardimg,Cardx, CardDowny, this);
			    		}		
			    	}
			    } 
			    //cards not shown if player is not active
			    else
			    {
			    	int Cardnum=game.getPlayerList().get(plyIdx).getCardsInHand().size();
			    	for (int i = 0; i < Cardnum; i++)
			    	{	
						int Cardx=cardx+cardw*i;
						int CardDowny=downCardy+verticalplus*plyIdx;
						
			    		g.drawImage(cardBackImage, Cardx, CardDowny, this);
			    	}
			    }
			    
			    
			    
			}
		    
			//show last hand
		    
		     
		    //checking if  last hands are empty or not
		    if (game.getHandsOnTable().isEmpty()==false)
		    {
		    	int sizeofallHands = game.getHandsOnTable().size();
		    	Hand lasthand = game.getHandsOnTable().get(sizeofallHands - 1);
		    	g.drawString("Played by " + lasthand.getPlayer().getName(), 10,580 );
		    	for (int i = 0; i < lasthand.size(); i++)
	    		{
		    		int suit = lasthand.getCard(i).getSuit();
		    		int rank = lasthand.getCard(i).getRank();
	    			g.drawImage(cardImages[suit][rank], 158 + 39*i, 590, this);
	    		}
	    	
		    }
		    
		}
		
		/**
		 * A method used to catch all the mouse click events and perform events/functions accordingly.
		 * It overrides the MouseClicked method of JPanel.
		 * 
		 * @param e This is a MouseEvent object which has been used to get the coordinates of the mouseClick
		 */
		@Override
		public void mouseReleased(MouseEvent e) {
			
			if (client.getPlayerID() != activePlayer){
                return;
            }
			
			if(activePlayer == game.getClient().getPlayerID()) {
				boolean mark = false; 
				int numberofCards = game.getPlayerList().get(activePlayer).getNumOfCards();
				int cardIdx = numberofCards-1;
				int x1=cardx+cardIdx*39;
				int x2=cardx+cardIdx*39+73;
				int CardUpy1=upCardY + verticalplus*activePlayer;
				int CardUpy2=upCardY + verticalplus*activePlayer+97;
				int CardDowny1=downCardy + verticalplus*activePlayer;
				int CardDowny2=downCardy + verticalplus*activePlayer+97;
				
				if ( e.getX() <= (x2)&&e.getX() >= (x1) ) 
				{
					if(!selected[cardIdx] && e.getY() >= (CardDowny1) && e.getY() <= (CardDowny2))
					{
						selected[cardIdx] = true;
						mark = true;
						//printChatMsg("select: " + cardIdx);
					} 
					else if (selected[cardIdx] && e.getY() >= (CardUpy1) && e.getY() <= (CardUpy2))
					{ 
						selected[cardIdx] = false;
						mark = true;
						//printChatMsg("deselect: " + cardIdx);
					}
				}
				for (cardIdx = numberofCards-2; cardIdx >= 0 && !mark; cardIdx--) 
				{
					x1=cardx+cardIdx*39;
					x2=cardx+cardIdx*39+39;
					int x3=cardx+cardIdx*cardw+73;
					CardUpy1=upCardY + verticalplus*activePlayer;
					CardUpy2=upCardY + verticalplus*activePlayer+97;
					CardDowny1=downCardy + verticalplus*activePlayer;
				    CardDowny2=downCardy + verticalplus*activePlayer+97;
					
					if (e.getX() >= (x1) && e.getX() <= (x2))
					{
						if(!selected[cardIdx] && e.getY() >= (CardDowny1) && e.getY() <= (CardDowny2))
						{
							selected[cardIdx] = true;
							mark = true;
						} 
						else if (selected[cardIdx] && e.getY() >= (CardUpy1) && e.getY() <= (CardUpy2))
						{
							selected[cardIdx] = false;
							mark = true;
						}
					}
					else if (e.getX() >= (x2) && e.getX() <= (x3) && e.getY() >= (CardDowny1) && e.getY() <= (CardDowny2)) 
					{
						if (selected[cardIdx+1] && !selected[cardIdx]) 
						{
							selected[cardIdx] = true;
							mark = true;
						}
					}
					else if (e.getX() >= (x2) && e.getX() <= (x3) && e.getY() >= (CardUpy1) && e.getY() <= (CardUpy2))
					{
						if (!selected[cardIdx+1] && selected[cardIdx])
						{
							selected[cardIdx] = false;
							mark = true;
						}
					}
				}
			}
			repaint();	
			}
		//}

		@Override
		public void mousePressed(MouseEvent e) {
		}

		@Override
		public void mouseClicked(MouseEvent e) {
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {
		}
	}
	
	/**
	 * An inner class that implements the ActionListener interface. 
	 * Implements the actionPerformed() method from the ActionListener interface to handle button-click events for the “Play” button. 
	 * When the “Play” button is clicked, it calls the makeMove() method of CardGame object to make a move.
	 * 
	 * @author apple
	 */
	class PlayButtonListener implements ActionListener{
		
		/**
		 * The function is overridden from the ActionListener Interface 
		 * and is used to perform the requisite function when the button is clicked.
		 * 
		 * @param e This is a ActionEvent object to detect if some user interaction was performed on the given object
		 */
		@Override
		public void actionPerformed(ActionEvent e) 
		{//cards shown if players is active
			//if(game.getClient().getPlayerID()==game.getCurrentPlayerIdx()) {
				
			
				if (getSelected() == null)
				{
					printMsg("Select cards to play.");
				}
				else {
					game.makeMove(activePlayer, getSelected());
				}
			
			repaint();
			//resetSelected();
			//repaint();
			
		//}
		}
		
	}
	
	/**
	 * This inner class implements the ActionListener interface and is used to detect the clicks on the passButton 
	 * and call the makeMove function based on the click.
	 *
	 * @author apple
	 **/
	class PassButtonListener implements ActionListener{
		
		/**
		 * The function is overridden from the ActionListener Interface 
		 * and is used to perform the requisite function when the button is clicked.
		 * 
		 * @param e This is a ActionEvent object to detect if some user interaction was performed on the given object.
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			//if(game.getClient().getPlayerID()==game.getCurrentPlayerIdx()) {
			game.makeMove(activePlayer, null);
			//resetSelected();
			repaint();
		}	
		
		}
	
	

	
	/**
	 * This inner class implements the actionListener interface for the Quit Menu Item in the JMenuBar to quit the game on click. 
	 * 
	 * @author apple
	 */
	class QuitMenuItemListener implements ActionListener{

		/**
		 * The function overrides the ActionPerformed function in ActionListener interface to detect 
		 * the user interaction on the object and carry out necessary functions.
		 *  
		 *  @param e This is a ActionEvent object to detect if some user interaction was performed on the given object.
		 */
		@Override
		public void actionPerformed(ActionEvent e) {

			System.exit(0);	
		}
	}
	
	/**
	 * This inner class implements the actionListener interface for the Restart Menu Item in the JMenuBar to restart the game on click.
	 * 
	 * @author apple
	 */
	class ConnectMenuItemListener implements ActionListener{

		/**
		 * The function overrides the ActionPerformed function in ActionListener interface to detect 
		 * the user interaction on the object and carry out necessary functions.
		 * 
		 * @param e This is a ActionEvent object to detect if some user interaction was performed on the given object.
		 */
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			//if(game.getClient().isConnected()==false) {
				//reset();
				client.connect();
			//}
		}
	}
	/**
	 * This inner class implements the actionListener interface for the Quit Menu Item in the JMenuBar to quit the game on click. 
	 * 
	 * @author apple
	 */
	class RestartMenuItemListener implements ActionListener{

		/**
		 * The function overrides the ActionPerformed function in ActionListener interface to detect 
		 * the user interaction on the object and carry out necessary functions.
		 *  
		 *  @param e This is a ActionEvent object to detect if some user interaction was performed on the given object.
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			
			// create and shuffle a deck of cards
			
			Deck deck= new BigTwoDeck();
			deck.shuffle();
			game.getGui().reset();
			// start the game with the deck of cards.
			game.start(deck);
		}
	}
	/**
	 * This inner class implements the actionListener interface for the Clear Information Board item in the JMenuBar to quit the game on click. 
	 * 
	 * @author apple
	 */
	class ClearInfoBoardListener implements ActionListener{
		
		/**
		 * The function overrides the ActionPerformed function in ActionListener interface to detect 
		 * the user interaction on the object and carry out necessary functions.
		 *  
		 * @param e This is a ActionEvent object to detect if some user interaction was performed on the given object.
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			clearMsgArea();
			
		}
	}
	
	/**
	 * This inner class implements the actionListener interface for the Clear Chat Board item in the JMenuBar to quit the game on click. 
	 * 
	 * @author apple
	 */
	class ClearChatListener implements ActionListener{
		
		/**
		 * The function overrides the ActionPerformed function in ActionListener interface to detect 
		 * the user interaction on the object and carry out necessary functions.
		 *  
		 * @param e This is a ActionEvent object to detect if some user interaction was performed on the given object.
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			clearChatMsgArea();
		}
	}
	
	/**
	 * This inner class extends the JTextField and implements the actionListener interface for the message field in the game.
	 * 
	 * @author apple
	 *
	 */
	class TextField extends JTextField implements ActionListener{

		/*
		 * The contructor for this class.
		 */
		public TextField(int i) {
			super(i);
			addActionListener(this);
		}
		
		/**
		 * The function overrides the ActionPerformed function in ActionListener interface to detect 
		 * the user interaction on the object and carry out necessary functions.
		 *  
		 * @param e This is a ActionEvent object to detect if some user interaction was performed on the given object.
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			String chatMsg = getText();
			CardGameMessage message = new CardGameMessage(CardGameMessage.MSG,-1,chatMsg);
			game.getClient().sendMessage(message);
			this.setText("");
			repaint();
		// System.out.print(msg);
		}
	}

	@Override
	public void promptActivePlayer() {
		if(!this.game.endOfGame()){
            printMsg(game.getPlayerList().get(activePlayer).getName() + "'s turn: \n");
        }
		repaint();
		resetSelected();
	}
	
	/**
	 * @return the client
	 */
	public BigTwoClient getClient() {
		return this.client;
	}
	/**
	 * @param client the client to set
	 */
	public void setClient(BigTwoClient client) {
		this.client = client;
	}
		
}
				

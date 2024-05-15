import java.io.*;
import java.net.*;
//import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 * The BigTwoClient class implements the CardGame interface and NetworkGame interface. 
 * It is used to model a Big Two card game that supports 4 players playing over the internet.
 * 
 * @author apple
 *
 */
public class BigTwoClient implements NetworkGame{
	
	/*
	 * An integer specifying the playerID (i.e., index) of the local player.
	 */
	private int playerID;
	
	/*
	 * A string specifying the name of the local player.
	 */
	private String playerName;
	
	/*
	 * A string specifying the IP address of the game server.
	 */
	private String serverIP;
	
	/*
	 * An integer specifying the TCP port of the game server.
	 */
	private int serverPort;
	
	/*
	 * A socket connection to the game server.
	 */
	private Socket sock;
	
	/*
	 * An ObjectOutputStream for sending messages to the server.
	 */
	private ObjectOutputStream oos;
	
	
	/*
	 * An int type specifying the index of the active player.
	 */
	// private int currentIdx; 
	
	/*
	 * A gui object which is provides the necessary GUI.
	 */
	private BigTwoGUI gui; 
	
	/*
	 * A gui object which is provides the necessary GUI.
	 */
	private BigTwo game;
	
	
	
	/**
	 *  A constructor for creating the BigTwo card game. 
	 */
	public BigTwoClient(BigTwo game,BigTwoGUI gui)
	{
		/*playerList = new ArrayList<CardGamePlayer> ();
		for(int i = 0; i < 4;i++)
		{
			CardGamePlayer player = new CardGamePlayer();
			playerList.add(player);
		}
		
		numOfPlayers = playerList.size();
		handsOnTable = new ArrayList<Hand>();
		//gui = new BigTwoGUI(this);
		 * 
		 */
		this.gui=gui;
		this.game=game;
		this.game.setClient(this);
        this.gui.setClient(this);
        
		setServerIP("127.0.0.1");
		setServerPort(2396);
		//this.connect();
		//gui.disable();
		//gui.repaint();
		//currentIdx = -1;
	}
	
	/**
     * launch the game
     */

    public void go() {

        this.gui.go();
        this.gui.disable();
        String name = JOptionPane.showInputDialog("Your name");
        // if player inputs nothing
        if(name == null || name.equals("") ){
            this.setPlayerName("Anonymous Player");
        }
        else{
            this.setPlayerName(name);
        }
        String ip = JOptionPane.showInputDialog("Server IP");
        // if player inputs nothing
        if(ip != null && !ip.equals("") ){
            this.setServerIP(ip);
        }

        String port = JOptionPane.showInputDialog("Server Port");
        // if player inputs nothing
        if(port != null && !port.equals("") ){
            this.setServerPort(Integer.valueOf(port));
        }



        this.connect();

    }


	@Override
	public int getPlayerID() {
		return playerID;
	}
	
	@Override
	public void setPlayerID(int playerID) {
		this.playerID = playerID;
	}
	
	@Override
	public String getPlayerName() {
		return playerName;
	}
	
	@Override
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
	
	@Override
	public String getServerIP() {
		return serverIP;
	}
	
	@Override
	public void setServerIP(String serverIP) {
		this.serverIP = serverIP;
	}
	
	@Override
	public int getServerPort() {
		return serverPort;
	}
	
	@Override
	public void setServerPort(int serverPort) {
		this.serverPort = serverPort;
	}
	
	/**
	 * A function which tells if the client is connected to the server or not.
	 * 
	 * @return boolean true if client is connected to server, otherwise false
	 */
	public boolean isConnected() {
		if(sock.isClosed()) {
			return false;
		}
		return true;
	}
	
	@Override
	public void connect() {
		try {
			sock = new Socket(getServerIP(), getServerPort());
			oos = new ObjectOutputStream(sock.getOutputStream());
			
			
			//CardGameMessage joinGame = new CardGameMessage(CardGameMessage.JOIN, -1, this.getPlayerName());
			//sendMessage(joinGame);
			
			//CardGameMessage readyGame = new CardGameMessage(CardGameMessage.READY, -1, null);
			//sendMessage(readyGame);
			
			//this.gui.repaint();
					
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}	
		
		// start a new thread to receive messages from the server
        Thread serverThread = new Thread(new ServerHandler());
        serverThread.start();
		
		
		
	}

	@Override
	public synchronized void parseMessage(GameMessage message) {
		if(message.getType()==CardGameMessage.FULL) {
			this.gui.printMsg("The server is full and cannot be joined!");
			try {
				sock.close();
			}
			catch(IOException e) {
				e.printStackTrace();
			}
		}
		else if(message.getType()==CardGameMessage.JOIN) {
			this.game.getPlayerList().set(message.getPlayerID(),new CardGamePlayer((String) message.getData()));
			this.gui.repaint();
			//ArrayList<CardGamePlayer> list=  this.game.getPlayerList();
			//list[1];
			//this.gui.printMsg("Player " + this.game.getPlayerList().get(message.getPlayerID()).getName() + " joined the game!");
			// set ready if just joined
            if (message.getPlayerID() == getPlayerID()) {
                sendMessage(new CardGameMessage(CardGameMessage.READY, -1, null));
            }
			
		}
		
		else if(message.getType()==CardGameMessage.MOVE) {
			this.game.checkMove(message.getPlayerID(),(int[])message.getData());
			// enable the current player
            if (getPlayerID() == this.game.getCurrentPlayerIdx()){
                this.gui.enable();
            }
            else {
                this.gui.disable();
            }
            if (game.endOfGame()) { // check if the game ends
                this.gui.endgame();
            }
			this.gui.repaint();
		}
		
		else if(message.getType()==CardGameMessage.MSG) {
			this.gui.printChatMsg((String)message.getData());
		}
		
		
		else if(message.getType()==CardGameMessage.PLAYER_LIST) {
			setPlayerID(message.getPlayerID());
			//gui.setActivePlayer(message.getPlayerID());
			for(int i=0;i<this.game.getNumOfPlayers();i++)	
			{
				if(((String[])message.getData())[i]!=null) {
					this.game.getPlayerList().get(i).setName(((String[])message.getData())[i]);
			  //System.out.print(this.game.getPlayerList().get(i));
				}
			}
			// send a JOIN message
            sendMessage(new CardGameMessage(CardGameMessage.JOIN, -1, getPlayerName()));
		}
		
		else if(message.getType()==CardGameMessage.QUIT) {
			gui.printMsg("Player " + message.getPlayerID() + " " + this.game.getPlayerList().get(message.getPlayerID()).getName() + " left the game.");
			this.game.getPlayerList().get(message.getPlayerID()).setName("");
			if(!this.game.endOfGame()) {
				gui.disable();
				CardGameMessage msg = new CardGameMessage(CardGameMessage.READY, -1, null);
				sendMessage(msg);
				for(int i=0;i<4;i++) {
					this.game.getPlayerList().get(i).removeAllCards();
				}
				this.gui.repaint();
			}
			this.gui.repaint();
		}
		
		
		else if(message.getType()==CardGameMessage.READY) {
			this.gui.printMsg("Player " + message.getPlayerID() + " is ready.");
		}
		else if(message.getType()==CardGameMessage.START) {
			this.game.setDeck(  (BigTwoDeck) message.getData());
			this.game.start(this.game.getDeck());
			this.gui.enable();
			this.gui.repaint();
		}
	}
	
	@Override
	public synchronized void sendMessage(GameMessage message) {
		try {
            // send a message to server
            oos.writeObject(new CardGameMessage(message.getType(),
                    message.getPlayerID(), message.getData()));
            oos.flush();}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * An inner class that implements the Runnable interface.
	 * 
	 * @author apple
	 *
	 */
	class ServerHandler implements Runnable{
        private ObjectInputStream oistream;// ObjectInputStream of the client
        
        /**
         * Creates and returns an instance of the ServerHandler class.
         */
        public ServerHandler() {
            try {
                oistream = new ObjectInputStream(sock.getInputStream());
            } catch (Exception ex) {
                System.out.println("Error in creating an ObjectInputStream for the client at "
                        + sock.getRemoteSocketAddress());
            }
        }

        // implementation of method from the Runnable interface
        public void run() {
            CardGameMessage message;
            try {
                
                while ((message = (CardGameMessage) oistream.readObject()) != null) {
                    parseMessage(message);
                } 
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }


	
}

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.text.DefaultCaret;

/**
 * The BigTwoTable class implements the CardGameTable interface. 
 * It is used to build a GUI for the Big Two card game and handle all user actions.
 * 
 * @author apple
 */
public class BigTwoUI {
	
	/*
	 * A card game associated with this table.
	 */
	private BigTwo game;
	
	/*
	 * A boolean array indicating which cards are being selected.
	 */
	private boolean[] selected;
	
	/*
	 * An integer specifying the index of the active player.
	 */
	private int activePlayer;
	
	
	private ArrayList<CardGamePlayer> playerList; // the list of players
	private ArrayList<Hand> handsOnTable; // the list of hands played on the
	
	
	public int getActivePlayer(){
		return activePlayer;
	}
	
	
	
	
	
	/**
	 * A constructor for creating a BigTwoTable. The parameter game is a reference to a card game associates with this table.
	 * 
	 * @param game A Card Game of BigTwo type to play through this GUI.
	 */
	public BigTwoUI(BigTwo game) {
		
		this.game = game;
		this.selected = new boolean[13];
		playerList = game.getPlayerList();
		handsOnTable = game.getHandsOnTable();
		//setActivePlayer(game.getCurrentPlayerIdx()); 
		
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
		
		int counter = 0;
		for(int i=0;i<selected.length;i++)
		{
			if(selected[i]==true) {
				counter++;
			}
		}
		int[] result;
		if(counter==0) {
			return null;
		}
		result = new int[counter];
		int counter2 = 0;
		for(int i=0;i<selected.length;i++) {
			if(selected[i]==true) {
				result[counter2] = i;
				counter2++;
			}
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
	}}




	

		

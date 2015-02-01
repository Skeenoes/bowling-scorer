package gui;


import javax.swing.table.AbstractTableModel;

import classes.Player;

import java.util.ArrayList;

public class PlayerTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	private String [] header = {"FRAME","1","2","3","4","5",
						"6","7","8","9","10","TOTAL SCORE"};
	private ArrayList<Player> playerList;
	
	public PlayerTableModel(ArrayList<Player> pList){
		playerList=pList;
	}
	
	@Override
	public int getColumnCount() {
		//player name + 10 scores + total score
		return 12;
	}

	@Override
	public int getRowCount() {
		return playerList.size() ;
	}
	public String getColumnName(int col){
		return header[col];
	}
	
	@Override
	public Object getValueAt(int row, int col) {
		
		Player player = playerList.get(row);
		
		if(col==0)
			return player.getPName();
		else if(col>=1 && col<=10)
			return player.frameList[col-1].getFrameScore();
		else if(col==11)
			return player.getTScore();
		
		return null;
	}
}

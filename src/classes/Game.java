package classes;

import java.awt.BorderLayout;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.text.DefaultCaret;

import gui.PlayerTableModel;

public class Game {

	private ArrayList <Player>players = new ArrayList<Player>();
	private int numberOfPlayers;
	private int playerCounter=0;
	private int frameCounter=0;

	private JFrame mainFrame;
	private JPanel textPane;
	private JPanel tablPane;
	private JScrollPane textScrollPane;
	private JScrollPane tableScrollPane;
	private JTextArea textArea;
	private JTable table;
	private PlayerTableModel pModel;
	
	public void playGame(){
			
		textArea.append("START GAME" + "\n");
		
		//main game loop
		while(frameCounter<10){
			
			textArea.append("FRAME " + (frameCounter+1) + "\n");
			
			//Cycle through each player
			for(int i =0;i<players.size();i++){			
				Player currentPlayer = players.get(i);
				Frame currentFrame = currentPlayer.frameList[frameCounter];
				String tempName = currentPlayer.getPName();
				
				//ball 1
				int currentBallScore = Integer.parseInt(enterScore(1,tempName,0));
				currentFrame.setBallOne(currentBallScore);
				
				if(currentBallScore==10){
					textArea.append(currentPlayer.getPName()+" scored a strike!" + "\n");
					currentFrame.setStatus("strike");	
				}else{
				//ball 2 - only run if a strike isn't scored
					int prevBallScore = currentBallScore;
					currentBallScore = Integer.parseInt(enterScore(2,tempName,prevBallScore));
				    //did player scored a spare?
					if((currentBallScore + prevBallScore) == 10){
						textArea.append(currentPlayer.getPName()+" scored a spare!" + "\n");
						currentFrame.setStatus("spare");
					}
					currentFrame.setBallTwo(currentBallScore);
					
					textArea.append(currentPlayer.getPName() + " BALL 1:" + 
									currentFrame.getBallOne() + " BALL 2:" + 
								    currentFrame.getBallTwo() + "\n");
				}
			
				//check for previous double strike
				if(currentPlayer.getPrevFrame().getStatus().equals("strike") && 
						currentPlayer.getLastFrame().getStatus().equals("strike")){
					currentPlayer.getPrevFrame().addBonusScore(currentFrame.getBallOne());
				}
							
				//add 2 ball bonus for strike
				if(currentPlayer.getLastFrame().getStatus().equals("strike")){
					currentPlayer.getLastFrame().addBonusScore(currentFrame.getBallOne()+currentFrame.getBallTwo());
					textArea.append("strike bonus added to previous frame" + "\n");
				}else if(currentPlayer.getLastFrame().getStatus().equals("spare")){
				//add 1 ball bonus for spare	
					currentPlayer.getLastFrame().addBonusScore(currentFrame.getBallOne());
					textArea.append("spare bonus added to previous frame" + "\n");
				}
					
				//add total current frame score
				currentFrame.setFrameScore();
				currentPlayer.updateTScore();
				
				//check for bonus round if player scored a strike in 10th frame
				if(frameCounter==9 && currentFrame.getStatus().equals("strike")){
					int bonus = playBonusRound(currentPlayer);
					currentPlayer.addEndBonus(bonus);
					currentPlayer.updateTScore();
				}
				
				//save previous frames for reference on subsequent frames
				currentPlayer.setPrevFrame(currentPlayer.getLastFrame());
				currentPlayer.setLastFrame(currentFrame);		
			}

			//update score table	
			pModel.fireTableDataChanged();
			
			//play next frame
			frameCounter++;		
		}//end main game loop
		
		textArea.append("GAME FINISHED" + "\n");		
	}
	
	//set up user interface window for inputting name
	private String EnterPlayerName(){
		String[] options = {"Enter"};
		JPanel panel = new JPanel();
		JLabel lbl = new JLabel("Enter player " + (playerCounter + 1) + "'s Name");
		JTextField txt = new JTextField(10);
		panel.add(lbl);
		panel.add(txt);
		String name; 
		
		int selectedOption = JOptionPane.showOptionDialog(null, panel, "Enter Player Names", 
										JOptionPane.NO_OPTION, JOptionPane.QUESTION_MESSAGE, 
										null, options , options[0]);	
		if(selectedOption==0){
			name = txt.getText();
			return name;
		}else{
			return "null";
		}
	}
	
	//opens score input window
	private String enterScore(int fNo,String pName,int prevScore){
		String[] scoreOptions = setScoreOptions(prevScore);
		String s = (String)JOptionPane.showInputDialog(null,"Enter ball " + fNo + " score",
				("Enter " + pName +  "'s Frame " + (frameCounter + 1) + " score"),JOptionPane.PLAIN_MESSAGE,
				null, scoreOptions,"0");	
		return s;
	}
	
	
	//handles the input of scores, prevents player from entering incorrect score on 2nd ball
	private String [] setScoreOptions(int inputScore){
		int tempScore = (10 - inputScore)+1;
		String [] scoreOptions = new String[tempScore]; 
		for(int i =0;i<tempScore;i++){
			scoreOptions[i] = Integer.toString(i);
		}
		return scoreOptions; 
	}
		
	//plays bonus round if player scores a strike on 10th frame
	private int playBonusRound(Player player){
		String[] scoreOptions = setScoreOptions(0);
		int bonusScore = 0;
		
		for(int i=0;i<2;i++){
			String s = (String)JOptionPane.showInputDialog(null,"Enter extra ball " + (i+1) + " score",
					player.getPName() + "'s bonus extra frame ",JOptionPane.PLAIN_MESSAGE,
					null, scoreOptions,"0");
			
			//check to see if player scored a strike in the 9th frame, 
			//if yes add the first bonus ball to 9th frame score
			if(player.getLastFrame().getStatus().equals("strike") && i<1)
				player.getLastFrame().addBonusScore(Integer.parseInt(s));		
			bonusScore = bonusScore + Integer.parseInt(s);
		}
		return bonusScore;
	}
	
	//ask how many players are playing
	public boolean setUpPlayers(){
		//select how many players are playing
		String [] playerList = {"2","3","4","5","6"};
		
		String playerString = (String)JOptionPane.showInputDialog(null,"Enter number of players",
														"Total Players",JOptionPane.PLAIN_MESSAGE,
														null, playerList,"1");
		//handle cancel button
		if(playerString==null){
			JOptionPane.showMessageDialog(null, "Game session cancelled");
			return false;
		}else{
			
			//instantiate number of players
			numberOfPlayers = Integer.parseInt(playerString);
			
			//use loop to instantiate all players and input each player's name
			while(playerCounter<numberOfPlayers){
				
				String nameString = EnterPlayerName();
				
				//error check to make sure user has written a name
				while(nameString.length()<=0){
					nameString = EnterPlayerName();
				}
				
				//error check to make sure user has written a name
				if(nameString.equals("null")){
					JOptionPane.showMessageDialog(null, "Game session cancelled");
					return false;
				}
				
				players.add(new Player(nameString,playerCounter+1));
				playerCounter++;						
			}	
			return true;		
		}	
	}                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                
	
	//set up the interface that the players see
	public void setUpScreen(){
		mainFrame = new JFrame();
		textPane = new JPanel();
		tablPane = new JPanel();
		textArea = new JTextArea(14,15);
		
		textArea.setEditable(false);
		DefaultCaret caret = (DefaultCaret)textArea.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		
		textScrollPane = new JScrollPane(textArea);
		textScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		textScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		
		pModel = new PlayerTableModel(players);
		table = new JTable(pModel);
		
		tableScrollPane = new JScrollPane(table);
		table.setFillsViewportHeight(true);
		
		mainFrame.setLayout(new BorderLayout());
		textPane.add(textScrollPane);
		tablPane.add(tableScrollPane);
		mainFrame.getContentPane().add(BorderLayout.WEST,textPane);
		mainFrame.getContentPane().add(BorderLayout.EAST,tablPane);
		mainFrame.setSize(700,300);
		mainFrame.setVisible(true);
		mainFrame.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);	
	}
}
package classes;

public class Player {
	public Frame[] frameList = new Frame[10];
	private String playerName;
	private int playerNumber;
	private Frame previousFrame;
	private Frame lastFrame;
	private int totalScore;
		
	public Player(String name,int playnum) {
		playerName = name;
		playerNumber = playnum;
		previousFrame = new Frame();
		lastFrame = new Frame();	
		for(int i =0;i<10;i++)
			frameList[i]=new Frame();
	}
	
	public String getPName(){
		return playerName;
	}
	
	public int getPNum(){
		return playerNumber;
	}
	
	public void setPrevFrame(Frame pFrame){
		previousFrame = pFrame;
	}
	
	public Frame getPrevFrame(){
		return previousFrame;
	}
	
	public void setLastFrame(Frame lFrame){
		lastFrame = lFrame;
	}
	
	public Frame getLastFrame(){
		return lastFrame;
	}
	
	public void updateTScore(){
		int tempNum=0;
		for (int i = 0; i < 10; i++) 
			tempNum = tempNum + frameList[i].getFrameScore();		
		totalScore = tempNum;
	}
	
	public int getTScore(){
		return 	totalScore;
	}
	
	public void addEndBonus(int score){
		frameList[9].addBonusScore(score);
	}
}

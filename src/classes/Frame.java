package classes;

public class Frame {

	private int ballOneScore;
	private int ballTwoScore;
	private int frameScore;
	private String bonusStatus;
	
	public Frame() {
		ballOneScore = 0;
		ballTwoScore =0;
		bonusStatus="none";
	}
	
	public int getBallOne(){
		return ballOneScore;
	}
	
	public int getBallTwo(){
		return ballTwoScore;
	}
	
	public int getFrameScore(){
		return frameScore;
	}
	
	public void setBallOne(int n){
		ballOneScore=n;
	}
	
	public void setBallTwo(int n){
		ballTwoScore=n;
	}
	
	public void setFrameScore(){
		frameScore = ballOneScore + ballTwoScore;
	}
	
	public String getStatus(){
		return bonusStatus;
	}
	
	public void setStatus(String status){
		bonusStatus=status;
	}
	
	public void addBonusScore(int addScore){
		frameScore = frameScore + addScore;
	}

}

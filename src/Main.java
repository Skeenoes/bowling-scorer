import classes.Game;

public class Main {

    //just a comment
    public static void main(String [] args){

        boolean isGameContinue;

        Game bowlingGame = new Game();

        isGameContinue = bowlingGame.setUpPlayers();
        if(isGameContinue==true){
            bowlingGame.setUpScreen();
            bowlingGame.playGame();
        }
    }
}

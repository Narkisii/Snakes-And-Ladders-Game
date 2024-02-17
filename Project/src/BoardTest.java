
import org.junit.Test;

import javafx.scene.paint.Color;
import model.Board;
import model.GameData;
import model.Ladder;
import model.Player;
import model.Snake;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import org.junit.Assert;

public class BoardTest {
	
    // This test will check if the game will end when it should to be
	 @Test
	    public void endGameTest() {
	        int numTilesInARow = 15; // As it is an "Easy" board, it's size is 7*7, we will move the player beyond the end tile to see result
	        Player player = new Player(1,"Yellow","ItayIsKing!","Hat");
	        GameData.getInstance().setDifficulty("Easy");//Set board difficulty
	        GameData.getInstance().addPlayer(player);//Add player to player list
			GameData.getInstance().init_board();
	        Board board = GameData.getInstance().getBoard(); 

	        // Move the player beyond the last tile
	        board.move(numTilesInARow * numTilesInARow + 1, player);
	        // Check if the game has ended
	        Assert.assertEquals(1, board.getGameEnd());
	    }
	 
	 @Test
	    public void snakeTest() {
	        
	        Player player = new Player(1,"Red","LiorIsKing!","Hat");
	        GameData.getInstance().addPlayer(player);//Add player to player list
			GameData.getInstance().init_board();
	        Snake snake = new Snake(11,5,Color.YELLOW); 
	        Board board = GameData.getInstance().getBoard(); 
	        board.getGameboard()[3][1].setSnake(snake);
	        player.addStep(1);
	        board.move(10, player);

	        // Check if player went down snake
	        Assert.assertEquals(5, player.getCurrentP());
	    }
	 @Test
	    public void ladderTest() {
	        
	        Player player = new Player(1,"Pink","NarkisIsQueen!","Hat");
	        GameData.getInstance().addPlayer(player);//Add player to player list
			GameData.getInstance().init_board();
	        Ladder ladder = new Ladder(5,11,1); 
	        Board board = GameData.getInstance().getBoard(); 
	        board.getGameboard()[4][0].setLadder(ladder);
	        player.addStep(1);
	        board.move(4, player);
	        
	        // Check if player went down snake
	        Assert.assertEquals(11, player.getCurrentP());
	    }
	 @Test
	    public void diceTest() {
	        
	        Player player = new Player(1,"Purple","LiaIsQueen!","Hat");
	        GameData.getInstance().setDifficulty("Easy");//Set board difficulty
	        GameData.getInstance().addPlayer(player);//Add player to player list
			GameData.getInstance().init_board();
	        Board board = GameData.getInstance().getBoard(); 
	        int diceResult = board.get_Dice_Result();//Roll the dice
	        
	        // Check if the dice roll is set between the given range
	        Assert.assertTrue(0 <= diceResult && diceResult <= 4 || 7 <= diceResult && diceResult <= 9);
	    }
	 
	 @Test
	    public void player_Move_Test() {
	        
	        Player player = new Player(1,"Green","ArielIsKing!","Hat");
	        GameData.getInstance().addPlayer(player);//Add player to player list
	        GameData.getInstance().setDifficulty("Easy");//Set board difficulty
			GameData.getInstance().init_board();
	        Board board = GameData.getInstance().getBoard(); 
	        int diceResult = board.get_Dice_Result();//Roll the dice
	        board.move(diceResult, player);//Move the player
	        
	        // Check if the player is on the correct tile
	        Assert.assertEquals(1+diceResult, player.getCurrentP());
	    }
	    	 
	 
}

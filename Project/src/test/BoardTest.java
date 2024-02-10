package test ;
import org.junit.Test;

import model.Board;
import model.Player;
import model.Snake;

import org.junit.Assert;

public class BoardTest {
	
    // This test will check if the game will end when it should to be
	 @Test
	    public void endGameTest() {
	        int numTilesInARow = 10; // that will create a board 10X10
	        Player player = new Player("Yellow","ItayIsKing!","Hat");
	        Player[] Players = new Player[1];
	        Players[0] = player;
	        Board board = new Board(numTilesInARow, Players); 

	        // Move the player beyond the last tile
	        board.move(numTilesInARow * numTilesInARow + 1, player);

	        // Check if the game has ended
	        Assert.assertEquals(1, board.getGameEnd());
	    }
	 

	 
	 @Test
	    public void snakeTest() {
	        int numTilesInARow = 10; // that will create a board 10X10
	        Player player = new Player("Yellow","ItayIsKing!","Hat");
	        Player[] Players = new Player[1];
	        Players[0] = player;
	        Board board = new Board(numTilesInARow, Players); 
	        Snake snake = new Snake(99,5,"Yellow"); 
	        board.getGameboard()[1][9].setSnake(snake);
	        board.move(98, player);

	        // Check if the game has ended
	        Assert.assertEquals(5, player.getCurrentP());
	    }
	 
	
	    
}

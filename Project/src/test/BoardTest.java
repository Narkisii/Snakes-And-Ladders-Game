package test ;
import org.junit.Test;

import model.Board;
import model.Player;

import org.junit.Assert;

public class BoardTest {
	
    // This test will check if the game will end when it sepose to be
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
}

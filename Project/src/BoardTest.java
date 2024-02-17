
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
	        int numTilesInARow = 10; // that will create a board 10X10
	        Player player = new Player(1,"Yellow","ItayIsKing!","Hat");
	        LinkedList<Player> Players = new LinkedList<Player>();
	        Players.add(player);
	        Board board = new Board(Players); 

	        // Move the player beyond the last tile
	        board.move(numTilesInARow * numTilesInARow + 1, player);

	        // Check if the game has ended
	        Assert.assertEquals(1, board.getGameEnd());
	    }
	 
	 
	 @Test
	    public void snakeTest() {
	        int numTilesInARow = 10; // that will create a board 10X10
	        
	        Player player = new Player(1,"Yellow","ItayIsKing!","Hat");
	        LinkedList<Player> Players = new LinkedList<Player>();
	        Players.add(player);
	        Board board = new Board(Players); 
	        Snake snake = new Snake(11,5,Color.YELLOW); 
	        GameData.getInstance().getplayer_list().add(player);
	        GameData.getInstance().setBoard(board);
	        board.getGameboard()[3][1].setSnake(snake);
	        player.addStep(1);
	        board.move(10, player);

	        // Check if player went down snake
	        Assert.assertEquals(5, player.getCurrentP());
	    }
	 @Test
	    public void ladderTest() {
	        int numTilesInARow = 10; // that will create a board 10X10
	        
	        Player player = new Player(1,"Yellow","ItayIsKing!","Hat");
	        LinkedList<Player> Players = new LinkedList<Player>();
	        Players.add(player);
	        Board board = new Board(Players); 
	        Ladder ladder = new Ladder(5,11,1); 
	        GameData.getInstance().getplayer_list().add(player);
	        GameData.getInstance().setBoard(board);
	        board.getGameboard()[4][0].setLadder(ladder);
	        player.addStep(1);
	        board.move(4, player);

	        // Check if player went down snake
	        Assert.assertEquals(11, player.getCurrentP());
	    }
	    
//	 test 3  - 	write diceResult if i get number in rolldice player will move the right number
	 
//	 test 4  - 	Create question, stand on question tile/rolldice question and answer correctly
//	 create question
//	 activate
	 
//   create function 
	 
	 
}

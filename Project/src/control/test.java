package control;

import model.Board;
import model.Player;

public class test {
    public static void main(String[] args) {
        // Create an instance of the class that contains the playerMovement method
        

        // Call the playerMovement method
        playerMovement();
    }
    
    public static void playerMovement() {
        int numTilesInARow = 10; // that will create a board 10X10
        Player player = new Player("Yellow","ItayIsKing!","Hat");
        Player[] Players = new Player[1];
        Players[0] = player;
      
        Board board = new Board(numTilesInARow, Players); 
     // Move the player beyond the last tile
        for(int i = 1 ; i < 100;i++)
        {
        	//System.out.println("playerInTile "+player.getCurrentP());
        	board.move( 1, player);
        }
        

    }
}

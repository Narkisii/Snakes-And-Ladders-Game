package control;

public class GameData {
    private static int numberOfPlayers = 3;

    public static int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public static void setNumberOfPlayers(int numberOfPlayers) {
        GameData.numberOfPlayers = numberOfPlayers;
    }
}

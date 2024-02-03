package control;

public class GameData {
    private static int numberOfPlayers = 1;
    private static String difficulty = "Easy";

    public static int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public static void setNumberOfPlayers(int numberOfPlayers) {
        GameData.numberOfPlayers = numberOfPlayers;
    }

    public static String getDifficulty() {
        return difficulty;
    }

    public static void setDifficulty(String difficulty) {
        GameData.difficulty = difficulty;
    }
}

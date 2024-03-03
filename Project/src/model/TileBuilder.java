package model;

/**
 * Builder class for creating a Tile object.
 */
public class TileBuilder {
    private int id;
    private int type;
    private Snake snake;
    private Ladder ladder;

    /**
     * Sets the ID of the tile.
     *
     * @param id The ID of the tile.
     * @return The TileBuilder instance.
     */
    public TileBuilder setId(int id) {
        this.id = id;
        return this;
    }

    /**
     * Sets the type of the tile.
     *
     * @param type The type of the tile.
     * @return The TileBuilder instance.
     */
    public TileBuilder setType(int type) {
        this.type = type;
        return this;
    }

    /**
     * Sets the snake of the tile.
     *
     * @param snake The snake of the tile.
     * @return The TileBuilder instance.
     */
    public TileBuilder setSnake(Snake snake) {
        this.snake = snake;
        return this;
    }

    /**
     * Sets the ladder of the tile.
     *
     * @param ladder The ladder of the tile.
     * @return The TileBuilder instance.
     */
    public TileBuilder setLadder(Ladder ladder) {
        this.ladder = ladder;
        return this;
    }

    /**
     * Builds the Tile object.
     *
     * @return The Tile object.
     */
    public Tile build() {
        return new Tile(id, type, snake, ladder);
    }
}

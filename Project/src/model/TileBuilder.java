package model;

public class TileBuilder {
    private int id;
    private int type;
    private Snake snake;
    private Ladder ladder;

    public TileBuilder setId(int id) {
        this.id = id;
        return this;
    }

    public TileBuilder setType(int type) {
        this.type = type;
        return this;
    }

    public TileBuilder setSnake(Snake snake) {
        this.snake = snake;
        return this;
    }

    public TileBuilder setLadder(Ladder ladder) {
        this.ladder = ladder;
        return this;
    }

    public Tile build() {
        return new Tile(id, type, snake, ladder);
    }
}


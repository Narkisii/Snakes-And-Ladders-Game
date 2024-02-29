package control;

import javafx.scene.layout.Pane;

public class GameElementFactory {
    private Pane canvas;

    public GameElementFactory(Pane canvas) {
        this.canvas = canvas;
    }

    public GameElement getGameElement(String elementType) {
        if (elementType == null) {
            return null;
        }
        if (elementType.equalsIgnoreCase("LADDER")) {
            return new Ladder_Object(canvas);
        } else if (elementType.equalsIgnoreCase("SNAKE")) {
            return new Snake_Object(canvas);
        }
        return null;
    }
}

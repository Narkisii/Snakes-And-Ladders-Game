package control;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public interface GameElement {
    void add(double startX, double startY, double endX,double endY, double distance);
    public void set_Color(Color color);
    public void set_Tile(Pane tile);

}

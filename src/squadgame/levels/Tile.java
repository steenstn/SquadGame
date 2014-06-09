package squadgame.levels;

import android.graphics.Rect;

public class Tile {

    private char type;
    private Rect drawingRect;

    public Tile(char type, Rect drawingRect) {
        this.type = type;
        this.drawingRect = drawingRect;
    }

    public char getType() {
        return type;
    }

    public Rect getDrawingRect() {
        return drawingRect;
    }

}

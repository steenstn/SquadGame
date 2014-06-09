package squadgame.levels;

import java.util.ArrayList;
import java.util.List;

import squadgame.interfaces.IRenderable;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class Level
    implements IRenderable {

    private List<Tile> tiles;
    private int width = 40;
    private int height = 13;
    private Bitmap tileSet;

    public Level(Bitmap tileset) {
        tiles = new ArrayList<Tile>();
        this.tileSet = tileset;
    }

    void addTile(Tile tile) {
        tiles.add(tile);
    }

    @Override
    public void render(Canvas c, float screenX, float screenY) {
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        for (int i = 0; i < tiles.size(); i++) {
            int x = (int) (screenX + i * 128);
            int y = (int) (screenY + 40);

            // c.drawCircle(x, y, 30, paint);
            // c.drawBitmap(tileSet, tiles.get(i).getDrawingRect(), new Rect(x, y, x + 128, y + 128), paint);

        }

        //Only loop through tiles that are almost in the screen
        int startx = (int) (Math.floor(-screenX / 128) - 1);
        int starty = (int) (Math.floor(-screenY / 128) - 1);
        if (startx < 0) {
            startx = 0;
        }
        if (starty < 0) {
            starty = 0;
        }
        int endx = startx + 2000 / 128 + 2;
        int endy = starty + 800 / 128 + 2;

        if (endx > width)
            endx = width;
        if (endy > height)
            endy = height;

        for (int y = starty; y < endy; y++)
            for (int x = startx; x < endx; x++) {
                int posx = Math.round(screenX + x * 128);
                int posy = Math.round(screenY + y * 128);
                c.drawBitmap(tileSet, tiles.get(x + y * width).getDrawingRect(), new Rect(posx, posy, posx + 128,
                    posy + 128), paint);

            }

    }
}

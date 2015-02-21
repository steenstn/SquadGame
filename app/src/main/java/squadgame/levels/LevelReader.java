package squadgame.levels;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.content.Context;
import android.graphics.Rect;

public class LevelReader {

    private Context context;

    public LevelReader(Context context) {
        this.context = context;
    }

    public void readLevelFromFile(Level level, int resourceId) {

        InputStream is = context.getResources().openRawResource(resourceId);
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        try {
            while (true) {
                int readInt = br.read();
                if (readInt == -1) {
                    break;
                }
                char readCharacter = (char) readInt;
                if (readCharacter != '\n' && readCharacter != '\r') {
                    Tile readTile = parseCharToTile(readCharacter);
                    level.addTile(readTile);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private Tile parseCharToTile(char readCharacter) {
        Rect drawingRect;
        switch (readCharacter) {
        case '.':
            drawingRect = new Rect(0, 0, 128, 128);
            return new Tile('.', drawingRect);
        case '1':
            drawingRect = new Rect(129, 0, 256, 128);
            return new Tile('1', drawingRect);
        default:
            throw new RuntimeException("Wrong tile! Character '" + readCharacter + "' not supported");
        }
    }
}

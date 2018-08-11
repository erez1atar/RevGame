package games.e.reversi.utility;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.HashMap;

import games.e.reversi.R;
import games.e.reversi.board.GamePiece;

/**
 * Created by LENOVO on 14/03/2016.
 */
public class BitmapContainer
{
    private static HashMap<GamePiece, Bitmap> bitmapContainerGreen;
    private static HashMap<GamePiece, Bitmap> bitmapContainerBlue;
    private static HashMap<GamePiece, Bitmap> bitmapContainerRed;

    static
{
    bitmapContainerGreen = new HashMap<>();
    bitmapContainerGreen.put(GamePiece.PLAYER1, BitmapFactory.decodeResource(App.Instance.getResources(), R.drawable.black_new));
    bitmapContainerGreen.put(GamePiece.PLAYER2, BitmapFactory.decodeResource(App.Instance.getResources(), R.drawable.white_ball));
    bitmapContainerGreen.put(GamePiece.EMPTY, BitmapFactory.decodeResource(App.Instance.getResources(), R.drawable.blank_white));
    bitmapContainerGreen.put(GamePiece.OPTION, BitmapFactory.decodeResource(App.Instance.getResources(), R.drawable.clue));
    bitmapContainerGreen.put(GamePiece.BLOCK, BitmapFactory.decodeResource(App.Instance.getResources(), R.drawable.block));
}

    static
    {
        bitmapContainerBlue = new HashMap<>();
        bitmapContainerBlue.put(GamePiece.PLAYER1, BitmapFactory.decodeResource(App.Instance.getResources(), R.drawable.black_new));
        bitmapContainerBlue.put(GamePiece.PLAYER2, BitmapFactory.decodeResource(App.Instance.getResources(), R.drawable.white_ball));
        bitmapContainerBlue.put(GamePiece.EMPTY, BitmapFactory.decodeResource(App.Instance.getResources(), R.drawable.blank_white_blue));
        bitmapContainerBlue.put(GamePiece.OPTION, BitmapFactory.decodeResource(App.Instance.getResources(), R.drawable.clue));
        bitmapContainerBlue.put(GamePiece.BLOCK, BitmapFactory.decodeResource(App.Instance.getResources(), R.drawable.block));
    }

    static
    {
        bitmapContainerRed = new HashMap<>();
        bitmapContainerRed.put(GamePiece.PLAYER1, BitmapFactory.decodeResource(App.Instance.getResources(), R.drawable.black_new));
        bitmapContainerRed.put(GamePiece.PLAYER2, BitmapFactory.decodeResource(App.Instance.getResources(), R.drawable.white_ball));
        bitmapContainerRed.put(GamePiece.EMPTY, BitmapFactory.decodeResource(App.Instance.getResources(), R.drawable.blank_white_red));
        bitmapContainerRed.put(GamePiece.OPTION, BitmapFactory.decodeResource(App.Instance.getResources(), R.drawable.clue));
        bitmapContainerRed.put(GamePiece.BLOCK, BitmapFactory.decodeResource(App.Instance.getResources(), R.drawable.block));
    }

    public static Bitmap get(GamePiece element, String color)
    {
        if(color.equals("BLUE")) {
            return bitmapContainerBlue.get(element);
        }
        else if(color.equals("RED")) {
            return bitmapContainerRed.get(element);
        }
        else {
            return bitmapContainerGreen.get(element);
        }
    }
}

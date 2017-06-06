package reversi.android.game.com.r.reversi.utility;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.HashMap;

import reversi.android.game.com.r.reversi.board.GamePiece;
import reversi.android.game.com.r.reversi.R;

/**
 * Created by LENOVO on 14/03/2016.
 */
public class BitmapContainer
{
    private static HashMap<GamePiece, Bitmap> bitmapContainer;

    static
    {
        bitmapContainer = new HashMap<>();
        bitmapContainer.put(GamePiece.PLAYER1, BitmapFactory.decodeResource(App.Instance.getResources(), R.drawable.black_new));
        bitmapContainer.put(GamePiece.PLAYER2, BitmapFactory.decodeResource(App.Instance.getResources(), R.drawable.white_ball));
        bitmapContainer.put(GamePiece.EMPTY, BitmapFactory.decodeResource(App.Instance.getResources(), R.drawable.blank_white));
        bitmapContainer.put(GamePiece.OPTION, BitmapFactory.decodeResource(App.Instance.getResources(), R.drawable.blank_gray));
    }

    public static Bitmap get(GamePiece element)
    {
        return bitmapContainer.get(element);
    }
}

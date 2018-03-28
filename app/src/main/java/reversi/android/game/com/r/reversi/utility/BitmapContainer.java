package reversi.android.game.com.r.reversi.utility;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.HashMap;

import reversi.android.game.com.r.reversi.Presention.GameActivity;
import reversi.android.game.com.r.reversi.board.GamePiece;
import reversi.android.game.com.r.reversi.R;

/**
 * Created by LENOVO on 14/03/2016.
 */
public class BitmapContainer
{
    private static HashMap<GamePiece, Bitmap> bitmapContainerGreen;
    private static HashMap<GamePiece, Bitmap> bitmapContainerBlue;

    static
    {
        bitmapContainerGreen = new HashMap<>();
        bitmapContainerGreen.put(GamePiece.PLAYER1, BitmapFactory.decodeResource(App.Instance.getResources(), R.drawable.black_new));
        bitmapContainerGreen.put(GamePiece.PLAYER2, BitmapFactory.decodeResource(App.Instance.getResources(), R.drawable.white_ball));
        bitmapContainerGreen.put(GamePiece.EMPTY, BitmapFactory.decodeResource(App.Instance.getResources(), R.drawable.blank_white));
        bitmapContainerGreen.put(GamePiece.OPTION, BitmapFactory.decodeResource(App.Instance.getResources(), R.drawable.blank_gray));
    }

    static
    {
        bitmapContainerBlue = new HashMap<>();
        bitmapContainerBlue.put(GamePiece.PLAYER1, BitmapFactory.decodeResource(App.Instance.getResources(), R.drawable.black_new));
        bitmapContainerBlue.put(GamePiece.PLAYER2, BitmapFactory.decodeResource(App.Instance.getResources(), R.drawable.white_ball));
        bitmapContainerBlue.put(GamePiece.EMPTY, BitmapFactory.decodeResource(App.Instance.getResources(), R.drawable.blank_white_blue));
        bitmapContainerBlue.put(GamePiece.OPTION, BitmapFactory.decodeResource(App.Instance.getResources(), R.drawable.blank_gray_blue));
    }

    public static Bitmap get(GamePiece element,String color)
    {
        if(color.equals("BLUE")) {
            return bitmapContainerBlue.get(element);
        }else {
            return bitmapContainerGreen.get(element);
        }
    }
}

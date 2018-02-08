package reversi.android.game.com.r.reversi.board;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

/**
 * TODO: document your custom view class.
 */
public class GameElementView extends ImageView
{
    private int row;
    private int col;

    public GameElementView(Context context)
    {
        super(context);
    }

    public int getCol()
    {
        return col;
    }

    public int getRow()
    {
        return row;
    }

    public void setCol(int col)
    {
        this.col = col;
    }

    public void setRow(int row)
    {
        this.row = row;
    }
}

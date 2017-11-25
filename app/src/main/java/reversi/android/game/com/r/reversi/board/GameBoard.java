package reversi.android.game.com.r.reversi.board;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;

/**
 * TODO: document your custom view class.
 */
public class GameBoard extends GridLayout
{
    private int height;
    private int width;
    private int numOfRows;
    private int numOfCol;
    private TurnListener turnListener;

    public GameBoard(Context context)
    {
        super(context);
        init(null, 0);
    }

    public GameBoard(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init(attrs, 0);
    }

    public GameBoard(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec)
    {
        super.onMeasure(widthSpec, heightSpec);
        this.fixBoardSize(MeasureSpec.getSize(heightSpec), MeasureSpec.getSize(widthSpec));

        int numOfViews = getChildCount();
        for(int i = 0 ; i < numOfViews; ++i)
        {
            View view = getChildAt(i);
            setCustomParam((GameElementView)view);
        }
    }

    private void fixBoardSize(int height , int width){ // to avoid black spaces on between elements

        if(height > width){  // make widht and height to be equeal
            this.setY(this.getY() + (height - width) / 2);
            height = width;
        }
        else{
            this.setX(this.getX() + (width - height) / 2);
            width = height;
        }

        int mudoloY = height % numOfRows;
        int mudoloX = width % numOfCol;

        ViewGroup.LayoutParams params = this.getLayoutParams();

        params.height = height - mudoloY;
        this.height = params.height;

        params.width = width - mudoloX;
        this.width = params.width;

        this.setLayoutParams(params);


    }

    private void init(AttributeSet attrs, int defStyle)
    {}

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
    }

    private void setCustomParam(GameElementView view)
    {
        GridLayout.LayoutParams param = new GridLayout.LayoutParams();
        param.height = height / numOfRows - 8;
        param.width = width / numOfCol - 8;
        param.rightMargin = 4;
        param.topMargin = 4;
        param.leftMargin = 4;
        param.bottomMargin = 4;
        param.setGravity(Gravity.CENTER);
        param.columnSpec = GridLayout.spec(view.getCol());
        param.rowSpec = GridLayout.spec(view.getRow());
        view.setLayoutParams(param);
    }

    public void setNumOfCol(int numOfCol)
    {
        this.numOfCol = numOfCol;
    }

    public void setNumOfRows(int numOfRaws)
    {
        this.numOfRows = numOfRaws;
    }

    public void setTurnListener(TurnListener turnListener)
    {
        this.turnListener = turnListener;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        Log.d("onTouch", "here");
        if(MotionEvent.ACTION_UP == event.getAction())
        {
            int row = (int)((event.getY() / height) * numOfRows);
            int col = (int)((event.getX() / width) * numOfCol);
            turnListener.onElementTouched(row, col);
        }
        return true;
    }

    private int getIndex(int row, int col)
    {
        return row * numOfCol + col;
    }


    public GameElementView getGameElementView(int row, int col)
    {
        return (GameElementView)getChildAt(getIndex(row, col));
    }

    public interface TurnListener
    {
        void onElementTouched(int row, int col);
    }
}

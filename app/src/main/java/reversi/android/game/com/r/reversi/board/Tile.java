package reversi.android.game.com.r.reversi.board;

/**
 * Created by LENOVO on 15/03/2016.
 */
public class Tile
{
    private int row;
    private int col;
    private GamePiece gamePiece;

    public Tile(int row, int col, GamePiece gamePiece)
    {
        this.col = col;
        this.row = row;
        this.gamePiece = gamePiece;
    }

    public int getRow()
    {
        return row;
    }

    public GamePiece getGamePiece()
    {
        return gamePiece;
    }

    public int getCol()
    {
        return col;
    }


}

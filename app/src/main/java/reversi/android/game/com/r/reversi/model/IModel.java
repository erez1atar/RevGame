package reversi.android.game.com.r.reversi.model;

import java.util.ArrayList;
import java.util.List;

import reversi.android.game.com.r.reversi.board.GamePiece;
import reversi.android.game.com.r.reversi.board.Tile;

/**
 * Created by LENOVO on 15/03/2016.
 */
public interface IModel
{
    void initBoardWithEmptyPieces();
    void clearBoard();
    GamePiece getElement(int row, int col);
    void setElement(int row, int col, GamePiece gamePiece);
    void setTiles(List<Tile> tiles);
    ArrayList<Tile> getFullyTiles();
    ArrayList<Tile> getEmptyTiles();
    int getNumOfCols();
    int getNumOfRows();
    List<List<GamePiece>> getGame();
}

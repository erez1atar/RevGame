package games.e.reversi.model;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import games.e.reversi.board.GamePiece;
import games.e.reversi.board.Tile;

/**
 * Created by LENOVO on 14/03/2016.
 */
public class GameModel implements IModel
{
    private List<List<GamePiece>> game;
    private int numOfRows;
    private int numOfCols;

    public GameModel(int numOfRows, int numOfCols)
    {
        this.numOfCols = numOfCols;
        this.numOfRows = numOfRows;
        game = new ArrayList<>(numOfRows);
        for(int i = 0 ; i < numOfRows ; ++i)
        {
            game.add(new ArrayList<GamePiece>(numOfCols));
        }
        initBoardWithEmptyPieces();
    }

    public void initBoardWithEmptyPieces()
    {
        for(int i = 0 ; i < game.size() ; ++i)
        {
            for(int j = 0 ; j < numOfCols ; ++j)
            {
                List<GamePiece> colList = game.get(i);
                colList.add(GamePiece.EMPTY);
            }
        }
    }

    @Override
    public void clearBoard()
    {
        game = new ArrayList<>(numOfRows);
        for(int i = 0 ; i < numOfRows ; ++i)
        {
            game.add(new ArrayList<GamePiece>(numOfCols));
        }
        initBoardWithEmptyPieces();
    }

    public GamePiece getElement(int row, int col)
    {
        if(row < 0 || row >= numOfRows || col < 0 || col >= numOfCols )
        {
            Log.d("getElement", "invalid index " + row + " " + col);
            //App.Instance.getGoogleAnalytics().TrackErrorElementEvent(row, col);
            return null;
        }
        return game.get(row).get(col);
    }

    public void setElement(int row, int col, GamePiece gamePiece)
    {
        if(row < 0 || row > numOfRows || col < 0 || col > numOfCols )
        {
            return ;
        }
        game.get(row).set(col, gamePiece);
    }

    public void setTiles(List<Tile> tiles)
    {
        for (Tile tile : tiles)
        {
            int row = tile.getRow();
            int col = tile.getCol();
            if(row >= 0 && row < numOfRows || col >= 0 || col < numOfCols)
            {
                game.get(row).set(col, tile.getGamePiece());
            }
        }
    }

    public ArrayList<Tile> getFullyTiles()
    {
        ArrayList<Tile> fullyTiles = new ArrayList<>();
        for(int i = 0 ; i < numOfRows ; i ++)
        {
            for (int j = 0 ; j < numOfCols ; j++)
            {
                GamePiece piece = game.get(i).get(j);
                if(piece != GamePiece.EMPTY)
                {
                    fullyTiles.add(new Tile(i, j, piece));
                }
            }
        }
        return fullyTiles;
    }

    @Override
    public ArrayList<Tile> getEmptyTiles()
    {
        ArrayList<Tile> emptyTiles = new ArrayList<>();
        for(int i = 0 ; i < numOfRows ; i ++)
        {
            for (int j = 0 ; j < numOfCols ; j++)
            {
                GamePiece piece = game.get(i).get(j);
                if(piece == GamePiece.EMPTY)
                {
                    emptyTiles.add(new Tile(i, j, piece));
                }
            }
        }
        return emptyTiles;
    }

    public int getNumOfCols()
    {
        return numOfCols;
    }

    public int getNumOfRows()
    {
        return numOfRows;
    }

    public List<List<GamePiece>> getGame()
    {
        return game;
    }
}

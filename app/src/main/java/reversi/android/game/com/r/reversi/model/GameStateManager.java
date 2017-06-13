package reversi.android.game.com.r.reversi.model;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import reversi.android.game.com.r.reversi.board.GamePiece;
import reversi.android.game.com.r.reversi.utility.App;

/**
 * Created by Erez on 06/06/2017.
 */

public class GameStateManager
{
    private ArrayList<ArrayList<ArrayList<GamePiece>>> gameState;
    private IModel gameModel;

    public GameStateManager()
    {
        gameState = new ArrayList<ArrayList<ArrayList<GamePiece>>>();
        gameModel = App.getModel();
    }

    public void saveState()
    {
        Log.d("GameState", "saveState size = " + gameState.size());
        ArrayList<ArrayList<GamePiece>> copyBoard = new ArrayList<>();
        List<List<GamePiece>> game = gameModel.getGame();
        for(int i = 0 ; i < game.size() ; i++)
        {
            ArrayList row = new ArrayList();
            for(int j = 0 ; j < game.get(i).size() ; j++)
            {
                row.add(j, game.get(i).get(j));
            }
            copyBoard.add(i,row);

        }
        gameState.add(copyBoard);

    }

    public ArrayList<ArrayList<GamePiece>> getGameState()
    {
        int movesBack = gameState.size() - 3;
        if(movesBack < 0)
        {
            movesBack = 0;
        }
        Log.d("getGameState", "idx = " + movesBack);
        return gameState.get(movesBack);
    }
}

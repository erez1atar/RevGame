package reversi.android.game.com.r.reversi.model;

import java.util.ArrayList;
import java.util.List;

import reversi.android.game.com.r.reversi.board.GamePiece;
import reversi.android.game.com.r.reversi.utility.App;

/**
 * Created by Erez on 06/06/2017.
 */

public class GameStateManager
{
    private ArrayList<List<List<GamePiece>>> gameState;
    private IModel gameModel;

    public GameStateManager()
    {
        gameState = new ArrayList<List<List<GamePiece>>>();
        gameModel = App.getModel();
    }

    public void saveState()
    {
        if(gameState.size() == 3)
        {
            gameState.remove(0);
        }
        gameState.add(gameModel.getGame());

    }
}

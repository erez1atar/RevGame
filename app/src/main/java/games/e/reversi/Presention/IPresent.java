package games.e.reversi.Presention;

import java.util.List;

import games.e.reversi.board.Player;
import games.e.reversi.board.Tile;
import games.e.reversi.controllers.GameResult;

/**
 * Created by LENOVO on 15/03/2016.
 */
public interface IPresent
{
    void updateChanges(List<Tile> listOfChanges);
    void updateChanges(Tile... listOfChanges);
    void endGame(Player winPlayer, GameResult gameResult);
    void winLevel(Player winPlayer, GameResult gameResult);
    void lossLevel(Player winPlayer, GameResult gameResult);
    void onTurnChange();
    void onConnectionError();
    void responseToShake();
    void updateOpponentName(String name);
    void onNoMovesAvailable();
}

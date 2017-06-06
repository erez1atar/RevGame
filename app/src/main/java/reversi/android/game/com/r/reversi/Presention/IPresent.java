package reversi.android.game.com.r.reversi.Presention;

import java.util.List;

import reversi.android.game.com.r.reversi.board.Player;
import reversi.android.game.com.r.reversi.board.Tile;

/**
 * Created by LENOVO on 15/03/2016.
 */
public interface IPresent
{
    void updateChanges(List<Tile> listOfChanges);
    void updateChanges(Tile... listOfChanges);
    void endGame(Player winPlayer);
    void winLevel(Player winPlayer);
    void lossLevel(Player winPlayer);
    void onTurnChange();
    void onConnectionError();
    void responseToShake();
    void updateOpponentName(String name);
}

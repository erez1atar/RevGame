package reversi.android.game.com.r.reversi.controllers;

import java.util.ArrayList;
import java.util.List;

import reversi.android.game.com.r.reversi.Connection.IConnectionManager;
import reversi.android.game.com.r.reversi.Presention.IPresent;
import reversi.android.game.com.r.reversi.board.GamePiece;
import reversi.android.game.com.r.reversi.board.Player;
import reversi.android.game.com.r.reversi.board.Tile;

/**
 * Created by LENOVO on 15/03/2016.
 */
public interface IController
{
    void startGame();
    void startRetryGame(ArrayList<ArrayList<GamePiece>> gameBoard);
    void saveGameStateToRetry();
    void onActionEvent(String actionType, String value, int row, int col);
    void onTileTouched(int row, int col);
    void setUp();
    int getNumOfPiecesP1();
    int getNumOfPiecesP2();
    void setIPresent(IPresent iPresent);
    void getOptionsToPlay(ArrayList<Tile> options);
    Player getCurrentPlayerTurn();
    void setIConnection(IConnectionManager iConnectionManager);
    void closeGame();
    int getNumOfTilesToChange(ArrayList<Tile> tilesToChange, int row, int col);
}

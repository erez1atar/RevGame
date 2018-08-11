package games.e.reversi.controllers;

import java.util.ArrayList;

import games.e.reversi.Connection.IConnectionManager;
import games.e.reversi.Presention.IPresent;
import games.e.reversi.board.GamePiece;
import games.e.reversi.board.Player;
import games.e.reversi.board.Tile;

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

    void TESTwinGame();
    void TESTlossGame();
}

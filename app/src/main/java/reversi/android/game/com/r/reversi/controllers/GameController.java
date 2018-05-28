package reversi.android.game.com.r.reversi.controllers;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import reversi.android.game.com.r.reversi.Connection.IConnectionManager;
import reversi.android.game.com.r.reversi.Connection.TurnData;
import reversi.android.game.com.r.reversi.Features.MyAccelometer;
import reversi.android.game.com.r.reversi.Presention.GameActivity;
import reversi.android.game.com.r.reversi.Presention.IPresent;
import reversi.android.game.com.r.reversi.R;
import reversi.android.game.com.r.reversi.board.Player;
import reversi.android.game.com.r.reversi.board.GamePiece;
import reversi.android.game.com.r.reversi.board.Tile;
import reversi.android.game.com.r.reversi.model.GameStateManager;
import reversi.android.game.com.r.reversi.model.IModel;
import reversi.android.game.com.r.reversi.utility.App;
import reversi.android.game.com.r.reversi.utility.LevelsModeManager;

/**
 * Created by LENOVO on 14/03/2016.
 */
public class GameController implements IController
{
    private IModel gameModel;
    private Player[] players;
    private int turnIdx = 0;
    private WeakReference<IPresent> iPresentWeak;
    private int numOfCols;
    private int numOfRows;
    private Boolean gameEndNormally;
    private int[] numOfPieces;
    private ExecutorService executorService = Executors.newFixedThreadPool(2);
    private IConnectionManager iConnectionManager;
    private MyAccelometer myAccelometer;
    private SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(App.Instance);
    private GameStateManager gameStateManager;
    private boolean toSaveStateThisTurn = false;

    public GameController(IPresent iPresent , IConnectionManager iConnectionManager)
    {
        players = new Player[2];
        players[0] = new Player(App.Instance.getResources().getString(R.string.black_player), GamePiece.PLAYER1);
        players[1] = new Player(App.Instance.getResources().getString(R.string.white_player), GamePiece.PLAYER2);
        this.iPresentWeak = new WeakReference<>(iPresent);
        numOfPieces = new int[]{0,0};
        this.iConnectionManager = iConnectionManager;
        //setAccelometerIfNeeded();
    }

    private void initModel()
    {
        this.gameModel = App.getModel();
        numOfCols = gameModel.getNumOfCols();
        numOfRows = gameModel.getNumOfRows();
    }

    public void setUp()
    {
        iPresentWeak.get().updateChanges(gameModel.getFullyTiles());
    }

    @Override
    public int getNumOfPiecesP1()
    {
        return numOfPieces[0];
    }

    @Override
    public int getNumOfPiecesP2()
    {
        return numOfPieces[1];
    }

    @Override
    public void startRetryGame(ArrayList<ArrayList<GamePiece>> gameBoard) {
        Log.d("GameContoller", "startRetryGame");
        initModel();
        turnIdx = 0;
        gameModel.clearBoard();
        gameStateManager = new GameStateManager();
        ArrayList<Tile> initTiles = new ArrayList<>(4);
        numOfPieces[0] = 0;
        numOfPieces[1] = 0;
        for(int i = 0 ; i < gameBoard.size() ; i++)
        {
            for(int j = 0 ; j < gameBoard.get(i).size() ; j++)
            {
                initTiles.add(new Tile(i, j, gameBoard.get(i).get(j)));
                if(gameBoard.get(i).get(j) == GamePiece.PLAYER1)
                {
                    ++numOfPieces[0];
                }
                else if (gameBoard.get(i).get(j) == GamePiece.PLAYER2)
                {
                    ++numOfPieces[1];
                }
            }
        }
        IPresent iPresent = iPresentWeak.get();
        if(iPresent != null)
        {
            iPresent.updateChanges(initTiles);
        }
        gameModel.setTiles(initTiles);
        gameEndNormally = false;
        iConnectionManager.sendTurnData(new TurnData(App.Instance.getString(R.string.action_key), App.Instance.getString(R.string.name_key), prefs.getString(App.Instance.getString(R.string.name_settings_key), "Opponent")));
    }

    @Override
    public void saveGameStateToRetry() {
        App.Instance.saveGameState(gameStateManager.getGameState());
    }

    public void startGame()
    {
        initModel();
        turnIdx = 0;
        toSaveStateThisTurn = true;
        gameModel.clearBoard();
        gameStateManager = new GameStateManager();
        GamePiece element1 = players[0].getGamePiece();
        GamePiece element2 = players[1].getGamePiece();
        ArrayList<Tile> initTiles = new ArrayList<>(4);
        int midRow = numOfRows / 2;
        int midCol = numOfCols / 2;
        if(App.getIsLevelsMode())
        {
            LevelsModeManager levelsModeManager = App.getLevelsModeManager();
            App.Instance.getGoogleAnalytics().TrackLevelStartEvent(App.getActiveLevel());
            initTiles = levelsModeManager.getStartGameDetails(App.getActiveLevel());
            numOfPieces[0] = levelsModeManager.getNumberOfPiecesPlayerOneStarted(App.getActiveLevel());
            numOfPieces[1] = levelsModeManager.getNumberOfPiecesPlayerTwoStarted(App.getActiveLevel());
        }
        else
        {
            initTiles.add(new Tile(midRow - 1, midCol - 1, element1));
            initTiles.add(new Tile(midRow, midCol, element1));
            initTiles.add(new Tile(midRow, midCol - 1, element2));
            initTiles.add(new Tile(midRow - 1, midCol, element2));
            Log.d("startGame contoller", "rows = " + numOfRows);
            numOfPieces[0] = 2;
            numOfPieces[1] = 2;
        }
        IPresent iPresent = iPresentWeak.get();
        if(iPresent != null)
        {
            iPresent.updateChanges(initTiles);
        }
        gameModel.setTiles(initTiles);
        gameEndNormally = false;
        iConnectionManager.sendTurnData(new TurnData(App.Instance.getString(R.string.action_key), App.Instance.getString(R.string.name_key), prefs.getString(App.Instance.getString(R.string.name_settings_key), "Opponent")));
        
    }

    @Override
    public void onActionEvent(String actionType,String value, int row, int col)
    {
        IPresent iPresent = iPresentWeak.get();
        if(iPresent == null)
        {
            Log.d("onActionEvent" ,  "iPresent = null");
            return;
        }
        if(actionType.compareTo(App.Instance.getString(R.string.turn_key)) == 0)
        {
            checkTileTouched(row, col, true);
        }
        else if(actionType.compareTo(App.Instance.getString(R.string.disconnect_action_key)) == 0)
        {
            Log.d("conroller", "disconnect");
            if(! gameEndNormally)
            {
                iPresent.onConnectionError();
            }
        }
        else if(actionType.compareTo(App.Instance.getString(R.string.accelometer_key)) == 0)
        {
            iPresent.responseToShake();
        }
        else if(actionType.compareTo(App.Instance.getString(R.string.name_key)) == 0)
        {
            iPresent.updateOpponentName(value);
        }
    }

    public void onTileTouched(int row, int col)
    {
        checkTileTouched(row, col, false);
    }

    private void checkTileTouched(int row, int col, Boolean arrivedFromConn)
    {
        GamePiece element = gameModel.getElement(row, col);
        if(element != GamePiece.EMPTY)
        {
            Log.d("onTileTouched", "get not empty");
            return;
        }
        Thread turnThread = new Thread(new TurnCalculate(row,col, arrivedFromConn));
        executorService.execute(turnThread);
    }

    public void TESTwinGame()  {

        IPresent iPresent = iPresentWeak.get();
        if(App.getIsLevelsMode())
        {
            int level = App.getActiveLevel();
            // dont report to analytics its test
            iPresent.winLevel(players[1], GameResult.END_GAME_NO_AVAILABLE_MOVES);

            App.getLevelsModeManager().trySetGreatestLevel(level + 1);
        }
        else if(iPresent != null)
        {
            iPresent.endGame(players[1], GameResult.END_GAME_NO_AVAILABLE_MOVES);
        }

        gameEndNormally = true;
    }
    public void TESTlossGame()  {

        IPresent iPresent = iPresentWeak.get();
        if(App.getIsLevelsMode())
        {
            int level = App.getActiveLevel();
            // dont report to analytics its test
            iPresent.lossLevel(players[0], GameResult.END_GAME_NO_AVAILABLE_MOVES);

        }

        else if(iPresent != null)
        {
            iPresent.endGame(players[0],  GameResult.END_GAME_NO_AVAILABLE_MOVES);
        }

        gameEndNormally = true;
    }


    private class TurnCalculate implements Runnable
    {
        private int row;
        private int col;
        private Boolean arrivedFromConn;

        public TurnCalculate(int row, int col, Boolean arrivedFromConn)
        {
            this.col = col;
            this.row = row;
            this.arrivedFromConn = arrivedFromConn;
        }

        @Override
        public void run()
        {
                ArrayList<Tile> tilesToChange = new ArrayList<>();
                int numOfEnemies = getNumOfTilesToChange(tilesToChange, row , col);

                if(toSaveStateThisTurn) {
                    gameStateManager.saveState();
                }
                toSaveStateThisTurn = !toSaveStateThisTurn;

                if (numOfEnemies > 0)
                {
                    IPresent iPresent = iPresentWeak.get();
                    if(iPresent != null)
                    {
                        iPresent.updateChanges(tilesToChange);
                    }
                    gameModel.setTiles(tilesToChange);
                    numOfPieces[turnIdx] += numOfEnemies + 1;
                    numOfPieces[1 - turnIdx] -= numOfEnemies;

                    gameModel.setElement(row, col, players[turnIdx].getGamePiece());
                    if(iPresent != null)
                    {
                        iPresent.updateChanges(new Tile(row, col, players[turnIdx].getGamePiece()));
                    }
                    turnIdx = 1 - turnIdx;
                    iPresentWeak.get().onTurnChange();
                    /*if(!arrivedFromConn)
                    {
                        iConnectionManager.sendTurnData(new TurnData(row, col, App.Instance.getString(R.string.game_key), App.Instance.getString(R.string.turn_key))); // TODO: 23/03/2016
                    }*/
                    GameResult endGameResult = checkEndOfGame();
                    if(endGameResult == GameResult.END_GAME_NO_AVAILABLE_MOVES || endGameResult == GameResult.END_GAME)
                    {
                        if(numOfPieces[0] > numOfPieces[1])
                        {
                            if(App.getIsLevelsMode())
                            {
                                int level = App.getActiveLevel();
                                App.Instance.getGoogleAnalytics().TrackLevelLossEvent(level);
                                if(iPresent != null)
                                {
                                    iPresent.lossLevel(players[0], endGameResult);
                                }
                            }

                            else if(iPresent != null)
                            {
                                iPresent.endGame(players[0], endGameResult);
                            }
                        }
                        else
                        {
                            if(App.getIsLevelsMode())
                            {
                                int level = App.getActiveLevel();
                                App.Instance.getGoogleAnalytics().TrackLevelWinEvent(level);

                                 if(iPresent != null)
                                {
                                     iPresent.winLevel(players[1], endGameResult);
                                }
                                App.getLevelsModeManager().trySetGreatestLevel(level + 1);
                            }
                            else if(iPresent != null)
                            {
                                iPresent.endGame(players[1], endGameResult);
                            }
                        }
                        gameEndNormally = true;
                        return;
                    }
                    if(iConnectionManager.isMoveTurnOnNoAvailable()) {
                        if(!arrivedFromConn){

                            boolean noMovesAvialable = checkPlayerNoMovesAvailable();
                            if(noMovesAvialable) {
                                turnIdx = 1 - turnIdx;
                                iPresentWeak.get().onTurnChange();
                                iPresentWeak.get().onNoMovesAvailable();
                            }
                            else {
                                iConnectionManager.sendTurnData(new TurnData(row, col, App.Instance.getString(R.string.game_key), App.Instance.getString(R.string.turn_key))); // TODO: 23/03/2016
                            }
                        }
                        else {
                            boolean noMovesAvialable = checkPlayerNoMovesAvailable();
                            if(noMovesAvialable) {
                                iPresentWeak.get().onNoMovesAvailable();
                                turnIdx = 1 - turnIdx;
                                iPresentWeak.get().onTurnChange();
                                iConnectionManager.sendTurnData(new TurnData(-10, -10, App.Instance.getString(R.string.game_key), App.Instance.getString(R.string.turn_key)));
                            }
                        }
                    }
                    else {
                        if(!arrivedFromConn)
                        {
                            iConnectionManager.sendTurnData(new TurnData(row, col, App.Instance.getString(R.string.game_key), App.Instance.getString(R.string.turn_key))); // TODO: 23/03/2016
                        }
                    }

            }
        }

        private GameResult checkEndOfGame()
        {
            if(numOfPieces[0] == 0  ||  numOfPieces[1] == 0 ||(numOfPieces[0] + numOfPieces[1] == numOfRows * numOfCols) || checkNoAvialableMovesAnyPlayer())
            {
                return GameResult.END_GAME;
            }
            ArrayList<Tile> emptyTiles = gameModel.getEmptyTiles();
            if(emptyTiles.size() == 0) {
                return GameResult.END_GAME_NO_AVAILABLE_MOVES;
            }
            return GameResult.NOT_END;
        }
    }

    private boolean checkPlayerNoMovesAvailable() {
        ArrayList<Tile> emptyTiles = gameModel.getEmptyTiles();
        ArrayList<Tile> tilesToChange = new ArrayList<>();
        int totalMovesAvailable = 0;
        for (Tile tile: emptyTiles)
        {
            totalMovesAvailable += getNumOfTilesToChange(tilesToChange, tile.getRow(), tile.getCol());
        }
        if(totalMovesAvailable == 0)
        {
            return true;
        }
        return false;
    }

    private boolean checkNoAvialableMovesAnyPlayer() {
        ArrayList<Tile> emptyTiles = gameModel.getEmptyTiles();
        ArrayList<Tile> tilesToChange = new ArrayList<>();
        int totalMovesAvailable = 0;
        for (Tile tile: emptyTiles)
        {
            totalMovesAvailable += getPlayerNumOfTilesToChange(tilesToChange, tile.getRow(), tile.getCol(),0);
            totalMovesAvailable += getPlayerNumOfTilesToChange(tilesToChange, tile.getRow(), tile.getCol(),1);
        }
        if(totalMovesAvailable == 0)
        {
            return true;
        }
        return false;
    }

    public int getPlayerNumOfTilesToChange(ArrayList<Tile> tilesToChange, int row, int col, int turnPlayerIdx)
    {
        int numOfEnemies;
        GamePiece otherPlayerPiece = players[1 - turnPlayerIdx].getGamePiece();
        GamePiece thisPlayerPiece = players[turnPlayerIdx].getGamePiece();
        numOfEnemies = turn(tilesToChange, row, col, -1, -1, otherPlayerPiece, thisPlayerPiece)
                + turn(tilesToChange, row, col, 0, -1, otherPlayerPiece, thisPlayerPiece)
                + turn(tilesToChange, row, col, 1, -1, otherPlayerPiece, thisPlayerPiece)
                + turn(tilesToChange, row, col, 1, 0, otherPlayerPiece, thisPlayerPiece)
                + turn(tilesToChange, row, col, 1, 1, otherPlayerPiece, thisPlayerPiece)
                + turn(tilesToChange, row, col, 0, 1, otherPlayerPiece, thisPlayerPiece)
                + turn(tilesToChange, row, col, -1, 1, otherPlayerPiece, thisPlayerPiece)
                + turn(tilesToChange, row, col, -1, 0, otherPlayerPiece, thisPlayerPiece);
        return numOfEnemies;
    }

    public int getNumOfTilesToChange(ArrayList<Tile> tilesToChange, int row, int col)
    {
        return getPlayerNumOfTilesToChange(tilesToChange, row, col, turnIdx);

    }


    private int turn(ArrayList totalList, int row, int col, int xDirection, int yDirection,GamePiece otherPlayerPiece, GamePiece thisPlayerPiece )
    {
        int numOfEnemies = 0;
        GamePiece current = GamePiece.EMPTY;
        ArrayList<Tile> list = new ArrayList<>();

        for(int x = row + xDirection , y = col + yDirection ; x >= 0 && y >= 0 && x < numOfRows && y < numOfCols && ((current = gameModel.getElement(x, y)) == otherPlayerPiece) ; x += xDirection , y += yDirection )
        {
            ++numOfEnemies;
            list.add(new Tile(x, y, thisPlayerPiece));
        }

        if(numOfEnemies > 0 && current == thisPlayerPiece)
        {
            for (Tile tile : list)
            {
                totalList.add(tile);
            }
            return numOfEnemies;
        }
        return 0;
    }



    public void setIPresent(IPresent iPresent)
    {
        iPresentWeak = new WeakReference<>(iPresent);
    }

    @Override
    public void getOptionsToPlay(ArrayList<Tile> options)
    {
        ArrayList<Tile> emptyTiles = gameModel.getEmptyTiles();
        ArrayList<Tile> temp = new ArrayList<>();
        for (Tile tile : emptyTiles)
        {
            if(getNumOfTilesToChange(temp, tile.getRow(), tile.getCol()) > 0)
            {
                options.add(tile);
            }
        }
    }

    @Override
    public Player getCurrentPlayerTurn()
    {
        return players[turnIdx];
    }

    @Override
    public void setIConnection(IConnectionManager iConnectionManager)
    {
        this.iConnectionManager = iConnectionManager;
    }

    @Override
    public void closeGame()
    {
        iConnectionManager.disconnect();
        /*if(myAccelometer != null)
        {
            myAccelometer.stop();
        }*/
    }

    /*private void setAccelometerIfNeeded() {
        if(prefs.getBoolean(App.Instance.getString(R.string.shake_feature_pref_key), true) )
        {
            myAccelometer = new MyAccelometer(800);
            myAccelometer.setListener(new MyAccelometer.MyAccelometerListener() {
                @Override
                public void onEvent() {
                    iConnectionManager.sendTurnData(new TurnData(App.Instance.getString(R.string.action_key), App.Instance.getString(R.string.accelometer_key), ""));
                }
            });
            myAccelometer.start(); // TODO: 24/03/2016
        }
    }*/
}

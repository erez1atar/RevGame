package reversi.android.game.com.r.reversi.utility;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

import reversi.android.game.com.r.reversi.board.GamePiece;
import reversi.android.game.com.r.reversi.board.Tile;

/**
 * Created by Erez on 02/05/2017.
 */

public class LevelsModeManager
{
    private SharedPreferences sharedPreferences;
    private static final String crntLevelStr = "LEVEL";
    private static final String FINISHED_LAST_LEVEL = "LAST_LEVEL_FINISHED";
    int maxLevel = 26;
    public class StartGameTile
    {
        public StartGameTile(int x, int y, PlayerType playerType)
        {
            this.x = x;
            this.y = y;
            this.type = playerType;
        }
        public int x;
        public int y;
        public PlayerType type;
    }

    public enum PlayerType
    {
        COMPUTER,
        PLAYER
    }

    public LevelsModeManager()
    {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(App.Instance);
    }

    public int getCurrentLevel()
    {
        return sharedPreferences.getInt(crntLevelStr, 1);
    }

    public void setCurrentLevel(int level)
    {
        if(level > maxLevel)
        {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(FINISHED_LAST_LEVEL, true);
            editor.apply();
            return;
        }
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(crntLevelStr, level);
        editor.apply();
    }

    public ArrayList<Tile> getStartGameDetails()
    {
        ArrayList<Tile> tiles = new ArrayList<>();
        int level = getCurrentLevel();
        int numOfCols = App.getModel().getNumOfCols();
        int numOfRows =  App.getModel().getNumOfRows();
        int midRow = numOfRows / 2;
        int midCol = numOfCols / 2;

        switch (level)
        {
            case 1:
                //PLAYER1 is COMPUTER
                tiles.add(new Tile(midRow -1 ,midCol -1, GamePiece.PLAYER1));
                tiles.add(new Tile(midRow, midCol, GamePiece.PLAYER1));
                tiles.add(new Tile(midRow, midCol -1, GamePiece.PLAYER2));
                tiles.add(new Tile(midRow - 1 , midCol, GamePiece.PLAYER2));
                break;

            case 2:
                tiles.add(new Tile(midRow -1 ,5, GamePiece.PLAYER1));
                tiles.add(new Tile(midRow, 6, GamePiece.PLAYER1));
                tiles.add(new Tile(midRow, 5 , GamePiece.PLAYER2));
                tiles.add(new Tile(midRow - 1 , 6, GamePiece.PLAYER2));

                tiles.add(new Tile(midRow -1 ,1, GamePiece.PLAYER1));
                tiles.add(new Tile(midRow, 2, GamePiece.PLAYER1));
                tiles.add(new Tile(midRow, 1 , GamePiece.PLAYER2));
                tiles.add(new Tile(midRow - 1 , 2, GamePiece.PLAYER2));

                break;
            case 3:
                tiles.add(new Tile(midRow -1 ,midCol -1, GamePiece.PLAYER1));
                tiles.add(new Tile(midRow, midCol, GamePiece.PLAYER1));
                tiles.add(new Tile(midRow, midCol -1, GamePiece.PLAYER2));
                tiles.add(new Tile(midRow - 1 , midCol, GamePiece.PLAYER2));
                tiles.add(new Tile(0 , 0, GamePiece.PLAYER1));
                tiles.add(new Tile(numOfRows - 1 , numOfCols - 1, GamePiece.PLAYER1));
                break;

            case 4:
                tiles.add(new Tile(midRow -1 ,midCol -1, GamePiece.PLAYER1));
                tiles.add(new Tile(midRow, midCol, GamePiece.PLAYER1));
                tiles.add(new Tile(midRow, midCol -1, GamePiece.PLAYER2));
                tiles.add(new Tile(midRow - 1 , midCol, GamePiece.PLAYER2));
                tiles.add(new Tile(0 , 0, GamePiece.PLAYER2));
                tiles.add(new Tile(7 , 0, GamePiece.PLAYER1));
                tiles.add(new Tile(7 , 3, GamePiece.PLAYER1));
                tiles.add(new Tile(7 , 7, GamePiece.PLAYER1));
                break;

            case 5:
                tiles.add(new Tile(midRow -1 ,midCol -1, GamePiece.PLAYER1));
                tiles.add(new Tile(midRow, midCol, GamePiece.PLAYER1));
                tiles.add(new Tile(midRow, midCol -1, GamePiece.PLAYER2));
                tiles.add(new Tile(midRow - 1 , midCol, GamePiece.PLAYER2));
                tiles.add(new Tile(0 , 0, GamePiece.PLAYER1));
                tiles.add(new Tile(0 , 1, GamePiece.PLAYER1));
                tiles.add(new Tile(0 , 2, GamePiece.PLAYER1));
                tiles.add(new Tile(0 , 3, GamePiece.PLAYER1));
                tiles.add(new Tile(0 , 4, GamePiece.PLAYER1));
                tiles.add(new Tile(0 , 5, GamePiece.PLAYER1));

                tiles.add(new Tile(7 , 0, GamePiece.PLAYER2));
                tiles.add(new Tile(7 , 1, GamePiece.PLAYER2));
                tiles.add(new Tile(7 , 2, GamePiece.PLAYER2));
                tiles.add(new Tile(7 , 3, GamePiece.PLAYER2));
                tiles.add(new Tile(7 , 4, GamePiece.PLAYER2));
                tiles.add(new Tile(7 , 5, GamePiece.PLAYER2));


                break;

            case 6:
                tiles.add(new Tile(midRow -1 ,midCol -1, GamePiece.PLAYER1));
                tiles.add(new Tile(midRow, midCol, GamePiece.PLAYER1));
                tiles.add(new Tile(midRow, midCol -1, GamePiece.PLAYER2));
                tiles.add(new Tile(midRow - 1 , midCol, GamePiece.PLAYER2));
                tiles.add(new Tile(1 , 1, GamePiece.PLAYER1));
                tiles.add(new Tile(6 , 6, GamePiece.PLAYER1));
                tiles.add(new Tile(1 , 4, GamePiece.PLAYER1));
                break;

            case 7:
                tiles.add(new Tile(midRow -1 ,midCol -1, GamePiece.PLAYER1));
                tiles.add(new Tile(midRow, midCol, GamePiece.PLAYER1));
                tiles.add(new Tile(midRow, midCol -1, GamePiece.PLAYER2));
                tiles.add(new Tile(midRow - 1 , midCol, GamePiece.PLAYER2));
                tiles.add(new Tile(0 , 0, GamePiece.PLAYER2));
                tiles.add(new Tile(numOfRows - 1 , numOfCols - 1, GamePiece.PLAYER1));
                tiles.add(new Tile(0 , numOfCols - 1, GamePiece.PLAYER1));
                break;

            case 8:
                tiles.add(new Tile(midRow -1 ,midCol -1, GamePiece.PLAYER1));
                tiles.add(new Tile(midRow, midCol, GamePiece.PLAYER1));
                tiles.add(new Tile(midRow, midCol -1, GamePiece.PLAYER2));
                tiles.add(new Tile(midRow - 1 , midCol, GamePiece.PLAYER2));
                tiles.add(new Tile(7 , 7, GamePiece.PLAYER2));
                tiles.add(new Tile(0 , 0, GamePiece.PLAYER1));
                tiles.add(new Tile(0 , 1, GamePiece.PLAYER1));
                tiles.add(new Tile(0 , 2, GamePiece.PLAYER1));
                tiles.add(new Tile(0 , 3, GamePiece.PLAYER1));
                break;

            case 9:
                tiles.add(new Tile(midRow -1 ,midCol -1, GamePiece.PLAYER1));
                tiles.add(new Tile(midRow, midCol, GamePiece.PLAYER1));
                tiles.add(new Tile(midRow, midCol -1, GamePiece.PLAYER2));
                tiles.add(new Tile(midRow - 1 , midCol, GamePiece.PLAYER2));
                tiles.add(new Tile(0 , 0, GamePiece.PLAYER1));
                tiles.add(new Tile(0 , 1, GamePiece.PLAYER1));
                tiles.add(new Tile(0 , 2, GamePiece.PLAYER1));
                tiles.add(new Tile(0 , 3, GamePiece.PLAYER1));
                tiles.add(new Tile(numOfRows - 1 , 3, GamePiece.PLAYER1));
                tiles.add(new Tile(numOfRows - 1 , 0, GamePiece.PLAYER2));
                break;

            case 10:
                tiles.add(new Tile(midRow -1 ,midCol -1, GamePiece.PLAYER1));
                tiles.add(new Tile(midRow, midCol, GamePiece.PLAYER1));
                tiles.add(new Tile(midRow, midCol -1, GamePiece.PLAYER2));
                tiles.add(new Tile(midRow - 1 , midCol, GamePiece.PLAYER2));
                tiles.add(new Tile(7, 7, GamePiece.PLAYER2));
                tiles.add(new Tile(0 , 0, GamePiece.PLAYER1));
                tiles.add(new Tile(0 , 1, GamePiece.PLAYER1));
                tiles.add(new Tile(0 , 2, GamePiece.PLAYER1));
                tiles.add(new Tile(0 , 3, GamePiece.PLAYER1));
                tiles.add(new Tile(0 , 4, GamePiece.PLAYER1));
                tiles.add(new Tile(0 , 5, GamePiece.PLAYER1));

                break;


            case 11:
                tiles.add(new Tile(7 , 7, GamePiece.PLAYER2));
                tiles.add(new Tile(3 , 1, GamePiece.PLAYER2));
                tiles.add(new Tile(3 , 2, GamePiece.PLAYER2));
                tiles.add(new Tile(3 , 3, GamePiece.PLAYER2));
                tiles.add(new Tile(3 , 4, GamePiece.PLAYER2));
                tiles.add(new Tile(3 , 5, GamePiece.PLAYER2));
                tiles.add(new Tile(3 , 6, GamePiece.PLAYER2));

                tiles.add(new Tile(0 , 0, GamePiece.PLAYER1));
                tiles.add(new Tile(4 , 1, GamePiece.PLAYER1));
                tiles.add(new Tile(4 , 2, GamePiece.PLAYER1));
                tiles.add(new Tile(4 , 3, GamePiece.PLAYER1));
                tiles.add(new Tile(4 , 4, GamePiece.PLAYER1));
                tiles.add(new Tile(4 , 5, GamePiece.PLAYER1));
                tiles.add(new Tile(4 , 6, GamePiece.PLAYER1));
                break;

            case 12:

                tiles.add(new Tile(3 , 1, GamePiece.PLAYER2));
                tiles.add(new Tile(4 , 2, GamePiece.PLAYER2));
                tiles.add(new Tile(3 , 3, GamePiece.PLAYER2));
                tiles.add(new Tile(4 , 4, GamePiece.PLAYER2));

                tiles.add(new Tile(7 , 7, GamePiece.PLAYER1));
                tiles.add(new Tile(0 , 0, GamePiece.PLAYER1));
                tiles.add(new Tile(4 , 1, GamePiece.PLAYER1));
                tiles.add(new Tile(3 , 2, GamePiece.PLAYER1));
                tiles.add(new Tile(4 , 3, GamePiece.PLAYER1));
                tiles.add(new Tile(3 , 4, GamePiece.PLAYER1));
                break;

            case 13:

                tiles.add(new Tile(3 , 1, GamePiece.PLAYER2));
                tiles.add(new Tile(4 , 2, GamePiece.PLAYER2));
                tiles.add(new Tile(3 , 3, GamePiece.PLAYER2));


                tiles.add(new Tile(7 , 7, GamePiece.PLAYER1));
                tiles.add(new Tile(0 , 0, GamePiece.PLAYER1));
                tiles.add(new Tile(4 , 1, GamePiece.PLAYER1));
                tiles.add(new Tile(7 , 5, GamePiece.PLAYER1));
                tiles.add(new Tile(7 , 6, GamePiece.PLAYER1));

                break;

            case 14:

                tiles.add(new Tile(5 , 1, GamePiece.PLAYER2));
                tiles.add(new Tile(4 , 2, GamePiece.PLAYER2));
                tiles.add(new Tile(3 , 3, GamePiece.PLAYER2));
                tiles.add(new Tile(0 , 0, GamePiece.PLAYER2));

                tiles.add(new Tile(7 , 7, GamePiece.PLAYER1));
                tiles.add(new Tile(7 , 0, GamePiece.PLAYER1));
                tiles.add(new Tile(4 , 1, GamePiece.PLAYER1));
                tiles.add(new Tile(3 , 2, GamePiece.PLAYER1));
                tiles.add(new Tile(4 , 3, GamePiece.PLAYER1));
                tiles.add(new Tile(3 , 4, GamePiece.PLAYER1));
                break;

            case 15:
                tiles.add(new Tile(midRow -1 ,midCol -1, GamePiece.PLAYER1));
                tiles.add(new Tile(midRow, midCol, GamePiece.PLAYER1));
                tiles.add(new Tile(midRow, midCol -1, GamePiece.PLAYER2));
                tiles.add(new Tile(midRow - 1 , midCol, GamePiece.PLAYER2));
                tiles.add(new Tile(0, 7, GamePiece.PLAYER2));
                tiles.add(new Tile(0 , 0, GamePiece.PLAYER1));
                tiles.add(new Tile(1 , 0, GamePiece.PLAYER1));
                tiles.add(new Tile(6 , 0, GamePiece.PLAYER1));
                tiles.add(new Tile(7 , 0, GamePiece.PLAYER1));

                break;

            case 16:
                tiles.add(new Tile(midRow -1 ,midCol -1, GamePiece.PLAYER1));
                tiles.add(new Tile(midRow, midCol, GamePiece.PLAYER1));
                tiles.add(new Tile(midRow, midCol -1, GamePiece.PLAYER2));
                tiles.add(new Tile(midRow - 1 , midCol, GamePiece.PLAYER2));
                tiles.add(new Tile(0 , 0, GamePiece.PLAYER1));
                tiles.add(new Tile(7 , 7, GamePiece.PLAYER1));
                tiles.add(new Tile(6 , 6, GamePiece.PLAYER1));
                break;

            case 17:
                tiles.add(new Tile(0 ,0, GamePiece.PLAYER1));
                tiles.add(new Tile(1 ,1, GamePiece.PLAYER1));
                tiles.add(new Tile(2 ,2, GamePiece.PLAYER1));
                tiles.add(new Tile(3 ,3, GamePiece.PLAYER1));
                tiles.add(new Tile(4 ,4, GamePiece.PLAYER1));
                tiles.add(new Tile(5 ,5, GamePiece.PLAYER1));
                tiles.add(new Tile(6 ,6, GamePiece.PLAYER1));
                tiles.add(new Tile(7 ,7, GamePiece.PLAYER1));

                tiles.add(new Tile(0 ,7, GamePiece.PLAYER2));
                tiles.add(new Tile(1 ,6, GamePiece.PLAYER2));
                tiles.add(new Tile(2 ,5, GamePiece.PLAYER2));
                tiles.add(new Tile(3 ,4, GamePiece.PLAYER2));
                tiles.add(new Tile(4 ,3, GamePiece.PLAYER2));
                tiles.add(new Tile(5 ,2, GamePiece.PLAYER2));
                tiles.add(new Tile(6 ,1, GamePiece.PLAYER2));
                tiles.add(new Tile(7 ,0, GamePiece.PLAYER2));

                break;

            case 18:
                tiles.add(new Tile(0 ,7, GamePiece.PLAYER1));
                tiles.add(new Tile(1 ,6, GamePiece.PLAYER1));
                tiles.add(new Tile(2 ,5, GamePiece.PLAYER1));
                tiles.add(new Tile(3 ,4, GamePiece.PLAYER1));
                tiles.add(new Tile(4 ,3, GamePiece.PLAYER1));

                tiles.add(new Tile(4 ,4, GamePiece.PLAYER2));
                tiles.add(new Tile(5 ,5, GamePiece.PLAYER2));
                tiles.add(new Tile(6 ,6, GamePiece.PLAYER2));
                break;

            case 19:
                tiles.add(new Tile(midRow -1 ,midCol -1, GamePiece.PLAYER1));
                tiles.add(new Tile(midRow, midCol, GamePiece.PLAYER1));
                tiles.add(new Tile(midRow, midCol -1, GamePiece.PLAYER2));
                tiles.add(new Tile(midRow - 1 , midCol, GamePiece.PLAYER2));
                tiles.add(new Tile(7, 7, GamePiece.PLAYER2));
                tiles.add(new Tile(7 , 0, GamePiece.PLAYER1));
                tiles.add(new Tile(7 , 1, GamePiece.PLAYER1));
                tiles.add(new Tile(7 , 2, GamePiece.PLAYER1));
                tiles.add(new Tile(6 , 0, GamePiece.PLAYER1));
                tiles.add(new Tile(5 , 0, GamePiece.PLAYER1));

            case 20:
                tiles.add(new Tile(1 ,2, GamePiece.PLAYER1));
                tiles.add(new Tile(1, 3, GamePiece.PLAYER1));
                tiles.add(new Tile(0 , 3, GamePiece.PLAYER2));
                tiles.add(new Tile(0 , 4, GamePiece.PLAYER2));
                tiles.add(new Tile(1 , 4, GamePiece.PLAYER2));
                tiles.add(new Tile(0, 0, GamePiece.PLAYER1));
                tiles.add(new Tile(7, 7, GamePiece.PLAYER1));
                tiles.add(new Tile(4, 4, GamePiece.PLAYER1));

                break;

            case 21:
            tiles.add(new Tile(1 ,2, GamePiece.PLAYER1));
            tiles.add(new Tile(1, 3, GamePiece.PLAYER1));
            tiles.add(new Tile(2 , 2, GamePiece.PLAYER2));
            tiles.add(new Tile(3 , 3, GamePiece.PLAYER2));
            tiles.add(new Tile(4 , 3, GamePiece.PLAYER2));
            tiles.add(new Tile(0, 0, GamePiece.PLAYER1));
            tiles.add(new Tile(7, 7, GamePiece.PLAYER1));

            break;

            case 22:
                tiles.add(new Tile(3 ,2, GamePiece.PLAYER1));
                tiles.add(new Tile(3, 3, GamePiece.PLAYER1));
                tiles.add(new Tile(3, 4, GamePiece.PLAYER1));
                tiles.add(new Tile(7, 7, GamePiece.PLAYER1));

                tiles.add(new Tile(4 ,2, GamePiece.PLAYER2));
                tiles.add(new Tile(4, 3, GamePiece.PLAYER2));
                tiles.add(new Tile(4, 4, GamePiece.PLAYER2));

                break;

            case 23:
                tiles.add(new Tile(3 , 3, GamePiece.PLAYER1));
                tiles.add(new Tile(4 , 4, GamePiece.PLAYER1));
                tiles.add(new Tile(3 , 4, GamePiece.PLAYER2));
                tiles.add(new Tile(4 , 3, GamePiece.PLAYER2));

                tiles.add(new Tile(0, 0, GamePiece.PLAYER1));
                tiles.add(new Tile(0, 1, GamePiece.PLAYER1));
                tiles.add(new Tile(7, 7, GamePiece.PLAYER1));
                tiles.add(new Tile(6, 7, GamePiece.PLAYER1));

                break;

            case 24:
                tiles.add(new Tile(midRow -1 ,midCol -1, GamePiece.PLAYER1));
                tiles.add(new Tile(midRow, midCol, GamePiece.PLAYER1));
                tiles.add(new Tile(midRow, midCol -1, GamePiece.PLAYER2));
                tiles.add(new Tile(midRow - 1 , midCol, GamePiece.PLAYER2));
                tiles.add(new Tile(7, 7, GamePiece.PLAYER2));
                tiles.add(new Tile(0 , 0, GamePiece.PLAYER1));
                tiles.add(new Tile(0 , 1, GamePiece.PLAYER1));
                tiles.add(new Tile(0 , 2, GamePiece.PLAYER1));
                tiles.add(new Tile(7 , 0, GamePiece.PLAYER1));
                tiles.add(new Tile(7 , 1, GamePiece.PLAYER1));
                tiles.add(new Tile(7 , 2, GamePiece.PLAYER1));

                break;

            case 25:
                tiles.add(new Tile(7 , 1, GamePiece.PLAYER2));
                tiles.add(new Tile(7 , 0, GamePiece.PLAYER1));
                tiles.add(new Tile(6 , 1, GamePiece.PLAYER2));
                tiles.add(new Tile(5 , 2, GamePiece.PLAYER2));
                tiles.add(new Tile(4 , 3, GamePiece.PLAYER2));
                tiles.add(new Tile(3 , 4, GamePiece.PLAYER2));
                tiles.add(new Tile(2 , 5, GamePiece.PLAYER2));
                tiles.add(new Tile(1 , 6, GamePiece.PLAYER2));
                tiles.add(new Tile(0 , 6, GamePiece.PLAYER2));

                break;

            case 26 :
                tiles.add(new Tile(3 , 2, GamePiece.PLAYER2));
                tiles.add(new Tile(4 , 3, GamePiece.PLAYER2));
                tiles.add(new Tile(3 , 4, GamePiece.PLAYER2));
                tiles.add(new Tile(4 , 5, GamePiece.PLAYER2));

                tiles.add(new Tile(3 , 3, GamePiece.PLAYER1));
                tiles.add(new Tile(4 , 2, GamePiece.PLAYER1));
                tiles.add(new Tile(3 , 5, GamePiece.PLAYER1));
                tiles.add(new Tile(4 , 4, GamePiece.PLAYER1));

                tiles.add(new Tile(0 , 0, GamePiece.PLAYER1));
                tiles.add(new Tile(0 , 7, GamePiece.PLAYER1));
                break;
        }

        return tiles;
    }

    public int getDifficulty()
    {
        int level = getCurrentLevel();
        switch (level)
        {
            case 1:
                return 6;

            case 2:
                return 4;

            case 3:
                return 2;

            case 4:
                return 3;

            case 5:
                return 1;

            case 6:
                return 1;
            case 7:
                return 0;
            case 8:
                return 0;
            case 9:
                return 1;
            case 10:
                return 0;
            case 11:
                return 0;
            case 12:
                return 0;
            case 13:
                return 0;
            case 14:
                return 0;
            case 15:
                return 0;
            case 16:
                return 0;
            case 17:
                return 0;
            case 18:
                return 0;
            case 19:
                return 0;
            case 20:
                return 0;
            case 21:
                return 0;
            case 22:
                return 0;
            case 23:
                return 0;
            case 24:
                return 0;
            case 25:
                return 0;
            case 26:
                return 0;
        }
        return -1;
    }

    public int getNumberOfPiecesPlayerOneStarted() // COMPUTER
    {
        int level = getCurrentLevel();
        switch (level)
        {
            case 1:
                return 2;
            case 2:
                return 4;
            case 3:
                return 4;
            case 4:
                return 5;
            case 5:
                return 8;
            case 6:
                return 5;
            case 7:
                return 4;
            case 8:
                return 6;
            case 9:
                return 7;
            case 10 :
                return 8;
            case 11 :
                return 7;
            case 12 :
                return 6;
            case 13 :
                return 5;
            case 14:
                return 6;
            case 15:
                return 6;
            case 16:
                return 5;
            case 17:
                return 8;
            case 18:
                return 5;
            case 19:
                return 7;
            case 20:
                return 5;
            case 21:
                return 4;
            case 22:
                return 4;
            case 23:
                return 6;
            case 24:
                return 8;
            case 25:
                return 1;
            case 26:
                return 6;

        }
        return -1;
    }

    public int getNumberOfPiecesPlayerTwoStarted()
    {
        int level = getCurrentLevel();
        switch (level)
        {
            case 1:
                return 2;
            case 2:
                return 4;
            case 3:
                return 2;
            case 4:
                return 3;
            case 5:
                return 8;
            case 6:
                return 2;
            case 7:
                return 3;
            case 8:
                return 3;
            case 9:
                return 3;
            case 10:
                return 3;
            case 11:
                return 7;
            case 12:
                return 4;
            case 13 :
                return 3;
            case 14:
                return 4;
            case 15:
                return 3;
            case 16:
                return 2;
            case 17:
                return 8;
            case 18:
                return 3;
            case 19:
                return 3;
            case 20:
                return 3;
            case 21:
                return 3;
            case 22:
                return 3;
            case 23:
                return 2;
            case 24:
                return 3;
            case 25:
                return 8;
            case 26:
                return 4;
        }
        return -1;
    }

    public int getMaxLevel() {
        return maxLevel;
    }

    public boolean isLastLevel()
    {
        return getCurrentLevel() == maxLevel;
    }

    public void updateCurrentLevelIfMaxLevelsChanged()
    {
        int level = getCurrentLevel();
        boolean finishTheLastLevel = sharedPreferences.getBoolean(FINISHED_LAST_LEVEL, false);
        if(finishTheLastLevel && level < maxLevel)
        {
            setCurrentLevel(level + 1);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(FINISHED_LAST_LEVEL, false);
            editor.apply();
        }
    }
}

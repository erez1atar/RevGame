package reversi.android.game.com.r.reversi.utility;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.ArrayList;

import reversi.android.game.com.r.reversi.board.GamePiece;
import reversi.android.game.com.r.reversi.board.Tile;

/**
 * Created by Erez on 02/05/2017.
 */

public class LevelsModeManager
{
    private SharedPreferences sharedPreferences;
    private static final String GREATEST_LEVEL = "LEVEL";
    private static final String FINISHED_LAST_LEVEL = "LAST_LEVEL_FINISHED";
    int maxLevel = 64;

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

    public int getGreatestLevel()
    {
        return sharedPreferences.getInt(GREATEST_LEVEL, 1);
    }

    public void forceSetGreatestLevel(int level)
    {
        if(level > maxLevel)
        {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(FINISHED_LAST_LEVEL, true);
            editor.apply();
            return;
        }
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(GREATEST_LEVEL, level);
        editor.apply();


    }

    public void trySetGreatestLevel(int level)
    {
        if(level > maxLevel)
        {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(FINISHED_LAST_LEVEL, true);
            editor.apply();
            return;
        }
        int lastGreatest = this.getGreatestLevel();
        if(level > lastGreatest) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(GREATEST_LEVEL, level);
            editor.apply();
        }

    }

    public ArrayList<Tile> getStartGameDetails(int level)
    {
        ArrayList<Tile> tiles = new ArrayList<>();
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

                tiles.add(new Tile(7 , 7, GamePiece.BLOCK));

                break;
            case 3:
                tiles.add(new Tile(midRow -1 ,midCol -1, GamePiece.PLAYER1));
                tiles.add(new Tile(midRow, midCol, GamePiece.PLAYER1));
                tiles.add(new Tile(midRow, midCol -1, GamePiece.PLAYER2));
                tiles.add(new Tile(midRow - 1 , midCol, GamePiece.PLAYER2));
                tiles.add(new Tile(0 , 0, GamePiece.PLAYER1));
                tiles.add(new Tile(numOfRows - 1 , numOfCols - 1, GamePiece.PLAYER1));
                break;

            case 4: //10x10
                tiles.add(new Tile(midRow -1 ,midCol -1, GamePiece.PLAYER1));
                tiles.add(new Tile(midRow, midCol, GamePiece.PLAYER1));
                tiles.add(new Tile(midRow, midCol -1, GamePiece.PLAYER2));
                tiles.add(new Tile(midRow - 1 , midCol, GamePiece.PLAYER2));
                tiles.add(new Tile(0 , 0, GamePiece.PLAYER2));
                tiles.add(new Tile(9 , 0, GamePiece.PLAYER1));
                tiles.add(new Tile(9 , 3, GamePiece.PLAYER1));
                tiles.add(new Tile(9 , 7, GamePiece.PLAYER1));

                tiles.add(new Tile(2 , 5, GamePiece.BLOCK));
                tiles.add(new Tile(3 , 5, GamePiece.BLOCK));
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

                tiles.add(new Tile(7 , 4, GamePiece.BLOCK));
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

                tiles.add(new Tile(2 , 1, GamePiece.BLOCK));
                tiles.add(new Tile(3 , 1, GamePiece.BLOCK));
                tiles.add(new Tile(9 , 9, GamePiece.BLOCK));
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

                tiles.add(new Tile(2 ,3, GamePiece.BLOCK));

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

            case 19: //10x10
                tiles.add(new Tile(midRow -1 ,midCol -1, GamePiece.PLAYER1));
                tiles.add(new Tile(midRow, midCol, GamePiece.PLAYER1));
                tiles.add(new Tile(midRow, midCol -1, GamePiece.PLAYER2));
                tiles.add(new Tile(midRow - 1 , midCol, GamePiece.PLAYER2));
                tiles.add(new Tile(9, 9, GamePiece.PLAYER2));
                tiles.add(new Tile(9 , 0, GamePiece.PLAYER1));
                tiles.add(new Tile(9 , 1, GamePiece.PLAYER1));
                tiles.add(new Tile(9 , 2, GamePiece.PLAYER1));
                tiles.add(new Tile(8 , 0, GamePiece.PLAYER1));
                tiles.add(new Tile(7 , 0, GamePiece.PLAYER1));

                break;

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

            case 26 : // 10x10
                tiles.add(new Tile(2 ,2, GamePiece.PLAYER1));
                tiles.add(new Tile(3 ,3, GamePiece.PLAYER1));
                tiles.add(new Tile(2 , 3, GamePiece.PLAYER2));
                tiles.add(new Tile(3  , 2, GamePiece.PLAYER2));

                tiles.add(new Tile(2 ,6, GamePiece.PLAYER1));
                tiles.add(new Tile(3 ,7, GamePiece.PLAYER1));
                tiles.add(new Tile(2 , 7, GamePiece.PLAYER2));
                tiles.add(new Tile(3  , 6, GamePiece.PLAYER2));

                tiles.add(new Tile(0 ,0, GamePiece.PLAYER1));
                tiles.add(new Tile(9 ,9, GamePiece.PLAYER1));

                break;

            case 27:
                tiles.add(new Tile(midRow -2 ,midCol -2, GamePiece.PLAYER1));
                tiles.add(new Tile(midRow -1 , midCol -1, GamePiece.PLAYER1));
                tiles.add(new Tile(midRow -1 , midCol -2, GamePiece.PLAYER2));
                tiles.add(new Tile(midRow - 2 , midCol -1, GamePiece.PLAYER2));
                tiles.add(new Tile(7 , 0, GamePiece.PLAYER1));
                tiles.add(new Tile(0 , 7, GamePiece.PLAYER1));
                tiles.add(new Tile(1 , 7, GamePiece.PLAYER1));
                tiles.add(new Tile(2 , 7, GamePiece.PLAYER1));
                tiles.add(new Tile(3 , 7, GamePiece.PLAYER1));
                tiles.add(new Tile(4 , 7, GamePiece.PLAYER1));
                break;

            case 28:

                tiles.add(new Tile(3 , 2, GamePiece.PLAYER2));
                tiles.add(new Tile(3 , 3, GamePiece.PLAYER2));
                tiles.add(new Tile(3 , 4, GamePiece.PLAYER2));

                tiles.add(new Tile(5 , 2, GamePiece.PLAYER2));
                tiles.add(new Tile(5 , 3, GamePiece.PLAYER2));
                tiles.add(new Tile(5 , 4, GamePiece.PLAYER2));

                tiles.add(new Tile(4 , 2, GamePiece.PLAYER2));
                tiles.add(new Tile(4 , 4, GamePiece.PLAYER2));


                tiles.add(new Tile(4 , 3, GamePiece.PLAYER1));
                tiles.add(new Tile(7 , 7, GamePiece.PLAYER1));
                tiles.add(new Tile(0 , 0, GamePiece.PLAYER1));

                break;

            case 29:

                tiles.add(new Tile(3 , 3, GamePiece.PLAYER2));
                tiles.add(new Tile(3 , 4, GamePiece.PLAYER2));
                tiles.add(new Tile(3 , 5, GamePiece.PLAYER2));

                tiles.add(new Tile(2 , 3, GamePiece.PLAYER1));
                tiles.add(new Tile(2 , 4, GamePiece.PLAYER1));


                tiles.add(new Tile(0 , 7, GamePiece.PLAYER1));
                tiles.add(new Tile(1 , 7, GamePiece.PLAYER1));
                tiles.add(new Tile(2 , 7, GamePiece.PLAYER1));
                tiles.add(new Tile(5 , 7, GamePiece.PLAYER1));
                tiles.add(new Tile(6 , 7, GamePiece.PLAYER1));
                tiles.add(new Tile(7 , 7, GamePiece.PLAYER1));
                break;

            case 30:

                tiles.add(new Tile(0 , 0, GamePiece.PLAYER1));
                tiles.add(new Tile(1 , 1, GamePiece.PLAYER1));
                tiles.add(new Tile(2 , 2, GamePiece.PLAYER1));
                tiles.add(new Tile(3 , 3, GamePiece.PLAYER1));
                tiles.add(new Tile(4 , 4, GamePiece.PLAYER1));
                tiles.add(new Tile(5 , 5, GamePiece.PLAYER1));

                tiles.add(new Tile(1 , 0, GamePiece.PLAYER2));
                tiles.add(new Tile(2 , 1, GamePiece.PLAYER2));
                tiles.add(new Tile(3 , 2, GamePiece.PLAYER2));
                tiles.add(new Tile(4 , 3, GamePiece.PLAYER2));
                tiles.add(new Tile(5 , 4, GamePiece.PLAYER2));
                tiles.add(new Tile(6 , 5, GamePiece.PLAYER2));
                break;

            case 31:

                tiles.add(new Tile(midRow  ,midCol -2, GamePiece.PLAYER1));
                tiles.add(new Tile(midRow + 1 , midCol -1, GamePiece.PLAYER1));
                tiles.add(new Tile(midRow + 1 , midCol -2, GamePiece.PLAYER2));
                tiles.add(new Tile(midRow  , midCol -1, GamePiece.PLAYER2));

                tiles.add(new Tile(0 , 0, GamePiece.PLAYER2));
                tiles.add(new Tile(0 , 1, GamePiece.PLAYER2));
                tiles.add(new Tile(0 , 2, GamePiece.PLAYER2));
                tiles.add(new Tile(0 , 3, GamePiece.PLAYER2));
                tiles.add(new Tile(0 , 4, GamePiece.PLAYER2));
                tiles.add(new Tile(0 , 5, GamePiece.PLAYER2));
                tiles.add(new Tile(0 , 6, GamePiece.PLAYER2));
                tiles.add(new Tile(0 , 7, GamePiece.PLAYER2));

                tiles.add(new Tile(7 , 0, GamePiece.PLAYER1));
                tiles.add(new Tile(7 , 1, GamePiece.PLAYER1));
                tiles.add(new Tile(7 , 2, GamePiece.PLAYER1));
                tiles.add(new Tile(7 , 3, GamePiece.PLAYER1));
                tiles.add(new Tile(7 , 4, GamePiece.PLAYER1));
                tiles.add(new Tile(7 , 5, GamePiece.PLAYER1));
                tiles.add(new Tile(7 , 6, GamePiece.PLAYER1));
                tiles.add(new Tile(7 , 7, GamePiece.PLAYER1));
                break;

            case 32: // 10x10

                tiles.add(new Tile(2 ,2, GamePiece.PLAYER1));
                tiles.add(new Tile(3 ,3, GamePiece.PLAYER1));
                tiles.add(new Tile(2 , 3, GamePiece.PLAYER2));
                tiles.add(new Tile(3  , 2, GamePiece.PLAYER2));

                tiles.add(new Tile(6 ,6, GamePiece.PLAYER1));
                tiles.add(new Tile(7 ,7, GamePiece.PLAYER1));
                tiles.add(new Tile(6 , 7, GamePiece.PLAYER2));
                tiles.add(new Tile(7  , 6, GamePiece.PLAYER2));

                tiles.add(new Tile(2 ,6, GamePiece.PLAYER1));
                tiles.add(new Tile(3 ,7, GamePiece.PLAYER1));
                tiles.add(new Tile(2 , 7, GamePiece.PLAYER2));
                tiles.add(new Tile(3 , 6, GamePiece.PLAYER2));
                break;

            case 33:

                tiles.add(new Tile(3 , 2, GamePiece.PLAYER2));
                tiles.add(new Tile(4 , 3, GamePiece.PLAYER2));
                tiles.add(new Tile(3 , 4, GamePiece.PLAYER2));
                tiles.add(new Tile(4 , 5, GamePiece.PLAYER2));

                tiles.add(new Tile(3 , 3, GamePiece.PLAYER1));
                tiles.add(new Tile(4 , 2, GamePiece.PLAYER1));
                tiles.add(new Tile(3 , 5, GamePiece.PLAYER1));
                tiles.add(new Tile(4 , 4, GamePiece.PLAYER1));

                tiles.add(new Tile(0 , 0, GamePiece.PLAYER1));

                break;

            case 34: // 10x10

                tiles.add(new Tile(2 ,2, GamePiece.PLAYER1));
                tiles.add(new Tile(3 ,3, GamePiece.PLAYER1));
                tiles.add(new Tile(4 ,4, GamePiece.PLAYER1));
                tiles.add(new Tile(5 ,5, GamePiece.PLAYER1));
                tiles.add(new Tile(6 ,6, GamePiece.PLAYER1));

                tiles.add(new Tile(3 ,2, GamePiece.PLAYER2));
                tiles.add(new Tile(4 ,3, GamePiece.PLAYER2));
                tiles.add(new Tile(5 ,4, GamePiece.PLAYER2));
                tiles.add(new Tile(6 ,5, GamePiece.PLAYER2));
                tiles.add(new Tile(7 ,6, GamePiece.PLAYER2));


                tiles.add(new Tile(0 , 0, GamePiece.PLAYER2));

                tiles.add(new Tile(9 , 0, GamePiece.PLAYER1));
                tiles.add(new Tile(9 , 1, GamePiece.PLAYER1));
                tiles.add(new Tile(9 , 2, GamePiece.PLAYER1));

                tiles.add(new Tile(9 , 7, GamePiece.PLAYER1));
                tiles.add(new Tile(9 , 8, GamePiece.PLAYER1));
                tiles.add(new Tile(9 , 9, GamePiece.PLAYER1));
            break;

            case 35: // 10x10

                tiles.add(new Tile(5 ,2, GamePiece.PLAYER1));
                tiles.add(new Tile(6 ,2, GamePiece.PLAYER2));
                tiles.add(new Tile(6 ,3, GamePiece.PLAYER1));
                tiles.add(new Tile(5 ,3, GamePiece.PLAYER2));
                tiles.add(new Tile(5 ,4, GamePiece.PLAYER1));
                tiles.add(new Tile(6 ,4, GamePiece.PLAYER2));
                tiles.add(new Tile(6 ,5, GamePiece.PLAYER1));
                tiles.add(new Tile(5 ,5, GamePiece.PLAYER2));
                tiles.add(new Tile(5 ,6, GamePiece.PLAYER1));
                tiles.add(new Tile(6 ,6, GamePiece.PLAYER2));

                tiles.add(new Tile(0 , 4, GamePiece.PLAYER1));
                tiles.add(new Tile(0 , 5, GamePiece.PLAYER1));
                tiles.add(new Tile(0 , 6, GamePiece.PLAYER1));
                tiles.add(new Tile(0 , 7, GamePiece.PLAYER1));
                tiles.add(new Tile(0 , 8, GamePiece.PLAYER1));
                tiles.add(new Tile(0 , 9, GamePiece.PLAYER1));

                break;

            case 36: // 10x10

            tiles.add(new Tile(1 ,2, GamePiece.PLAYER1));
            tiles.add(new Tile(2 ,2, GamePiece.PLAYER2));
            tiles.add(new Tile(4 ,3, GamePiece.PLAYER1));
            tiles.add(new Tile(3 ,3, GamePiece.PLAYER2));

            tiles.add(new Tile(6 ,7, GamePiece.PLAYER1));
            tiles.add(new Tile(6 ,6, GamePiece.PLAYER2));
            tiles.add(new Tile(5 ,7, GamePiece.PLAYER1));
            tiles.add(new Tile(5 ,6, GamePiece.PLAYER2));

            tiles.add(new Tile(0 , 0, GamePiece.PLAYER1));
            tiles.add(new Tile(9 , 9, GamePiece.PLAYER1));
            tiles.add(new Tile(8 , 9, GamePiece.PLAYER1));
            tiles.add(new Tile(9 , 8, GamePiece.PLAYER1));

            break;

            case 37: // 10x10

                tiles.add(new Tile(1 ,2, GamePiece.PLAYER1));
                tiles.add(new Tile(2 ,2, GamePiece.PLAYER2));
                tiles.add(new Tile(4 ,3, GamePiece.PLAYER1));
                tiles.add(new Tile(3 ,3, GamePiece.PLAYER2));

                tiles.add(new Tile(1 , 9, GamePiece.PLAYER1));
                tiles.add(new Tile(2 , 9, GamePiece.PLAYER1));
                tiles.add(new Tile(3 , 9, GamePiece.PLAYER1));
                tiles.add(new Tile(4 , 9, GamePiece.PLAYER1));
                tiles.add(new Tile(5 , 9, GamePiece.PLAYER1));
                tiles.add(new Tile(6 , 9, GamePiece.PLAYER1));
                tiles.add(new Tile(7 , 9, GamePiece.PLAYER1));

                break;

            case 38: // 12x12

                tiles.add(new Tile(2 ,2, GamePiece.PLAYER2));
                tiles.add(new Tile(2 ,3, GamePiece.PLAYER2));
                tiles.add(new Tile(2 ,4, GamePiece.PLAYER2));
                tiles.add(new Tile(2 ,5, GamePiece.PLAYER2));
                tiles.add(new Tile(2 ,6, GamePiece.PLAYER2));

                tiles.add(new Tile(3 ,2, GamePiece.PLAYER1));
                tiles.add(new Tile(3 ,3, GamePiece.PLAYER1));
                tiles.add(new Tile(3 ,4, GamePiece.PLAYER1));
                tiles.add(new Tile(3 ,5, GamePiece.PLAYER1));
                tiles.add(new Tile(3 ,6, GamePiece.PLAYER1));

                tiles.add(new Tile(11 , 11, GamePiece.PLAYER1));
                tiles.add(new Tile(10 , 11, GamePiece.PLAYER1));
                tiles.add(new Tile(9 , 11, GamePiece.PLAYER1));


                break;

            case 39: // 12x12

                tiles.add(new Tile(2 ,2, GamePiece.PLAYER2));
                tiles.add(new Tile(3 ,3, GamePiece.PLAYER2));
                tiles.add(new Tile(2 ,4, GamePiece.PLAYER2));
                tiles.add(new Tile(2 ,5, GamePiece.PLAYER2));
                tiles.add(new Tile(2 ,6, GamePiece.PLAYER2));

                tiles.add(new Tile(3 ,2, GamePiece.PLAYER1));
                tiles.add(new Tile(2 ,3, GamePiece.PLAYER1));
                tiles.add(new Tile(3 ,4, GamePiece.PLAYER1));
                tiles.add(new Tile(3 ,5, GamePiece.PLAYER1));
                tiles.add(new Tile(3 ,6, GamePiece.PLAYER1));

                tiles.add(new Tile(11 , 8, GamePiece.PLAYER1));
                tiles.add(new Tile(0 , 0, GamePiece.PLAYER1));
                tiles.add(new Tile(1 , 0, GamePiece.PLAYER1));
                tiles.add(new Tile(2 , 0, GamePiece.PLAYER1));
                tiles.add(new Tile(11 , 9, GamePiece.PLAYER1));
                tiles.add(new Tile(11 , 10, GamePiece.PLAYER1));
                tiles.add(new Tile(11 , 11, GamePiece.PLAYER1));


                break;

            case 40: // 12x12

                tiles.add(new Tile(2 ,2, GamePiece.PLAYER2));
                tiles.add(new Tile(3 ,3, GamePiece.PLAYER2));
                tiles.add(new Tile(4 ,4, GamePiece.PLAYER2));
                tiles.add(new Tile(5 ,5, GamePiece.PLAYER2));
                tiles.add(new Tile(6 ,6, GamePiece.PLAYER2));

                tiles.add(new Tile(0 ,0, GamePiece.PLAYER1));
                tiles.add(new Tile(1 ,2, GamePiece.PLAYER1));
                tiles.add(new Tile(2 ,3, GamePiece.PLAYER1));
                tiles.add(new Tile(3 ,4, GamePiece.PLAYER1));
                tiles.add(new Tile(4 ,5, GamePiece.PLAYER1));
                tiles.add(new Tile(5 ,6, GamePiece.PLAYER1));
                tiles.add(new Tile(7 ,8, GamePiece.PLAYER1));
                tiles.add(new Tile(8 , 9, GamePiece.PLAYER1));
                tiles.add(new Tile(9 , 10, GamePiece.PLAYER1));
                tiles.add(new Tile(10 , 11, GamePiece.PLAYER1));



                break;

            case 41: // 12x12

                tiles.add(new Tile(2 ,2, GamePiece.PLAYER1));
                tiles.add(new Tile(3, 3, GamePiece.PLAYER1));
                tiles.add(new Tile(3, 2, GamePiece.PLAYER2));
                tiles.add(new Tile(2 , 3, GamePiece.PLAYER2));

                tiles.add(new Tile(6 ,6, GamePiece.PLAYER1));
                tiles.add(new Tile(7, 7, GamePiece.PLAYER1));
                tiles.add(new Tile(7, 6, GamePiece.PLAYER2));
                tiles.add(new Tile(6 , 7, GamePiece.PLAYER2));

                tiles.add(new Tile(2 ,7, GamePiece.PLAYER1));
                tiles.add(new Tile(3, 8, GamePiece.PLAYER1));
                tiles.add(new Tile(3, 7, GamePiece.PLAYER2));
                tiles.add(new Tile(2 , 8, GamePiece.PLAYER2));

                tiles.add(new Tile(11 ,11, GamePiece.PLAYER1));

                break;

            case 42: // 12x12

                tiles.add(new Tile(4 ,2, GamePiece.PLAYER1));
                tiles.add(new Tile(5, 3, GamePiece.PLAYER1));
                tiles.add(new Tile(5, 2, GamePiece.PLAYER2));
                tiles.add(new Tile(4 , 3, GamePiece.PLAYER2));

                tiles.add(new Tile(6 ,6, GamePiece.PLAYER1));
                tiles.add(new Tile(7, 7, GamePiece.PLAYER1));
                tiles.add(new Tile(7, 6, GamePiece.PLAYER2));
                tiles.add(new Tile(6 , 7, GamePiece.PLAYER2));

                tiles.add(new Tile(11 ,11, GamePiece.PLAYER1));
                tiles.add(new Tile(10 ,11, GamePiece.PLAYER1));
                tiles.add(new Tile(9 ,11, GamePiece.PLAYER1));
                tiles.add(new Tile(8 ,11, GamePiece.PLAYER1));
                tiles.add(new Tile(7 ,11, GamePiece.PLAYER1));
                tiles.add(new Tile(6 ,11, GamePiece.PLAYER1));
                tiles.add(new Tile(5 ,11, GamePiece.PLAYER1));
                tiles.add(new Tile(4 ,11, GamePiece.PLAYER1));
                tiles.add(new Tile(3 ,11, GamePiece.PLAYER1));

                tiles.add(new Tile(11 ,0, GamePiece.PLAYER2));
                tiles.add(new Tile(10 ,0, GamePiece.PLAYER2));
                tiles.add(new Tile(9 ,0, GamePiece.PLAYER2));
                tiles.add(new Tile(8 ,0, GamePiece.PLAYER2));
                tiles.add(new Tile(7 ,0, GamePiece.PLAYER2));
                tiles.add(new Tile(6 ,0, GamePiece.PLAYER2));

                break;

            case 43: // 14x14

                tiles.add(new Tile(4 ,4, GamePiece.PLAYER1));
                tiles.add(new Tile(4 ,5, GamePiece.PLAYER1));
                tiles.add(new Tile(4 ,6, GamePiece.PLAYER1));
                tiles.add(new Tile(4 ,7, GamePiece.PLAYER1));
                tiles.add(new Tile(4 ,8, GamePiece.PLAYER1));
                tiles.add(new Tile(4 ,9, GamePiece.PLAYER1));
                tiles.add(new Tile(4 ,10, GamePiece.PLAYER1));
                tiles.add(new Tile(4 ,11, GamePiece.PLAYER1));
                tiles.add(new Tile(4 ,12, GamePiece.PLAYER1));

                tiles.add(new Tile(5 ,4, GamePiece.PLAYER2));
                tiles.add(new Tile(5 ,5, GamePiece.PLAYER2));
                tiles.add(new Tile(5 ,6, GamePiece.PLAYER2));
                tiles.add(new Tile(5 ,7, GamePiece.PLAYER2));
                tiles.add(new Tile(5 ,8, GamePiece.PLAYER2));
                tiles.add(new Tile(5 ,9, GamePiece.PLAYER2));
                tiles.add(new Tile(5 ,10, GamePiece.PLAYER2));
                tiles.add(new Tile(5 ,11, GamePiece.PLAYER2));
                tiles.add(new Tile(5 ,12, GamePiece.PLAYER2));

                tiles.add(new Tile(0 ,0, GamePiece.PLAYER1));
                tiles.add(new Tile(0 ,1, GamePiece.PLAYER1));
                tiles.add(new Tile(0 ,2, GamePiece.PLAYER1));
                tiles.add(new Tile(0 ,3, GamePiece.PLAYER1));
                tiles.add(new Tile(0 ,4, GamePiece.PLAYER1));
                tiles.add(new Tile(0 ,5, GamePiece.PLAYER1));
                tiles.add(new Tile(13 ,0, GamePiece.PLAYER1));

                break;

            case 44: // 14x14

                tiles.add(new Tile(4 ,4, GamePiece.PLAYER1));
                tiles.add(new Tile(5 ,5, GamePiece.PLAYER1));
                tiles.add(new Tile(5 ,4, GamePiece.PLAYER2));
                tiles.add(new Tile(4 ,5, GamePiece.PLAYER2));

                tiles.add(new Tile(8 ,8, GamePiece.PLAYER1));
                tiles.add(new Tile(9 ,9, GamePiece.PLAYER1));
                tiles.add(new Tile(8 ,9, GamePiece.PLAYER2));
                tiles.add(new Tile(9 ,8, GamePiece.PLAYER2));

                tiles.add(new Tile(8 ,4, GamePiece.PLAYER1));
                tiles.add(new Tile(9 ,5, GamePiece.PLAYER1));
                tiles.add(new Tile(9 ,4, GamePiece.PLAYER2));
                tiles.add(new Tile(8 ,5, GamePiece.PLAYER2));
                tiles.add(new Tile(10 ,4, GamePiece.PLAYER1));
                tiles.add(new Tile(11 ,5, GamePiece.PLAYER1));
                tiles.add(new Tile(11 ,4, GamePiece.PLAYER2));
                tiles.add(new Tile(10 ,5, GamePiece.PLAYER2));


                tiles.add(new Tile(0 ,0, GamePiece.PLAYER1));
                tiles.add(new Tile(0 ,13, GamePiece.PLAYER1));
                tiles.add(new Tile(0 ,12, GamePiece.PLAYER1));
                tiles.add(new Tile(1 ,13, GamePiece.PLAYER1));

                break;

            case 45: // 14x14

                tiles.add(new Tile(4 ,4, GamePiece.PLAYER1));
                tiles.add(new Tile(5 ,5, GamePiece.PLAYER1));
                tiles.add(new Tile(5 ,4, GamePiece.PLAYER2));
                tiles.add(new Tile(4 ,5, GamePiece.PLAYER2));

                tiles.add(new Tile(8 ,4, GamePiece.PLAYER1));
                tiles.add(new Tile(8 ,5, GamePiece.PLAYER1));
                tiles.add(new Tile(8 ,6, GamePiece.PLAYER1));
                tiles.add(new Tile(8 ,7, GamePiece.PLAYER1));
                tiles.add(new Tile(8 ,8, GamePiece.PLAYER1));

                tiles.add(new Tile(9 ,4, GamePiece.PLAYER2));
                tiles.add(new Tile(9 ,5, GamePiece.PLAYER2));
                tiles.add(new Tile(9 ,6, GamePiece.PLAYER2));
                tiles.add(new Tile(9 ,7, GamePiece.PLAYER2));
                tiles.add(new Tile(9 ,8, GamePiece.PLAYER2));

                tiles.add(new Tile(0 ,0, GamePiece.PLAYER1));
                tiles.add(new Tile(0 ,1, GamePiece.PLAYER1));
                tiles.add(new Tile(0 ,2, GamePiece.PLAYER1));
                tiles.add(new Tile(0 ,3, GamePiece.PLAYER1));
                tiles.add(new Tile(0 ,4, GamePiece.PLAYER1));
                tiles.add(new Tile(0 ,5, GamePiece.PLAYER1));
                tiles.add(new Tile(0 ,6, GamePiece.PLAYER1));
                tiles.add(new Tile(0 ,7, GamePiece.PLAYER1));
                tiles.add(new Tile(0 ,8, GamePiece.PLAYER1));
                tiles.add(new Tile(0 ,9, GamePiece.PLAYER1));
                tiles.add(new Tile(0 ,10, GamePiece.PLAYER1));


                break;
            case 46: // 12x12

                tiles.add(new Tile(5 ,6, GamePiece.PLAYER1));
                tiles.add(new Tile(5 ,5, GamePiece.PLAYER2));
                tiles.add(new Tile(4 ,6, GamePiece.PLAYER1));
                tiles.add(new Tile(4 ,5, GamePiece.PLAYER2));

                tiles.add(new Tile(7 ,8, GamePiece.PLAYER1));
                tiles.add(new Tile(7 ,7, GamePiece.PLAYER2));
                tiles.add(new Tile(6 ,8, GamePiece.PLAYER1));
                tiles.add(new Tile(6 ,7, GamePiece.PLAYER2));

                tiles.add(new Tile(11 , 11, GamePiece.PLAYER1));
                tiles.add(new Tile(10 , 11, GamePiece.PLAYER1));
                tiles.add(new Tile(11 , 10, GamePiece.PLAYER1));
                tiles.add(new Tile(10 , 10, GamePiece.PLAYER1));

                tiles.add(new Tile(0 , 0, GamePiece.PLAYER1));
                tiles.add(new Tile(1 , 0, GamePiece.PLAYER1));
                tiles.add(new Tile(0 , 1, GamePiece.PLAYER1));
                tiles.add(new Tile(1 , 1, GamePiece.PLAYER1));

                tiles.add(new Tile(1 , 9, GamePiece.BLOCK));
                tiles.add(new Tile(0 , 9, GamePiece.BLOCK));
                tiles.add(new Tile(1 , 8, GamePiece.BLOCK));
                tiles.add(new Tile(0 , 8, GamePiece.BLOCK));

                break;

            case 47: // 10x10

                tiles.add(new Tile(3 , 1, GamePiece.PLAYER1));
                tiles.add(new Tile(3 , 2, GamePiece.PLAYER1));
                tiles.add(new Tile(3 , 3, GamePiece.PLAYER1));
                tiles.add(new Tile(3 , 4, GamePiece.PLAYER1));
                tiles.add(new Tile(4 , 1, GamePiece.PLAYER2));
                tiles.add(new Tile(4 , 2, GamePiece.PLAYER2));
                tiles.add(new Tile(4 , 3, GamePiece.PLAYER2));
                tiles.add(new Tile(4 , 4, GamePiece.PLAYER2));
                tiles.add(new Tile(4 , 5, GamePiece.PLAYER2));
                tiles.add(new Tile(4 , 6, GamePiece.PLAYER2));

                tiles.add(new Tile(0 , 0, GamePiece.PLAYER2));

                tiles.add(new Tile(9 , 0, GamePiece.PLAYER1));
                tiles.add(new Tile(9 , 1, GamePiece.PLAYER1));
                tiles.add(new Tile(9 , 2, GamePiece.PLAYER1));
                tiles.add(new Tile(9 , 7, GamePiece.PLAYER1));
                tiles.add(new Tile(9 , 8, GamePiece.PLAYER1));
                tiles.add(new Tile(9 , 9, GamePiece.PLAYER1));

                tiles.add(new Tile(8 , 0, GamePiece.PLAYER1));
                tiles.add(new Tile(8 , 1, GamePiece.PLAYER1));
                tiles.add(new Tile(8 , 2, GamePiece.PLAYER1));
                tiles.add(new Tile(8 , 7, GamePiece.PLAYER1));
                tiles.add(new Tile(8 , 8, GamePiece.PLAYER1));
                tiles.add(new Tile(8 , 9, GamePiece.PLAYER1));

                break;

            case 48: // 12ס12

                tiles.add(new Tile(8 , 6, GamePiece.PLAYER1));
                tiles.add(new Tile(9 , 6, GamePiece.PLAYER2));
                tiles.add(new Tile(9 , 7, GamePiece.PLAYER1));
                tiles.add(new Tile(8 , 7, GamePiece.PLAYER2));
                tiles.add(new Tile(8 , 8, GamePiece.PLAYER1));
                tiles.add(new Tile(9 , 8, GamePiece.PLAYER2));
                tiles.add(new Tile(9 , 9, GamePiece.PLAYER1));
                tiles.add(new Tile(8 , 9, GamePiece.PLAYER2));


                tiles.add(new Tile(0 , 0, GamePiece.PLAYER2));

                tiles.add(new Tile(0 , 11, GamePiece.PLAYER1));
                tiles.add(new Tile(0 , 10, GamePiece.PLAYER1));
                tiles.add(new Tile(0 , 9, GamePiece.PLAYER1));
                tiles.add(new Tile(0 , 8, GamePiece.PLAYER1));

                tiles.add(new Tile(4 , 4, GamePiece.PLAYER1));
                tiles.add(new Tile(5 , 5, GamePiece.PLAYER1));
                tiles.add(new Tile(4 , 5, GamePiece.PLAYER1));
                tiles.add(new Tile(5 , 4, GamePiece.PLAYER1));

                tiles.add(new Tile(11 , 11, GamePiece.PLAYER1));
                tiles.add(new Tile(10 , 11, GamePiece.PLAYER1));
                tiles.add(new Tile(11 , 10, GamePiece.PLAYER1));
                tiles.add(new Tile(10 , 10, GamePiece.PLAYER1));

                break;

            case 49: // 12x12


                tiles.add(new Tile(0 , 0, GamePiece.PLAYER1));
                tiles.add(new Tile(0 , 1, GamePiece.PLAYER1));
                tiles.add(new Tile(0 , 2, GamePiece.PLAYER1));
                tiles.add(new Tile(0 , 3, GamePiece.PLAYER1));
                tiles.add(new Tile(0 , 4, GamePiece.PLAYER1));
                tiles.add(new Tile(1 , 0, GamePiece.PLAYER1));
                tiles.add(new Tile(2 , 0, GamePiece.PLAYER1));
                tiles.add(new Tile(3 , 0, GamePiece.PLAYER1));
                tiles.add(new Tile(4 , 0, GamePiece.PLAYER1));

                tiles.add(new Tile(11 , 11, GamePiece.PLAYER2));
                tiles.add(new Tile(11 , 10, GamePiece.PLAYER2));
                tiles.add(new Tile(11 , 9, GamePiece.PLAYER2));
                tiles.add(new Tile(11 , 8, GamePiece.PLAYER2));
                tiles.add(new Tile(11 , 7, GamePiece.PLAYER2));
                tiles.add(new Tile(10 , 11, GamePiece.PLAYER2));
                tiles.add(new Tile(9 , 11, GamePiece.PLAYER2));
                tiles.add(new Tile(8 , 11, GamePiece.PLAYER2));
                tiles.add(new Tile(7 , 11, GamePiece.PLAYER2));

                tiles.add(new Tile(5 , 5, GamePiece.PLAYER2));
                tiles.add(new Tile(6 , 6, GamePiece.PLAYER2));
                tiles.add(new Tile(7 , 7, GamePiece.PLAYER2));
                tiles.add(new Tile(5 , 6, GamePiece.PLAYER1));
                tiles.add(new Tile(6 , 5, GamePiece.PLAYER1));
                tiles.add(new Tile(6 , 7, GamePiece.PLAYER1));
                tiles.add(new Tile(7 , 6, GamePiece.PLAYER1));

                break;

            case 50: // 12x12


            tiles.add(new Tile(7 , 3, GamePiece.PLAYER1));
            tiles.add(new Tile(8 , 4, GamePiece.PLAYER1));
            tiles.add(new Tile(8 , 5, GamePiece.PLAYER1));
            tiles.add(new Tile(8 , 6, GamePiece.PLAYER1));
            tiles.add(new Tile(8 , 7, GamePiece.PLAYER1));
            tiles.add(new Tile(8 , 8, GamePiece.PLAYER1));
            tiles.add(new Tile(7 , 9, GamePiece.PLAYER1));

            tiles.add(new Tile(4 , 4, GamePiece.PLAYER2));
            tiles.add(new Tile(4 , 8, GamePiece.PLAYER2));
            tiles.add(new Tile(6 , 6, GamePiece.PLAYER2));

            tiles.add(new Tile(10 , 5, GamePiece.PLAYER2));
            tiles.add(new Tile(10 , 6, GamePiece.PLAYER2));
            tiles.add(new Tile(10 , 7, GamePiece.PLAYER2));
            tiles.add(new Tile(11 , 5, GamePiece.PLAYER1));
            tiles.add(new Tile(11 , 6, GamePiece.PLAYER1));
            tiles.add(new Tile(11 , 7, GamePiece.PLAYER1));


            break;


            case 51: // 12x12


                tiles.add(new Tile(0 , 0, GamePiece.PLAYER1));
                tiles.add(new Tile(0 , 1, GamePiece.PLAYER1));
                tiles.add(new Tile(1 , 0, GamePiece.PLAYER1));
                tiles.add(new Tile(1 , 1, GamePiece.PLAYER1));

                tiles.add(new Tile(11 , 0, GamePiece.PLAYER1));
                tiles.add(new Tile(11 , 1, GamePiece.PLAYER1));
                tiles.add(new Tile(10 , 0, GamePiece.PLAYER1));
                tiles.add(new Tile(10 , 1, GamePiece.PLAYER1));

                tiles.add(new Tile(11 , 11, GamePiece.PLAYER2));
                tiles.add(new Tile(11 , 10, GamePiece.PLAYER2));
                tiles.add(new Tile(10 , 10, GamePiece.PLAYER2));
                tiles.add(new Tile(10, 11, GamePiece.PLAYER2));

                tiles.add(new Tile(0 , 11, GamePiece.PLAYER2));
                tiles.add(new Tile(1 , 11, GamePiece.PLAYER2));
                tiles.add(new Tile(1 , 10, GamePiece.PLAYER2));
                tiles.add(new Tile(0, 10, GamePiece.PLAYER2));


                tiles.add(new Tile(4 , 4, GamePiece.PLAYER2));
                tiles.add(new Tile(4 , 8, GamePiece.PLAYER2));
                tiles.add(new Tile(6 , 7, GamePiece.PLAYER2));
                tiles.add(new Tile(5 , 4, GamePiece.PLAYER1));
                tiles.add(new Tile(5 , 8, GamePiece.PLAYER1));
                tiles.add(new Tile(7 , 6, GamePiece.PLAYER1));

                break;
            case 52: // 11x11


                tiles.add(new Tile(4 , 4, GamePiece.PLAYER1));
                tiles.add(new Tile(4 , 3, GamePiece.PLAYER2));
                tiles.add(new Tile(3 , 4, GamePiece.PLAYER2));
                tiles.add(new Tile(4 , 5, GamePiece.PLAYER2));
                tiles.add(new Tile(5 , 4, GamePiece.PLAYER2));
                tiles.add(new Tile(3 , 3, GamePiece.PLAYER2));
                tiles.add(new Tile(5 , 5, GamePiece.PLAYER2));
                tiles.add(new Tile(0 , 0, GamePiece.PLAYER1));

                break;

            case 53: // 6x6


                tiles.add(new Tile(5 , 5, GamePiece.PLAYER1));
                tiles.add(new Tile(4 , 5, GamePiece.PLAYER1));
                tiles.add(new Tile(0 , 5, GamePiece.PLAYER1));

                tiles.add(new Tile(1 , 2, GamePiece.PLAYER2));
                tiles.add(new Tile(2 , 1, GamePiece.PLAYER2));
                tiles.add(new Tile(2 , 2, GamePiece.PLAYER1));
                tiles.add(new Tile(1 , 1, GamePiece.PLAYER1));

                break;

            case 54: // 9x9


                tiles.add(new Tile(0 , 0, GamePiece.PLAYER1));
                tiles.add(new Tile(0 , 1, GamePiece.PLAYER1));
                tiles.add(new Tile(0 , 2, GamePiece.PLAYER1));
                tiles.add(new Tile(0 , 3, GamePiece.PLAYER1));
                tiles.add(new Tile(0 , 4, GamePiece.PLAYER1));
                tiles.add(new Tile(0 , 5, GamePiece.PLAYER1));
                tiles.add(new Tile(0 , 6, GamePiece.PLAYER1));
                tiles.add(new Tile(0 , 7, GamePiece.PLAYER1));
                tiles.add(new Tile(0 , 8, GamePiece.PLAYER2));

                tiles.add(new Tile(5 , 6, GamePiece.PLAYER2));
                tiles.add(new Tile(6 , 5, GamePiece.PLAYER2));
                tiles.add(new Tile(6 , 6, GamePiece.PLAYER1));
                tiles.add(new Tile(5 , 5, GamePiece.PLAYER1));

                break;

            case 55: // 12x12


                tiles.add(new Tile(2 , 2, GamePiece.PLAYER1));
                tiles.add(new Tile(3 , 3, GamePiece.PLAYER1));
                tiles.add(new Tile(4 , 4, GamePiece.PLAYER1));

                tiles.add(new Tile(1 , 2, GamePiece.PLAYER2));
                tiles.add(new Tile(2 , 3, GamePiece.PLAYER2));
                tiles.add(new Tile(3 , 4, GamePiece.PLAYER2));

                tiles.add(new Tile(7 , 7, GamePiece.PLAYER1));
                tiles.add(new Tile(8 , 8, GamePiece.PLAYER1));
                tiles.add(new Tile(9 , 9, GamePiece.PLAYER1));

                tiles.add(new Tile(6 , 7, GamePiece.PLAYER2));
                tiles.add(new Tile(7 , 8, GamePiece.PLAYER2));
                tiles.add(new Tile(8 , 9, GamePiece.PLAYER2));

                tiles.add(new Tile(0 , 0, GamePiece.PLAYER1));
                tiles.add(new Tile(0 , 11, GamePiece.PLAYER1));

                break;

            case 56: // 12x12

                tiles.add(new Tile(0 , 0, GamePiece.PLAYER1));
                tiles.add(new Tile(0 , 1, GamePiece.PLAYER1));
                tiles.add(new Tile(1 , 0, GamePiece.PLAYER1));
                tiles.add(new Tile(1 , 1, GamePiece.PLAYER1));
                tiles.add(new Tile(0 , 2, GamePiece.PLAYER1));
                tiles.add(new Tile(2 , 0, GamePiece.PLAYER1));
                tiles.add(new Tile(2 , 2, GamePiece.PLAYER1));

                tiles.add(new Tile(4 , 4, GamePiece.PLAYER1));
                tiles.add(new Tile(4 , 6, GamePiece.PLAYER1));
                tiles.add(new Tile(4 , 5, GamePiece.PLAYER1));

                tiles.add(new Tile(3 , 4, GamePiece.PLAYER2));
                tiles.add(new Tile(3 , 5, GamePiece.PLAYER2));
                tiles.add(new Tile(3 , 6, GamePiece.PLAYER2));


                break;

            case 57: // 10x10

                tiles.add(new Tile(0 , 0, GamePiece.PLAYER1));
                tiles.add(new Tile(0 , 1, GamePiece.PLAYER1));
                tiles.add(new Tile(0 , 2, GamePiece.PLAYER1));
                tiles.add(new Tile(0 , 3, GamePiece.PLAYER1));

                tiles.add(new Tile(0 , 6, GamePiece.PLAYER1));
                tiles.add(new Tile(0 , 7, GamePiece.PLAYER1));
                tiles.add(new Tile(0 , 8, GamePiece.PLAYER1));
                tiles.add(new Tile(0 , 9, GamePiece.PLAYER1));

                tiles.add(new Tile(2 , 2, GamePiece.PLAYER1));
                tiles.add(new Tile(3 , 3, GamePiece.PLAYER1));

                tiles.add(new Tile(2 , 3, GamePiece.PLAYER2));
                tiles.add(new Tile(3 , 2, GamePiece.PLAYER2));
                tiles.add(new Tile(4 , 2, GamePiece.PLAYER2));
                tiles.add(new Tile(4 , 3, GamePiece.PLAYER2));
                tiles.add(new Tile(0 , 4, GamePiece.PLAYER2));
                tiles.add(new Tile(0 , 5, GamePiece.PLAYER2));

                tiles.add(new Tile(9 , 0, GamePiece.PLAYER2));
                tiles.add(new Tile(9 , 1, GamePiece.PLAYER2));
                tiles.add(new Tile(9 , 2, GamePiece.PLAYER2));
                tiles.add(new Tile(9 , 3, GamePiece.PLAYER2));
                tiles.add(new Tile(9 , 4, GamePiece.PLAYER2));
                tiles.add(new Tile(9 , 5, GamePiece.PLAYER2));
                tiles.add(new Tile(9 , 6, GamePiece.PLAYER2));
                tiles.add(new Tile(9 , 7, GamePiece.PLAYER2));
                tiles.add(new Tile(9 , 8, GamePiece.PLAYER2));
                tiles.add(new Tile(9 , 9, GamePiece.PLAYER2));
                break;

            case 58: // 8x

                tiles.add(new Tile(0 , 0, GamePiece.PLAYER1));
                tiles.add(new Tile(0 , 2, GamePiece.PLAYER1));
                tiles.add(new Tile(1 , 2, GamePiece.PLAYER1));
                tiles.add(new Tile(2 , 2, GamePiece.PLAYER1));
                tiles.add(new Tile(3 , 2, GamePiece.PLAYER1));
                tiles.add(new Tile(4 , 2, GamePiece.PLAYER1));
                tiles.add(new Tile(5 , 2, GamePiece.PLAYER1));
                tiles.add(new Tile(6 , 2, GamePiece.PLAYER1));
                tiles.add(new Tile(7 , 2, GamePiece.PLAYER1));
                tiles.add(new Tile(5 , 6, GamePiece.PLAYER1));
                tiles.add(new Tile(6 , 5, GamePiece.PLAYER1));

                tiles.add(new Tile(1 , 4, GamePiece.PLAYER2));
                tiles.add(new Tile(2 , 4, GamePiece.PLAYER2));
                tiles.add(new Tile(3 , 4, GamePiece.PLAYER2));
                tiles.add(new Tile(4 , 4, GamePiece.PLAYER2));
                tiles.add(new Tile(5 , 4, GamePiece.PLAYER2));
                tiles.add(new Tile(6 , 4, GamePiece.PLAYER2));
                tiles.add(new Tile(5 , 5, GamePiece.PLAYER2));
                tiles.add(new Tile(6 , 6, GamePiece.PLAYER2));
                break;

            case 59: // 10x10

                tiles.add(new Tile(2 , 1, GamePiece.PLAYER1));
                tiles.add(new Tile(2 , 2, GamePiece.PLAYER1));
                tiles.add(new Tile(2 , 3, GamePiece.PLAYER1));
                tiles.add(new Tile(2 , 4, GamePiece.PLAYER1));
                tiles.add(new Tile(2 , 5, GamePiece.PLAYER1));
                tiles.add(new Tile(4 , 1, GamePiece.PLAYER1));
                tiles.add(new Tile(4 , 2, GamePiece.PLAYER1));
                tiles.add(new Tile(4 , 3, GamePiece.PLAYER1));
                tiles.add(new Tile(4 , 1, GamePiece.PLAYER1));
                tiles.add(new Tile(4 , 5, GamePiece.PLAYER1));

                tiles.add(new Tile(3 , 1, GamePiece.PLAYER2));
                tiles.add(new Tile(3 , 2, GamePiece.PLAYER2));
                tiles.add(new Tile(3 , 3, GamePiece.PLAYER2));
                tiles.add(new Tile(3 , 4, GamePiece.PLAYER2));
                tiles.add(new Tile(3 , 5, GamePiece.PLAYER2));

                tiles.add(new Tile(0 , 0, GamePiece.BLOCK));
                tiles.add(new Tile(5 , 2, GamePiece.BLOCK));
                tiles.add(new Tile(5 , 3, GamePiece.BLOCK));
                tiles.add(new Tile(5 , 4, GamePiece.BLOCK));


                break;

            case 60: // 10x10

                tiles.add(new Tile(0 , 0, GamePiece.PLAYER1));
                tiles.add(new Tile(2 , 1, GamePiece.PLAYER1));
                tiles.add(new Tile(3 , 2, GamePiece.PLAYER1));
                tiles.add(new Tile(4 , 3, GamePiece.PLAYER1));
                tiles.add(new Tile(5 , 4, GamePiece.PLAYER1));
                tiles.add(new Tile(6 , 5, GamePiece.PLAYER1));
                tiles.add(new Tile(7 , 1, GamePiece.PLAYER1));
                tiles.add(new Tile(8 , 2, GamePiece.PLAYER1));
                tiles.add(new Tile(9 , 3, GamePiece.PLAYER1));

                tiles.add(new Tile(3 , 1, GamePiece.PLAYER2));
                tiles.add(new Tile(2 , 2, GamePiece.PLAYER2));
                tiles.add(new Tile(1 , 3, GamePiece.PLAYER2));
                tiles.add(new Tile(4 , 4, GamePiece.PLAYER2));
                tiles.add(new Tile(5 , 5, GamePiece.PLAYER2));

                tiles.add(new Tile(6 , 6, GamePiece.BLOCK));
                tiles.add(new Tile(5 , 2, GamePiece.BLOCK));


                break;

            case 61: // 10x10

                tiles.add(new Tile(4 , 1, GamePiece.PLAYER1));
                tiles.add(new Tile(5 , 2, GamePiece.PLAYER1));
                tiles.add(new Tile(4 , 3, GamePiece.PLAYER1));
                tiles.add(new Tile(5 , 4, GamePiece.PLAYER1));
                tiles.add(new Tile(4 , 5, GamePiece.PLAYER1));
                tiles.add(new Tile(5 , 6, GamePiece.PLAYER1));
                tiles.add(new Tile(4 , 7, GamePiece.PLAYER1));
                tiles.add(new Tile(5 , 8, GamePiece.PLAYER1));
                tiles.add(new Tile(6 , 8, GamePiece.PLAYER1));
                tiles.add(new Tile(0 , 0, GamePiece.PLAYER1));
                tiles.add(new Tile(0 , 1, GamePiece.PLAYER1));


                tiles.add(new Tile(5 , 1, GamePiece.PLAYER2));
                tiles.add(new Tile(4 , 2, GamePiece.PLAYER2));
                tiles.add(new Tile(5 , 3, GamePiece.PLAYER2));
                tiles.add(new Tile(4 , 4, GamePiece.PLAYER2));
                tiles.add(new Tile(5 , 5, GamePiece.PLAYER2));
                tiles.add(new Tile(4 , 6, GamePiece.PLAYER2));
                tiles.add(new Tile(5 , 7, GamePiece.PLAYER2));
                tiles.add(new Tile(4 , 8, GamePiece.PLAYER2));

                tiles.add(new Tile(6 , 6, GamePiece.BLOCK));
                tiles.add(new Tile(6 , 4, GamePiece.BLOCK));
                tiles.add(new Tile(6 , 2, GamePiece.BLOCK));


                break;

            case 62: // 12x12

                tiles.add(new Tile(0 , 0, GamePiece.PLAYER1));
                tiles.add(new Tile(1 , 1, GamePiece.PLAYER1));
                tiles.add(new Tile(0 , 1, GamePiece.PLAYER2));
                tiles.add(new Tile(1 , 0, GamePiece.PLAYER2));

                tiles.add(new Tile(5 , 5, GamePiece.PLAYER1));
                tiles.add(new Tile(6 , 6, GamePiece.PLAYER1));
                tiles.add(new Tile(5 , 6, GamePiece.PLAYER2));
                tiles.add(new Tile(6 , 5, GamePiece.PLAYER2));

                tiles.add(new Tile(9 , 9, GamePiece.PLAYER1));
                tiles.add(new Tile(10 , 10, GamePiece.PLAYER1));
                tiles.add(new Tile(9 , 10, GamePiece.PLAYER2));
                tiles.add(new Tile(10 , 9, GamePiece.PLAYER2));

                tiles.add(new Tile(9 , 1, GamePiece.PLAYER1));
                tiles.add(new Tile(10 , 2, GamePiece.PLAYER1));
                tiles.add(new Tile( 9, 2, GamePiece.PLAYER2));
                tiles.add(new Tile(10 , 1, GamePiece.PLAYER2));

                tiles.add(new Tile(0 , 10, GamePiece.PLAYER1));
                tiles.add(new Tile(0 , 11, GamePiece.PLAYER1));
                tiles.add(new Tile( 1, 10, GamePiece.PLAYER1));
                tiles.add(new Tile(1 , 11, GamePiece.PLAYER1));

                tiles.add(new Tile(0 , 10, GamePiece.PLAYER1));
                tiles.add(new Tile(0 , 11, GamePiece.PLAYER1));
                tiles.add(new Tile( 1, 10, GamePiece.PLAYER1));
                tiles.add(new Tile(1 , 11, GamePiece.PLAYER1));

                tiles.add(new Tile( 0, 9, GamePiece.BLOCK));
                tiles.add(new Tile(1 , 9, GamePiece.BLOCK));
                tiles.add(new Tile( 2, 9, GamePiece.BLOCK));
                tiles.add(new Tile(2 , 10, GamePiece.BLOCK));
                tiles.add(new Tile(2 , 11, GamePiece.BLOCK));

                break;

            case 63: // 12x12

                tiles.add(new Tile(4 , 4, GamePiece.PLAYER1));
                tiles.add(new Tile(5 , 5, GamePiece.PLAYER1));
                tiles.add(new Tile(4 , 5, GamePiece.PLAYER2));
                tiles.add(new Tile(5 , 4, GamePiece.PLAYER2));

                tiles.add(new Tile(8 , 8, GamePiece.PLAYER1));
                tiles.add(new Tile(8 , 9, GamePiece.PLAYER1));
                tiles.add(new Tile(9 , 9, GamePiece.PLAYER2));
                tiles.add(new Tile(9 , 10, GamePiece.PLAYER2));

                tiles.add(new Tile(0 , 0, GamePiece.PLAYER1));
                tiles.add(new Tile(1 , 0, GamePiece.PLAYER1));
                tiles.add(new Tile(2 , 0, GamePiece.PLAYER1));
                tiles.add(new Tile( 3, 0, GamePiece.PLAYER1));
                tiles.add(new Tile(4 , 0, GamePiece.PLAYER1));
                tiles.add(new Tile(5 , 0, GamePiece.PLAYER1));
                tiles.add(new Tile(6 , 0, GamePiece.PLAYER1));
                tiles.add(new Tile(7 , 0, GamePiece.PLAYER1));
                tiles.add(new Tile( 8, 0, GamePiece.PLAYER1));
                tiles.add(new Tile(9 , 0, GamePiece.PLAYER1));


                tiles.add(new Tile(2 , 10, GamePiece.BLOCK));
                tiles.add(new Tile(2 , 11, GamePiece.BLOCK));

                break;

            case 64: // 12x12

                tiles.add(new Tile(5 , 3, GamePiece.PLAYER1));
                tiles.add(new Tile(5 , 4, GamePiece.PLAYER1));
                tiles.add(new Tile(5 , 5, GamePiece.PLAYER2));
                tiles.add(new Tile(5 , 6, GamePiece.PLAYER2));

                tiles.add(new Tile( 6, 5, GamePiece.PLAYER1));
                tiles.add(new Tile(6 , 6, GamePiece.PLAYER1));
                tiles.add(new Tile(6 , 3, GamePiece.PLAYER2));
                tiles.add(new Tile(6 , 4, GamePiece.PLAYER2));

                tiles.add(new Tile( 0, 0, GamePiece.PLAYER1));
                tiles.add(new Tile(0 , 11, GamePiece.PLAYER1));


                tiles.add(new Tile(5 , 2, GamePiece.BLOCK));
                tiles.add(new Tile(6 , 2, GamePiece.BLOCK));
                tiles.add(new Tile(4 , 2, GamePiece.BLOCK));
                tiles.add(new Tile( 4, 3, GamePiece.BLOCK));
                tiles.add(new Tile(4 , 4, GamePiece.BLOCK));
                tiles.add(new Tile( 4, 5, GamePiece.BLOCK));
                tiles.add(new Tile(4 , 6, GamePiece.BLOCK));
                tiles.add(new Tile( 4, 7, GamePiece.BLOCK));
                tiles.add(new Tile( 5, 7, GamePiece.BLOCK));
                tiles.add(new Tile( 6, 7, GamePiece.BLOCK));

                break;

        }

        return tiles;
    }

    public int getDifficulty(int level)
    {
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
            default:
                return 0;
        }
    }

    public int getNumberOfPiecesPlayerOneStarted(int level) // COMPUTER
    {
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
                return 7;
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
            case 27:
                return 8;
            case 28:
                return 3;
            case 29:
                return 8;
            case 30:
                return 6;
            case 31:
                return 10;
            case 32:
                return 6;
            case 33:
                return 5;
            case 34:
                return 11;
            case 35:
                return 11;
            case 36:
                return 8;
            case 37:
                return 9;
            case 38:
                return 8;
            case 39:
                return 12;
            case 40:
                return 10;
            case 41:
                return 7;
            case 42:
                return 13;
            case 43:
                return 16;
            case 44:
                return 12;
            case 45:
                return 18;
            case 46:
                return 12;
            case 47:
                return 16;
            case 48:
                return 16;
            case 49:
                return 13;
            case 50:
                return 10;
            case 51:
                return 11;
            case 52:
                return 2;
            case 53:
                return 5;
            case 54:
                return 10;
            case 55:
                return 8;
            case 56:
                return 10;
            case 57:
                return 10;
            case 58:
                return 11;
            case 59:
                return 10;
            case 60:
                return 9;
            case 61:
                return 11;
            case 62:
                return 12;
            case 63:
                return 14;
            case 64:
                return 6;

        }
        return -1;
    }

    public int getNumberOfPiecesPlayerTwoStarted(int level)
    {
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
            case 27:
                return 2;
            case 28:
                return 8;
            case 29:
                return 3;
            case 30:
                return 6;
            case 31:
                return 10;
            case 32:
                return 6;
            case 33:
                return 4;
            case 34:
                return 6;
            case 35:
                return 5;
            case 36:
                return 4;
            case 37:
                return 2;
            case 38:
                return 5;
            case 39:
                return 5;
            case 40:
                return 5;
            case 41:
                return 6;
            case 42:
                return 10;
            case 43:
                return 9;
            case 44:
                return 8;
            case 45:
                return 7;
            case 46:
                return 4;
            case 47:
                return 7;
            case 48:
                return 5;
            case 49:
                return 12;
            case 50:
                return 6;
            case 51:
                return 11;
            case 52:
                return 6;
            case 53:
                return 2;
            case 54:
                return 3;
            case 55:
                return 6;
            case 56:
                return 3;
            case 57:
                return 16;
            case 58:
                return 8;
            case 59:
                return 5;
            case 60:
                return 5;
            case 61:
                return 8;
            case 62:
                return 8;
            case 63:
                return 4;
            case 64:
                return 4;
        }
        return -1;
    }

    public int getBoardSize(int level)
    {
        switch (level)
        {
            case 1:
                return 8;
            case 2:
                return 8;
            case 3:
                return 8;
            case 4:
                return 10;
            case 5:
                return 8;
            case 6:
                return 8;
            case 7:
                return 8;
            case 8:
                return 8;
            case 9:
                return 10;
            case 10:
                return 8;
            case 11:
                return 8;
            case 12:
                return 8;
            case 13 :
                return 8;
            case 14:
                return 8;
            case 15:
                return 8;
            case 16:
                return 8;
            case 17:
                return 8;
            case 18:
                return 8;
            case 19:
                return 10;
            case 20:
                return 8;
            case 21:
                return 8;
            case 22:
                return 8;
            case 23:
                return 8;
            case 24:
                return 8;
            case 25:
                return 8;
            case 26:
                return 10;
            case 27:
                return 8;
            case 28:
                return 8;
            case 29:
                return 8;
            case 30:
                return 8;
            case 31:
                return 8;
            case 32:
                return 10;
            case 33:
                return 8;
            case 34:
                return 10;
            case 35:
                return 10;
            case 36:
                return 10;
            case 37:
                return 10;
            case 38:
                return 12;
            case 39:
                return 12;
            case 40:
                return 12;
            case 41:
                return 12;
            case 42:
                return 12;
            case 43:
                return 14;
            case 44:
                return 14;
            case 45:
                return 14;
            case 46:
                return 12;
            case 47:
                return 10;
            case 48:
                return 12;
            case 49:
                return 12;
            case 50:
                return 12;
            case 51:
                return 12;
            case 52:
                return 10;
            case 53:
                return 6;
            case 54:
                return 9;
            case 55:
                return 12;
            case 56:
                return 12;
            case 57:
                return 10;
            case 58:
                return 8;
            case 59:
                return 10;
            case 60:
                return 10;
            case 61:
                return 10;
            case 62:
                return 12;
            case 63:
                return 12;
            case 64:
                return 12;
        }
        return -1;
    }

    public int getMaxLevel() {
        return maxLevel;
    }

    public boolean isLastLevel(int level)
    {
        return level == maxLevel;
    }

    public void updateCurrentLevelIfMaxLevelsChanged()
    {
        int level = getGreatestLevel();
        boolean finishTheLastLevel = sharedPreferences.getBoolean(FINISHED_LAST_LEVEL, false);
        if(finishTheLastLevel && level < maxLevel)
        {
            trySetGreatestLevel(level + 1);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(FINISHED_LAST_LEVEL, false);
            editor.apply();
        }
    }
}

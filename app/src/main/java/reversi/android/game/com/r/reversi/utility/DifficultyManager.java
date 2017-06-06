package reversi.android.game.com.r.reversi.utility;

import android.content.SharedPreferences;

/**
 * Created by Erez on 14/05/2017.
 */

public class DifficultyManager
{

    private final int EASY_LEVEL_IDX = 5;
    private final int MEDIUM_LEVEL_IDX = 2;
    private final int HARD_LEVEL_IDX = 0;
    private final String DIFFICULTY_KEY = "difficulty";

    public static class Difficulty
    {
       public static String EASY_LEVEL_STR =  "EASY";
        public static String MEDIUM_LEVEL_STR =  "MEDIUM";
        public static String HARD_LEVEL_STR =  "HARD";

    }
    public void setCurrentDifficulty(String difficulty)
    {
        SharedPreferences sharedPreferences = App.Instance.getSharedPreferences();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(DIFFICULTY_KEY, difficulty);
        editor.apply();
    }

    public String getDifficulty()
    {
        SharedPreferences sharedPreferences = App.Instance.getSharedPreferences();
        return sharedPreferences.getString(DIFFICULTY_KEY, Difficulty.EASY_LEVEL_STR);
    }

    public int getCurrentDifficultyIdx( )
    {
        SharedPreferences sharedPreferences = App.Instance.getSharedPreferences();
        String difficulty = sharedPreferences.getString(DIFFICULTY_KEY, Difficulty.EASY_LEVEL_STR);
        if(difficulty.compareTo(Difficulty.EASY_LEVEL_STR) == 0)
        {
            return EASY_LEVEL_IDX;
        }
        else if (difficulty.compareTo(Difficulty.MEDIUM_LEVEL_STR) == 0)
        {
            return MEDIUM_LEVEL_IDX;
        }
        else
        {
            return HARD_LEVEL_IDX;
        }
    }
}

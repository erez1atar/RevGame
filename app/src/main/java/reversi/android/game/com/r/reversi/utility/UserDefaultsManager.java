package reversi.android.game.com.r.reversi.utility;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Erez on 23/06/2017.
 */

public class UserDefaultsManager
{
    private SharedPreferences sharedPreferences;
    private final static String BOARD_SIZE = "board_size";
    private final int defaultBoardSize = 8;
    public UserDefaultsManager()
    {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(App.Instance);
    }

    public void setBoardSize(int size)
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(BOARD_SIZE, size);
        editor.apply();
    }

    public int getBoardSize()
    {
        int size = sharedPreferences.getInt(BOARD_SIZE, defaultBoardSize);
        return size;
    }
}

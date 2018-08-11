package games.e.reversi.utility;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Erez on 23/06/2017.
 */

public class UserDefaultsManager
{
    private SharedPreferences sharedPreferences;
    private final static String BOARD_SIZE = "board_size";
    private final static String NOTIFICATION_WEEKLY_NUM = "weekly_noti";
    private final static String SESSION_NUMBER = "session_num";
    private final static String UI_STYLE = "ui_style";
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

    public void setNotificationWeeklyNum(int num)
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(NOTIFICATION_WEEKLY_NUM, num);
        editor.apply();
    }

    public void increaseSessionNUm() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(SESSION_NUMBER, this.getSessionNUmber() + 1);
        editor.apply();
    }

    public int getNotificatioWeeklyNum()
    {
        int num = sharedPreferences.getInt(NOTIFICATION_WEEKLY_NUM, 0);
        return num;
    }

    public int getSessionNUmber() {
        int num = sharedPreferences.getInt(SESSION_NUMBER, 1);
        return num;
    }

    public void setUIString(String ui)
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(UI_STYLE, ui);
        editor.apply();
    }

    public String getUIString() {
        String ui = sharedPreferences.getString(UI_STYLE, "GREEN");
        return ui;
    }
}

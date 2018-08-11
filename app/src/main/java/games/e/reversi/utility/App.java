package games.e.reversi.utility;

import android.app.Application;
import android.content.SharedPreferences;
import android.net.wifi.WifiManager;
import android.preference.PreferenceManager;
import android.util.Log;


import java.math.BigInteger;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteOrder;
import java.util.ArrayList;

import games.e.reversi.R;
import games.e.reversi.Connection.ComputerPlayer;
import games.e.reversi.Connection.ConnectionManager;
import games.e.reversi.Connection.DummyConnection;
import games.e.reversi.Connection.IConnectionManager;
import games.e.reversi.Connection.LevelPlayer;
import games.e.reversi.Presention.IPresent;
import games.e.reversi.board.GamePiece;
import games.e.reversi.controllers.GameController;
import games.e.reversi.controllers.IController;
import games.e.reversi.model.GameModel;
import games.e.reversi.model.IModel;

/**
 * Created by LENOVO on 14/03/2016.
 */
public class App extends Application
{
    //private Tracker mTracker;
    private GoogleAnalyticsHelper googleAnalyticsHelper;
    private static StatsManager statsManager = null;
    public static games.e.reversi.utility.App Instance;
    private static GameModel model;
    private static GameController controller;
    private static DummyConnection dummyConnection;
    private static ConnectionManager connectionManager;
    private static ComputerPlayer computerPlayer;
    private static LevelPlayer levelPlayer;
    private static boolean isLevelsMode = false;
    private static LevelsModeManager levelsModeManager;
    private final static String developerModeStr = "dev_mode";
    private SharedPreferences sharedPreferences;
    private DifficultyManager difficultyManager;
    private ArrayList<ArrayList<GamePiece>> gameState;
    private static UserDefaultsManager userDefaultsManager;
    private static Boolean updateSessionNum = false;
    private static int activeLevel = 0;

    public App()
    {
        Instance = this;
        updateSessionNum = false;
    }

    public static void tryUpdateSession() {
        if(!updateSessionNum) {
            games.e.reversi.utility.App.getUserDefault().increaseSessionNUm();
            updateSessionNum = true;
        }
    }

    public static IModel getModel()
    {
        return model;
    }

    public static void createNewModel(int boardSize)
    {
        Log.d("App", "createNewModel , size =" + boardSize);
        model = new GameModel(boardSize, boardSize);
    }

    public static IController getController(IPresent iPresent, IConnectionManager iConnectionManager)
    {
        if(controller == null)
        {
            controller = new GameController(iPresent , iConnectionManager);
        }
        if(iPresent != null)
        {
            controller.setIPresent(iPresent);
        }
        controller.setIConnection(iConnectionManager);
        return controller;
    }

    public static String wifiIpAddress()
    {
        WifiManager wifiManager = (WifiManager) Instance.getSystemService(WIFI_SERVICE);
        int ipAddress = wifiManager.getConnectionInfo().getIpAddress();

        // Convert little-endian to big-endian if needed
        if (ByteOrder.nativeOrder().equals(ByteOrder.LITTLE_ENDIAN)) {
            ipAddress = Integer.reverseBytes(ipAddress);
        }

        byte[] ipByteArray = BigInteger.valueOf(ipAddress).toByteArray();

        String ipAddressString;
        try {
            ipAddressString = InetAddress.getByAddress(ipByteArray).getHostAddress();
        } catch (UnknownHostException ex) {
            ipAddressString = null;
        }

        return ipAddressString;
    }

    public static ConnectionManager getConnectionManager()
    {
        if(connectionManager == null)
        {
            connectionManager = new ConnectionManager();
        }
        return connectionManager;
    }

    public static DummyConnection getDummyConnection()
    {
        if(dummyConnection == null)
        {
            dummyConnection = new DummyConnection();
        }
        return dummyConnection;
    }

    public static ComputerPlayer getComputerPlayer()
    {
        if(computerPlayer == null)
        {
            computerPlayer = new ComputerPlayer();
        }
        return computerPlayer;
    }

    public static LevelPlayer getLevelPlayer()
    {
        if(levelPlayer == null)
        {
            levelPlayer = new LevelPlayer();
        }
        return levelPlayer;
    }

    public static StatsManager getStatsManager()
    {
        if(statsManager == null)
        {
            statsManager = new StatsManager();
        }
        return statsManager;
    }

    public static boolean getIsLevelsMode()
    {
        return isLevelsMode;
    }

    public static void setNOTLevelsMode()
    {
        isLevelsMode = false;
    }

    public static void setIsInLevelsMode(int level) {
        isLevelsMode = true;
        activeLevel = level;
    }

    public static int getActiveLevel() {
        return activeLevel;
    }

    public static LevelsModeManager getLevelsModeManager()
    {
        if(levelsModeManager == null)
        {
            levelsModeManager = new LevelsModeManager();
        }
        return levelsModeManager;
    }

    /*synchronized public Tracker getDefaultTracker() {
        if (mTracker == null) {
            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
            // To enable debug logging use: adb shell setprop log.tag.GAv4 DEBUG
            mTracker = analytics.newTracker(R.xml.global_tracker);
        }
        return mTracker;
    }*/

    public GoogleAnalyticsHelper getGoogleAnalytics()
    {
        if(googleAnalyticsHelper == null)
        {
            googleAnalyticsHelper = new GoogleAnalyticsHelper();
        }
        return googleAnalyticsHelper;
    }

    public boolean getIsDeveloperMode()
    {
        if(sharedPreferences == null)
        {
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(games.e.reversi.utility.App.Instance);
        }
        return sharedPreferences.getBoolean(developerModeStr, false);
    }

    public void setIsDeveloperMode()
    {
        if(sharedPreferences == null)
        {
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(games.e.reversi.utility.App.Instance);
        }
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(developerModeStr, true);
        editor.apply();
    }

    public SharedPreferences getSharedPreferences()
    {
        if(sharedPreferences == null)
        {
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(games.e.reversi.utility.App.Instance);;
        }
        return sharedPreferences;
    }

    public DifficultyManager getDifficultyManager()
    {
        if(difficultyManager == null)
        {
            difficultyManager = new DifficultyManager();
        }
        return difficultyManager;
    }

    public void saveGameState(ArrayList<ArrayList<GamePiece>> game)
    {
        this.gameState = game;
    }

    public ArrayList<ArrayList<GamePiece>>  getGameState()
    {
        return gameState;
    }

    public static UserDefaultsManager getUserDefault()
    {
        if(userDefaultsManager == null)
        {
            userDefaultsManager = new UserDefaultsManager();
        }
        return userDefaultsManager;
    }


}

package reversi.android.game.com.r.reversi.utility;

import android.app.Application;
import android.content.SharedPreferences;
import android.net.wifi.WifiManager;
import android.preference.PreferenceManager;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

import java.math.BigInteger;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;

import reversi.android.game.com.r.reversi.Connection.ComputerPlayer;
import reversi.android.game.com.r.reversi.Connection.ConnectionManager;
import reversi.android.game.com.r.reversi.Connection.DummyConnection;
import reversi.android.game.com.r.reversi.Connection.IConnectionManager;
import reversi.android.game.com.r.reversi.Connection.LevelPlayer;
import reversi.android.game.com.r.reversi.Presention.IPresent;
import reversi.android.game.com.r.reversi.board.GamePiece;
import reversi.android.game.com.r.reversi.controllers.GameController;
import reversi.android.game.com.r.reversi.controllers.IController;
import reversi.android.game.com.r.reversi.model.GameModel;
import reversi.android.game.com.r.reversi.R;
import reversi.android.game.com.r.reversi.model.IModel;

/**
 * Created by LENOVO on 14/03/2016.
 */
public class App extends Application
{
    private Tracker mTracker;
    private GoogleAnalyticsHelper googleAnalyticsHelper;
    public static App Instance;
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

    public App()
    {
        Instance = this;
    }

    public static IModel getModel()
    {
        if(null == model)
        {
            model = new GameModel(Instance.getResources().getInteger(R.integer.numOfRows) ,Instance.getResources().getInteger(R.integer.numOfCols));
        }
        return model;
    }

    public static IController getController(IPresent iPresent, IConnectionManager iConnectionManager)
    {
        if(controller == null)
        {
            controller = new GameController(getModel(), iPresent , iConnectionManager);
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

    public static boolean getIsLevelsMode()
    {
        return isLevelsMode;
    }

    public static void setIsLevelsMode(boolean levelsMode)
    {
        isLevelsMode = levelsMode;
    }

    public static LevelsModeManager getLevelsModeManager()
    {
        if(levelsModeManager == null)
        {
            levelsModeManager = new LevelsModeManager();
        }
        return levelsModeManager;
    }

    synchronized public Tracker getDefaultTracker() {
        if (mTracker == null) {
            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
            // To enable debug logging use: adb shell setprop log.tag.GAv4 DEBUG
            mTracker = analytics.newTracker(R.xml.global_tracker);
        }
        return mTracker;
    }

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
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(App.Instance);
        }
        return sharedPreferences.getBoolean(developerModeStr, false);
    }

    public void setIsDeveloperMode()
    {
        if(sharedPreferences == null)
        {
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(App.Instance);
        }
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(developerModeStr, true);
        editor.apply();
    }

    public SharedPreferences getSharedPreferences()
    {
        if(sharedPreferences == null)
        {
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(App.Instance);;
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


}

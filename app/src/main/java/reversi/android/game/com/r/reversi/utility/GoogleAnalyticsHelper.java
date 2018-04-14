package reversi.android.game.com.r.reversi.utility;

import android.util.Log;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

/**
 * Created by Erez on 05/05/2017.
 */

public class GoogleAnalyticsHelper
{
    private Tracker mTracker;
    private final static String LEVEL_START = "level_start_";
    private final static String LEVEL_WIN = "level_win_";
    private final static String LEVEL_LOSS = "level_loss_";

    private final static String GAME_TYPE_CATEGORY = "GAME_TYPE";

    public final static String HOST_GAME_PRESSED = "host_game";
    public final static String JOIN_PLAYERS_GAME_PRESSED  = "join_game";
    public final static String TWO_PLAYERS_GAME_PRESSED  = "two_players_game";
    public final static String ONE_PLAYERS_GAME_PRESSED  = "one_players_game";
    public final static String MULTI_PLAYER_GAME_SECCES  = "multi_player_success";
    public final static String LEVELS_PRESSED  = "levels_game";

    private final static String VIDEO_CATEGORY = "VIDEO";

    private final static String VIDEO_STARTED = "video_start";

    private final static String VIDEO_REWARDED = "video_rewarded";


    public GoogleAnalyticsHelper()
    {
        mTracker = App.Instance.getDefaultTracker();
    }

    public void TrackLevelStartEvent(int level)
    {
        if(App.Instance.getIsDeveloperMode())
        {
            Log.d("startGame", "isdeveloper");
            return;
        }
        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory("Level")
                .setAction(LEVEL_START + String.valueOf(level))
                .build());
    }

    public void TrackLevelWinEvent(int level)
    {
        Log.d("GoogleAnalyticsHelper" , "TrackLevelWinEvent");
        if(App.Instance.getIsDeveloperMode())
        {
            return;
        }
        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory("Level")
                .setAction(LEVEL_WIN + String.valueOf(level))
                .build());
    }

    public void TrackLevelLossEvent(int level)
    {
        Log.d("GoogleAnalyticsHelper" , "TrackLevelLossEvent");
        if(App.Instance.getIsDeveloperMode())
        {
            return;
        }
        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory("Level")
                .setAction(LEVEL_LOSS + String.valueOf(level))
                .build());
    }

    public void TrackGameTypeEvent(String gameType)
    {
        Log.d("GoogleAnalyticsHelper" , "TrackGameTypeEvent");
        if(App.Instance.getIsDeveloperMode())
        {
            return;
        }
        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory(GAME_TYPE_CATEGORY)
                .setAction(gameType)
                .build());
    }

    public void TrackErrorElementEvent(int row, int col)
    {
        if(App.Instance.getIsDeveloperMode())
        {
            return;
        }
        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory("ERROR")
                .setAction("invalid_item_" + row + "_" + col)
                .build());
    }

    public void TrackVideoRewarded()
    {
        Log.d("GoogleAnalyticsHelper" , "TrackLevelWinEvent");
        if(App.Instance.getIsDeveloperMode())
        {
            return;
        }
        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory(VIDEO_CATEGORY)
                .setAction(VIDEO_REWARDED)
                .build());
    }

    public void TrackVideoStarted()
    {
        Log.d("GoogleAnalyticsHelper" , "TrackLevelWinEvent");
        if(App.Instance.getIsDeveloperMode())
        {
            return;
        }
        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory(VIDEO_CATEGORY)
                .setAction(VIDEO_STARTED)
                .build());
    }
}

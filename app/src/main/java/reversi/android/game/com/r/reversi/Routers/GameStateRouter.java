package reversi.android.game.com.r.reversi.Routers;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import reversi.android.game.com.r.reversi.R;
import reversi.android.game.com.r.reversi.utility.App;

/**
 * Created by LENOVO on 16/03/2016.
 */
public class GameStateRouter
{
    private static LocalBroadcastManager localReceiver = LocalBroadcastManager.getInstance(App.Instance);

    public static void sendStartGame(Boolean isMyTurn, Boolean multiPlayer, Boolean computerMode)
    {
        Intent intent = new Intent(App.Instance.getString(R.string.game_state));
        intent.putExtra(App.Instance.getString(R.string.game_state),App.Instance.getString(R.string.start_game));
        intent.putExtra(App.Instance.getString(R.string.is_my_turn_key), isMyTurn);
        intent.putExtra(App.Instance.getString(R.string.multi_player_key), multiPlayer);
        intent.putExtra(App.Instance.getString(R.string.computer_mode_key), computerMode);
        Log.d("intent", String.valueOf(isMyTurn) + " " + String.valueOf(multiPlayer) + " " + String.valueOf(computerMode));
        localReceiver.sendBroadcast(intent);
    }

    public static void sendWaitToOpp()
    {
        Intent intent = new Intent(App.Instance.getString(R.string.game_state));
        intent.putExtra(App.Instance.getString(R.string.game_state),App.Instance.getString(R.string.wait_for_opp_to_host));
        localReceiver.sendBroadcast(intent);
    }

    public static void sendWaitToPlayerJoin()
    {
        Intent intent = new Intent(App.Instance.getString(R.string.game_state));
        intent.putExtra(App.Instance.getString(R.string.game_state),App.Instance.getString(R.string.wait_for_opp_join));
        localReceiver.sendBroadcast(intent);
    }

    public static void sendConnectionFail()
    {
        Intent intentFail = new Intent(App.Instance.getString(R.string.game_state));
        intentFail.putExtra(App.Instance.getString(R.string.game_state),App.Instance.getString(R.string.connection_fail));
        intentFail.putExtra(App.Instance.getString(R.string.is_my_turn_key), false);
        intentFail.putExtra(App.Instance.getString(R.string.multi_player_key), false);
        localReceiver.sendBroadcast(intentFail);
    }

    public static void sendDisconnect()
    {
        Intent intent = new Intent(App.Instance.getString(R.string.connection_key));
        intent.putExtra(App.Instance.getString(R.string.connection_key), App.Instance.getString(R.string.disconnect_value));
        localReceiver.sendBroadcast(intent);
    }


}

package reversi.android.game.com.r.reversi.Connection;

/**
 * Created by LENOVO on 17/03/2016.
 */
public interface IConnection
{
    void startConnection();
    void sendTurnData(TurnData turnData);
    void disconnect();
}

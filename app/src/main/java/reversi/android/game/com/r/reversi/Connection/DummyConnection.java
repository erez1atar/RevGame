package reversi.android.game.com.r.reversi.Connection;

/**
 * Created by LENOVO on 23/03/2016.
 */
public class DummyConnection implements IConnectionManager
{
    @Override
    public void startHostGame()
    {}

    @Override
    public void startJoinGame(String ip)
    {}

    @Override
    public void sendTurnData(TurnData turnData)
    {}

    @Override
    public void disconnect()
    {}

    @Override
    public boolean isMoveTurnOnNoAvailable() {
        return true;
    }

    @Override
    public void setBoardSize(int rows, int cols) {

    }
}

package games.e.reversi.Connection;

/**
 * Created by erez on 18/03/2016.
 */
public class ConnectionManager implements IConnectionManager
{
    private IConnection iConnection;

    public void startHostGame()
    {
        iConnection = new ServerConnection();
        iConnection.startConnection();
    }

    public void startJoinGame(String ip)
    {
        iConnection = new ClientConnection(ip);
        iConnection.startConnection();
    }

    public void sendTurnData(TurnData turnData)
    {
        iConnection.sendTurnData(turnData);
    }

    public void disconnect()
    {
        iConnection.disconnect();
    }

    @Override
    public boolean isMoveTurnOnNoAvailable() {
        return false;
    }

    @Override
    public void setBoardSize(int rows, int cols) {

    }
}

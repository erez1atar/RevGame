package games.e.reversi.Connection;

/**
 * Created by LENOVO on 23/03/2016.
 */
public interface IConnectionManager
{
    void startHostGame();
    void startJoinGame(String ip);
    void sendTurnData(TurnData turnData);
    void disconnect();
    boolean isMoveTurnOnNoAvailable();
    void setBoardSize(int rows, int cols);

}

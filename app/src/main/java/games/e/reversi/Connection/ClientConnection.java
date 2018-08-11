package games.e.reversi.Connection;

import android.util.Log;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import games.e.reversi.R;
import games.e.reversi.Routers.GameStateRouter;
import games.e.reversi.utility.App;

/**
 * Created by LENOVO on 17/03/2016.
 */
public class ClientConnection implements IConnection
{
    private Socket socket;
    private Thread tcpThread;
    private String ip;
    private ExecutorService executor = Executors.newFixedThreadPool(4);

    public ClientConnection(String ip)
    {
        this.ip = ip;
    }

    public void startConnection()
    {
        GameStateRouter.sendWaitToOpp();
        Thread connectionThread = new Thread(new Runnable() {
            @Override
            public void run()
            {
                try
                {
                    socket = new Socket(ip , App.Instance.getResources().getInteger(R.integer.my_server_port));
                }
                catch (IOException e)
                {
                    GameStateRouter.sendConnectionFail();
                    return;
                }
                tcpThread = new Thread(new TCPRecvConnection(socket));
                executor.execute(tcpThread);
                GameStateRouter.sendStartGame(false, true, false);
            }
        });
        executor.execute(connectionThread);
    }

    @Override
    public void sendTurnData(TurnData turnData)
    {
        Thread sendThread = new Thread(new TCPSendConnection(socket, turnData.getJsonStr()));
        executor.execute(sendThread);
    }

    @Override
    public void disconnect()
    {
        try
        {
            socket.close();
        }
        catch (IOException e)
        {
            Log.d("ClientConnection" , "socket close");
        }
    }
}

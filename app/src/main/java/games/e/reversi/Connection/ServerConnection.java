package games.e.reversi.Connection;

import android.util.Log;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import games.e.reversi.R;
import games.e.reversi.Routers.GameStateRouter;
import games.e.reversi.utility.App;

/**
 * Created by LENOVO on 17/03/2016.
 */
public class ServerConnection implements IConnection
{
    private ServerSocket server;
    private Socket socket;
    private Thread tcpThread;
    ExecutorService executor = Executors.newFixedThreadPool(2);

    public void startConnection()
    {
        GameStateRouter.sendWaitToPlayerJoin();
        Thread connectThread = new Thread(new Runnable() {
            @Override
            public void run()
            {
                try
                {
                    server = new ServerSocket(App.Instance.getResources().getInteger(R.integer.my_server_port));
                    socket = server.accept();
                }
                catch (IOException e)
                {
                    Log.d("Service", "cant open and accept server socket");
                    return;
                }
                tcpThread = new Thread(new TCPRecvConnection(socket));
                executor.execute(tcpThread);
                GameStateRouter.sendStartGame(true, true, false);
            }
        });
        executor.execute(connectThread);

    }

    @Override
    public void sendTurnData(TurnData turnData)
    {
        Thread sendThread = new Thread(new TCPSendConnection(socket, turnData.getJsonStr() ));
        executor.execute(sendThread);
    }

    @Override
    public void disconnect()
    {
        try
        {
            server.close();
            socket.close();
        }
        catch (IOException e)
        {
            Log.d("disconnect server", "exception close server socket");
        }
    }
}

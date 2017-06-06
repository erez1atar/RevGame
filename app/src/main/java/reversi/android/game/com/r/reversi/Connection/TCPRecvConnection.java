package reversi.android.game.com.r.reversi.Connection;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Created by LENOVO on 17/03/2016.
 */
public class TCPRecvConnection implements Runnable
{
    private Socket client;

    public TCPRecvConnection(Socket socket)
    {
        this.client = socket;
    }

    @Override
    public void run()
    {
        try
        {
            Log.d("TCPClient", "started");
            BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
            while(! Thread.currentThread().isInterrupted())
            {
                Log.d("TCPCLIENT", "trying to read line");
                String jsonStr = reader.readLine();
                if(jsonStr == null)
                {
                    break;
                }
                Log.d("TCPCLIENT", "read line :" + jsonStr);
                EventBus.sendMSG(jsonStr);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != client)
                {
                    Log.d("TCPCLIENT", "socket closed");
                    client.close();
                    EventBus.sendDisconnectMSG();
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
}

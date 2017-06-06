package reversi.android.game.com.r.reversi.Connection;

import android.util.Log;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by LENOVO on 17/03/2016.
 */
public class TCPSendConnection implements Runnable
{
    private String turnData;
    private Socket socket;

    public TCPSendConnection(Socket socket, String turnData)
    {
        this.turnData = turnData;
        this.socket = socket;
    }

    @Override
    public void run()
    {
        try {

            BufferedWriter output = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            PrintWriter writer = new PrintWriter(output, true);
            writer.println(turnData);
            Log.d("SendMSG", turnData);
        }
        catch (IOException e)
        {
            Log.d("SendMSG", "run exception");
        }
    }
}

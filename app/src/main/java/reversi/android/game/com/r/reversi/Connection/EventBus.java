package reversi.android.game.com.r.reversi.Connection;

import java.util.ArrayList;

import reversi.android.game.com.r.reversi.R;
import reversi.android.game.com.r.reversi.controllers.IController;
import reversi.android.game.com.r.reversi.utility.App;

/**
 * Created by LENOVO on 23/03/2016.
 */
public class EventBus
{
    private static ArrayList<IController> controllers = new ArrayList<>();

    public static void registerToGameMSGS(IController iController)
    {
        controllers.add(iController);
    }

    public static void unregisterToGameMSGS(IController iController)
    {
        controllers.remove(iController);
    }

    public static void sendMSG(String turnStr)
    {
        TurnData turnData = new TurnData(turnStr);

        for (IController iController : controllers)
        {
            iController.onActionEvent(turnData.getActionType(), turnData.getValue(), turnData.getRow(), turnData.getCol());
        }
    }

    public static void sendDisconnectMSG()
    {
        for (IController iController : controllers)
        {
            iController.onActionEvent(App.Instance.getString(R.string.disconnect_action_key), "", 0, 0);
        }
    }
}

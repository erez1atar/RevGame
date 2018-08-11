package games.e.reversi.Connection;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import games.e.reversi.R;
import games.e.reversi.board.Tile;
import games.e.reversi.controllers.IController;
import games.e.reversi.utility.App;

/**
 * Created by erez on 26/03/2016.
 */
public class ComputerPlayer implements IConnectionManager
{

    ExecutorService executorService = Executors.newSingleThreadExecutor();

    @Override
    public void startHostGame() {}

    @Override
    public void startJoinGame(String ip) {}

   /* @Override
    public void sendTurnData(TurnData turnData)
    {
        final IController controller = App.getController(null, this);
        final Random random = new Random();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.currentThread().sleep(1000);
                } catch (InterruptedException e) {
                    Log.d("ComputerPlayer", "sleep interupted");
                }
                ArrayList<Tile> options = new ArrayList<>();
                controller.getOptionsToPlay(options);
                if(options.size() > 0)
                {
                    int idx = random.nextInt(options.size());
                    TurnData turn = new TurnData(options.get(idx).getRow(), options.get(idx).getCol(), App.Instance.getString(R.string.game_key), App.Instance.getString(R.string.turn_key));
                    EventBus.sendMSG(turn.getJsonStr());
                }

            }
        });
    }
*/
   @Override
   public void sendTurnData(TurnData turnData)
   {
       final IController controller = App.getController(null, this);
       executorService.execute(new Runnable() {
           @Override
           public void run() {
               try {
                   Thread.currentThread().sleep(1000);
               } catch (InterruptedException e) {
                   Log.d("ComputerPlayer", "sleep interupted");
               }
               ArrayList<Tile> options = new ArrayList<>();
               controller.getOptionsToPlay(options);

               if(options.size() > 0)
               {
                   ArrayList<games.e.reversi.Connection.ComputerPlayer.PointWithEnemies> choices = getChoices(controller, options);
                   games.e.reversi.Connection.ComputerPlayer.PointWithEnemies pointWithEnemies = getSelectedPointByLevel(choices);
                   TurnData turn = new TurnData(pointWithEnemies.row, pointWithEnemies.col, App.Instance.getString(R.string.game_key), App.Instance.getString(R.string.turn_key));
                   EventBus.sendMSG(turn.getJsonStr());
               }
                /*if(options.size() > 0)
                {
                    int idx = random.nextInt(options.size());
                    TurnData turn = new TurnData(options.get(idx).getRow(), options.get(idx).getCol(), App.Instance.getString(R.string.game_key), App.Instance.getString(R.string.turn_key));
                    EventBus.sendMSG(turn.getJsonStr());
                }*/

           }
       });
   }

    private games.e.reversi.Connection.ComputerPlayer.PointWithEnemies getSelectedPointByLevel(ArrayList<games.e.reversi.Connection.ComputerPlayer.PointWithEnemies> choices)
    {
        int idx = App.Instance.getDifficultyManager().getCurrentDifficultyIdx();
        Log.d("Difiiculty" , "idx=" + idx);
        if(idx > choices.size() - 1)
        {
            return choices.get(choices.size() - 1);
        }
        return choices.get(idx);
    }

    private ArrayList<games.e.reversi.Connection.ComputerPlayer.PointWithEnemies> getChoices(IController controller, ArrayList<Tile> options)
    {
        ArrayList<games.e.reversi.Connection.ComputerPlayer.PointWithEnemies> points = new ArrayList<>();
        for(int i = 0 ; i < options.size() ; i++)
        {
            Tile tile = options.get(i);
            ArrayList<Tile> tilesToChange =  new ArrayList<>();
            int enemies  = controller.getNumOfTilesToChange(tilesToChange, tile.getRow(), tile.getCol());
            points.add(new games.e.reversi.Connection.ComputerPlayer.PointWithEnemies(tile.getRow(), tile.getCol(),enemies));
        }
        Collections.sort(points, new games.e.reversi.Connection.ComputerPlayer.CustomComparator());
        return points;
    }

    private class PointWithEnemies
    {
        public int row;
        public int col;
        public int enemies;

        public PointWithEnemies(int row, int col, int enemies)
        {
            this.row = row;
            this.col = col;
            this.enemies = enemies;
        }

    }

    public class CustomComparator implements Comparator<games.e.reversi.Connection.ComputerPlayer.PointWithEnemies> {
        @Override
        public int compare(games.e.reversi.Connection.ComputerPlayer.PointWithEnemies o1, games.e.reversi.Connection.ComputerPlayer.PointWithEnemies o2) {
            return o2.enemies - o1.enemies;
        }
    }
    @Override
    public void disconnect() {}

    @Override
    public boolean isMoveTurnOnNoAvailable() {
        return true;
    }

    @Override
    public void setBoardSize(int rows, int cols) {

    }
}

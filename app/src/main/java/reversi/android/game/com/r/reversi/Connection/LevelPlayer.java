package reversi.android.game.com.r.reversi.Connection;


import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import reversi.android.game.com.r.reversi.R;
import reversi.android.game.com.r.reversi.board.Tile;
import reversi.android.game.com.r.reversi.controllers.IController;
import reversi.android.game.com.r.reversi.utility.App;

/**
 * Created by Erez on 04/05/2017.
 */

public class LevelPlayer implements IConnectionManager {
    ExecutorService executorService = Executors.newSingleThreadExecutor();

    @Override
    public void startHostGame() {}

    @Override
    public void startJoinGame(String ip) {}

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
                    ArrayList<PointWithEnemies> choices = getChoices(controller, options);
                    PointWithEnemies pointWithEnemies = getSelectedPointByLevel(choices);
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

    private PointWithEnemies getSelectedPointByLevel(ArrayList<PointWithEnemies> choices)
    {
        int idx = App.getLevelsModeManager().getDifficulty(App.getActiveLevel());
        if(idx > choices.size() - 1)
        {
            return choices.get(choices.size() - 1);
        }
        return choices.get(idx);
    }

    private ArrayList<PointWithEnemies> getChoices(IController controller, ArrayList<Tile> options)
    {
        ArrayList<PointWithEnemies> points = new ArrayList<>();
        for(int i = 0 ; i < options.size() ; i++)
        {
            Tile tile = options.get(i);
            ArrayList<Tile> tilesToChange =  new ArrayList<>();
            int enemies  = controller.getNumOfTilesToChange(tilesToChange, tile.getRow(), tile.getCol());
            points.add(new PointWithEnemies(tile.getRow(), tile.getCol(),enemies));
        }
        Collections.sort(points, new CustomComparator());
        for(int j = 0 ; j < points.size() ; j++)
        {
            Log.d("Array", "" + points.get(j).enemies);
        }
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

    public class CustomComparator implements Comparator<PointWithEnemies> {
        @Override
        public int compare(PointWithEnemies o1, PointWithEnemies o2) {
            return o2.enemies - o1.enemies;
        }
    }
    @Override
    public void disconnect() {}
}

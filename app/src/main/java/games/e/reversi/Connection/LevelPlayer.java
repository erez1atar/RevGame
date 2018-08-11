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
 * Created by Erez on 04/05/2017.
 */

public class LevelPlayer implements IConnectionManager {
    ExecutorService executorService = Executors.newSingleThreadExecutor();
    private int numOfRows = 0;
    private int numOfCols = 0;

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
            int point2Rank = o2.enemies + getExtraRank(o2.row, o2.col);
            int point1Rank = o1.enemies + getExtraRank(o1.row, o1.col);;
            return point2Rank - point1Rank;
        }
    }

    private int getExtraRank(int pointRow,int pointCol) {

        if(pointRow == numOfRows  - 1  && pointCol == numOfCols  - 1 ) {
            return numOfRows / 2;
        }

        if(pointRow == 0 && pointCol == numOfCols  - 1 ) {
            return numOfRows / 2;
        }

        if(pointRow == numOfRows - 1  && 0 == pointCol) {
            return numOfRows / 2;
        }

        if(pointRow == 0 && 0 == pointCol) {
            return numOfRows / 2;
        }
        return 0;

    }
    @Override
    public void disconnect() {}

    @Override
    public boolean isMoveTurnOnNoAvailable() {
        return true;
    }

    @Override
    public void setBoardSize(int rows, int cols) {
        numOfRows = rows;
        numOfCols = cols;
    }
}

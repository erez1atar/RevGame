package games.e.reversi.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Erez on 27/07/2018.
 */

public class StatsManager {

    public class LevelsDataStats {
        public int levelNum =0;
        public int wins = 0;
        public int losses =0;
    }

    private final String WIN_KEY = "_w";
    private final String LOSS_KEY = "_l";
    private HashMap<String,Integer> levelsData = null;
    public StatsManager() {
        this.levelsData = this.getLevelsDataFromFile();
        if(levelsData == null) {
            levelsData = new HashMap<>();
        }
    }

    public void updateLossLevel(int level) {
        String lossStr = level + LOSS_KEY;

        boolean containLoss = levelsData.containsKey(lossStr);
        if(containLoss) {
            int losses = levelsData.get(lossStr);
            levelsData.put(lossStr, losses + 1);
        }
        else {
            levelsData.put(lossStr, 1);
        }
        this.flushData();
    }

    public void updateWinLevel(int level) {
        String lossStr = level + LOSS_KEY;
        String winStr = level + WIN_KEY;

        boolean containLoss = levelsData.containsKey(lossStr);
        if(containLoss) {
            int losses = levelsData.get(lossStr);
            levelsData.put(lossStr, losses - 1);
        }

        boolean containWin = levelsData.containsKey(winStr);
        if(containWin) {
            int wins = levelsData.get(winStr);
            levelsData.put(winStr, wins + 1);
        }
        else {
            levelsData.put(winStr, 1);
        }

        this.flushData();

    }


    private void flushData() {
        File file = new File(App.Instance.getDir("data", MODE_PRIVATE), "lvls_data");
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(file));
            outputStream.writeObject(levelsData);
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private HashMap<String,Integer> getLevelsDataFromFile() {
        try
        {
            File file = new File(App.Instance.getDir("data", MODE_PRIVATE), "lvls_data");
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream((file)));
            Object result = ois.readObject();
            //you can feel free to cast result to HashMap<String, Integer> if you know that only a HashMap is stored in the file
            return (HashMap<String, Integer>)result;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<LevelsDataStats> getLevelsData() {
        ArrayList<LevelsDataStats> levelsDataStatsArray = new ArrayList<>();
        int greatestLevel = App.getLevelsModeManager().getMaxLevel();
        for(int levelNum = 1 ; levelNum <= greatestLevel ; levelNum++) {
            LevelsDataStats levelData = new LevelsDataStats();
            levelData.levelNum = levelNum;
            levelData.wins = this.getNumOfWins(levelNum);
            levelData.losses = this.getNumOfLosses(levelNum);
            levelsDataStatsArray.add(levelData);

        }
        return levelsDataStatsArray;
    }

    private int getNumOfWins(int level) {
        String winStr = level + WIN_KEY;
        boolean hasWins = levelsData.containsKey(winStr);
        if(hasWins) {
            return levelsData.get(winStr);
        }
        return 0;
    }

    private int getNumOfLosses(int level) {
        String lossStr = level + LOSS_KEY;
        boolean hasWins = levelsData.containsKey(lossStr);
        if(hasWins) {
            return levelsData.get(lossStr);
        }
        return 0;
    }

}

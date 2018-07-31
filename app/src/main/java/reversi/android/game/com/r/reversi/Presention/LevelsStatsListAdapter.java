package reversi.android.game.com.r.reversi.Presention;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import reversi.android.game.com.r.reversi.R;
import reversi.android.game.com.r.reversi.utility.StatsManager;


/**
 * Created by erez on 05/10/2016.
 */
public class LevelsStatsListAdapter extends ArrayAdapter<StatsManager.LevelsDataStats>
{
    private final ArrayList<StatsManager.LevelsDataStats> levelsData;
    private final Activity context;

    public LevelsStatsListAdapter(Activity context, int resource, ArrayList<StatsManager.LevelsDataStats> levelsData) {
        super(context, resource);
        this.context = context;
        this.levelsData = levelsData;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return levelsData.size();
    }

    public class ViewHolderLocation
    {
        TextView levelNum;
        TextView numOfWins;
        TextView numOfLosses;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = context.getLayoutInflater();
        ViewHolderLocation holder = null;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.level_stat_card, parent, false);
            holder  = new ViewHolderLocation();
            holder.levelNum = (TextView)convertView.findViewById(R.id.level_card_level_num);
            holder.numOfWins = (TextView)convertView.findViewById(R.id.level_card_level_wins);
            holder.numOfLosses = (TextView)convertView.findViewById(R.id.level_card_level_losses);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolderLocation)convertView.getTag();
        }
        Log.d("getView", "position = " + position);

        holder.levelNum.setText("# " +  String.valueOf(levelsData.get(position).levelNum));
        holder.numOfWins.setText(String.valueOf(levelsData.get(position).wins));
        holder.numOfLosses.setText(String.valueOf(levelsData.get(position).losses));

        return convertView;
    }
}

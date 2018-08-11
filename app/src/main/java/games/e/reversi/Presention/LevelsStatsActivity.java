package games.e.reversi.Presention;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import games.e.reversi.R;
import games.e.reversi.utility.App;

public class LevelsStatsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_levels_stats);

        Typeface type = Typeface.createFromAsset(getAssets(),"fonts/edosz.ttf");
        TextView reversi = (TextView)findViewById(R.id.stats_reversi) ;
        TextView levelHeader = (TextView)findViewById(R.id.level_card_level_num) ;
        TextView winsHeader = (TextView)findViewById(R.id.level_card_level_wins) ;
        TextView lossesHeader = (TextView)findViewById(R.id.level_card_level_losses) ;
        levelHeader.setTypeface(type);
        winsHeader.setTypeface(type);
        lossesHeader.setTypeface(type);

        reversi.setTypeface(type);
        Animation flip = AnimationUtils.loadAnimation(this, R.anim.flip_rotation);
        reversi.startAnimation(flip);

        ImageView crown_left = findViewById(R.id.list_left_crown);
        ImageView crown_right = findViewById(R.id.list_right_crown);


        ListView listView = (ListView)findViewById(R.id.levels_dadat_stats_list);
        LevelsStatsListAdapter adapter = new LevelsStatsListAdapter(games.e.reversi.Presention.LevelsStatsActivity.this,R.layout.level_stat_card, App.getStatsManager().getLevelsData());
        listView.setAdapter(adapter);
    }
}

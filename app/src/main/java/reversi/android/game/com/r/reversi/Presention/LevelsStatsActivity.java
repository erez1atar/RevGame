package reversi.android.game.com.r.reversi.Presention;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import reversi.android.game.com.r.reversi.R;
import reversi.android.game.com.r.reversi.utility.App;

public class LevelsStatsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_levels_stats);

        Typeface type = Typeface.createFromAsset(getAssets(),"fonts/edosz.ttf");
        TextView reversi = (TextView)findViewById(R.id.stats_reversi) ;
        reversi.setTypeface(type);
        Animation flip = AnimationUtils.loadAnimation(this, R.anim.flip_rotation);
        reversi.startAnimation(flip);

        ImageView crown_left = findViewById(R.id.list_left_crown);
        ImageView crown_right = findViewById(R.id.list_right_crown);
        Animation bounce = AnimationUtils.loadAnimation(this, R.anim.crown_left_rotate_1);
        bounce.setRepeatCount(Animation.INFINITE);
        crown_right.startAnimation(bounce);
        crown_left.startAnimation(bounce);


        ListView listView = (ListView)findViewById(R.id.levels_dadat_stats_list);
        LevelsStatsListAdapter adapter = new LevelsStatsListAdapter(LevelsStatsActivity.this,R.layout.level_stat_card, App.getStatsManager().getLevelsData());
        listView.setAdapter(adapter);
    }
}

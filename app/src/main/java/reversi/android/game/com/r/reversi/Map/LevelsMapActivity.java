package reversi.android.game.com.r.reversi.Map;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import reversi.android.game.com.r.reversi.Presention.GameActivity;
import reversi.android.game.com.r.reversi.Presention.MainActivity;
import reversi.android.game.com.r.reversi.R;
import reversi.android.game.com.r.reversi.utility.App;
import reversi.android.game.com.r.reversi.utility.GoogleAnalyticsHelper;

public class LevelsMapActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_levels_map);

        String preStrLevel = "map_level_";

        int maxLevel = App.getLevelsModeManager().getMaxLevel();
        int userLevel = App.getLevelsModeManager().getCurrentLevel();
        int tmpUserLevel = userLevel;
        Typeface type = Typeface.createFromAsset(getAssets(),"fonts/edosz.ttf");
        int i = 1;
        int levelsInThisMap = 1;
        while(tmpUserLevel > 20)
        {
            i +=20;
            tmpUserLevel -= 20;
        }
        for(; i <= maxLevel && levelsInThisMap <= 20 ; i++ , levelsInThisMap++)
        {

            StringBuilder builder = new StringBuilder(preStrLevel);
            builder.append( String.valueOf(levelsInThisMap));
            int resID = getResources().getIdentifier(builder.toString(), "id", getPackageName());
            Button levelBtn = (Button)findViewById(resID);
            if(i <= userLevel)
            {
                levelBtn.setBackgroundResource(R.drawable.unlock);
                if(i == userLevel)
                {
                    levelBtn.setEnabled(true);
                    levelBtn.setTextColor(Color.BLUE);
                }
            }
            else
            {
                levelBtn.setBackgroundResource(R.drawable.lock);
            }
            levelBtn.setTypeface(type);
            levelBtn.setText(String.valueOf(i));
            levelBtn.setVisibility(View.VISIBLE);
        }

        Button playBtn = (Button)findViewById(R.id.map_play_level);
        playBtn.setTypeface(type);
        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                App.Instance.getGoogleAnalytics().TrackGameTypeEvent(GoogleAnalyticsHelper.LEVELS_PRESSED);
                App.setIsLevelsMode(true);
                Intent intentToGame = new Intent(LevelsMapActivity.this, GameActivity.class);
                intentToGame.putExtra(App.Instance.getString(R.string.is_my_turn_key), false);
                intentToGame.putExtra(App.Instance.getString(R.string.multi_player_key), true);
                intentToGame.putExtra(App.Instance.getString(R.string.computer_mode_key), true);
                startActivity(intentToGame);
            }
        });
    }
}

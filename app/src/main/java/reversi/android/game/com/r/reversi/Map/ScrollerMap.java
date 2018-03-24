package reversi.android.game.com.r.reversi.Map;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import reversi.android.game.com.r.reversi.Presention.GameActivity;
import reversi.android.game.com.r.reversi.R;
import reversi.android.game.com.r.reversi.utility.App;
import reversi.android.game.com.r.reversi.utility.GoogleAnalyticsHelper;

public class ScrollerMap extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroller_map);


        String preStrLevel = "map_level_";

        int maxLevel = App.getLevelsModeManager().getMaxLevel();
        int userLevel = App.getLevelsModeManager().getCurrentLevel();
        Typeface type = Typeface.createFromAsset(getAssets(),"fonts/edosz.ttf");
        int level = 1;
        for( ; level <= maxLevel ; level++ )
        {

            StringBuilder builder = new StringBuilder(preStrLevel);
            builder.append( String.valueOf(level));
            int resID = getResources().getIdentifier(builder.toString(), "id", getPackageName());
            Button levelBtn = (Button)findViewById(resID);
            if(level <= userLevel)
            {
                levelBtn.setBackgroundResource(R.drawable.unlock);
                if(level == userLevel)
                {
                    levelBtn.setEnabled(true);
                    levelBtn.setTextColor(Color.YELLOW);
                }
            }
            else
            {
                levelBtn.setBackgroundResource(R.drawable.lock);
            }
            levelBtn.setTypeface(type);
            levelBtn.setText(String.valueOf(level));
            levelBtn.setVisibility(View.VISIBLE);
        }
        this.focusOnUserLevel(preStrLevel, userLevel);
        while(true) {
            StringBuilder builder = new StringBuilder(preStrLevel);
            builder.append( String.valueOf(level));
            int resID = getResources().getIdentifier(builder.toString(), "id", getPackageName());
            Button levelBtn = (Button)findViewById(resID);
            if(levelBtn != null) {
                levelBtn.setVisibility(View.INVISIBLE);
            }
            else {
                break;
            }
            level++;
        }


        Button playBtn = (Button)findViewById(R.id.map_play_level);
        playBtn.setTypeface(type);
        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                App.Instance.getGoogleAnalytics().TrackGameTypeEvent(GoogleAnalyticsHelper.LEVELS_PRESSED);
                App.setIsLevelsMode(true);
                Intent intentToGame = new Intent(ScrollerMap.this, GameActivity.class);
                intentToGame.putExtra(App.Instance.getString(R.string.is_my_turn_key), false);
                intentToGame.putExtra(App.Instance.getString(R.string.multi_player_key), true);
                intentToGame.putExtra(App.Instance.getString(R.string.computer_mode_key), true);
                startActivity(intentToGame);
            }
        });
    }

    private void focusOnUserLevel(String preStrLevel , int userLevel) {
        int targetToFocus = userLevel - 1;
        View targetView = null;
        for(; userLevel >= targetToFocus ; userLevel--){
            StringBuilder builder = new StringBuilder(preStrLevel);
            builder.append( String.valueOf(userLevel));
            int resIDUserLevel = getResources().getIdentifier(builder.toString(), "id", getPackageName());
            View crntView = findViewById(resIDUserLevel);
            if(crntView != null) {
                targetView = crntView;
            }
        }
        if(targetView != null) {
            targetView.getParent().requestChildFocus(targetView,targetView);
        }
    }
}

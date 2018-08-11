package games.e.reversi.Map;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.crashlytics.android.Crashlytics;

import games.e.reversi.R;
import games.e.reversi.Presention.GameActivity;
import games.e.reversi.Presention.LevelsStatsActivity;
import games.e.reversi.utility.App;
import games.e.reversi.utility.GoogleAnalyticsHelper;

public class ScrollerMap extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroller_map);

        Button stats = findViewById(R.id.statstics_btn);
        stats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(games.e.reversi.Map.ScrollerMap.this, LevelsStatsActivity.class));
            }
        });
        Animation statsAnim = AnimationUtils.loadAnimation(this, R.anim.levels_btn_anim);
        statsAnim.setRepeatCount(Animation.INFINITE);
        stats.setAnimation(statsAnim);


    }

    @Override
    protected void onResume() {
        super.onResume();
        String preStrLevel = "map_level_";

        int maxLevel = App.getLevelsModeManager().getMaxLevel();
        int userLevel = App.getLevelsModeManager().getGreatestLevel();
        Typeface type = Typeface.createFromAsset(getAssets(),"fonts/edosz.ttf");
        int level = 1;
        Animation pulse = AnimationUtils.loadAnimation(this, R.anim.levels_btn_anim);
        for( ; level <= maxLevel ; level++ )
        {
            final int currentLevel = level;
            StringBuilder builder = new StringBuilder(preStrLevel);
            builder.append( String.valueOf(level));
            int resID = getResources().getIdentifier(builder.toString(), "id", getPackageName());
            Button levelBtn = (Button)findViewById(resID);
            final int lvl = level;
            levelBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Crashlytics.log(Log.ERROR,"[SCROLL_MAP]"," lvl clicked " + String.valueOf(lvl));
                    App.setIsInLevelsMode(currentLevel);
                    games.e.reversi.Map.ScrollerMap.this.enterGame();
                }
            });
            if(level <= userLevel)
            {
                levelBtn.startAnimation(pulse);
                levelBtn.setBackgroundResource(R.drawable.unlock);
                levelBtn.setEnabled(true);
                if(level == userLevel)
                {
                    levelBtn.setTextColor(Color.YELLOW);
                }
                else
                {
                    levelBtn.setTextColor(Color.BLACK);
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
    }

    private void enterGame() {
        Crashlytics.log(Log.ERROR,"[SCROLL_MAP]"," enter game ");
        App.Instance.getGoogleAnalytics().TrackGameTypeEvent(GoogleAnalyticsHelper.LEVELS_PRESSED);
        Intent intentToGame = new Intent(games.e.reversi.Map.ScrollerMap.this, GameActivity.class);
        intentToGame.putExtra(App.Instance.getString(R.string.is_my_turn_key), false);
        intentToGame.putExtra(App.Instance.getString(R.string.multi_player_key), true);
        intentToGame.putExtra(App.Instance.getString(R.string.computer_mode_key), true);
        startActivity(intentToGame);
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

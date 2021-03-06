package games.e.reversi.Presention;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.google.android.gms.ads.MobileAds;

import games.e.reversi.Features.ReversyMediaPlayer;
import games.e.reversi.R;
import games.e.reversi.Routers.GameStateRouter;
import games.e.reversi.utility.AnimationHelper;
import games.e.reversi.utility.App;
import games.e.reversi.utility.DialogUtility;
import games.e.reversi.utility.DifficultyManager;
import games.e.reversi.utility.GoogleAnalyticsHelper;
import io.fabric.sdk.android.Fabric;
import games.e.reversi.Map.ScrollerMap;
import games.e.reversi.Settings.SettingsActivity;

public class MainActivity extends Activity implements SharedPreferences.OnSharedPreferenceChangeListener
{
    private ActivityBroadCastReceiver receiver = new ActivityBroadCastReceiver();
    private AlertDialog alertDialog = null;
    private Typeface type;
    private SharedPreferences prefs = null;

    private Button rateBtn = null;
    private Button hostButton = null;
    private Button startButton = null;
    private Button joinButton = null;
    private Button computerModeGame = null;
    private Button levelModeGame = null;
    private Button settings = null;
    private Button credits = null;
    private Animation pulse = null;
    private Animation flip = null;
    private TextView reversi;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        App.tryUpdateSession();
        /*if(App.getUserDefault().getSessionNUmber() <= 1){
            MyNotificationManager.scheduleWeeklyNotification();
        }*/
        prefs = PreferenceManager.getDefaultSharedPreferences(App.Instance);
        Fabric.with(this, new Crashlytics());

        type = Typeface.createFromAsset(getAssets(),"fonts/edosz.ttf");
        TextView com = (TextView)findViewById(R.id.computerButton) ;
        com.setTypeface(type);
        TextView start = (TextView)findViewById(R.id.buttonStart) ;
        start.setTypeface(type);
        TextView join = (TextView)findViewById(R.id.JoinGame) ;
        join.setTypeface(type);
        TextView host = (TextView)findViewById(R.id.hostButton) ;
        host.setTypeface(type);
        TextView level = (TextView)findViewById(R.id.levelButton) ;
        level.setTypeface(type);
        TextView credits = (TextView)findViewById(R.id.creditsButton) ;
        credits.setTypeface(type);
        TextView settings = (TextView)findViewById(R.id.settingBtn) ;
        settings.setTypeface(type);
        reversi = (TextView)findViewById(R.id.games_e_reversi) ;
        reversi.setTypeface(type);

        App.getLevelsModeManager().updateCurrentLevelIfMaxLevelsChanged();

        initButton();
//        ImageView reversiPic = (ImageView)findViewById(R.id.imageView);
//        reversiPic.setImageResource(R.drawable.games.e.reversi);
        initBackgroundMusicIfNeeded();
        MobileAds.initialize(getApplicationContext(), "ca-app-pub-3454009395741796~8636243937");
        this.changeUI();
        PreferenceManager.getDefaultSharedPreferences(this).registerOnSharedPreferenceChangeListener(this);

    }

    private void runInAnimation() {

        reversi.startAnimation(flip);


        AnimationHelper.runAnimationsSequence(hostButton,new int[]{R.anim.board_in_animation_from_right_p1, R.anim.board_in_animation_from_right_p2, R.anim.heart_pulse}, null);
        AnimationHelper.runAnimationsSequence(startButton,new int[]{R.anim.board_in_animation_from_right_p1, R.anim.board_in_animation_from_right_p2, R.anim.heart_pulse}, null);
        AnimationHelper.runAnimationsSequence(joinButton,new int[]{R.anim.board_in_animation_from_right_p1, R.anim.board_in_animation_from_right_p2,R.anim.heart_pulse},null);
        AnimationHelper.runAnimationsSequence(computerModeGame,new int[]{R.anim.board_in_animation_from_right_p1, R.anim.board_in_animation_from_right_p2,R.anim.heart_pulse},null);
        AnimationHelper.runAnimationsSequence(levelModeGame,new int[]{R.anim.board_in_animation_from_right_p1, R.anim.board_in_animation_from_right_p2,R.anim.heart_pulse},null);

        AnimationHelper.runAnimationsSequence(settings,new int[]{R.anim.board_in_animation_from_left_p1, R.anim.board_in_animation_from_left_p2,R.anim.heart_pulse},null);
        AnimationHelper.runAnimationsSequence(credits,new int[]{R.anim.board_in_animation_from_left_p1, R.anim.board_in_animation_from_left_p2,R.anim.heart_pulse}, null);
        AnimationHelper.runAnimationsSequence(rateBtn,new int[]{R.anim.board_in_animation_from_left_p1, R.anim.board_in_animation_from_left_p2,R.anim.heart_pulse}, null);
    }

    private void changeUI() {
        int button_res = 0;
        if(App.getUserDefault().getUIString().equals("BLUE")) {
            button_res = R.drawable.button_syle_blue;
        }
        else if(App.getUserDefault().getUIString().equals("RED")) {
            button_res = R.drawable.button_style_red;
        }
        else {
            button_res = R.drawable.button_syle;
        }
        rateBtn.setBackgroundResource(button_res);
        hostButton.setBackgroundResource(button_res);
        startButton.setBackgroundResource(button_res);
        joinButton.setBackgroundResource(button_res);
        computerModeGame.setBackgroundResource(button_res);
        levelModeGame.setBackgroundResource(button_res);
        settings.setBackgroundResource(button_res);
        credits.setBackgroundResource(button_res);

    }

    private void initButton()
    {
        Button blue = (Button)findViewById(R.id.pic_blue);
        Button green = (Button)findViewById(R.id.pic_green);
        Button red = (Button)findViewById(R.id.pic_red);
        blue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                App.getUserDefault().setUIString("BLUE");
                games.e.reversi.Presention.MainActivity.this.changeUI();
            }
        });

        green.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                App.getUserDefault().setUIString("GREEN");
                games.e.reversi.Presention.MainActivity.this.changeUI();
            }
        });

        red.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                App.getUserDefault().setUIString("RED");
                games.e.reversi.Presention.MainActivity.this.changeUI();
            }
        });

        pulse = AnimationUtils.loadAnimation(this, R.anim.heart_pulse);
        flip = AnimationUtils.loadAnimation(this, R.anim.flip_rotation);

        rateBtn = (Button)findViewById(R.id.rate_us_btn);
        rateBtn.setTypeface(type);
        rateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Crashlytics.log(Log.DEBUG,"[MAIN]"," rate us button clicked");
                Uri uri = Uri.parse("market://details?id=" + App.Instance.getPackageName());
                Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                // To count with Play market backstack, After pressing back button,
                // to taken back to our application, we need to add following flags to intent.
                goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                        Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                        Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                try {
                    startActivity(goToMarket);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://play.google.com/store/apps/details?id=" + App.Instance.getPackageName())));
                }
            }
        });

        if(App.getLevelsModeManager().getGreatestLevel() < 7)
        {
            rateBtn.setEnabled(false);
            rateBtn.setVisibility(View.INVISIBLE);
        }

        credits = (Button)findViewById(R.id.creditsButton);
        credits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Crashlytics.log(Log.DEBUG,"[MAIN]"," credits button clicked");
                Intent intent = new Intent(games.e.reversi.Presention.MainActivity.this, CreditsActivity.class);
                startActivity(intent);
            }
        });

        settings = (Button)findViewById(R.id.settingBtn);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Crashlytics.log(Log.DEBUG,"[MAIN]"," settings button clicked");
                Intent intent = new Intent(games.e.reversi.Presention.MainActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });

        levelModeGame = (Button)findViewById(R.id.levelButton);
        levelModeGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Crashlytics.log(Log.DEBUG,"[MAIN]"," levels mode button clicked");
                Intent intent = new Intent(games.e.reversi.Presention.MainActivity.this, ScrollerMap.class);
                startActivity(intent);;
            }
        });

        computerModeGame = (Button)findViewById(R.id.computerButton);
        computerModeGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Crashlytics.log(Log.DEBUG,"[MAIN]","computer mode button clicked");
                App.Instance.getGoogleAnalytics().TrackGameTypeEvent( GoogleAnalyticsHelper.ONE_PLAYERS_GAME_PRESSED);
                App.setNOTLevelsMode();

                final Dialog dialog = new Dialog(games.e.reversi.Presention.MainActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.start_computer_game_dialog);

                final TextView chooseDiffBtn = (TextView) dialog.findViewById(R.id.choose_diff_text);
                final TextView boardSizeBtn = (TextView) dialog.findViewById(R.id.board_size_text);
                boardSizeBtn.setTypeface(type);
                chooseDiffBtn.setTypeface(type);

                final Button boardSize8Btn = (Button) dialog.findViewById(R.id.board_size_8_btn);
                final Button boardSize10Btn = (Button) dialog.findViewById(R.id.board_size_10_btn);

                final ImageView xBtn = (ImageView) dialog.findViewById(R.id.start_x);
                xBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                boardSize8Btn.setTypeface(type);
                boardSize10Btn.setTypeface(type);
                boardSize8Btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        App.getUserDefault().setBoardSize(8);
                        boardSize8Btn.setTextColor(Color.BLACK);
                        boardSize10Btn.setTextColor(Color.GRAY);
                    }
                });

                boardSize10Btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        App.getUserDefault().setBoardSize(10);
                        boardSize8Btn.setTextColor(Color.GRAY);
                        boardSize10Btn.setTextColor(Color.BLACK);
                    }
                });

                final Spinner spinner = (Spinner)dialog.findViewById(R.id.start_game_spinner_difficulty);
                String style = App.getUserDefault().getUIString();

                int spinner_style = R.layout.spinner_item_blue;
                if(style.equals("GREEN")) {
                    spinner_style = R.layout.spinner_item;
                }
                else if(style.equals("RED")) {
                    spinner_style = R.layout.spinner_item_red;
                }

                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(games.e.reversi.Presention.MainActivity.this, spinner_style, getResources().getStringArray(R.array.difficulty));
                String difficultyDefault = App.Instance.getDifficultyManager().getDifficulty();
                spinner.setAdapter(spinnerArrayAdapter);
                if(difficultyDefault.compareTo(DifficultyManager.Difficulty.EASY_LEVEL_STR) == 0)
                {
                    spinner.setSelection(0);
                    Log.d("spiiner ", "0");
                }
                else if(difficultyDefault.compareTo(DifficultyManager.Difficulty.MEDIUM_LEVEL_STR) == 0)
                {
                    spinner.setSelection(1);
                    Log.d("spiiner ", "1");
                }
                else
                {
                    spinner.setSelection(2);
                    Log.d("spiiner ", "2");
                }


                Button startGame = (Button) dialog.findViewById(R.id.start_game_btn);
                startGame.setTypeface(type);
                startGame.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int dif  = spinner.getSelectedItemPosition();
                        Crashlytics.log(Log.DEBUG,"[MAIN]","start computer game button clicked dificulty " + String.valueOf(dif));
                        switch (dif)
                        {
                            case  0 :
                                Log.d("Spinner", "EASY");
                                App.Instance.getDifficultyManager().setCurrentDifficulty(DifficultyManager.Difficulty.EASY_LEVEL_STR);
                                break;
                            case  1 :
                                Log.d("Spinner", "MEDIUM");
                                App.Instance.getDifficultyManager().setCurrentDifficulty(DifficultyManager.Difficulty.MEDIUM_LEVEL_STR);
                                break;
                            case  2 :
                                App.Instance.getDifficultyManager().setCurrentDifficulty(DifficultyManager.Difficulty.HARD_LEVEL_STR);
                                break;
                        }
                        GameStateRouter.sendStartGame(false, true, true);
                        dialog.dismiss();
                    }
                });

                int defaultBoardSize = App.getUserDefault().getBoardSize();
                switch (defaultBoardSize)
                {
                    case 8:
                        boardSize8Btn.setTextColor(Color.BLACK);
                        boardSize10Btn.setTextColor(Color.GRAY);
                        break;
                    case 10 :
                        boardSize8Btn.setTextColor(Color.GRAY);
                        boardSize10Btn.setTextColor(Color.BLACK);
                        break;
                }
                Button[] btns = {startGame, boardSize8Btn, boardSize10Btn};
                DialogUtility.setBackGround(dialog, btns);
                dialog.show();


            }

            });
        startButton = (Button)findViewById(R.id.buttonStart);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Crashlytics.log(Log.DEBUG,"[MAIN]","start button two plyers clicked");
                App.Instance.getGoogleAnalytics().TrackGameTypeEvent(GoogleAnalyticsHelper.TWO_PLAYERS_GAME_PRESSED);
                Intent intentToGame = new Intent(MainActivity.this, GameActivity.class);
                App.setNOTLevelsMode();
                intentToGame.putExtra(App.Instance.getString(R.string.is_my_turn_key), true);
                intentToGame.putExtra(App.Instance.getString(R.string.multi_player_key), false);
                startActivity(intentToGame);
            }
        });

        joinButton = (Button)findViewById(R.id.JoinGame);
        joinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Crashlytics.log(Log.DEBUG,"[MAIN]","join game clicked");
                App.Instance.getGoogleAnalytics().TrackGameTypeEvent(GoogleAnalyticsHelper.JOIN_PLAYERS_GAME_PRESSED);
                App.setNOTLevelsMode();
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                alertDialogBuilder.setMessage("Enter you host's ip address\n(You're both must be connected to the same WIFI)");
                final EditText editText = new EditText(App.Instance);
                editText.setTextColor(Color.BLUE);
                alertDialogBuilder.setView(editText);
                alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String ip = editText.getText().toString();
                        sendConnectionRec(ip);

                    }
                });
                alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

            }
        });

        hostButton = (Button)findViewById(R.id.hostButton);
        hostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Crashlytics.log(Log.DEBUG,"[MAIN]"," host game clicked");
                App.Instance.getGoogleAnalytics().TrackGameTypeEvent(GoogleAnalyticsHelper.HOST_GAME_PRESSED);
                if(App.wifiIpAddress() == null)
                {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(games.e.reversi.Presention.MainActivity.this);
                    alertDialogBuilder.setMessage("Please connect to WIFI");
                    alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            dialog.dismiss();
                        }
                    });

                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }
                else
                {
                    App.setNOTLevelsMode();
                    App.getConnectionManager().startHostGame();
                }
            }
        });




//        Button helpButton = (Button)findViewById(R.id.helpButton);
//        helpButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v)
//            {
//                Intent intent = new Intent(MainActivity.this, ScreenSlideActivity.class);
//                startActivity(intent);
//            }
//        });
    }

    private void sendConnectionRec(String ip)
    {
        App.getConnectionManager().startJoinGame(ip);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        Crashlytics.log(Log.DEBUG,"[MAIN]"," on resume");
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(this);
        IntentFilter filter = new IntentFilter();
        filter.addAction(getString(R.string.game_state));
        localBroadcastManager.registerReceiver(receiver, filter);
        this.runInAnimation();
    }

    @Override
    protected void onPause() {
        rateBtn.clearAnimation();
        hostButton.clearAnimation();
        startButton.clearAnimation();
        joinButton.clearAnimation();
        computerModeGame.clearAnimation();
        levelModeGame.clearAnimation();
        credits.clearAnimation();
        reversi.clearAnimation();
        settings.clearAnimation();
        super.onPause();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key)
    {
        if(key.equals(getString(R.string.backgroung_music_pref_key)))
        {
            initBackgroundMusicIfNeeded();
        }
    }

    private void initBackgroundMusicIfNeeded() {
        if (prefs.getBoolean(getString(R.string.backgroung_music_pref_key), false))
        {
            ReversyMediaPlayer.startBackgroundSound();
        }
        else
        {
            ReversyMediaPlayer.stopBackgroundMusic();
        }
    }

    private class ActivityBroadCastReceiver extends BroadcastReceiver
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            String gameState = intent.getStringExtra(getString(R.string.game_state));
            if(gameState.compareTo(getString(R.string.start_game)) == 0)
            {
                Intent intentToGame = new Intent(games.e.reversi.Presention.MainActivity.this, GameActivity.class);
                intentToGame.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                intentToGame.putExtra(App.Instance.getString(R.string.is_my_turn_key), intent.getBooleanExtra(getString(R.string.is_my_turn_key), true));
                intentToGame.putExtra(App.Instance.getString(R.string.multi_player_key), intent.getBooleanExtra(getString(R.string.multi_player_key), true));
                intentToGame.putExtra(App.Instance.getString(R.string.computer_mode_key), intent.getBooleanExtra(App.Instance.getString(R.string.computer_mode_key), false));
                intentToGame.putExtra("retry",intent.getBooleanExtra("retry", false));
                startActivity(intentToGame);
                if(alertDialog != null)
                {
                    alertDialog.dismiss();
                }
            }
            else if(gameState.compareTo(getString(R.string.wait_for_opp_to_host)) == 0)
            {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(games.e.reversi.Presention.MainActivity.this);
                alertDialogBuilder.setMessage(R.string.waiting);
                alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }

            else if(gameState.compareTo(getString(R.string.wait_for_opp_join)) == 0)
            {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(games.e.reversi.Presention.MainActivity.this);
                alertDialogBuilder.setMessage("Waiting..." + "\n" + "Your ip is " + App.wifiIpAddress() + "\n  (You're both must be connected to the same WIFI)");
                alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }

            else if(gameState.compareTo(getString(R.string.connection_fail)) == 0)
            {
                alertDialog.dismiss();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.settings :
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;

        }
    return  false;
    }



}

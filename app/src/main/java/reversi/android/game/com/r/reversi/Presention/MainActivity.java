package reversi.android.game.com.r.reversi.Presention;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.ads.MobileAds;

import reversi.android.game.com.r.reversi.Map.LevelsMapActivity;
import reversi.android.game.com.r.reversi.R;
import reversi.android.game.com.r.reversi.Features.ReversyMediaPlayer;
import reversi.android.game.com.r.reversi.Routers.GameStateRouter;
import reversi.android.game.com.r.reversi.Settings.SettingsActivity;
import reversi.android.game.com.r.reversi.utility.App;
import reversi.android.game.com.r.reversi.utility.DifficultyManager;
import reversi.android.game.com.r.reversi.utility.GoogleAnalyticsHelper;

public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener
{
    private ActivityBroadCastReceiver receiver = new ActivityBroadCastReceiver();
    private AlertDialog alertDialog = null;
    private SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(App.Instance);

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Typeface type = Typeface.createFromAsset(getAssets(),"fonts/edosz.ttf");
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
        TextView reversi = (TextView)findViewById(R.id.reversi) ;
        reversi.setTypeface(type);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        App.getLevelsModeManager().updateCurrentLevelIfMaxLevelsChanged();

        initButton();
//        ImageView reversiPic = (ImageView)findViewById(R.id.imageView);
//        reversiPic.setImageResource(R.drawable.reversi);
        initBackgroundMusicIfNeeded();
        MobileAds.initialize(getApplicationContext(), "ca-app-pub-1765755909018734~4242310807");

        PreferenceManager.getDefaultSharedPreferences(this).registerOnSharedPreferenceChangeListener(this);

    }

    private void initButton()
    {

        Button credits = (Button)findViewById(R.id.creditsButton);
        credits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CreditsActivity.class);
                startActivity(intent);
            }
        });

        Button levelModeGame = (Button)findViewById(R.id.levelButton);
        levelModeGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LevelsMapActivity.class);
                startActivity(intent);;
            }
        });

        Button computerModeGame = (Button)findViewById(R.id.computerButton);
        computerModeGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                App.Instance.getGoogleAnalytics().TrackGameTypeEvent(GoogleAnalyticsHelper.ONE_PLAYERS_GAME_PRESSED);
                App.setIsLevelsMode(false);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                alertDialogBuilder.setTitle("Choose Difficulty");

                final Spinner spinner = new Spinner(MainActivity.this);
                spinner.setGravity(Gravity.CENTER_HORIZONTAL);
                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.difficulty));
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

                alertDialogBuilder.setView(spinner);

                alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int dif  = spinner.getSelectedItemPosition();
                        switch (dif)
                        {
                            case  0 :
                                App.Instance.getDifficultyManager().setCurrentDifficulty(DifficultyManager.Difficulty.EASY_LEVEL_STR);
                                break;
                            case  1 :
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


                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();


            }

            });
        Button startButton = (Button)findViewById(R.id.buttonStart);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                App.Instance.getGoogleAnalytics().TrackGameTypeEvent(GoogleAnalyticsHelper.TWO_PLAYERS_GAME_PRESSED);
                Intent intentToGame = new Intent(MainActivity.this, GameActivity.class);
                App.setIsLevelsMode(false);
                intentToGame.putExtra(App.Instance.getString(R.string.is_my_turn_key), true);
                intentToGame.putExtra(App.Instance.getString(R.string.multi_player_key), false);
                startActivity(intentToGame);
            }
        });

        Button joinButton = (Button)findViewById(R.id.JoinGame);
        joinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                App.Instance.getGoogleAnalytics().TrackGameTypeEvent(GoogleAnalyticsHelper.JOIN_PLAYERS_GAME_PRESSED);
                App.setIsLevelsMode(false);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                alertDialogBuilder.setMessage("Enter you host's ip address");
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

        Button hostButton = (Button)findViewById(R.id.hostButton);
        hostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                App.Instance.getGoogleAnalytics().TrackGameTypeEvent(GoogleAnalyticsHelper.HOST_GAME_PRESSED);
                if(App.wifiIpAddress() == null)
                {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
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
                    App.setIsLevelsMode(false);
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
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(this);
        IntentFilter filter = new IntentFilter();
        filter.addAction(getString(R.string.game_state));
        localBroadcastManager.registerReceiver(receiver, filter);
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
            Log.d("music", "true");
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
                Intent intentToGame = new Intent(MainActivity.this, GameActivity.class);
                intentToGame.putExtra(App.Instance.getString(R.string.is_my_turn_key), intent.getBooleanExtra(getString(R.string.is_my_turn_key), true));
                intentToGame.putExtra(App.Instance.getString(R.string.multi_player_key), intent.getBooleanExtra(getString(R.string.multi_player_key), true));
                intentToGame.putExtra(App.Instance.getString(R.string.computer_mode_key), intent.getBooleanExtra(App.Instance.getString(R.string.computer_mode_key), false));
                startActivity(intentToGame);
                if(alertDialog != null)
                {
                    alertDialog.dismiss();
                }
            }
            else if(gameState.compareTo(getString(R.string.wait_for_opp_to_host)) == 0)
            {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                alertDialogBuilder.setMessage("Waiting ...");
                alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }

            else if(gameState.compareTo(getString(R.string.wait_for_opp_join)) == 0)
            {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                alertDialogBuilder.setMessage("Waiting..." + "\n" + "Your ip is " + App.wifiIpAddress());
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

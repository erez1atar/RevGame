package reversi.android.game.com.r.reversi.Presention;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import reversi.android.game.com.r.reversi.Connection.EventBus;
import reversi.android.game.com.r.reversi.Features.ReversyMediaPlayer;
import reversi.android.game.com.r.reversi.R;
import reversi.android.game.com.r.reversi.Routers.GameStateRouter;
import reversi.android.game.com.r.reversi.board.GameBoard;
import reversi.android.game.com.r.reversi.board.GameElementView;
import reversi.android.game.com.r.reversi.board.GamePiece;
import reversi.android.game.com.r.reversi.board.Player;
import reversi.android.game.com.r.reversi.board.Tile;
import reversi.android.game.com.r.reversi.controllers.IController;
import reversi.android.game.com.r.reversi.utility.App;
import reversi.android.game.com.r.reversi.utility.BitmapContainer;
import reversi.android.game.com.r.reversi.utility.DifficultyManager;
import reversi.android.game.com.r.reversi.utility.GoogleAnalyticsHelper;
import reversi.android.game.com.r.reversi.utility.LevelsModeManager;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;


public class GameActivity extends Activity implements IPresent
{
    private int numOfRows;
    private int numOfCols;
    private Boolean doCrowdSound;
    private Boolean doMusic;
    private boolean isOptionsDisplay;
    private GameBoard gameBoard;
    private IController controller;
    private InterstitialAd mInterstitialAd;

    private Animation rotation;
    private Animation flashAnim;

    private volatile Boolean isMyTurn;
    private Boolean multiPlayerGame;
    private TextView player1Text;
    private TextView player2Text;
    private TextView turnText;
    private String opponentName = "Opponent";

    private Button soundBtn;
    private Button musicBtn;
    private Button exitBtn;


    private ArrayList<GameElementView> viewsBlink = new ArrayList<>(10);
    private SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(App.Instance);

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Typeface type = Typeface.createFromAsset(getAssets(),"fonts/edosz.ttf");
        TextView turnText = (TextView)findViewById(R.id.turnText) ;
        turnText.setTypeface(type);
        TextView piece1 = (TextView)findViewById(R.id.player1Pieces) ;
        piece1.setTypeface(type);
        TextView piece2 = (TextView)findViewById(R.id.player2Pieces) ;
        piece2.setTypeface(type);


        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getString(R.string.inter_ad_unit_id));

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                //requestNewInterstitial();
                InnerEndGame();
            }
        });

        requestNewInterstitial();

        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        //getSupportActionBar().setDisplayShowTitleEnabled(false);

        isOptionsDisplay = false;

        initAnimation();
        initTextViews();
        initPictures();

        doCrowdSound = prefs.getBoolean(getString(R.string.cheers_sound_key), false);
        doMusic = prefs.getBoolean(getString(R.string.backgroung_music_pref_key), false);
        initMenuButtons();

        isMyTurn = getIntent().getBooleanExtra(getString(R.string.is_my_turn_key), false);
        multiPlayerGame = getIntent().getBooleanExtra(getString(R.string.multi_player_key), false);
        Boolean computerMode = getIntent().getBooleanExtra(getString(R.string.computer_mode_key), false);
        numOfRows = getResources().getInteger(R.integer.numOfRows);
        numOfCols = getResources().getInteger(R.integer.numOfCols);

        initBoard();
        initController(computerMode);

        controller.startGame();
        setTurnText();
        blinkOptionsTiles();

        TextView levelText = (TextView)findViewById(R.id.levelText);
        levelText.setTypeface(type);
        if (App.getIsLevelsMode())
        {
            int level = App.getLevelsModeManager().getCurrentLevel();
            levelText.setVisibility(View.VISIBLE);
            levelText.setText("Level " + String.valueOf(level));
        }
        else
        {

            levelText.setVisibility(View.INVISIBLE);
        }
    }

    private void initMenuButtons()
    {
        soundBtn = (Button)findViewById(R.id.sound_in_game);
        musicBtn = (Button)findViewById(R.id.music_in_game);
        exitBtn = (Button)findViewById(R.id.exit_game);
        soundBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doSound(! doCrowdSound);
            }
        });
        musicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doMusic(! doMusic);
            }
        });
        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        doMusic(doMusic);
        doSound(doCrowdSound);
    }

    private void doSound(boolean soundOn)
    {
        if(soundOn) {
            soundBtn.setBackgroundResource(R.drawable.sound_on);
        }
        else
        {
            soundBtn.setBackgroundResource(R.drawable.sound_off);
        }
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(getString(R.string.cheers_sound_key), soundOn);
        editor.apply();
        doCrowdSound = soundOn;
    }

    private void doMusic(boolean musicOn)
    {
        if(musicOn) {
            musicBtn.setBackgroundResource(R.drawable.music_on);
            ReversyMediaPlayer.startBackgroundSound();
        }
        else
        {
            musicBtn.setBackgroundResource(R.drawable.music_off);
            ReversyMediaPlayer.stopBackgroundMusic();
        }
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(getString(R.string.backgroung_music_pref_key), musicOn);
        editor.apply();
        doMusic = musicOn;
    }



    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("298931F3D2F59527C0E466D49F431D25")
                .build();

        mInterstitialAd.loadAd(adRequest);
    }

    private void initController(Boolean computerMode) {
        if(multiPlayerGame)
        {
            if(computerMode)
            {
                if(App.getIsLevelsMode())
                {
                    controller = App.getController(this, App.getLevelPlayer());
                }
                else {
                    controller = App.getController(this, App.getComputerPlayer());

                }
                opponentName = "Computer";
            }
            else
            {
                controller = App.getController(this, App.getConnectionManager());
            }
        }
        else
        {
            controller = App.getController(this , App.getDummyConnection());
        }
    }

    private void initBoard() {
        ViewGroup viewGroup = (ViewGroup)findViewById(R.id.board_game_include);
        gameBoard = (GameBoard) viewGroup.findViewById(R.id.game_board_custom);

        gameBoard.setNumOfCol(numOfCols);
        gameBoard.setNumOfRows(numOfRows);
        initBoardWithEmptyPic();

        gameBoard.setTurnListener(new GameBoard.TurnListener() {
            @Override
            public void onElementTouched(int row, int col) {
                if (isMyTurn || !multiPlayerGame) {
                    controller.onTileTouched(row, col);
                }
            }
        });
    }

    private void initTextViews()
    {
        player1Text = (TextView)findViewById(R.id.player1Pieces);
        player2Text = (TextView)findViewById(R.id.player2Pieces);
        turnText = (TextView)findViewById(R.id.turnText);
    }

    private void initBoardWithEmptyPic()
    {
        gameBoard.removeAllViews();
        for (int i = 0; i < numOfRows; ++i) {
            for (int j = 0; j < numOfCols; ++j)
            {
                GameElementView gameElementView = new GameElementView(this);
                gameElementView.setImageBitmap(BitmapContainer.get(GamePiece.EMPTY));
                gameElementView.setRow(i);
                gameElementView.setCol(j);
                gameBoard.addView(gameElementView);
            }
        }
    }

    private void initAnimation()
    {

        flashAnim = new AlphaAnimation(0.0f, 1.0f);
        flashAnim.setDuration(800); //You can manage the blinking time with this parameter
        flashAnim.setStartOffset(20);
        flashAnim.setRepeatMode(Animation.REVERSE);
        flashAnim.setRepeatCount(Animation.INFINITE);
        rotation = AnimationUtils.loadAnimation(this, R.anim.rotate);
    }

    private void initPictures()
    {
        ImageView player1Pic = (ImageView)findViewById(R.id.player1Pic);
        ImageView player2Pic = (ImageView)findViewById(R.id.player2Pic);
        player1Pic.setImageResource(R.drawable.black_ball_menu);
        player2Pic.setImageResource(R.drawable.white_ball_menu);
    }

    private void setTurnText()
    {
        if(multiPlayerGame)
        {
            if (isMyTurn )
            {
                turnText.setText(getString(R.string.my_turn_text));
                turnText.startAnimation(flashAnim);
            }
            else
            {
                turnText.setText(opponentName + "'s turn");
                turnText.clearAnimation();
            }
        }
        else
        {
            turnText.setText(controller.getCurrentPlayerTurn().getName() + "'s turn");
        }
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        controller.setUp();
        IntentFilter filter = new IntentFilter();
        filter.addAction(getString(R.string.turn_key));
        filter.addAction(getString(R.string.connection_key));
        EventBus.registerToGameMSGS(controller);
    }

    @Override
    protected void onPause()
    {
        EventBus.unregisterToGameMSGS(controller);
        super.onPause();
    }

    @Override
    public void updateChanges(final List<Tile> listOfChanges)
    {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.d("updateChanges", "here");
                for (Tile tile : listOfChanges) {
                    if(isOptionsDisplay)
                    {
                        stopViewsBlink();
                    }
                    GameElementView newView = gameBoard.getGameElementView(tile.getRow(), tile.getCol());
                    newView.setImageBitmap(BitmapContainer.get(tile.getGamePiece()));
                    newView.startAnimation(rotation);

                    player1Text.setText(String.valueOf(controller.getNumOfPiecesP1()));
                    player2Text.setText(String.valueOf(controller.getNumOfPiecesP2()));
                    if (doCrowdSound) {
                        ReversyMediaPlayer.startCrowdsSound();
                    }
                }
            }
        });

    }

    @Override
    public void updateChanges(Tile... listOfChanges)
    {
        updateChanges(new ArrayList<>(Arrays.asList(listOfChanges)));
    }

    @Override
    public void endGame(final Player winPlayer)
    {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(GameActivity.this);
                alertDialogBuilder.setMessage(winPlayer.getName() + " Won !");

                alertDialogBuilder.setPositiveButton("Back", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (mInterstitialAd.isLoaded()) {
                            Log.d("inter", "loaded");
                            mInterstitialAd.show();
                        } else {
                            Log.d("inter", "not loaded");
                            InnerEndGame();
                        }
                    }
                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });

    }

    @Override
    public void winLevel(final Player winPlayer) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(GameActivity.this);
                if(App.getLevelsModeManager().isLastLevel())
                {
                    alertDialogBuilder.setMessage("More Level Coming soon!");
                }
                else {
                    alertDialogBuilder.setMessage("You Won !");
                }

                alertDialogBuilder.setPositiveButton("Back", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (mInterstitialAd.isLoaded()) {
                            Log.d("inter", "loaded");
                            mInterstitialAd.show();
                        } else {
                            Log.d("inter", "not loaded");
                            InnerEndGame();
                        }
                    }
                });
                alertDialogBuilder.setNegativeButton("Next", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mInterstitialAd.setAdListener(new AdListener() {
                            @Override
                            public void onAdClosed() {
                                //requestNewInterstitial();
                                getToNextLevel();
                            }
                        });
                        if (mInterstitialAd.isLoaded()) {
                            Log.d("inter", "loaded");
                            mInterstitialAd.show();
                        } else {
                            Log.d("inter", "not loaded");
                            getToNextLevel();
                        }
                    }
                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });

    }

    @Override
    public void lossLevel(final Player winPlayer) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(GameActivity.this);
                alertDialogBuilder.setMessage(winPlayer.getName() + " Won !");

                alertDialogBuilder.setPositiveButton("Back", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (mInterstitialAd.isLoaded()) {
                            Log.d("inter", "loaded");
                            mInterstitialAd.show();
                        } else {
                            Log.d("inter", "not loaded");
                            InnerEndGame();
                        }
                    }
                });
                alertDialogBuilder.setNegativeButton("Replay", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mInterstitialAd.setAdListener(new AdListener() {
                            @Override
                            public void onAdClosed() {
                                //requestNewInterstitial();
                                replayLevel();
                            }
                        });
                        if (mInterstitialAd.isLoaded()) {
                            Log.d("inter", "loaded");
                            mInterstitialAd.show();
                        } else {
                            Log.d("inter", "not loaded");
                            replayLevel();
                        }
                    }
                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });
    }

    private void InnerEndGame()
    {
        Intent intentEndGame = new Intent(GameActivity.this, MainActivity.class);
        startActivity(intentEndGame);
        controller.closeGame();
    }

    private void getToNextLevel()
    {
        controller.closeGame();
        App.Instance.getGoogleAnalytics().TrackGameTypeEvent(GoogleAnalyticsHelper.LEVELS_PRESSED);
        App.setIsLevelsMode(true);
        GameStateRouter.sendStartGame(false, true, true);
    }

    private void replayLevel()
    {
        controller.closeGame();
        App.Instance.getGoogleAnalytics().TrackGameTypeEvent(GoogleAnalyticsHelper.LEVELS_PRESSED);
        App.setIsLevelsMode(true);
        GameStateRouter.sendStartGame(false, true, true);
    }

    @Override
    public void onTurnChange()
    {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                isMyTurn = !isMyTurn;
                Log.d("is my turn", "" + isMyTurn);
                setTurnText();
                blinkOptionsTiles();
            }
        });
    }

    private void showShakeMSG() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(App.Instance, "Come on !",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onConnectionError()
    {
        showDisconnectedDailog();
    }

    @Override
    public void responseToShake()
    {
        showShakeMSG();
    }

    @Override
    public void updateOpponentName(String name)
    {
        opponentName = name;
    }

    private void blinkOptionsTiles()
    {
        if(! multiPlayerGame || (multiPlayerGame && isMyTurn))
        {
            isOptionsDisplay = true;
            ArrayList<Tile> tilesToMark = new ArrayList<>();
            controller.getOptionsToPlay(tilesToMark);
            blinkView(tilesToMark);
        }
    }

    private void blinkView(List<Tile> tilesToBlink)
    {
        for(int i = 0 ; i < tilesToBlink.size() ; ++i)
        {
            Tile tile = tilesToBlink.get(i);
            GameElementView view = gameBoard.getGameElementView(tile.getRow(), tile.getCol());
            viewsBlink.add(view);
            view.setImageBitmap(BitmapContainer.get(GamePiece.OPTION));
            //view.startAnimation(flashAnim);
        }
    }

    private void stopViewsBlink()
    {
        isOptionsDisplay = false;
        for(int i = 0 ; i < viewsBlink.size() ; ++i)
        {
            GameElementView view = viewsBlink.get(i);
            view.setImageBitmap(BitmapContainer.get(GamePiece.EMPTY));
            //view.clearAnimation();
        }
        viewsBlink.clear();
    }

    private void showDisconnectedDailog()
    {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(GameActivity.this);
                alertDialogBuilder.setMessage("Connection error");
                alertDialogBuilder.setPositiveButton("Back", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intentEndGame = new Intent(GameActivity.this, MainActivity.class);
                        startActivity(intentEndGame);

                    }
                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });

    }

    @Override
    protected void onStop()
    {
        super.onStop();
        Log.d("gameActivity", "onStop");
        controller.closeGame();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.ECLAIR
                && keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            // Take care of calling this method on earlier versions of
            // the platform where it doesn't exist.
            onBackPressed();
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        // This will be called either automatically for you on 2.0
        // or later, or by the code above on earlier versions of the
        // platform.
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                //requestNewInterstitial();
                finish();
            }
        });
        if (mInterstitialAd.isLoaded()) {
            Log.d("inter", "loaded");
            mInterstitialAd.show();
        } else {
            Log.d("inter", "not loaded");
            finish();
        }
        return;
    }

}

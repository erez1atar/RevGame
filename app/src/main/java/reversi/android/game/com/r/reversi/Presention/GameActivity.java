package reversi.android.game.com.r.reversi.Presention;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import reversi.android.game.com.r.reversi.controllers.GameResult;
import reversi.android.game.com.r.reversi.controllers.IController;
import reversi.android.game.com.r.reversi.utility.App;
import reversi.android.game.com.r.reversi.utility.BitmapContainer;
import reversi.android.game.com.r.reversi.utility.DialogUtility;
import reversi.android.game.com.r.reversi.utility.GoogleAnalyticsHelper;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.plattysoft.leonids.ParticleSystem;


public class GameActivity extends Activity implements IPresent,RewardedVideoAdListener
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
    private String opponentName = null;
    private Typeface type;

    private Button soundBtn;
    private Button musicBtn;
    private Button exitBtn;
    private RewardedVideoAd mAd;;
    private final static String SHOW_VIDEO = "show_video_key";
    private ViewGroup.LayoutParams lp;


    private ArrayList<GameElementView> viewsBlink = new ArrayList<>(10);
    private SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(App.Instance);

    private ImageView greatView;
    private ImageView noMoves;
    private Animation fadeIn;
    private Animation scaleIn;
    private Animation rotate;
    private Animation pulse;

    private boolean animationRunning = false;

    private int numOfClicksonTest = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        this.changeUI();

        opponentName = getResources().getString(R.string.opponent);

        mAd = MobileAds.getRewardedVideoAdInstance(this);
        mAd.setRewardedVideoAdListener(this);
        loadRewardedVideoAd();

        this.initGreatMoveAnim();


        type = Typeface.createFromAsset(getAssets(),"fonts/edosz.ttf");
        TextView turnText = (TextView)findViewById(R.id.turnText) ;
        turnText.setTypeface(type);
        TextView piece1 = (TextView)findViewById(R.id.player1Pieces) ;
        piece1.setTypeface(type);
        TextView piece2 = (TextView)findViewById(R.id.player2Pieces) ;
        piece2.setTypeface(type);

        pulse = AnimationUtils.loadAnimation(this, R.anim.heart_pulse_big);

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
        numOfRows = App.Instance.getResources().getInteger(R.integer.numOfRows);
        numOfCols = App.Instance.getResources().getInteger(R.integer.numOfCols);;
        if(App.getIsLevelsMode()) {
            numOfRows = App.getLevelsModeManager().getBoardSize(App.getActiveLevel());
            numOfCols = App.getLevelsModeManager().getBoardSize(App.getActiveLevel());;
        }


        initController(computerMode);
        initBoard();
        Boolean retryGame = getIntent().getBooleanExtra("retry", false);
        if(retryGame)
        {
            controller.startRetryGame(App.Instance.getGameState());
        }
        else
        {
            controller.startGame();
        }
        setTurnText();
        blinkOptionsTiles();

        TextView levelText = (TextView)findViewById(R.id.levelText);
        levelText.setTypeface(type);
        if (App.getIsLevelsMode())
        {
            int level = App.getActiveLevel();
            levelText.setVisibility(View.VISIBLE);
            levelText.setText(getString(R.string.level_string) + " "+ String.valueOf(level));
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
                    App.createNewModel(App.getLevelsModeManager().getBoardSize(App.getActiveLevel()));
                    controller = App.getController(this, App.getLevelPlayer());
                }
                else {
                    numOfRows = App.getUserDefault().getBoardSize();
                    numOfCols = App.getUserDefault().getBoardSize();
                    App.createNewModel(App.getUserDefault().getBoardSize());
                    controller = App.getController(this, App.getComputerPlayer());

                }
                opponentName = getResources().getString(R.string.computer_string);
            }
            else
            {
                App.createNewModel(getResources().getInteger(R.integer.numOfRows));
                App.Instance.getGoogleAnalytics().TrackGameTypeEvent(GoogleAnalyticsHelper.MULTI_PLAYER_GAME_SECCES);
                controller = App.getController(this, App.getConnectionManager());
            }
        }
        else
        {
            App.createNewModel(getResources().getInteger(R.integer.numOfRows));
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
        player1Text.setAnimation(pulse);
        player2Text.setAnimation(pulse);
        turnText = (TextView)findViewById(R.id.turnText);
    }

    private void initBoardWithEmptyPic()
    {
        gameBoard.removeAllViews();
        for (int i = 0; i < numOfRows; ++i) {
            for (int j = 0; j < numOfCols; ++j)
            {
                GameElementView gameElementView = new GameElementView(this, App.getUserDefault().getUIString());
                //gameElementView.setImage(BitmapContainer.get(GamePiece.EMPTY));
                gameElementView.setRow(i);
                gameElementView.setCol(j);
                gameBoard.addView(gameElementView);
            }
        }
    }

    private void changeUI() {
        int border_res = 0;
        int border_dark_res = 0;

        LinearLayout playersPanel = (LinearLayout)findViewById(R.id.player1Lay);
        LinearLayout player1Panel = (LinearLayout)findViewById(R.id.game_player_1_stat_panel);
        LinearLayout player2Panel = (LinearLayout)findViewById(R.id.game_player_2_stat_panel);
        LinearLayout menu = (LinearLayout)findViewById(R.id.menu_game);

        if(App.getUserDefault().getUIString().equals("BLUE")) {
            border_res = R.drawable.border_blue;
            border_dark_res = R.drawable.border_dark_blue;
        }
        else {
            border_res = R.drawable.border;
            border_dark_res = R.drawable.border_dark;
        }
        player1Panel.setBackgroundResource(border_res);
        player2Panel.setBackgroundResource(border_res);
        menu.setBackgroundResource(border_dark_res);
    }

    private void initAnimation()
    {

        flashAnim = new AlphaAnimation(0.0f, 1.0f);
        flashAnim.setDuration(800); //You can manage the blinking time with this parameter
        flashAnim.setStartOffset(20);
        flashAnim.setRepeatMode(Animation.REVERSE);
        flashAnim.setRepeatCount(Animation.INFINITE);
        rotation = AnimationUtils.loadAnimation(this, R.anim.flip_rotation);
    }

    private void initPictures()
    {
        ImageView player1Pic = (ImageView)findViewById(R.id.player1Pic);
        ImageView player2Pic = (ImageView)findViewById(R.id.player2Pic);

        if(App.Instance.getIsDeveloperMode()) {
            player1Pic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    numOfClicksonTest++;
                    if(numOfClicksonTest > 10) {
                        controller.TESTlossGame();
                    }
                }
            });

            player2Pic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    numOfClicksonTest++;
                    if(numOfClicksonTest > 10) {
                        controller.TESTwinGame();
                    }
                }
            });
        }

        player1Pic.setImageResource(R.drawable.black_new);
        player2Pic.setImageResource(R.drawable.white_ball);

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
                turnText.setText(String.format(getResources().getString(R.string.base_turn_str), opponentName));
                turnText.clearAnimation();
            }
        }
        else
        {
            turnText.setText(String.format(getResources().getString(R.string.base_turn_str),controller.getCurrentPlayerTurn().getName()));
        }
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        mAd.resume(this);
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
        mAd.pause(this);
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mAd.destroy(this);
        super.onDestroy();

    }

    @Override
    public void updateChanges(final List<Tile> listOfChanges)
    {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                int numOfPlayer2 = 0;
                for (Tile tile : listOfChanges) {
                    if(isOptionsDisplay)
                    {
                        stopViewsBlink();
                    }
                    GameElementView newView = gameBoard.getGameElementView(tile.getRow(), tile.getCol());
                    newView.setImageWithChangeAnimation(BitmapContainer.get(tile.getGamePiece(),App.getUserDefault().getUIString()));

                    player1Text.setText(String.valueOf(controller.getNumOfPiecesP1()));
                    player2Text.setText(String.valueOf(controller.getNumOfPiecesP2()));
                    if(controller.getNumOfPiecesP1() > controller.getNumOfPiecesP2())
                    {
                        player1Text.setTextColor(Color.RED);
                        player2Text.setTextColor(Color.BLACK);
                    }
                    else
                    {
                        player1Text.setTextColor(Color.BLACK);
                        player2Text.setTextColor(Color.RED);
                    }
                    if (doCrowdSound) {
                        ReversyMediaPlayer.startCrowdsSound();
                    }
                    if(tile.getGamePiece() == GamePiece.PLAYER2)
                    {
                        ++numOfPlayer2;
                    }
                }
                if(numOfPlayer2 > (listOfChanges.size() / 2)) { // check if most of the tile is of player2, trying to avoid startgame
                    if ((listOfChanges.size() >= numOfRows * 0.6 && (numOfRows <= 10)) || ((listOfChanges.size() >= numOfRows) && (numOfRows > 10))) {
                        startGreatMoveAnim();
                    }
                }
            }
        });

    }

    private void initGreatMoveAnim()
    {
        greatView = new ImageView(this);
        greatView.setImageResource(R.drawable.great);
        lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        GameActivity.this.addContentView(greatView, lp);
        greatView.setVisibility(View.INVISIBLE);

        noMoves = new ImageView(this);
        noMoves.setImageResource(R.drawable.no_moves);
        lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        GameActivity.this.addContentView(noMoves, lp);
        noMoves.setVisibility(View.INVISIBLE);
    }
    private void startGreatMoveAnim() {
        if(!animationRunning) {
            animationRunning = true;
            fadeIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.scale_out);
            rotate = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.one_rotation);
            scaleIn =  AnimationUtils.loadAnimation(getApplicationContext(), R.anim.scale_in);
            fadeIn.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    greatView.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    greatView.startAnimation(rotate);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {}
            });

            rotate.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {}
                @Override
                public void onAnimationEnd(Animation animation) {
                    greatView.startAnimation(scaleIn);
                }
                @Override
                public void onAnimationRepeat(Animation animation) {}
            });

            scaleIn.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    Log.d("start anim", "start");
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    greatView.setVisibility(View.INVISIBLE);
                    greatView.clearAnimation();
                    animationRunning = false;

                }

                @Override
                public void onAnimationRepeat(Animation animation) {}
            });
            greatView.startAnimation(fadeIn);
        }


    }

    private void starNoMovesAnim() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(!animationRunning) {
                    animationRunning = true;
                    fadeIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.scale_out);
                    scaleIn =  AnimationUtils.loadAnimation(getApplicationContext(), R.anim.scale_in);
                    fadeIn.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {
                            noMoves.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            noMoves.startAnimation(scaleIn);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {}
                    });


                    scaleIn.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {
                            Log.d("start anim", "start");
                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            noMoves.setVisibility(View.INVISIBLE);
                            noMoves.clearAnimation();
                            animationRunning = false;

                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {}
                    });
                    noMoves.startAnimation(fadeIn);
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
    public void endGame(final Player winPlayer, final GameResult gameResult)
    {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                final Dialog dialog = new Dialog(GameActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(false);

                dialog.setContentView(R.layout.end_game_dialog);
                TextView text = (TextView)dialog.findViewById(R.id.end_game_message);
                if(gameResult == GameResult.END_GAME) {
                    text.setText(String.format(getResources().getString(R.string.base_won_regular_str),winPlayer.getName() ));
                }
                else if(gameResult == GameResult.END_GAME_PLAYER_NO_AVAILABLE_MOVES) {
                    text.setText(String.format(getResources().getString(R.string.base_won_after_player_no_moves_str),winPlayer.getName() ));
                }
                else if(gameResult == GameResult.END_GAME_NO_MOVES_ANY) {
                    text.setText(String.format(getResources().getString(R.string.base_won_after_any_no_moves_str),winPlayer.getName() ));
                }
                Button backBtn = (Button)dialog.findViewById(R.id.end_game_back_btn);
                backBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mInterstitialAd.setAdListener(new AdListener() {
                            @Override
                            public void onAdClosed() {
                                InnerEndGame();
                            }
                        });
                        if (mInterstitialAd.isLoaded()) {
                            mInterstitialAd.show();
                        } else {
                            InnerEndGame();
                        }
                    }
                });
                Button[] btns = {backBtn};
                DialogUtility.setBackGround(dialog, btns);
                dialog.show();

            }
        });

    }

    @Override
    public void winLevel(final Player winPlayer, final GameResult gameResult) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                GameActivity.this.confetiAnimation();
                final Dialog dialog = new Dialog(GameActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.win_level_dialog);

                TextView text = (TextView)dialog.findViewById(R.id.win_level_dialog_text);
                if(App.getLevelsModeManager().isLastLevel(App.getActiveLevel()))
                {
                    text.setText("More Level Coming soon!");
                }
                else {
                    if(App.getActiveLevel() + 1 != App.getLevelsModeManager().getGreatestLevel()) {
                        if(gameResult == GameResult.END_GAME_PLAYER_NO_AVAILABLE_MOVES) {
                            text.setText(String.format(getResources().getString(R.string.win_after_player_no_moves_level)));
                        }
                        else if(gameResult == GameResult.END_GAME_NO_MOVES_ANY) {
                            text.setText(String.format(getResources().getString(R.string.win_after_any_no_moves_level)));
                        }
                        else if(gameResult == GameResult.END_GAME) {
                            text.setText(String.format(getResources().getString(R.string.win_regular_level)));
                        }
                    }
                    else {
                        if(gameResult == GameResult.END_GAME_PLAYER_NO_AVAILABLE_MOVES) {
                            text.setText(String.format(getResources().getString(R.string.win_after_player_no_moves_and_unlock_lvl) , App.getActiveLevel() + 1));
                        }
                        else if(gameResult == GameResult.END_GAME_NO_MOVES_ANY) {
                            text.setText(String.format(getResources().getString(R.string.win_after_any_no_moves_and_unlock_lvl) , App.getActiveLevel() + 1));
                        }
                        else if(gameResult == GameResult.END_GAME) {
                            text.setText(String.format(getResources().getString(R.string.win_regular_and_unlock_lvl) , App.getActiveLevel() + 1));
                        }
                    }

                }

                Button nextBtn = (Button) dialog.findViewById(R.id.win_level_dialog_next);
                nextBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        mInterstitialAd.setAdListener(new AdListener() {
                            @Override
                            public void onAdClosed() {
                                //requestNewInterstitial();
                                finish();
                                getToNextLevel();
                            }
                        });
                        if (mInterstitialAd.isLoaded()) {
                            mInterstitialAd.show();
                        } else {
                            finish();
                            getToNextLevel();
                        }
                    }
                });

                Button backBtn = (Button) dialog.findViewById(R.id.win_level_dialog_back);
                backBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        mInterstitialAd.setAdListener(new AdListener() {
                            @Override
                            public void onAdClosed() {
                                //requestNewInterstitial();
                                InnerEndGame();
                            }
                        });
                        if (mInterstitialAd.isLoaded()) {
                            mInterstitialAd.show();
                        } else {
                            InnerEndGame();
                        }
                    }
                });
                backBtn.setTypeface(type);
                nextBtn.setTypeface(type);
                text.setTypeface(type);
                Button[] btns = {backBtn,nextBtn};
                DialogUtility.setBackGround(dialog,btns);
                dialog.show();
            }
        });

    }

    @Override
    public void lossLevel(final Player winPlayer, final GameResult gameResult) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                final Dialog dialog = new Dialog(GameActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.loss_level_dialog);

                TextView text = (TextView)dialog.findViewById(R.id.loss_level_dialog_text);
                if(gameResult == GameResult.END_GAME) {
                    text.setText(String.format(getResources().getString(R.string.base_won_regular_str),winPlayer.getName()));
                }
                else if(gameResult == GameResult.END_GAME_PLAYER_NO_AVAILABLE_MOVES) {
                    text.setText(String.format(getResources().getString(R.string.base_won_after_player_no_moves_str),winPlayer.getName()));
                }
                else if(gameResult == GameResult.END_GAME_NO_MOVES_ANY) {
                    text.setText(String.format(getResources().getString(R.string.base_won_after_any_no_moves_str),winPlayer.getName()));
                }


                Button replayBtn = (Button) dialog.findViewById(R.id.loss_level_dialog_replay);
                replayBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mInterstitialAd.setAdListener(new AdListener() {
                            @Override
                            public void onAdClosed() {
                                //requestNewInterstitial();
                                finish();
                                replayLevel();
                            }
                        });
                        if (mInterstitialAd.isLoaded()) {
                            mInterstitialAd.show();
                        } else {
                            finish();
                            replayLevel();
                        }
                    }
                });

                Button backBtn = (Button) dialog.findViewById(R.id.loss_level_dialog_back);
                backBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mInterstitialAd.setAdListener(new AdListener() {
                            @Override
                            public void onAdClosed() {
                                InnerEndGame();
                            }
                        });
                        if (mInterstitialAd.isLoaded()) {
                            mInterstitialAd.show();
                        } else {
                            InnerEndGame();
                        }
                    }
                });
                backBtn.setTypeface(type);
                replayBtn.setTypeface(type);
                text.setTypeface(type);
                Button[] btns = {replayBtn,backBtn};
                DialogUtility.setBackGround(dialog,btns);
                dialog.show();

                boolean show = prefs.getBoolean(GameActivity.SHOW_VIDEO, false);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putBoolean(GameActivity.SHOW_VIDEO, !show);
                editor.apply();
                if (mAd.isLoaded() && show) {
                    showVideoDialog();
                }


            }
        });
    }

    private void InnerEndGame()
    {
        finish();
        controller.closeGame();
    }

    private void getToNextLevel()
    {
        controller.closeGame();
        App.Instance.getGoogleAnalytics().TrackGameTypeEvent(GoogleAnalyticsHelper.LEVELS_PRESSED);
        App.setIsInLevelsMode(App.getActiveLevel() + 1);
        GameStateRouter.sendStartGame(false, true, true);
    }

    private void replayLevel()
    {
        controller.closeGame();
        App.Instance.getGoogleAnalytics().TrackGameTypeEvent(GoogleAnalyticsHelper.LEVELS_PRESSED);
        App.setIsInLevelsMode(App.getActiveLevel());
        GameStateRouter.sendStartGame(false, true, true);
    }

    @Override
    public void onTurnChange()
    {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                isMyTurn = !isMyTurn;
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

    @Override
    public void onNoMovesAvailable() {
        starNoMovesAnim();
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
            view.setImage(BitmapContainer.get(GamePiece.OPTION, App.getUserDefault().getUIString()));
            //view.startAnimation(flashAnim);
        }
    }

    private void stopViewsBlink()
    {
        isOptionsDisplay = false;
        for(int i = 0 ; i < viewsBlink.size() ; ++i)
        {
            GameElementView view = viewsBlink.get(i);
            view.setImage(BitmapContainer.get(GamePiece.EMPTY,App.getUserDefault().getUIString()));
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
            mInterstitialAd.show();
        } else {
            finish();
        }
        return;
    }

    @Override
    public void onRewardedVideoAdLoaded() {

    }

    @Override
    public void onRewardedVideoAdOpened() {

    }

    @Override
    public void onRewardedVideoStarted() {

    }

    @Override
    public void onRewardedVideoAdClosed() {
        finish();
    }

    @Override
    public void onRewarded(RewardItem rewardItem) {
        App.Instance.getGoogleAnalytics().TrackVideoRewarded();
        controller.closeGame();
        App.setIsInLevelsMode(App.getActiveLevel());
        GameStateRouter.sendRetryLevelStartGame(false, true, true);
    }

    @Override
    public void onRewardedVideoAdLeftApplication() {

    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int i) {

    }

    private void loadRewardedVideoAd() {
        mAd.loadAd("ca-app-pub-1765755909018734/4827186000", new AdRequest.Builder().build());
    }

    private void showVideoDialog(){
        final Dialog dialog = new Dialog(GameActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.video_dialog);

        Button skipBtn = (Button) dialog.findViewById(R.id.video_x);
        skipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        Button watchBtn = (Button) dialog.findViewById(R.id.video_dialog_watch_btn);
        watchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAd.isLoaded()) {
                    App.Instance.getGoogleAnalytics().TrackVideoStarted();
                    controller.saveGameStateToRetry();
                    mAd.show();
                }
            }
        });

        TextView watchTxt = (TextView) dialog.findViewById(R.id.watch_video_text);

        skipBtn.setTypeface(type);
        watchBtn.setTypeface(type);
        watchTxt.setTypeface(type);

        Button[] btns = {watchBtn};
        DialogUtility.setBackGround(dialog, btns);

        dialog.show();

    }

    private void confetiAnimation()
    {
        new ParticleSystem(this, 100, R.drawable.android_confeti, 5000)
                .setSpeedRange(0.1f, 0.25f)
                .setRotationSpeedRange(90, 180)
                .setInitialRotationRange(0, 360)
                .oneShot(this.player2Text, 100);
        new ParticleSystem(this, 100, R.drawable.android_confeti, 5000)
                .setSpeedRange(0.1f, 0.25f)
                .setRotationSpeedRange(90, 180)
                .setInitialRotationRange(0, 360)
                .oneShot(this.player1Text, 100);
        new ParticleSystem(this, 100, R.drawable.android_confeti, 5000)
                .setSpeedRange(0.1f, 0.25f)
                .setRotationSpeedRange(90, 180)
                .setInitialRotationRange(0, 360)
                .oneShot(this.turnText, 100);
    }
}

package reversi.android.game.com.r.reversi.Features;

import android.media.MediaPlayer;

import reversi.android.game.com.r.reversi.R;
import reversi.android.game.com.r.reversi.utility.App;

/**
 * Created by erez on 18/03/2016.
 */
public class ReversyMediaPlayer
{
    private static MediaPlayer backgroundMusic;
    private static MediaPlayer crowdSound;

    public static void startBackgroundSound()
    {
        if(backgroundMusic == null)
        {
            backgroundMusic = MediaPlayer.create(App.Instance, R.raw.game);
            backgroundMusic.setLooping(true);
        }
        backgroundMusic.start();
    }

    public static void stopBackgroundMusic()
    {
        if(backgroundMusic != null)
        {
            backgroundMusic.pause();
        }
    }

    public static void startCrowdsSound()
    {
        if(crowdSound == null)
        {
            crowdSound = MediaPlayer.create(App.Instance, R.raw.door_lock);
        }
        crowdSound.start();
    }
}

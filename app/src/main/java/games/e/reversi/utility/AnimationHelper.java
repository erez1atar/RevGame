package games.e.reversi.utility;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import java.util.ArrayList;
import java.util.concurrent.Callable;

/**
 * Created by Erez on 16/07/2018.
 */

public class AnimationHelper {

    public static void runAnimationsSequence(final View view, int[] animationsIds,final Callable<Void> onEnd) {
        ArrayList<Animation> animations = new ArrayList();
        for (int idx = 0; idx < animationsIds.length; idx++) {
            animations.add(AnimationUtils.loadAnimation(App.Instance, animationsIds[idx]));
        }
        for (int idx = 0; idx < animations.size(); idx++) {
            if (idx == 0) {
                view.startAnimation(animations.get(idx));
            }
            final boolean isFinalAnim = (idx + 1) == animations.size();
            Animation nextAnim = null;
            if(!isFinalAnim) {
                nextAnim = animations.get(idx + 1);
            }
            final Animation nextAnimFinal = nextAnim;

            animations.get(idx).setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    if(isFinalAnim) {
                        if(onEnd != null) {
                            try {
                                onEnd.call();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    else {
                        view.startAnimation(nextAnimFinal);
                    }
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });

        }
    }

}

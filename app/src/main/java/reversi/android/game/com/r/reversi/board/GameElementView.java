package reversi.android.game.com.r.reversi.board;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.text.Layout;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import reversi.android.game.com.r.reversi.utility.BitmapContainer;

/**
 * TODO: document your custom view class.
 */
public class GameElementView extends RelativeLayout
{
    private int row;
    private int col;
    private Context context = null;
    private ImageView image = null;

    public GameElementView(Context context)
    {
        super(context);
        this.setBackground(new BitmapDrawable(context.getResources(),BitmapContainer.get(GamePiece.EMPTY)));
        this.context = context;
    }

    public int getCol()
    {
        return col;
    }

    public int getRow()
    {
        return row;
    }

    public void setCol(int col)
    {
        this.col = col;
    }

    public void setRow(int row)
    {
        this.row = row;
    }

    public void setImage(Bitmap bitmap) {
        this.removeAllViews();
        image = new ImageView(this.context);

        image.setImageBitmap(bitmap);

        this.addView(image);
    }

    public void setImageWithChangeAnimation(Bitmap bitmap) {
        this.startFlipAnimationInner(bitmap);
    }

    public void startFlipAnimation(){
        this.startFlipAnimationInner(null);
    }

    private void startFlipAnimationInner(final Bitmap bitmapTarget) {
        final ObjectAnimator oa1 = ObjectAnimator.ofFloat(image, "scaleX", 1f, 0f);
        oa1.setDuration(150);
        oa1.setInterpolator(new DecelerateInterpolator());
        oa1.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if(bitmapTarget != null){
                    GameElementView.this.setImage(bitmapTarget);
                }
                final ObjectAnimator oa2 = ObjectAnimator.ofFloat(image, "scaleX", 0f, 1f);
                oa2.setInterpolator(new AccelerateDecelerateInterpolator());
                oa2.setDuration(150);
                oa2.start();
            }
        });
        oa1.start();
    }
}

package games.e.reversi.board;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import games.e.reversi.utility.BitmapContainer;

/**
 * TODO: document your custom view class.
 */
public class GameElementView extends RelativeLayout
{
    private int row;
    private int col;
    private Context context = null;
    private ImageView image = null;
    private float alpha = 0;

    public GameElementView(Context context, String color)
    {
        super(context);
        this.setBackground(new BitmapDrawable(context.getResources(), BitmapContainer.get(GamePiece.EMPTY,color)));
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

    public void clearView() {
        this.removeAllViews();
    }

    public void setAlphaToImage(int alpha) {
        this.getBackground().setAlpha(alpha);
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
                    games.e.reversi.board.GameElementView.this.setImage(bitmapTarget);
                }
                final ObjectAnimator oa2 = ObjectAnimator.ofFloat(image, "scaleX", 0f, 1f);
                oa2.setInterpolator(new AccelerateDecelerateInterpolator());
                oa2.setDuration(150);
                oa2.start();
            }
        });
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            // only for and newer versions
            this.setImage(bitmapTarget);
            return;
        }
        oa1.start();
    }
}

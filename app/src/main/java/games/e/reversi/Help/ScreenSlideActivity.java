package games.e.reversi.Help;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import games.e.reversi.R;


public class ScreenSlideActivity extends FragmentActivity
{
    private static final int NUM_PAGES = 3;
    private static String[] titles = new String[]{"Start game", "Move options", "Move result"};
    private static int[] imagesId = new int[]{R.drawable.start_game_pic, R.drawable.move_option_pic, R.drawable.move_result_pic};
    private ViewPager mPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_screen_slide);

        mPager = (ViewPager) findViewById(R.id.pager);
        PagerAdapter mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
    }

    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0)
        {
            super.onBackPressed();
        }
        else
        {
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }

    private class ScreenSlidePagerAdapter extends FragmentPagerAdapter
    {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return HelpScreenSlidePage.newInstance(titles[position], imagesId[position]);
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }
}

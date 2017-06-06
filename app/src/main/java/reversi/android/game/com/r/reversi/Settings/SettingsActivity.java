package reversi.android.game.com.r.reversi.Settings;

import android.preference.PreferenceActivity;
import android.os.Bundle;

import java.util.List;

import reversi.android.game.com.r.reversi.R;

public class SettingsActivity extends PreferenceActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onBuildHeaders(List<Header> target)
    {
        loadHeadersFromResource(R.xml.prefrences_headers, target);
    }

    @Override
    protected boolean isValidFragment(String fragmentName)
    {
        return FragmentSettings.class.getName().equals(fragmentName);
    }
}

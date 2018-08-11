package games.e.reversi.Settings;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import java.util.List;

import games.e.reversi.R;

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

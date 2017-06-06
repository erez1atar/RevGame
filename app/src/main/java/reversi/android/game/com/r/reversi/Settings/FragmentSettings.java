package reversi.android.game.com.r.reversi.Settings;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import reversi.android.game.com.r.reversi.R;

public class FragmentSettings extends PreferenceFragment
{
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        String settings = getArguments().getString(this.getString(R.string.settings_type_key));

        if(settings.compareTo(this.getString(R.string.general_setting_key)) == 0)
        {
            addPreferencesFromResource(R.xml.general_settings);
        }
    }
}

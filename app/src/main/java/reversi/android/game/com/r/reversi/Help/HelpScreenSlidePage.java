package reversi.android.game.com.r.reversi.Help;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import reversi.android.game.com.r.reversi.R;

public class HelpScreenSlidePage extends Fragment
{
    private static String key = "title";
    private static String imageKey = "image";
    private String textHelp;
    private int picId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_help_screen_slide_page, container, false);

        TextView text = (TextView)rootView.findViewById(R.id.helpTitle);
        text.setText(textHelp);
        ImageView pic = (ImageView)rootView.findViewById(R.id.game_help_pic);
        pic.setImageResource(picId);
        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        textHelp = bundle.getString(key);
        picId = bundle.getInt(imageKey);
    }

    public static HelpScreenSlidePage newInstance(String text , int imageId)
    {
        Bundle args = new Bundle();
        HelpScreenSlidePage fragment = new HelpScreenSlidePage();
        args.putString(key, text);
        args.putInt(imageKey,imageId);
        fragment.setArguments(args);
        return fragment;
    }
}

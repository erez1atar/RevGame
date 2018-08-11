package games.e.reversi.Presention;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import games.e.reversi.R;
import games.e.reversi.utility.App;

public class CreditsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credits);

        TextView textView = (TextView)findViewById(R.id.credit_feture_graphic);
        textView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Log.d("creditActivity", "onLongClick");
                Intent intent = new Intent(games.e.reversi.Presention.CreditsActivity.this,Developer_area_password.class);
                startActivity(intent);
                return false;
            }
        });

        TextView developerText = (TextView)findViewById(R.id.developerMode_text);
        if(App.Instance.getIsDeveloperMode())
        {
            developerText.setVisibility(View.VISIBLE);
        }
        else
        {
            developerText.setVisibility(View.INVISIBLE);
        }
    }
}

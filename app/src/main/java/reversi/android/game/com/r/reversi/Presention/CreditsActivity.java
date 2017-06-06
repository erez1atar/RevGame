package reversi.android.game.com.r.reversi.Presention;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import reversi.android.game.com.r.reversi.R;
import reversi.android.game.com.r.reversi.utility.App;

public class CreditsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credits);

        TextView textView = (TextView)findViewById(R.id.credit_feture_graphic);
        textView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Log.d("creditActivity", "onLongClick");
                Intent intent = new Intent(CreditsActivity.this,Developer_area_password.class);
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

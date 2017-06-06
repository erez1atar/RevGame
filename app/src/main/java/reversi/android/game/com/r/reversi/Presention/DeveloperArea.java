package reversi.android.game.com.r.reversi.Presention;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import reversi.android.game.com.r.reversi.R;
import reversi.android.game.com.r.reversi.utility.App;

public class DeveloperArea extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developer_area);
        final EditText level = (EditText) findViewById(R.id.developer_area_set_level);

        Button okBtn = (Button)findViewById(R.id.developer_area_ok_btn);
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int levelToSet = Integer.valueOf(level.getText().toString());
                App.getLevelsModeManager().setCurrentLevel(levelToSet);
            }
        });
    }
}

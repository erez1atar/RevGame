package games.e.reversi.Presention;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import games.e.reversi.R;
import games.e.reversi.utility.App;

public class Developer_area_password extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developer_area_password);

        final EditText password = (EditText) findViewById(R.id.password_text);

        Button okBtn = (Button)findViewById(R.id.password_ok_button);
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(App.Instance.getIsDeveloperMode() || password.getText().toString().compareTo("Erez12345678") == 0)
                {
                    App.Instance.setIsDeveloperMode();
                    Intent intent = new Intent(games.e.reversi.Presention.Developer_area_password.this,DeveloperArea.class);
                    startActivity(intent);
                }
            }
        });

        Button backBtn = (Button)findViewById(R.id.password_back_button);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}

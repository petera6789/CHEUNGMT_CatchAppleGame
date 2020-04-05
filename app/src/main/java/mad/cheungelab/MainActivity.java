package mad.cheungelab;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    public void toGuideScreen (View view){
        startActivity(new Intent(this, GuidelineActivity.class));
    }
    public void toAboutScreen (View view){
        startActivity(new Intent(this, AboutmeActivity.class));
    }
    public void toGameScreen (View view){
        startActivity(new Intent(this, GameActivity.class));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
}

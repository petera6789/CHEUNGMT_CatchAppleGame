package mad.cheungelab;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;

public class GuidelineActivity extends AppCompatActivity {
    MediaPlayer myMus = null;
    protected void onResume(){ // callback method, when interacting with user
        super.onResume(); // always call superclass
        if (myMus != null) myMus.start(); // start playing
    }
    @Override
    protected void onPause(){ // callback method, inactive: when no interacting
        super.onPause(); // always call superclass
        if (myMus != null) myMus.pause(); // pause playing
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guideline);
        myMus = MediaPlayer.create(this,R.raw.bm);
        myMus.setLooping(true);
    }
}

package mad.cheungelab;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;

public class GuidelineActivity extends AppCompatActivity {
    MediaPlayer myMus = null;
    protected void onResume(){
        super.onResume();
        if (myMus != null) myMus.start();
    }
    @Override
    protected void onPause(){
        super.onPause();
        if (myMus != null) myMus.pause();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guideline);
        myMus = MediaPlayer.create(this,R.raw.bm);
        myMus.setLooping(true);
    }
}

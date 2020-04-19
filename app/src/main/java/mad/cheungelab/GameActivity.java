package mad.cheungelab;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class GameActivity extends AppCompatActivity {
    private ImageView DropApple, badApple;
    private int screenWidth;
    private int screenHeight;
    private float appleX, appleY;
    private float badAppleX, badAppleY;
    private ImageView guy;
    private float guyX, guyY;
    private Handler handler = new Handler();
    private Timer timer = new Timer();
    private int layWidth;
    private TextView scoreShow, timeShow;
    private int score, time;
    private Button backButton, RetryButton;
    private TextView LastScore, gameover;
    public static int level = 1;
    ImageView heart1, heart2, heart3;
    MediaPlayer myMus = null;
    MediaPlayer coinS = null;
    MediaPlayer badS = null;
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
        setContentView(R.layout.activity_game);
        ConstraintLayout lay = findViewById(R.id.CLayout);
        guy = findViewById(R.id.Guy);
        DropApple = findViewById(R.id.Apple);
        scoreShow = findViewById(R.id.ScoreShow);
        timeShow = findViewById(R.id.Time);
        badApple = findViewById(R.id.BadApple);
        backButton = findViewById(R.id.BackBut);
        RetryButton = findViewById(R.id.RetryBut);
        heart1 = findViewById(R.id.Heart1);
        heart2 = findViewById(R.id.Heart2);
        heart3 = findViewById(R.id.Heart3);
        badS = MediaPlayer.create(this, R.raw.pickup02);
        myMus = MediaPlayer.create(this, R.raw.gamemusic);
        myMus.setLooping(true);
        gameover = findViewById(R.id.Gameover);
        LastScore = findViewById(R.id.LastSco);
        coinS = MediaPlayer.create(this, R.raw.coin01);
        LastScore.setVisibility(View.INVISIBLE);
        backButton.setVisibility(View.INVISIBLE);
        RetryButton.setVisibility(View.INVISIBLE);
        gameover.setVisibility(View.INVISIBLE);
        layWidth = lay.getWidth();
        guyX = guy.getX();
        guyY = guy.getY();
        badAppleX = badApple.getX();
        badAppleY = badApple.getY();

        guy.setX(0.0f);
        DropApple.setY(3000.0f);
        badApple.setY(3000.0f);
        appleY = DropApple.getY();
        DropApple.setVisibility(View.VISIBLE);
        guy.setVisibility(View.VISIBLE);

        time = 15;
        score = 0;
        scoreShow.setText("Score : 0");
        timeShow.setText("30");

        WindowManager wm = getWindowManager();
        Display disp = wm.getDefaultDisplay();
        Point size = new Point();
        disp.getSize(size);
        screenWidth = size.x;
        screenHeight = size.y;

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        time -= 1;
                        timeShow.setText("" + time);
                    }
                });
            }
        }, 0, 1000);

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        changePos();
                    }
                });
            }
        }, 0, 20);



    }

    public void changePos(){

        if(level ==1){
            badAppleY += 28;
        }
        if (level == 2){
            badAppleY += 36;
        }
        if(level ==3){
            badAppleY += 64;
        }
        float BACenterX = badAppleX + badApple.getWidth() /2;
        float BACenterY = badAppleY + badApple.getHeight() / 2;
        if (Hit(BACenterX, BACenterY)){
            badS.start();
            badAppleY = screenHeight+100;
            if(heart3.getVisibility() == View.VISIBLE){
                heart3.setVisibility(View.INVISIBLE);
            }
            else if (heart3.getVisibility() == View.INVISIBLE && heart2.getVisibility() == View.VISIBLE){
                heart2.setVisibility((View.INVISIBLE));
            }
            else {
                heart1.setVisibility((View.INVISIBLE));
            }
        }
        if (badAppleY > screenHeight){
            badAppleY = -100;
            badAppleX = (float) Math.floor(Math.random() * (screenWidth - badApple.getWidth()));
        }
        badApple.setX(badAppleX);
        badApple.setY(badAppleY);

        if(level ==1){
            appleY += 18;
        }
        if (level == 2){
            appleY += 24;
        }
        if(level ==3){
            appleY += 48;
        }
        float appleCenterX = appleX + DropApple.getWidth() /2;
        float appleCenterY = appleY + DropApple.getHeight() / 2;
        if(Hit(appleCenterX, appleCenterY)){
            coinS.start();
            appleY = screenHeight +100;
            score += 10 ;
        }

        if (appleY > screenHeight){
            appleY = -100;
            appleX = (float) Math.floor(Math.random() * (screenWidth - DropApple.getWidth()));
        }
        DropApple.setX(appleX);
        DropApple.setY(appleY);
        guy.setX(guyX);
        scoreShow.setText("Score : "+ score);

        if (time == 0 && heart1.getVisibility() != View.INVISIBLE){
            timer.cancel();
            final int finalScore = score;
            timeShow.setVisibility(View.INVISIBLE);
            guy.setVisibility(View.INVISIBLE);
            DropApple.setImageDrawable(null);
            badApple.setImageDrawable(null);
            scoreShow.setVisibility(View.INVISIBLE);
            LastScore.setText("Your Score: " + finalScore);
            if(level != 3) {
                RetryButton.setText("Next Level");
            }
            LastScore.setVisibility(View.VISIBLE);
            RetryButton.setVisibility(View.VISIBLE);
            backButton.setVisibility(View.VISIBLE);
            heart3.setVisibility((View.INVISIBLE));
            heart2.setVisibility((View.INVISIBLE));
            heart1.setVisibility((View.INVISIBLE));
            gameover.setVisibility((View.INVISIBLE));
        }

        else if(heart1.getVisibility() == View.INVISIBLE && time > 0){
            timer.cancel();
            final int finalScore = score;
            timeShow.setVisibility(View.INVISIBLE);
            guy.setVisibility(View.INVISIBLE);
            DropApple.setImageDrawable(null);
            badApple.setImageDrawable(null);
            scoreShow.setVisibility(View.INVISIBLE);
            LastScore.setText("Your Score: " + finalScore);
            LastScore.setVisibility(View.VISIBLE);
            RetryButton.setVisibility(View.VISIBLE);
            backButton.setVisibility(View.VISIBLE);
            gameover.setVisibility(View.VISIBLE);
        }
    }
    public boolean Hit(float x, float y){
        if(guyX <= x && x <= guyX+guy.getWidth() && guy.getY() <= y && y <= screenHeight){
            return true;
        }
        return false;
    }

    public boolean onTouchEvent(MotionEvent me){
        if(me.getAction() == MotionEvent.ACTION_MOVE){
            guyX = (int)me.getX()-120;
        }
        return true;
    }

    public void toMainScreen(View view){
        startActivity(new Intent(this, MainActivity.class));
    }
    public void reStart(View view){
        this.finish();
        if(RetryButton.getText().equals("Next Level")){
            level += 1;
        }
        startActivity(new Intent(this, GameActivity.class));
    }
}

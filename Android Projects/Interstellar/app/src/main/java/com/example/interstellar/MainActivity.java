package com.example.interstellar;

/**
 * Created by Mohsin Braer.
 */

import android.widget.EditText;
import android.content.Intent;
import android.graphics.Point;
import android.os.Handler;
//import android.support.v7.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Timer;
import java.util.TimerTask;
import java.lang.Runnable;

public class MainActivity extends AppCompatActivity {

    private TextView scoreLabel;
    private TextView startLabel;
    private ImageView ship;
    private ImageView goldStar;
    private ImageView bronzeStar;
    private ImageView meteor;
    private ImageView livesView;
    private ImageView doublePoints;
    private ImageView extrahealth;
    private boolean doublePointsOn;

    private int frameHeight;
    private int shipSize;
    private int screenWidth;
    private int screenHeight;

    private int doublePointsX;
    private int doublePointsY;
    private int counter;

    private int extrahealthX;
    private int extrahealthY;

    private int doublePointsSpeed;
    private int extrahealthSpeed;

    private int shipY;
    private int shipX;
    private int goldStarX;
    private int goldStarY;
    private int bronzeStarX;
    private int bronzeStarY;
    private int meteorX;
    private int meteorY;
    private int mysteryshipX;
    private int mysteryshipY;
    private int score;
    private int livesCount;
    private int speed;
    private int goldStarSpeed;
    private int bronzeStarSpeed;
    private int meteorSpeed;

    private final int interval = 1000; // 1 Second
    private Handler handler = new Handler();
    private Timer timer = new Timer();

    private boolean action_flg = false;
    private boolean start_flg = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) { // Creates the game field
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        livesCount = 3;
        speed = 20;
        goldStarSpeed = 16; //12
        bronzeStarSpeed = 25; //20
        meteorSpeed = 20;//16
        doublePointsSpeed = 19;
        extrahealthSpeed = 19;
        counter = 0;

        scoreLabel = (TextView) findViewById(R.id.scoreLabel);
        startLabel = (TextView) findViewById(R.id.startLabel);
        ship = (ImageView) findViewById(R.id.ship);
        goldStar = (ImageView) findViewById(R.id.goldStar);
        bronzeStar = (ImageView) findViewById(R.id.bronzeStar);
        meteor = (ImageView) findViewById(R.id.meteor);
        livesView = (ImageView) findViewById(R.id.livesView);
        doublePoints = (ImageView) findViewById(R.id.pointsPowerup);
        extrahealth = (ImageView) findViewById(R.id.healthPowerup);

        WindowManager wm = getWindowManager();
        Display disp = wm.getDefaultDisplay();
        Point size = new Point();
        disp.getSize(size);

        screenHeight = size.y;
        screenWidth = size.x;
        scoreLabel.setText("Score = 0");

        goldStar.setX(-80.0f);
        goldStar.setY(-80.0f);
        bronzeStar.setX(-80.0f);
        bronzeStar.setY(-80.0f);
        meteor.setX(-80.0f);
        meteor.setY(-80.0f);
        //mysteryship.setX(-80.0f);
        //mysteryship.setY(-80.0f);

        doublePoints.setX(-80.0f);
        doublePoints.setY(-80.0f);

        extrahealth.setX(-80.0f);
        extrahealth.setY(-80.0f);

    }

    public void invincibilityMode() { //NC
    }

    public void extraHealth() { // extra health powerup increases livesCount by one
        if(!(livesCount == 3)) {
            livesCount++;

            if(livesCount == 1) {
                livesView.setImageResource(R.drawable.heart2);
            }
            else if(livesCount == 2){
                livesView.setImageResource(R.drawable.heart3);
            }
        }
    }

    public void drawfMode(){

    }

    public void doublePointsToggle(){
        doublePointsOn = true;
    }

    public void changePos() {

        hitCheck();

        goldStarX -= goldStarSpeed;
        if (goldStarX < 0) {
            goldStarX = screenWidth + 20;
            goldStarY = (int)Math.floor(Math.random() * (frameHeight - goldStar.getHeight()));
        }
        goldStar.setX(goldStarX);
        goldStar.setY(goldStarY);

        extrahealthX -= extrahealthSpeed;
        if (extrahealthX < 0) {
            extrahealthX = screenWidth + 10000;
            extrahealthY = (int)Math.floor(Math.random() * (frameHeight - extrahealth.getHeight()));
        }
        extrahealth.setX(extrahealthX);
        extrahealth.setY(extrahealthY);


        doublePointsX -= doublePointsSpeed;
        if (doublePointsX < 0) {
            doublePointsX = screenWidth + 10000;
            doublePointsY = (int)Math.floor(Math.random() * (frameHeight - doublePoints.getHeight()));
        }
        doublePoints.setX(doublePointsX);
        doublePoints.setY(doublePointsY);

        meteorX -= meteorSpeed;
        if (meteorX < 0) {
            meteorX = screenWidth + 10;
            meteorY = (int)Math.floor(Math.random() * (frameHeight - meteor.getHeight()));
        }
        meteor.setX(meteorX);
        meteor.setY(meteorY);

        bronzeStarX -= bronzeStarSpeed;
        if (bronzeStarX < 0) {
            bronzeStarX = screenWidth + 5000;
            bronzeStarY = (int)Math.floor(Math.random() * (frameHeight - bronzeStar.getHeight()));
        }
        bronzeStar.setX(bronzeStarX);
        bronzeStar.setY(bronzeStarY);

        if (action_flg) {
            shipY -= 30f;
        } else {
            shipY += 30f;
        }
        if(shipY < 0)shipY = 0;

        if(shipY > frameHeight - shipSize)shipY = frameHeight - shipSize;

        ship.setY(shipY);
        scoreLabel.setText("Score = " + score);

        // Changes ship (each ship has its own abilities) after user reaches a score level
        if(score >= 100) {
            ship.setImageResource(R.drawable.ship2);
        }

        if(score >= 200) {
            ship.setImageResource(R.drawable.ship3);
        }

        if(score >= 300) {
            ship.setImageResource(R.drawable.ship4);
        }

        //Determine how many hearts should be displayed
        if(livesCount == 1){
            livesView.setImageResource(R.drawable.heart);
            //livesView.setX(0);
        }

        if(livesCount == 2){
            livesView.setImageResource(R.drawable.heart2);
        }

        if(livesCount == 3){
            livesView.setImageResource(R.drawable.heart3);
        }

        if(livesCount == 0){
            timer.cancel();
            timer = null;

            //After lives are lost, take user to the results activity and pass in score to be displayed
            Intent intent = new Intent(getApplicationContext(), result.class);  //getApplicationContext()
            intent.putExtra("SCORE", score);
            startActivity(intent);
        }

    }

    public void hitCheck(){ // checks to see if ship touches any of the objects on the screen (if touches any of the stars add points) (if touches meteor livesCount subtracts by one)
        int goldStarCenterX = goldStarX + goldStar.getWidth()/2;
        int goldStarCenterY = goldStarY + goldStar.getHeight()/2;
        int bronzeStarCenterX = bronzeStarX + bronzeStar.getWidth()/2;
        int bronzeStarCenterY = bronzeStarY + bronzeStar.getHeight()/2;
        int meteorCenterX = meteorX + meteor.getWidth()/2;
        int meteorCenterY = meteorY + meteor.getWidth()/2;

        int doublePointCenterX = doublePointsX + doublePoints.getWidth()/2;
        int doublePointCenterY = doublePointsY + doublePoints.getHeight()/2;

        int extrahealthCenterX = extrahealthX + extrahealth.getWidth()/2;
        int extrahealthCenterY = extrahealthY + extrahealth.getHeight()/2;


        //If the doublepoints icon's center touches the ship, doublepoints is activated
        if(0 <= doublePointCenterX && doublePointCenterX <= shipSize && shipY <= doublePointCenterY && doublePointCenterY <= shipY + shipSize){
            doublePointsToggle();
            Runnable runnable = new Runnable(){
                public void run() {
                    Toast.makeText(MainActivity.this, "Double Points!", Toast.LENGTH_SHORT).show();
                }
            };

            handler.postAtTime(runnable, System.currentTimeMillis()+interval);
            handler.postDelayed(runnable, interval);
        }


        //If the extra health icon's center touches the ship, extraHealth is activated
        if(0 <= extrahealthCenterX && extrahealthCenterX <= shipSize && shipY <= extrahealthCenterY && extrahealthCenterY <= shipY + shipSize){
            livesCount++;
            Runnable runnable = new Runnable(){
                public void run() {
                    Toast.makeText(MainActivity.this, "Extra Health!", Toast.LENGTH_SHORT).show();
                }
            };

            handler.postAtTime(runnable, System.currentTimeMillis()+interval);
            handler.postDelayed(runnable, interval);

        }


        if(0 <= goldStarCenterX && goldStarCenterX <= shipSize && shipY <= goldStarCenterY && goldStarCenterY <= shipY + shipSize){
            if(doublePointsOn){
                score += 10;
                counter ++;
            }
            goldStarX = -10;
            score += 10;
            goldStarSpeed ++;
            bronzeStarSpeed ++;
            meteorSpeed ++;
        }

        //If the ship touches the center of the star score increases by 30  point and 60 if doublepoints is active
        if(0 <= bronzeStarCenterX && bronzeStarCenterX <= shipSize && shipY <= bronzeStarCenterY && bronzeStarCenterY <= shipY + shipSize){
            if(doublePointsOn){
                score += 30;
                counter++;
            }
            bronzeStarX = -10;
            score += 30;
            goldStarSpeed ++;
            bronzeStarSpeed ++;
            meteorSpeed ++;
        }

        if(0 <= meteorCenterX && meteorCenterX <= shipSize && shipY <= meteorCenterY && meteorCenterY <= shipY + shipSize){
            meteorX = -10;
            livesCount -= 1;

        }


        if(counter == 5){
            doublePointsOn = false;
            counter = 0;
        }

    }
    public boolean onTouchEvent(MotionEvent me) {
        if (start_flg == false) {
            start_flg = true;

            FrameLayout frame = (FrameLayout) findViewById(R.id.frame);
            frameHeight = frame.getHeight();

            shipY=(int)ship.getY();

            shipSize = ship.getHeight();

            startLabel.setVisibility(View.GONE);

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
            }, 0, speed);
        } else {
            if (me.getAction() == MotionEvent.ACTION_DOWN) {
                action_flg = true;
            } else if (me.getAction() == MotionEvent.ACTION_UP) {
                action_flg = false;
            }
        }
        return true;
    }
}

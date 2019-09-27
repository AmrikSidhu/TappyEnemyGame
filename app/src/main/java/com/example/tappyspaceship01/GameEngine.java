package com.example.tappyspaceship01;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.Random;

public class GameEngine extends SurfaceView implements Runnable {

    // Android debug variables
    final static String TAG="TAPPY-SPACESHIP";

    // screen size
    int screenHeight;
    int screenWidth;
    int lives = 30;

    // game state
    boolean gameIsRunning;

    // threading
    Thread gameThread;


    // drawing variables
    SurfaceHolder holder;
    Canvas canvas;
    Paint paintbrush;



    // -----------------------------------
    // GAME SPECIFIC VARIABLES
    // -----------------------------------

    // ----------------------------
    // ## SPRITES
    // ----------------------------
int playerXposition;
int playerYposition;
    int enemyXposition;
    int enemyYposition;
    Bitmap playerImage;
    Rect playerHitBox;
    Rect enemyHitBox;
    int hitBoxXPosition;
    int hitBoxYPosition;
    int enemyHitBoxXPosition;
    int enemyHitBoxYPosition;
    Bitmap enemyImage;
   // int boom ;


    // ----------------------------
    // ## GAME STATS
    // ----------------------------

    public GameEngine(Context context, int w, int h) {
        super(context);


        this.holder = this.getHolder();
        this.paintbrush = new Paint();

        this.screenWidth = w;
        this.screenHeight = h;


        this.printScreenInfo();

        // @TODO: Add your sprites
        // @TODO: Any other game setup
        //pos player and enemy
        this.playerXposition = 300;
        this.playerYposition = 300;
        this.enemyXposition=1300;
        this.enemyYposition = 300;
        this.hitBoxXPosition = 300;
        this.hitBoxYPosition = 300;
        this.enemyHitBoxXPosition = 1300;
        this.enemyHitBoxYPosition = 300;


        this.playerImage = BitmapFactory.decodeResource(this.getContext().getResources(),
                R.drawable.player_ship);
        this.enemyImage = BitmapFactory.decodeResource(this.getContext().getResources(),R.drawable.alien_ship2);


                      this.playerHitBox = new Rect(this.hitBoxXPosition,
                              this.hitBoxYPosition,
                              this.hitBoxXPosition+playerImage.getWidth(),
                                this.hitBoxYPosition+playerImage.getHeight());


                      this.enemyHitBox = new Rect(this.enemyXposition,this.enemyYposition,this.enemyHitBoxXPosition+enemyImage.getWidth(),this.enemyHitBoxYPosition+enemyImage.getHeight());



    }


    private void printScreenInfo() {

        Log.d(TAG, "Screen (w, h) = " + this.screenWidth + "," + this.screenHeight);
    }

    private void spawnPlayer() {
        //@TODO: Start the player at the left side of screen
    }
    private void spawnEnemyShips() {
        Random random = new Random();

        //@TODO: Place the enemies in a random location

    }

    // ------------------------------
    // GAME STATE FUNCTIONS (run, stop, start)
    // ------------------------------
    @Override
    public void run() {
        while (gameIsRunning == true) {
            this.updatePositions();
            this.redrawSprites();
            this.setFPS();
        }
    }


    public void pauseGame() {
        gameIsRunning = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            // Error
        }
    }

    public void startGame() {
        gameIsRunning = true;
        gameThread = new Thread(this);
        gameThread.start();
    }


    // ------------------------------
    // GAME ENGINE FUNCTIONS
    // - update, draw, setFPS
    // ------------------------------

    String palyerAction ="";

    public void updatePositions() {
        // @TODO: Update position of player

        if(palyerAction == "mouse_down")
        {

// make player move
            this.playerYposition = playerYposition -20;
            this.playerHitBox.left  = this.playerXposition;
                        this.playerHitBox.top = this.playerYposition;
                        this.playerHitBox.right  = this.playerXposition + this.playerImage.getWidth();
                        this.playerHitBox.bottom = this.playerYposition + this.playerImage.getHeight();


           // this.hitBox


    }
        if(palyerAction == "mouse_up")
        {


            this.playerYposition = playerYposition +60;
            this.playerHitBox.left  = this.playerXposition;
            this.playerHitBox.top = this.playerYposition;
            this.playerHitBox.right  = this.playerXposition + this.playerImage.getWidth();
            this.playerHitBox.bottom = this.playerYposition + this.playerImage.getHeight();


        }


        this.enemyXposition = this.enemyXposition -20;
        this.enemyHitBox.left = this.enemyXposition;
        this.enemyHitBox.top = this.enemyYposition;
        this.enemyHitBox.right = this.enemyXposition + this.enemyImage.getWidth();
        this.enemyHitBox.bottom = this.enemyYposition + this.enemyImage.getHeight();

        if(this.enemyXposition <= this.playerXposition+150) {

            this.enemyXposition = 1300;
            this.enemyHitBox.left = this.enemyXposition;
            this.enemyHitBox.top = this.enemyYposition;
            this.enemyHitBox.right = this.enemyXposition + this.enemyImage.getWidth();
            this.enemyHitBox.bottom = this.enemyYposition + this.enemyImage.getHeight();

        }

        if (this.enemyHitBox.intersect(this.playerHitBox) == true)
        {
            lives = lives-1;
            Log.d(TAG, "********* Hit Boxes Collied successfully ");
            this.playerXposition = 300;
            this.playerYposition = 300;
            //this.enemyHitBoxYPosition = this.playerXposition;
            this.playerHitBox = new Rect
                    (this.playerXposition,this.playerYposition,
                            this.playerXposition+playerImage.getWidth(),
                            this.playerYposition+playerImage.getHeight());
            //Log.d(TAG,"Lives: " + lives);




        }


    }




    public void redrawSprites() {
        if (this.holder.getSurface().isValid()) {
            this.canvas = this.holder.lockCanvas();

            //----------------

            // configure the drawing tools
this.canvas.drawColor(Color.BLACK);
           // this.canvas.drawColor(Color.argb(155,155,125,15));
            paintbrush.setColor(Color.WHITE);


            // DRAW THE PLAYER HITBOX
            // ------------------------
            // 1. change the paintbrush settings so we can see the hitbox
            paintbrush.setColor(Color.BLUE);
            paintbrush.setStyle(Paint.Style.STROKE);
            paintbrush.setStrokeWidth(5);


//            Bitmap playerImage = BitmapFactory.decodeResource(this.getContext().getResources(),
//                    R.drawable.player_ship);

            canvas.drawBitmap(playerImage, playerXposition, playerYposition, paintbrush);

            //enemy
            canvas.drawRect(playerHitBox,paintbrush);
//            Bitmap playerImageEn = BitmapFactory.decodeResource(this.getContext().getResources(),
//                    R.drawable.alien_ship2);
            canvas.drawBitmap(enemyImage,enemyXposition,enemyYposition,paintbrush);
            canvas.drawRect(enemyHitBox,paintbrush);

            //canvas.drawBitmap(playerImageEn, this.enemyXposition, this.enemyYposition-50, paintbrush);

            //----------------

            paintbrush.setTextSize(60);
            canvas.drawText("Lives remaining: "+lives,0,100,paintbrush);
            paintbrush.setColor(Color.GREEN);
            this.holder.unlockCanvasAndPost(canvas);




        }
    }

    public void setFPS() {
        try {
            gameThread.sleep(120);
        }
        catch (Exception e) {

        }
    }

    // ------------------------------
    // USER INPUT FUNCTIONS
    // ------------------------------

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int userAction = event.getActionMasked();
        //@TODO: What should happen when person touches the screen?
        if (userAction == MotionEvent.ACTION_DOWN) {
            Log.d(TAG, "Person tapped the screen");
// press down _ Move player up
            palyerAction ="mouse_down";


        }

        else if (userAction == MotionEvent.ACTION_UP) {


            palyerAction ="mouse_up";
           // Log.d(TAG, "Person lifted finger");
        }

        return true;
    }
}

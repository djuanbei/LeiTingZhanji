package com.example.leitingzhanji;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.*;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private int speed = 10;
    PlaneView planeView;
    Bitmap background;
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            Matrix matrix = new Matrix();
            Random rand = new Random();
            int startX = rand.nextInt() % 100;
            if (startX < 0) {
                startX *= -1;
            }
            int startY = rand.nextInt() % 100;
            if (startY < 0) {
                startY *= -1;
            }
            Bitmap m = Bitmap.createBitmap(background, startX, startY, startX + 1000,
                    startY + 1200, matrix, true);
            Drawable dd = new BitmapDrawable(MainActivity.this.getResources(), m);
            planeView.setBackground(dd);

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        background = BitmapFactory.decodeResource(getResources(), R.drawable.black);

        // setContentView(R.layout.activity_main);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        planeView = new PlaneView(this);
        setContentView(planeView);
        planeView.setBackgroundResource(R.drawable.black);
        // handler.sendEmptyMessage(1);

        WindowManager windowManager = getWindowManager();

        Display display = windowManager.getDefaultDisplay();

        DisplayMetrics metrics = new DisplayMetrics();

        display.getMetrics(metrics);
        planeView.setFocusable(true);
        planeView.currentX = metrics.widthPixels / 2;
        planeView.currentY = metrics.heightPixels - 50;
        planeView.invalidate();

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(1);

            }
        };
        Timer timer = new Timer();
        timer.schedule(task, 2000, 30);

        planeView.setOnTouchListener(new View.OnTouchListener() {
                                         public boolean onTouch(View v, MotionEvent event) {
                                             planeView.currentX = event.getX();
                                             planeView.currentY = event.getY();
                                             planeView.invalidate();
                                             return true;
                                         }
                                     }

        );

        planeView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                switch (event.getKeyCode()) {
                    case KeyEvent.KEYCODE_S:
                        planeView.currentY += speed;
                        break;
                    case KeyEvent.KEYCODE_W:
                        planeView.currentY -= speed;
                        break;
                    case KeyEvent.KEYCODE_A:
                        planeView.currentX -= speed;
                        break;
                    case KeyEvent.KEYCODE_D:
                        planeView.currentX += speed;
                        break;
                }
                planeView.invalidate();
                return true;
            }

        });
    }
}

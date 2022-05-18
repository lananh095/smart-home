package com.example.smarthome;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import pl.pawelkleczkowski.customgauge.CustomGauge;

public class activity_gar extends AppCompatActivity {
    Runnable refresh;
    Handler handler = new Handler();
    public static Button click;
    public static CustomGauge gauge;
    public static TextView value_ppm, condi, txt_end;
    public static MediaPlayer bd;
    //public static Intent intent;
    public static Float val;
    //Notification notification;
    FloatingActionButton mfab;
    Boolean show_hide = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gar);
        mfab = (FloatingActionButton) findViewById(R.id.fab);
        bd = MediaPlayer.create(this, R.raw.baodongnhe);
        bd.setLooping(true);
        //intent = new Intent(activity_gar.this, baodong_Service.class);
        mfab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (show_hide == false) {
                    Intent intent_fab = new Intent(activity_gar.this, MainActivity.class);
                    startActivity(intent_fab);
                    finish();
                    show_hide = true;
                } else {
                    show_hide = false;
                }
            }
        });
        init();
        start();
        //setVal();
        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click.setVisibility(View.GONE);
                start();
                if (bd.isPlaying()) {
                    bd.stop();
                }
                FatchData process = new FatchData();
                process.execute();

            }
        });
        refresh = new Runnable() {
            public void run() {
                start();
                handler.postDelayed(refresh, 15000);
            }
        };
        handler.post(refresh);
    }


    private void start() {
        //click.setVisibility(View.GONE);
        FatchData process = new FatchData();
        process.execute();
    }


    private void init() {
        txt_end = findViewById(R.id.txt_end);
        click = findViewById(R.id.button);
        gauge = (CustomGauge) findViewById(R.id.gauge1);
        value_ppm = findViewById(R.id.textView);
        condi = findViewById(R.id.txt_condi);
        gauge.setStartValue(0);
        gauge.setEndValue(3000);
    }

   /* private void setNotification() {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.flames);
        notification = new Notification.Builder(this)
                .setContentTitle("Title push notification")
                .setContentText("Message push notification")
                .setSmallIcon(R.drawable.bellfire)
                //.setLargeIcon(bitmap)
                .setColor(getResources().getColor(R.color.color))
                .build();
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager != null) {
            notificationManager.notify(NOTIFICATON_ID, notification);
        }*/

    }

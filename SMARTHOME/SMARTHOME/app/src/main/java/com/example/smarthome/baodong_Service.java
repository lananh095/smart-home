package com.example.smarthome;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.widget.Toast;

public class baodong_Service extends Service {
    MediaPlayer bd;
    public baodong_Service() {
        bd = MediaPlayer.create(this,R.raw.baodongnhe);
        bd.setLooping(true);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(getApplicationContext(), "BAO DONG", Toast.LENGTH_SHORT);
        if (!bd.isPlaying()){
            bd.start();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Toast.makeText(getApplicationContext()," ", Toast.LENGTH_SHORT);
        if (bd.isPlaying()){
            bd.stop();
        }
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
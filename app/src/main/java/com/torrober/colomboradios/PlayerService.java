package com.torrober.colomboradios;


import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class PlayerService extends Service {
    private MediaPlayer mediaPlayer;
    private int status;
    private String url;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onCreate() {
        mediaPlayer = MediaPlayer.create(getApplicationContext(), Uri.parse(url));
        mediaPlayer.setLooping(false);
        this.status = -1;
    }

    public void onStart(Intent intent, int startId){
        mediaPlayer.start();
        this.status = 1;
    }
    public void onDestroy() {
        mediaPlayer.stop();
        this.status = 0;
    }

    public int getStatus() {
        return status;
    }
}

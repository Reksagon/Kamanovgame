package com.kamanov.game;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseError;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kamanov.game.databinding.ActivityMainBinding;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    static SharedPreferences unity;

    private ActivityMainBinding binding;
    static SharedPreferences myPrefrence;
    static SharedPreferences.Editor editor;
    static DatabaseReference base;
    float curVolume,maxVolume,leftVolume, rightVolume;
    int sound_start;
    SoundPool sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myPrefrence = getSharedPreferences("App_settings", MODE_PRIVATE);
        editor = myPrefrence.edit();
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        unity = getSharedPreferences("com.kamanov.game.v2.playerprefs", MODE_PRIVATE);
        base = FirebaseDatabase
                .getInstance("https://kamanovgame-default-rtdb.europe-west1.firebasedatabase.app/")
                .getReference()
                .child("Users");

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        curVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        leftVolume = curVolume / maxVolume;
        rightVolume = curVolume / maxVolume;

        Runnable mHidePart2Runnable = new Runnable() {
            @SuppressLint("InlinedApi")
            @Override
            public void run() {
                MediaPlayer voice = MediaPlayer.create(getApplicationContext(), R.raw.main);
                voice.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        if (voice != null) {
                            voice.release();
                        }
                    }
                });
                voice.setVolume(leftVolume/4, rightVolume/4);
                voice.start();
            }
        };
        mHidePart2Runnable.run();
        FB();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    static void FB() {
        String name = myPrefrence.getString("nickname", "nickname");
        String photo = myPrefrence.getString("PRODUCT_PHOTO", "photo");
        Integer value = unity.getInt("Level", 1);

        final boolean[] exist = {false};
        base.orderByChild("name")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot child : snapshot.getChildren()) {
                            User item = child.getValue(User.class);
                            if (item.getName().equals(name)) {
                                exist[0] = true;
                                child.getRef().setValue(new User(myPrefrence.getString("nickname", "nickname"),
                                        myPrefrence.getString("age", "111"),
                                        photo, value

                                ));
                                return;
                            }
                        }
                        if (!exist[0])
                            base
                                    .push()
                                    .setValue(new User(myPrefrence.getString("nickname", "nickname"),
                                            myPrefrence.getString("age", "111"),
                                            photo, value

                                    ));
                        else
                        {

                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

    }

}
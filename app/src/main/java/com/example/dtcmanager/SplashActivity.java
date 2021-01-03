package com.example.dtcmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Window;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Timer;
import java.util.TimerTask;

import io.paperdb.Paper;

public class SplashActivity extends AppCompatActivity {
    Window window;
    Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Paper.init(this);
        getSHA();
        if (Paper.book().read("user_id") != null) {
            startActivity(new Intent(SplashActivity.this, HomeActivity.class));
            finish();
        } else {
            if (Build.VERSION.SDK_INT >= 21) {
                window = this.getWindow();
                window.setStatusBarColor(this.getResources().getColor(R.color.black));
            }

            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }, 3000);
        }
    }
    private void getSHA() {
        PackageInfo info;
        try {
            info = getPackageManager().getPackageInfo("com.example.dtcmanager", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md;
                md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String something = new String(Base64.encode(md.digest(), 0));

                //String something = new String(Base64.encodeBytes(md.digest()));
                Log.i("Splash", "getSHA: "+ something);
            }
        } catch (PackageManager.NameNotFoundException e1) {
            Log.i("name not found", e1.toString());
        } catch (NoSuchAlgorithmException e) {
            Log.i("no such an algorithm", e.toString());
        } catch (Exception e) {
            Log.i("exception", e.toString());
        }
    }
}
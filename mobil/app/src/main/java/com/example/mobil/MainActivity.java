package com.example.mobil;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.content.Context;

public class MainActivity extends AppCompatActivity {
    private ImageView spinnerImage;
    private TextView statusText;
    private Handler handler = new Handler();
    private Runnable checkConnectionRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinnerImage = findViewById(R.id.spinnerImage);
        statusText = findViewById(R.id.statusText);

        // Sürekli dönen animasyon oluştur
        RotateAnimation rotateAnimation = new RotateAnimation(
                0, 720,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f
        );
        rotateAnimation.setDuration(4000); // 4 saniye dönme süresi
        rotateAnimation.setRepeatCount(0); // Tekrar yok
        rotateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        updateInternetStatus(isConnected());
                    }
                }, 3000); // 3. saniyede kontrol
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // Animasyon bittiğinde ListActivity'e geç
                Intent intent = new Intent(MainActivity.this, ListActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });

        // İnternet bağlantısını kontrol et ve animasyonu başlat
        checkConnectionRunnable = new Runnable() {
            @Override
            public void run() {
                if (isConnected()) {
                    statusText.setText("");
                    spinnerImage.startAnimation(rotateAnimation);
                } else {
                    updateInternetStatus(false);
                    handler.postDelayed(this, 1000);
                }
            }
        };
        handler.post(checkConnectionRunnable);
    }

    private boolean isConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    private void updateInternetStatus(boolean isConnected) {
        statusText.setText(isConnected ? "BAĞLANTI VAR" : "BAĞLANTI YOK");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(checkConnectionRunnable);
    }
}
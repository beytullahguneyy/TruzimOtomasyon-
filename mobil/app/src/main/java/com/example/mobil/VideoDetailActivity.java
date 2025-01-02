package com.example.mobil;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;
import android.widget.Button;
import com.google.android.material.textfield.TextInputEditText;
import android.text.TextUtils;

public class VideoDetailActivity extends AppCompatActivity {

    private WebView webView;
    private DatabaseHelper dbHelper;
    private static final String TAG = "VideoDetailActivity";

    private TextInputEditText tcNoInput;
    private TextInputEditText cardNumberInput;
    private TextInputEditText expiryDateInput;
    private TextInputEditText cvvInput;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_detail);

        try {
            // Form elemanlarını tanımla
            tcNoInput = findViewById(R.id.tcNoInput);
            cardNumberInput = findViewById(R.id.cardNumberInput);
            expiryDateInput = findViewById(R.id.expiryDateInput);
            cvvInput = findViewById(R.id.cvvInput);
            saveButton = findViewById(R.id.saveButton);

            // Satın Al butonuna tıklama olayı
            saveButton.setOnClickListener(v -> {
                if (validateForm()) {
                    processPurchase();
                }
            });

            // WebView'ı başlat
            webView = findViewById(R.id.webView);
            if (webView == null) {
                throw new Exception("WebView bulunamadı!");
            }

            WebSettings webSettings = webView.getSettings();
            webSettings.setJavaScriptEnabled(true);
            webSettings.setDomStorageEnabled(true);
            webSettings.setAllowContentAccess(true);
            webSettings.setAllowFileAccess(true);
            webSettings.setMediaPlaybackRequiresUserGesture(false);

            webView.setWebViewClient(new WebViewClient());

            dbHelper = new DatabaseHelper(this);

            String videoId = getIntent().getStringExtra("video_id");
            if (videoId == null) {
                throw new Exception("Video ID bulunamadı!");
            }

            VideoItem videoItem = dbHelper.getVideoItem(videoId);
            if (videoItem == null) {
                throw new Exception("Video bilgileri veritabanında bulunamadı!");
            }

            TextView titleView = findViewById(R.id.titleText);
            TextView descriptionView = findViewById(R.id.descriptionText);

            titleView.setText(videoItem.getTitle());
            descriptionView.setText(videoItem.getLongDesc());

            String youtubeId = videoItem.getYoutubeId();
            if (youtubeId != null && !youtubeId.isEmpty()) {
                loadYouTubeVideo(youtubeId);
            } else {
                throw new Exception("YouTube video ID'si bulunamadı!");
            }

        } catch (Exception e) {
            Log.e(TAG, "Hata oluştu: " + e.getMessage(), e);
            Toast.makeText(this, "Bir hata oluştu: " + e.getMessage(), Toast.LENGTH_LONG).show();
            finish();
        }
    }

    private boolean validateForm() {
        boolean isValid = true;

        String tcNo = tcNoInput.getText().toString();
        if (TextUtils.isEmpty(tcNo) || tcNo.length() != 11) {
            tcNoInput.setError("Geçerli bir T.C. Kimlik No giriniz");
            isValid = false;
        }

        // Kart numarası kontrolü
        String cardNumber = cardNumberInput.getText().toString();
        if (TextUtils.isEmpty(cardNumber) || cardNumber.length() != 16) {
            cardNumberInput.setError("Geçerli bir kart numarası giriniz");
            isValid = false;
        }

        // Son kullanma tarihi kontrolü
        String expiryDate = expiryDateInput.getText().toString();
        if (TextUtils.isEmpty(expiryDate) || expiryDate.length() != 5) {
            expiryDateInput.setError("Geçerli bir son kullanma tarihi giriniz (AA/YY)");
            isValid = false;
        }

        // CVV kontrolü
        String cvv = cvvInput.getText().toString();
        if (TextUtils.isEmpty(cvv) || cvv.length() != 3) {
            cvvInput.setError("Geçerli bir CVV giriniz");
            isValid = false;
        }

        return isValid;
    }

    private void processPurchase() {
        // Burada gerçek satın alma işlemi yapılabilir
        Toast.makeText(this, "Satın alma işlemi başarıyla tamamlandı!", Toast.LENGTH_LONG).show();
        finish();
    }

    private void loadYouTubeVideo(String videoId) {
        try {
            String html = "<!DOCTYPE html>" +
                    "<html>" +
                    "<head>" +
                    "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">" +
                    "<style>body { margin: 0; }</style>" +
                    "</head>" +
                    "<body>" +
                    "<div style=\"position: relative; padding-bottom: 56.25%; height: 0; overflow: hidden;\">" +
                    "<iframe style=\"position: absolute; top: 0; left: 0; width: 100%; height: 100%;\" " +
                    "src=\"https://www.youtube.com/embed/" + videoId + "\" " +
                    "frameborder=\"0\" " +
                    "allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture\" " +
                    "allowfullscreen></iframe>" +
                    "</div>" +
                    "</body>" +
                    "</html>";

            webView.loadDataWithBaseURL("https://www.youtube.com", html, "text/html", "UTF-8", null);
        } catch (Exception e) {
            Log.e(TAG, "Video yüklenirken hata oluştu: " + e.getMessage());
            Toast.makeText(this, "Video yüklenirken hata oluştu: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (webView != null) {
            webView.onPause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (webView != null) {
            webView.onResume();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (webView != null) {
            webView.destroy();
        }
    }
}
package com.example.mobil;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {
    private ListView listView;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        listView = findViewById(R.id.listView);
        dbHelper = new DatabaseHelper(this);

        // Liste öğelerini oluştur
        ArrayList<VideoItem> tourList = new ArrayList<>();
        tourList.add(new VideoItem("Almanya Turu", "Almanya'nın tarihi ve modern yüzü", "newalmanya"));
        tourList.add(new VideoItem("Rusya Turu", "Rusya'nın büyüleyici atmosferi", "newrusya"));
        tourList.add(new VideoItem("USA Turu", "Amerika'nın muhteşem manzaraları", "newusa"));
        tourList.add(new VideoItem("İspanya Turu", "İspanya'nın güzelliklerini keşfedin", "newispanya"));
        tourList.add(new VideoItem("Belçika Turu", "Belçika'nın eşsiz mimarisi", "newbelcika"));

        // Özel adapter'ı ayarla
        VideoAdapter adapter = new VideoAdapter(this, tourList);
        listView.setAdapter(adapter);

        // Liste öğelerine tıklama olayını dinle
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                VideoItem selectedTour = tourList.get(position);
                Intent intent = new Intent(ListActivity.this, VideoDetailActivity.class);
                intent.putExtra("video_id", selectedTour.getVideoId());
                startActivity(intent);
            }
        });
    }
}
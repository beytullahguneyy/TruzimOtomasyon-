package com.example.mobil;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;

public class VideoAdapter extends ArrayAdapter<VideoItem> {
    private Context context;
    private ArrayList<VideoItem> tours;

    public VideoAdapter(Context context, ArrayList<VideoItem> tours) {
        super(context, 0, tours);
        this.context = context;
        this.tours = tours;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.video_list_item, parent, false);
        }

        VideoItem tour = tours.get(position);

        ImageView imageView = convertView.findViewById(R.id.thumbnailImage);
        TextView titleView = convertView.findViewById(R.id.titleText);

        // Resim adına göre drawable ID'sini al
        String imageName = tour.getVideoId(); // örneğin "almanya", "rusya" vs.
        int resourceId = context.getResources().getIdentifier(
                imageName, // resim adı
                "drawable", // kaynak türü
                context.getPackageName()
        );

        // Resmi ve başlığı ayarla
        if (resourceId != 0) {
            imageView.setImageResource(resourceId);
        }
        titleView.setText(tour.getTitle());

        return convertView;
    }
}
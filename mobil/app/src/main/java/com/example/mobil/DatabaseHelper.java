package com.example.mobil;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "TourDB";
    private static final int DATABASE_VERSION = 7;
    private static final String TABLE_VIDEOS = "tours";

    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_VIDEO_ID = "video_id";
    private static final String KEY_YOUTUBE_ID = "youtube_id";
    private static final String KEY_LONG_DESC = "long_description";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_VIDEOS_TABLE = "CREATE TABLE " + TABLE_VIDEOS + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_TITLE + " TEXT,"
                + KEY_DESCRIPTION + " TEXT,"
                + KEY_VIDEO_ID + " TEXT,"
                + KEY_YOUTUBE_ID + " TEXT,"
                + KEY_LONG_DESC + " TEXT" + ")";
        db.execSQL(CREATE_VIDEOS_TABLE);

        insertTours(db);
    }

    private void insertTours(SQLiteDatabase db) {
        db.delete(TABLE_VIDEOS, null, null);

        ContentValues values = new ContentValues();

        // Almanya Turu
        values.clear();
        values.put(KEY_TITLE, "Almanya Turu");
        values.put(KEY_DESCRIPTION, "Almanya'nın tarihi ve modern yüzü");
        values.put(KEY_VIDEO_ID, "newalmanya");
        values.put(KEY_YOUTUBE_ID, "Gy54Y13Cquk");  // Yeni Almanya video ID'si
        values.put(KEY_LONG_DESC, "Almanya'nın büyüleyici dünyasına hoş geldiniz! Berlin'in tarihi atmosferi, " +
                "Münih'in Oktoberfest coşkusu, Hamburg'un modern limanları ve daha fazlası... " +
                "Almanya'nın zengin kültürü, muhteşem mimarisi, lezzetli yemekleri ve misafirperver " +
                "insanlarıyla unutulmaz bir deneyim yaşayın.");
        db.insert(TABLE_VIDEOS, null, values);

        // Rusya Turu
        values.clear();
        values.put(KEY_TITLE, "Rusya Turu");
        values.put(KEY_DESCRIPTION, "Rusya'nın büyüleyici atmosferi");
        values.put(KEY_VIDEO_ID, "newrusya");
        values.put(KEY_YOUTUBE_ID, "6mL8_U8gqhA");
        values.put(KEY_LONG_DESC, "Rusya'nın muhteşem mimarisi ve kültürünü keşfedin. " +
                "Kızıl Meydan, Kremlin Sarayı, St. Petersburg'daki Hermitage Müzesi ve daha fazlası... " +
                "Rus kültürünün derinliklerine inin ve unutulmaz anılar biriktirin.");
        db.insert(TABLE_VIDEOS, null, values);

        // USA Turu
        values.clear();
        values.put(KEY_TITLE, "USA Turu");
        values.put(KEY_DESCRIPTION, "Amerika'nın muhteşem manzaraları");
        values.put(KEY_VIDEO_ID, "newusa");
        values.put(KEY_YOUTUBE_ID, "q0R3NEjnPvA");  // Yeni USA video ID'si
        values.put(KEY_LONG_DESC, "Amerika'nın en güzel yerlerini keşfedin. " +
                "New York'ta Times Meydanı, Grand Canyon'un muhteşem manzarası, " +
                "Las Vegas'ın göz kamaştırıcı ışıkları ve San Francisco'nun Golden Gate Köprüsü... " +
                "Amerika'nın çeşitliliğini ve dinamizmini yaşayın.");
        db.insert(TABLE_VIDEOS, null, values);

        // İspanya Turu
        values.clear();
        values.put(KEY_TITLE, "İspanya Turu");
        values.put(KEY_DESCRIPTION, "İspanya'nın güzelliklerini keşfedin");
        values.put(KEY_VIDEO_ID, "newispanya");
        values.put(KEY_YOUTUBE_ID, "HHJLy83S_e0");  // Yeni İspanya video ID'si
        values.put(KEY_LONG_DESC, "İspanya'nın büyüleyici dünyasına hoş geldiniz! " +
                "Barcelona'da Sagrada Familia, Madrid'de Royal Palace, " +
                "Sevilla'da Plaza de España ve daha fazlasını keşfedin. " +
                "İspanyol kültürü, flamenko dansı, tapas lezzetleri ve muhteşem plajlarıyla unutulmaz bir deneyim yaşayın." +
                " " +
                "ücret: 35000TL");
        db.insert(TABLE_VIDEOS, null, values);

        // Belçika Turu
        values.clear();
        values.put(KEY_TITLE, "Belçika Turu");
        values.put(KEY_DESCRIPTION, "Belçika'nın eşsiz mimarisi");
        values.put(KEY_VIDEO_ID, "newbelcika");
        values.put(KEY_YOUTUBE_ID, "dXCQxjpusfk");  // Yeni Belçika video ID'si
        values.put(KEY_LONG_DESC, "Belçika'nın tarihi şehirlerini keşfedin. " +
                "Brüksel'de Grand Place, Brugge'nin orta çağ mimarisi, " +
                "Antwerp'in elmas bölgesi ve daha fazlası... " +
                "Belçika çikolatası, birası ve waffle'ı ile unutulmaz bir lezzet deneyimi yaşayın.");
        db.insert(TABLE_VIDEOS, null, values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_VIDEOS);
        onCreate(db);
    }

    public VideoItem getVideoItem(String videoId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_VIDEOS, null, KEY_VIDEO_ID + "=?",
                new String[]{videoId}, null, null, null);

        VideoItem item = null;
        if (cursor.moveToFirst()) {
            item = new VideoItem(
                    cursor.getString(cursor.getColumnIndexOrThrow(KEY_TITLE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(KEY_DESCRIPTION)),
                    cursor.getString(cursor.getColumnIndexOrThrow(KEY_VIDEO_ID))
            );
            item.setYoutubeId(cursor.getString(cursor.getColumnIndexOrThrow(KEY_YOUTUBE_ID)));
            item.setLongDesc(cursor.getString(cursor.getColumnIndexOrThrow(KEY_LONG_DESC)));
        }
        cursor.close();
        return item;
    }
}
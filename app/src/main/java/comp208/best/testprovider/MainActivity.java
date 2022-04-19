package comp208.best.testprovider;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

import comp208.best.testprovider.adapters.AlbumAdapter;
import comp208.best.testprovider.model.Album;

public class MainActivity extends AppCompatActivity {

    String TAG = "--";

    Handler handler = new Handler();
    ListView listView;
    ArrayList<Album> albums = new ArrayList<>();
    AlbumAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.txtData);
        listView = (ListView) findViewById(R.id.txtData);
        loadAlbums();



    }

    Runnable showDataList = () ->
    {
        listView.setAdapter(null);
        arrayAdapter = new AlbumAdapter(
                this,
                albums);
        listView.setAdapter(arrayAdapter);
    };

    void loadAlbums() {
        Runnable getAlbums = () -> {
            ContentResolver contentResolver = getContentResolver();
            Uri.Builder builder = new Uri.Builder();
            builder.scheme("content").authority(TestContentProvider.Contract.AUTHORITY);
            Uri uri = builder.build();
            Cursor cursor = contentResolver.query(uri, null, null, null, null);

            Log.i("onCreate: ", uri.toString());


            Log.i(TAG, "onCreate: " + "query returned");

            if (cursor == null) {
                Log.i(TAG, "onCreate: " + "cursor is null");
            } else {
                while (cursor.moveToNext()) {
                    Album album = new Album();
                    Log.i(TAG, "onCreate: " + cursor.getInt(0) + " " + cursor.getInt(1) + " " + cursor.getString(2));
                    album.setUserId(cursor.getInt(0));
                    album.setId(cursor.getInt(1));
                    album.setTitle(cursor.getString(2));
                    albums.add(album);
                }
                cursor.close();
            }
            handler.post(showDataList);
        };
        Thread thread = new Thread(getAlbums);
        thread.start();

    }


}
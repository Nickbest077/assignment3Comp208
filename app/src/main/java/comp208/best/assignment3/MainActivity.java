package comp208.best.assignment3;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import comp208.best.assignment3.adapters.AlbumAdapter;
import comp208.best.assignment3.model.Album;
import comp208.best.assignment3.R;

public class MainActivity extends AppCompatActivity {

    String TAG = "--";

    Handler handler = new Handler();
    ListView listView;
    ArrayList<Album> albums = new ArrayList<>();
    AlbumAdapter arrayAdapter;

    /**
     * When activity is created listView is assigned
     * and albums are loaded and displayed on the screen
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.txtData);
        loadAlbums();

    }

    /**
     * Runnable that displays the list of albums as ListView
     * uses custom Album Adapter to handle display of album objects
     */
    Runnable showDataList = () ->
    {
        listView.setAdapter(null);
        arrayAdapter = new AlbumAdapter(
                this,
                albums);
        listView.setAdapter(arrayAdapter);
    };

    /**
     * function to load values from matrix cursor into a list in main activity
     * content provider is queried then returns the list of albums
     * showData list isd then called to display values as a ListView with custom layout
     */
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
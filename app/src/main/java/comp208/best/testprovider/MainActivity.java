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
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import comp208.best.testprovider.model.Album;

public class MainActivity extends AppCompatActivity {

    String TAG = "--";

     Handler handler = new Handler();
     TextView txtData;
     Album[] albums = new Album[] {};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtData = findViewById(R.id.txtData);

        ContentResolver contentResolver = getContentResolver();
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("content").authority(TestContentProvider.Contract.AUTHORITY);
        Uri uri = builder.build();


        Log.i("onCreate: ", uri.toString());

        Cursor cursor = contentResolver.query(uri, null, null, null, null);

        Log.i(TAG, "onCreate: " + "query returned" );
        Log.i(TAG, DatabaseUtils.dumpCursorToString(cursor));

        if (cursor == null)
        {
            Log.i(TAG, "onCreate: " + "cursor is null");
        }

        else{
            while (cursor.moveToNext()){
                Log.i(TAG, "onCreate: " + cursor.getInt(0) + " "+ cursor.getString(1));
            }
            cursor.close();
        }



    }

    Runnable showDataList = ()->
    {
        txtData.setText("");
        for (Album item: albums) {
            Log.i(TAG, "Show:  " + item.getId() + " " + item.getUserId() + " "+ item.getTitle());
            txtData.append(item.getId() + " " + item.getUserId() + " "+ item.getTitle()+ "\n");
        }
    };



}
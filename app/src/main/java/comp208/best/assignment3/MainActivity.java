package comp208.best.assignment3;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import comp208.best.assignment3.adapters.AlbumAdapter;
import comp208.best.assignment3.adapters.CommentAdapter;
import comp208.best.assignment3.adapters.PostAdapter;
import comp208.best.assignment3.model.Album;
import comp208.best.assignment3.model.Comment;
import comp208.best.assignment3.model.Post;

public class MainActivity extends AppCompatActivity {

    String TAG = "--";

    private static final String ALBUMS = "albums";
    private static final String POSTS = "posts";
    private static final String COMMENTS = "comments";
    Handler handler = new Handler();
    ListView listView;
    ArrayList<Album> albums = new ArrayList<>();
    ArrayList<Post> posts = new ArrayList<>();
    ArrayList<Comment> comments = new ArrayList<>();
    BaseAdapter adapter;

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

    }

    /**
     * Runnable that displays the list of albums as ListView
     * uses custom Album Adapter to handle display of album objects
     */
    void displayData(String dataType) {
        Runnable showDataList = () ->
        {
            listView.setAdapter(null);
            if(dataType.equals( ALBUMS)) {
                adapter = new AlbumAdapter(
                        this,
                        albums);
            }
            if(dataType .equals(POSTS)) {
                adapter = new PostAdapter(
                        this,
                        posts);
            }
            if(dataType.equals(COMMENTS)){
                adapter = new CommentAdapter(
                        this,
                        comments);
            }
            listView.setAdapter(adapter);
        };
        handler.post(showDataList);
    }

    /**
     * function to load values from matrix cursor into a list in main activity
     * content provider is queried then returns the list of albums
     * showData list isd then called to display values as a ListView with custom layout
     */
    public void onClick(View view){
        String data = null;
        switch (view.getId()) {
            case (R.id.albumBtn):
                data = ALBUMS;
                break;
            case (R.id.postsBtn):
                data = POSTS;
                break;
            case (R.id.commentsBtn):
                data = COMMENTS;
                break;
        }
        loadData(data);
    }
    void loadData(String dataType) {
        Runnable getAlbums = () -> {
            ContentResolver contentResolver = getContentResolver();
            Uri.Builder builder = new Uri.Builder();
            builder.scheme("content").authority(TestContentProvider.Contract.AUTHORITY).path(dataType);
            Uri uri = builder.build();
            Cursor cursor = contentResolver.query(uri, null, null, null, null);

            Log.i("onCreate: ", uri.toString());


            Log.i(TAG, "onCreate: " + "query returned");

            if (cursor == null) {
                Log.i(TAG, "onCreate: " + "cursor is null");
            } else {
                cursor.moveToPosition(-1);
                if (dataType.equals(ALBUMS)) {
                    while (cursor.moveToNext()) {
                        Album album = new Album();
                        album.setUserId(cursor.getInt(0));
                        album.setId(cursor.getInt(1));
                        album.setTitle(cursor.getString(2));
                        albums.add(album);
                    }
                }
                if (dataType.equals(POSTS)){
                    while (cursor.moveToNext()) {
                        Post post = new Post();
                        post.setUserId(cursor.getInt(0));
                        post.setId(cursor.getInt(1));
                        post.setTitle(cursor.getString(2));
                        post.setBody(cursor.getString(3));
                        posts.add(post);
                    }
                }
                if (dataType.equals(COMMENTS)){
                    while (cursor.moveToNext()) {
                        Comment comment= new Comment();
                        comment.setPostId(cursor.getInt(0));
                        comment.setId(cursor.getInt(1));
                        comment.setName(cursor.getString(2));
                        comment.setEmail(cursor.getString(3));
                        comment.setBody(cursor.getString(4));
                        comments.add(comment);
                    }
                }
                cursor.close();
            }
            displayData(dataType);
        };
        Thread thread = new Thread(getAlbums);
        thread.start();

    }


}
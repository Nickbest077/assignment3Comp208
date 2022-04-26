package comp208.best.assignment3;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import comp208.best.assignment3.model.Album;
import comp208.best.assignment3.model.Comment;
import comp208.best.assignment3.model.Post;

public class TestContentProvider extends ContentProvider {
    MatrixCursor mc;
    MatrixCursor.RowBuilder rb;

    public static final UriMatcher MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    public TestContentProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.
        MATCHER.addURI(Contract.AUTHORITY, "albums", 1);
        MATCHER.addURI(Contract.AUTHORITY, "posts", 2);
        MATCHER.addURI(Contract.AUTHORITY, "comments", 3);

        return false;
    }

    String TAG = "--++";


    /**
     * query function that sends get request to the api
     * that returns a json list of album objects
     * Builds the returned string into an array which is then used
     * to populate the cursor which is then returned
     *
     * @param uri
     * @param projection
     * @param selection
     * @param selectionArgs
     * @param sortOrder
     * @return
     */
    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        Log.i(TAG, "query: " + "inside content provider");
        /**
         * This method typically runs a database query, or make a request to a remote API
         */

        String dataType = null;
        switch (MATCHER.match(uri))
        {
            case UriMatcher.NO_MATCH:
                dataType = "none";
                break;
            case 1:
                mc = new MatrixCursor(Contract.columnNamesAlbums);
                dataType = "albums";
                break;
            case 2:
                mc = new MatrixCursor(Contract.columnNamesPosts);
                dataType = "posts";
                break;
            case 3:
                mc = new MatrixCursor(Contract.columnNamesComments);
                dataType = "comments";
                break;
        }

            Log.i(TAG, "getAlbums: started to make get request");
            try {
                URL url = new URL("https://jsonplaceholder.typicode.com/"+ dataType);
                HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
                Log.i(TAG, "getData: connection made");

                InputStream inputStream = connection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder builder = new StringBuilder();
                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    builder.append(line);
                }
                String json = builder.toString();
                //                Log.i(TAG, json);

                Gson gson = new Gson();
                if (dataType.equals("albums")) {
                    Album[] albums = gson.fromJson(json, Album[].class);
                    for (Album album : albums) {
                        rb = mc.newRow();
                        rb.add("userId", album.getUserId());
                        rb.add("id", album.getId());
                        rb.add("title", album.getTitle());
                        Log.i(TAG, album.toString());
                    }
                }
                if(dataType.equals( "posts")){
                    Post [] posts = gson.fromJson(json, Post[].class);
                    for (Post post : posts) {
                        rb = mc.newRow();
                        rb.add("userId", post.getUserId());
                        rb.add("id", post.getId());
                        rb.add("title", post.getTitle());
                        rb.add("body", post.getBody());
                        Log.i(TAG, post.toString());
                    }
                }
                if(dataType .equals("comments")) {
                    Comment[] comments = gson.fromJson(json, Comment[].class);
                    for (Comment comment : comments) {
                        rb = mc.newRow();
                        rb.add("userId", comment.getPostId());
                        rb.add("id", comment.getId());
                        rb.add("name", comment.getName());
                        rb.add("email", comment.getEmail());
                        rb.add("body", comment.getBody());
                        Log.i(TAG, comment.toString());
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
        };
        return mc;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    // Contract class to hold all relevant data
    public static class Contract {
        public static final String AUTHORITY = "test.provider.new";
        public static String[] columnNamesAlbums = {"userId", "id", "title"};
        public static String[] columnNamesPosts = {"userId", "id", "title", "body"};
        public static String[] columnNamesComments = {"postId", "id", "name", "email", "body"};
    }
}
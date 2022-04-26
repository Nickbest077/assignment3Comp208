package comp208.best.assignment3.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import comp208.best.assignment3.R;
import comp208.best.assignment3.model.Album;
import comp208.best.assignment3.model.Post;

public class PostAdapter extends BaseAdapter {
    Context context;
    List<Post> textArray;
    LayoutInflater inflater;

    public PostAdapter(Context context, ArrayList<Post> textarray) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.textArray = textarray;

    }

    @Override
    public int getCount() {
        return textArray.size();
    }

    @Override
    public Object getItem(int i) {
        return textArray.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    /**
     * Loads text views from custom layout with values from album objects
     */
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewGroup va;

        if (view != null) {
            va = (ViewGroup) view;
        } else {
            va = (ViewGroup) inflater.inflate(R.layout.post_layout, null);
        }

        Post post = textArray.get(i);

        TextView userView = va.findViewById(R.id.userId);
        TextView idView = va.findViewById(R.id.id);
        TextView titleView = va.findViewById(R.id.title);
        TextView bodyView = va.findViewById(R.id.body);

        userView.setText(post.getUserId().toString());
        idView.setText(post.getId().toString());
        titleView.setText(post.getTitle());
        bodyView.setText(post.getBody());


        return va;
    }
}

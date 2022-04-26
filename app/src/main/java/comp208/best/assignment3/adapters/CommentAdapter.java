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
import comp208.best.assignment3.model.Comment;

public class CommentAdapter extends BaseAdapter {
    Context context;
    List<Comment> textArray;
    LayoutInflater inflater;

    public CommentAdapter(Context context, ArrayList<Comment> textarray) {
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
            va = (ViewGroup) inflater.inflate(R.layout.comment_layout, null);
        }

        Comment comment = textArray.get(i);

        TextView userView = va.findViewById(R.id.postId);
        TextView idView = va.findViewById(R.id.id);
        TextView nameView = va.findViewById(R.id.name);
        TextView emailView = va.findViewById(R.id.email);
        TextView bodyView = va.findViewById(R.id.body);

        userView.setText(comment.getPostId().toString());
        idView.setText(comment.getId().toString());
        nameView.setText(comment.getName());
        emailView.setText(comment.getEmail());
        bodyView.setText(comment.getBody());


        return va;
    }
}

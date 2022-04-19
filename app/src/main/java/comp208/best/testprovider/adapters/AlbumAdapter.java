package comp208.best.testprovider.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import comp208.best.testprovider.R;
import comp208.best.testprovider.model.Album;

public class AlbumAdapter extends BaseAdapter {

    Context context;
    List<Album> textArray;
    LayoutInflater inflater;

    public AlbumAdapter(Context context, ArrayList<Album> textarray) {
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

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewGroup va;

        if (view != null) {
            va = (ViewGroup) view;
        } else {
            va = (ViewGroup) inflater.inflate(R.layout.album_layout, null);
        }

        Album album = textArray.get(i);

        TextView userView = va.findViewById(R.id.userId);
        TextView idView = va.findViewById(R.id.id);
        TextView titleView = va.findViewById(R.id.title);

        userView.setText(album.getUserId().toString());
        idView.setText(album.getId().toString());
        titleView.setText(album.getTitle());


        return va;
    }
}

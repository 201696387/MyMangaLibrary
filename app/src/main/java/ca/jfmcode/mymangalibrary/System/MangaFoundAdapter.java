package ca.jfmcode.mymangalibrary.System;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import ca.jfmcode.mymangalibrary.R;
import ca.jfmcode.mymangalibrary.Tools.ImageLoadTask;

/**
 * Created by ONi on 11/11/2017.
 */

public class MangaFoundAdapter extends ArrayAdapter<Manga> {

    private ArrayList<Manga> data;
    private Context context;

    private static class ViewHolder{
        ImageView mangaImage;
        TextView mangaTitle;
    }

    public MangaFoundAdapter(@NonNull Context context, int resource, @NonNull List<Manga> objects) {
        super(context, resource, objects);

        this.context = context;
        this.data = (ArrayList<Manga>) objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;

        Manga manga = getItem(position);

        if(convertView == null){
            viewHolder = new ViewHolder();

            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.cell_search_list, parent, false);

            viewHolder.mangaImage = (ImageView)convertView.findViewById(R.id.cell_search_imageIV);
            viewHolder.mangaTitle = (TextView)convertView.findViewById(R.id.cell_search_titleTV);

            convertView.setTag(viewHolder);
        } else{
            viewHolder = (ViewHolder)convertView.getTag();
        }

        new ImageLoadTask(manga.getImage(), viewHolder.mangaImage).execute();
        viewHolder.mangaTitle.setText(manga.getTitle());

        return convertView;
    }

    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            return null;
        }
    }
}

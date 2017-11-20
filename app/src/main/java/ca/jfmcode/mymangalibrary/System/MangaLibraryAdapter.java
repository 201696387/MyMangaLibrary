package ca.jfmcode.mymangalibrary.System;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ca.jfmcode.mymangalibrary.R;
import ca.jfmcode.mymangalibrary.Tools.ImageLoadTask;

/**
 * Created by ONi on 19/11/2017.
 */

public class MangaLibraryAdapter extends ArrayAdapter<Manga> {

    private Context context;

    private static class ViewHolder{
        TextView titleTV, chaptersTV;
        ImageView imageIV, starIV;
    }

    public MangaLibraryAdapter(@NonNull Context context, int resource, @NonNull List<Manga> objects) {
        super(context, resource, objects);

        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;

        Manga manga = getItem(position);

        if(convertView == null) {
            viewHolder = new ViewHolder();

            LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(R.layout.cell_manga_list, parent, false);

            viewHolder.imageIV = (ImageView)convertView.findViewById(R.id.cell_manga_imageIV);
            viewHolder.starIV = (ImageView)convertView.findViewById(R.id.cell_manga_newIV);

            viewHolder.titleTV = (TextView)convertView.findViewById(R.id.cell_manga_titleTV);
            viewHolder.chaptersTV = (TextView)convertView.findViewById(R.id.cell_manga_chapterTV);

            convertView.setTag(viewHolder);
        } else{
            viewHolder = (ViewHolder)convertView.getTag();
        }

        viewHolder.titleTV.setText(manga.getTitle());
        viewHolder.chaptersTV.setText(""+manga.getChapters());

        if(manga.isUnread()){
            viewHolder.starIV.setVisibility(View.VISIBLE);
        }else{
            viewHolder.starIV.setVisibility(View.GONE);
        }
        new ImageLoadTask(manga.getImage(), viewHolder.imageIV).execute();

        return convertView;
    }
}

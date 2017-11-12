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
 * Created by ONi on 12/11/2017.
 */

public class MangaSelectedAdapter extends ArrayAdapter<Manga> {

    private Context context;

    private static class ViewHolder{
        TextView titleTV, englishTV, chaptersTV, scoreTV, statusTV, synopsisTV;
        ImageView imageIV;
    }

    public MangaSelectedAdapter(Context context, List<Manga> objects){
        this(context, R.layout.cell_search_selected, objects);

        this.context = context;
    }

    public MangaSelectedAdapter(@NonNull Context context, int resource, @NonNull List<Manga> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;

        Manga manga = getItem(position);

        if(convertView == null) {
            viewHolder = new ViewHolder();

            LayoutInflater layoutInflater = LayoutInflater.from(context);

            convertView = layoutInflater.inflate(R.layout.cell_search_selected, parent, false);

            viewHolder.titleTV = (TextView) convertView.findViewById(R.id.cell_selected_titleTV);
            viewHolder.englishTV = (TextView) convertView.findViewById(R.id.cell_selected_englishTV);
            viewHolder.chaptersTV = (TextView) convertView.findViewById(R.id.cell_selected_chapterTV);
            viewHolder.scoreTV = (TextView) convertView.findViewById(R.id.cell_selected_scoreTV);
            viewHolder.statusTV = (TextView) convertView.findViewById(R.id.cell_selected_statusTV);
            viewHolder.synopsisTV = (TextView) convertView.findViewById(R.id.cell_selected_synopsisTV);
            viewHolder.imageIV = (ImageView) convertView.findViewById(R.id.cell_selected_imageIV);

            convertView.setTag(viewHolder);
        } else{
            viewHolder = (ViewHolder)convertView.getTag();
        }

        viewHolder.titleTV.setText(manga.getTitle());
        viewHolder.englishTV.setText(manga.getEnglish());
        viewHolder.chaptersTV.setText("Chapter : ch. "+manga.getChapters());
        viewHolder.scoreTV.setText("Score : "+manga.getScore());
        viewHolder.statusTV.setText(manga.getStatus());
        viewHolder.synopsisTV.setText("Synopsis : "+manga.getSynopsis());
        new ImageLoadTask(manga.getImage(), viewHolder.imageIV).execute();

        return convertView;
    }
}

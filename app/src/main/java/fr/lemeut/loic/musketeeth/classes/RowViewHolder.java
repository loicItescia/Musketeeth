package fr.lemeut.loic.musketeeth.classes;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import android.widget.ImageView;
import android.widget.TextView;

import fr.lemeut.loic.musketeeth.R;

public class RowViewHolder extends RecyclerView.ViewHolder {

    TextView dateView;
    TextView textView;
    ImageView icon;

    public RowViewHolder(View view) {
        super(view);
        this.textView = (TextView) view.findViewById(R.id.title);
        this.dateView = (TextView) view.findViewById(R.id.date);
        this.icon = (ImageView) view.findViewById(R.id.star);
    }
}

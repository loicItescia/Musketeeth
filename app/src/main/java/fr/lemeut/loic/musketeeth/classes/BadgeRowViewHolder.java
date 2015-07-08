package fr.lemeut.loic.musketeeth.classes;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import fr.lemeut.loic.musketeeth.R;

public class BadgeRowViewHolder extends RecyclerView.ViewHolder {

    TextView nom;
    TextView score;
    ImageView icon;

    public BadgeRowViewHolder(View view) {
        super(view);
        this.nom = (TextView) view.findViewById(R.id.BadgeName);
        this.score = (TextView) view.findViewById(R.id.ScoreMax);
        this.icon = (ImageView) view.findViewById(R.id.star);
    }
}

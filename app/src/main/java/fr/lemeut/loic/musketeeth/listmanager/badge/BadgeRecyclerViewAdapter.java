package fr.lemeut.loic.musketeeth.listmanager.badge;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import fr.lemeut.loic.musketeeth.R;

public class BadgeRecyclerViewAdapter extends RecyclerView.Adapter<BadgeRowViewHolder> {
    Context context;
    ArrayList<BadgeRowItem> itemsList;

    public BadgeRecyclerViewAdapter(Context context, ArrayList<BadgeRowItem> itemsList) {
        this.context = context;
        this.itemsList = itemsList;
    }

    @Override
    public int getItemCount() {
        if (itemsList == null) {
            return 0;
        } else {
            return itemsList.size();
        }
    }

    @Override
    public BadgeRowViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.badge_row, null);
        BadgeRowViewHolder viewHolder = new BadgeRowViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(BadgeRowViewHolder holder, int position) {
        BadgeRowItem items = itemsList.get(position);
        holder.nom.setText(String.valueOf(items.getBadges_BadgeName()));
        holder.score.setText(String.valueOf(items.getBadges_ScoreMax()));

        Long idBadge=Long.valueOf(items.getId());
        Integer idBadgeInt = Integer.parseInt(idBadge.toString());
        int imageBadge=-2;
        switch (idBadgeInt) {
            case -1:
                imageBadge =  R.mipmap.ico_princ;
                break;
            case 0:
                imageBadge =  R.mipmap.ico_badge_1;
                break;
            case 1:
                imageBadge =  R.mipmap.ico_badge_1;
                break;
            case 2:
                imageBadge =  R.mipmap.ico_badge_2;
                break;
            case 3:
                imageBadge =  R.mipmap.ico_badge_3;
                break;
            case 4:
                imageBadge =  R.mipmap.ico_badge_4;
                break;
            case 5:
                imageBadge =  R.mipmap.ico_badge_5;
                break;
            case 6:
                imageBadge =  R.mipmap.ico_badge_6;
                break;
            case 7:
                imageBadge =  R.mipmap.ico_badge_7;
                break;
        }

        holder.icon.setImageResource(imageBadge);

        if(items.getBadges_HasBadge()!=1){
            holder.itemView.setBackgroundColor(holder.itemView.getResources().getColor(R.color.ColorDisabled));
        }

    }

}
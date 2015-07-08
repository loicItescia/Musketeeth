package fr.lemeut.loic.musketeeth.classes;
import java.util.ArrayList;



import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;

import fr.lemeut.loic.musketeeth.R;

public class ScoreRecyclerViewAdapter extends RecyclerView.Adapter<ScoreRowViewHolder> {
    Context context;
    ArrayList<ScoreRowItem> itemsList;

    public ScoreRecyclerViewAdapter(Context context, ArrayList<ScoreRowItem> itemsList) {
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
    public ScoreRowViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.score_row, null);
        ScoreRowViewHolder viewHolder = new ScoreRowViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ScoreRowViewHolder scoreRowViewHolder, int position) {
        ScoreRowItem items = itemsList.get(position);
        scoreRowViewHolder.textView.setText(String.valueOf(items.getTitle()));
        scoreRowViewHolder.dateView.setText(String.valueOf(items.getDate()));
        scoreRowViewHolder.icon.setImageDrawable(new IconicsDrawable(context, GoogleMaterial.Icon.gmd_star).sizeDp(30));
    }
}
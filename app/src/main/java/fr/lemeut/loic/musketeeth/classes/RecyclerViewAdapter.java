package fr.lemeut.loic.musketeeth.classes;
import java.util.ArrayList;



import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;

import fr.lemeut.loic.musketeeth.R;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RowViewHolder> {
    Context context;
    ArrayList<ScoreRowItem> itemsList;

    public RecyclerViewAdapter(Context context, ArrayList<ScoreRowItem> itemsList) {
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
    public RowViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.score_row, null);
        RowViewHolder viewHolder = new RowViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RowViewHolder rowViewHolder, int position) {
        ScoreRowItem items = itemsList.get(position);
        rowViewHolder.textView.setText(String.valueOf(items.getTitle()));
        rowViewHolder.dateView.setText(String.valueOf(items.getDate()));
        rowViewHolder.icon.setImageDrawable(new IconicsDrawable(context, GoogleMaterial.Icon.gmd_star).sizeDp(30));
    }
}
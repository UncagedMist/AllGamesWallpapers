package tbc.uncagedmist.mobilewallpapers.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.flaviofaria.kenburnsview.KenBurnsView;

import tbc.uncagedmist.mobilewallpapers.Interface.ItemClickListener;
import tbc.uncagedmist.mobilewallpapers.R;

public class WallpaperViewHolder extends
        RecyclerView.ViewHolder implements View.OnClickListener {

    public KenBurnsView background_image;
    public ProgressBar progressBar;

    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public WallpaperViewHolder(View itemView) {
        super(itemView);

        background_image = itemView.findViewById(R.id.imageView);
        progressBar = itemView.findViewById(R.id.progress_bar);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v,getAdapterPosition());
    }
}
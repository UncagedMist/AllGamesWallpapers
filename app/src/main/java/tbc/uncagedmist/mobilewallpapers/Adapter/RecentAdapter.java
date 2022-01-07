package tbc.uncagedmist.mobilewallpapers.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import tbc.uncagedmist.mobilewallpapers.Ads.GoogleAds;
import tbc.uncagedmist.mobilewallpapers.Common.Common;
import tbc.uncagedmist.mobilewallpapers.Common.MyApplicationClass;
import tbc.uncagedmist.mobilewallpapers.Fragments.ViewWallpaperFragment;
import tbc.uncagedmist.mobilewallpapers.Model.WallpaperItem;
import tbc.uncagedmist.mobilewallpapers.RecentDB.Recent;
import tbc.uncagedmist.mobilewallpapers.R;
import tbc.uncagedmist.mobilewallpapers.ViewHolder.ListWallpaperViewHolder;

public class RecentAdapter extends RecyclerView.Adapter<ListWallpaperViewHolder> {

    private Context context;
    private List<Recent> recent;

    public RecentAdapter(Context context, List<Recent> recent) {
        this.context = context;
        this.recent = recent;
    }

    @NonNull
    @Override
    public ListWallpaperViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_wallpaper_item,parent,false);

        int height = parent.getMeasuredHeight() / 2;
        itemView.setMinimumHeight(height);

        if (MyApplicationClass.getInstance().isShowAds())   {
            GoogleAds.loadGoogleFullscreen(context);
        }

        return new ListWallpaperViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ListWallpaperViewHolder holder, final int position) {

        holder.progressBar.setVisibility(View.VISIBLE);
        Picasso.get()
                .load(recent.get(position).getImageLink())
                .into(holder.wallpaper, new Callback() {
                    @Override
                    public void onSuccess() {
                        holder.progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Exception e) {
                        Toast.makeText(context, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        holder.setItemClickListener((view, position1) -> {

            if (GoogleAds.mInterstitialAd != null)  {
                GoogleAds.mInterstitialAd.show((Activity) context);
            }
            else {
                ViewWallpaperFragment viewWallpaperFragment = new ViewWallpaperFragment();
                FragmentTransaction transaction = ((AppCompatActivity)context)
                        .getSupportFragmentManager().beginTransaction();

                WallpaperItem wallpapers = new WallpaperItem();
                wallpapers.setImageUrl(recent.get(position1).getImageLink());
                Common.selected_background = wallpapers;
                Common.selected_background_key = recent.get(position1).getKey();

                transaction.replace(R.id.main_frame,viewWallpaperFragment).commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return recent.size();
    }
}
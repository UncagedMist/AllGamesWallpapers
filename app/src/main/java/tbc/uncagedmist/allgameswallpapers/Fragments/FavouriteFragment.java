package tbc.uncagedmist.allgameswallpapers.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import tbc.uncagedmist.allgameswallpapers.Adapter.MyFavAdapter;
import tbc.uncagedmist.allgameswallpapers.Common.Common;
import tbc.uncagedmist.allgameswallpapers.FavDB.DataSource.FavouriteRepository;
import tbc.uncagedmist.allgameswallpapers.FavDB.Favourites;
import tbc.uncagedmist.allgameswallpapers.FavDB.LocalDB.FavouritesDataSource;
import tbc.uncagedmist.allgameswallpapers.FavDB.LocalDB.LocalDatabase;
import tbc.uncagedmist.allgameswallpapers.R;

@SuppressLint("ValidFragment")
public class FavouriteFragment extends Fragment {

    AdView aboveBanner, bottomBanner;

    private static FavouriteFragment INSTANCE = null;

    RecyclerView recyclerView;

    List<Favourites> favouritesList;
    MyFavAdapter adapter;

    Context context;

    CompositeDisposable compositeDisposable;
    FavouriteRepository favouriteRepository;

    @SuppressLint("ValidFragment")
    public FavouriteFragment(Context context) {
        this.context = context;

        compositeDisposable = new CompositeDisposable();
        LocalDatabase database = LocalDatabase.getInstance(context);
        favouriteRepository = FavouriteRepository.getInstance(FavouritesDataSource.getInstance(database.favouritesDAO()));
    }

    public FavouriteFragment() {
    }

    public static FavouriteFragment getInstance(Context context)    {

        if (INSTANCE == null)   {
            INSTANCE = new FavouriteFragment(context);
        }
        return INSTANCE;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favourite, container, false);

        recyclerView = view.findViewById(R.id.recycler_favs);

        aboveBanner = view.findViewById(R.id.aboveBanner);
        bottomBanner = view.findViewById(R.id.bottomBanner);

        AdRequest adRequest = new AdRequest.Builder().build();

        aboveBanner.loadAd(adRequest);
        bottomBanner.loadAd(adRequest);

        recyclerView.setHasFixedSize(true);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),2);
        recyclerView.setLayoutManager(gridLayoutManager);

        favouritesList = new ArrayList<>();

        adapter = new MyFavAdapter(context,favouritesList);
        recyclerView.setAdapter(adapter);

        if (Common.isConnectedToInternet(getContext()))
            loadFav();
        else
            Toast.makeText(getContext(), "Please Connect to Internet...", Toast.LENGTH_SHORT).show();

        adMethod();

        return view;
    }

    private void adMethod() {
        aboveBanner.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
            }

            @Override
            public void onAdFailedToLoad(LoadAdError adError) {
                // Code to be executed when an ad request fails.
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }

            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
            }
        });

        bottomBanner.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
            }

            @Override
            public void onAdFailedToLoad(LoadAdError adError) {
                // Code to be executed when an ad request fails.
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }

            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
            }
        });
    }

    private void loadFav() {
        Disposable disposable = favouriteRepository.getAllFavourites()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(favourites ->
                        onGetAllFavSuccess(favourites),
                        throwable ->
                                Log.d("ERROR", throwable.getMessage()));
        compositeDisposable.add(disposable);
    }

    private void onGetAllFavSuccess(List<Favourites> favourites) {
        favouritesList.clear();
        favouritesList.addAll(favourites);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}
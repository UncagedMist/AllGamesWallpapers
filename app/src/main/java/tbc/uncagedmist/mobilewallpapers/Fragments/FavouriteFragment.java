package tbc.uncagedmist.mobilewallpapers.Fragments;

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

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import tbc.uncagedmist.mobilewallpapers.Adapter.FavouriteAdapter;
import tbc.uncagedmist.mobilewallpapers.Common.Common;
import tbc.uncagedmist.mobilewallpapers.FavDB.DataSource.FavouriteRepository;
import tbc.uncagedmist.mobilewallpapers.FavDB.Favourites;
import tbc.uncagedmist.mobilewallpapers.FavDB.LocalDB.FavouriteDatabase;
import tbc.uncagedmist.mobilewallpapers.FavDB.LocalDB.FavouritesDataSource;
import tbc.uncagedmist.mobilewallpapers.R;

@SuppressLint("ValidFragment")
public class FavouriteFragment extends Fragment {

    private static FavouriteFragment INSTANCE = null;

    RecyclerView recyclerView;

    List<Favourites> favouritesList;
    FavouriteAdapter adapter;

    Context context;

    CompositeDisposable compositeDisposable;
    FavouriteRepository favouriteRepository;

    @SuppressLint("ValidFragment")
    public FavouriteFragment(Context context) {
        this.context = context;

        compositeDisposable = new CompositeDisposable();
        FavouriteDatabase database = FavouriteDatabase.getInstance(context);
        favouriteRepository = FavouriteRepository.getInstance(FavouritesDataSource.getInstance(database.favouritesDAO()));
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

        recyclerView.setHasFixedSize(true);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),2);
        recyclerView.setLayoutManager(gridLayoutManager);

        favouritesList = new ArrayList<>();

        adapter = new FavouriteAdapter(context,favouritesList);
        recyclerView.setAdapter(adapter);

        if (Common.isConnectedToInternet(getContext()))
            loadFav();
        else
            Toast.makeText(getContext(), "Please Connect to Internet...", Toast.LENGTH_SHORT).show();

        return view;
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
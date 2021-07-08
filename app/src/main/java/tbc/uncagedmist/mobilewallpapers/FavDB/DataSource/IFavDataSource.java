package tbc.uncagedmist.mobilewallpapers.FavDB.DataSource;

import java.util.List;

import io.reactivex.Flowable;
import tbc.uncagedmist.mobilewallpapers.FavDB.Favourites;


public interface IFavDataSource {
    Flowable<List<Favourites>> getAllFavourites();
    void insertFav(Favourites...favourites);
    void updateFav(Favourites...favourites);
    void deleteFav(Favourites...favourites);
    void deleteAllFav();
}

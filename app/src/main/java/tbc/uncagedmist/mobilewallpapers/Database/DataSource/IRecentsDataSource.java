package tbc.uncagedmist.mobilewallpapers.Database.DataSource;

import java.util.List;

import io.reactivex.Flowable;
import tbc.uncagedmist.mobilewallpapers.Database.Recents;

public interface IRecentsDataSource {

    Flowable<List<Recents>> getAllRecents();
    void insertRecents(Recents...recents);
    void updateRecents(Recents...recents);
    void deleteRecents(Recents...recents);
    void deleteAllRecents();
}

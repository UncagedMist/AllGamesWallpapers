package tbc.uncagedmist.allgameswallpapers.Database.DataSource;

import java.util.List;

import io.reactivex.Flowable;
import tbc.uncagedmist.allgameswallpapers.Database.Recents;

public interface IRecentsDataSource {

    Flowable<List<Recents>> getAllRecents();
    void insertRecents(Recents...recents);
    void updateRecents(Recents...recents);
    void deleteRecents(Recents...recents);
    void deleteAllRecents();
}

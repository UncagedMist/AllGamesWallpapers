package tbc.uncagedmist.mobilewallpapers.RecentDB.LocalDatabase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import tbc.uncagedmist.mobilewallpapers.RecentDB.Recent;

@Database(entities = Recent.class,version = RecentDatabase.DATABASE_VERSION)
public abstract class RecentDatabase extends RoomDatabase {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "RE_RECENT";

    public abstract RecentDAO recentDAO();

    public static RecentDatabase instance;

    public static RecentDatabase getInstance(Context context)    {

        if (instance == null)   {
            instance = Room.databaseBuilder(context,RecentDatabase.class,DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}

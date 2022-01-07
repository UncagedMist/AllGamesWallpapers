package tbc.uncagedmist.mobilewallpapers.Common;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import tbc.uncagedmist.mobilewallpapers.Model.WallpaperItem;

public class Common {
    public static final String FB_DB_NAME = "RE_Wallpapers";

    public static String CURRENT_WALLPAPER_ID;
    public static String CATEGORY_ID_SELECTED;

    public static String selected_background_key;
    public static WallpaperItem selected_background = new WallpaperItem();

    public static boolean IS_FAV = false;

    public static boolean isConnectedToInternet(Context context)    {

        ConnectivityManager connectivityManager = (
                ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager != null)    {

            NetworkInfo[] info = connectivityManager.getAllNetworkInfo();

            if (info != null)   {

                for (int i = 0; i <info.length;i++)   {

                    if (info[i].getState() == NetworkInfo.State.CONNECTED)  {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
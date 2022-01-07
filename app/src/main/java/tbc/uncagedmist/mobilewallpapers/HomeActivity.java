package tbc.uncagedmist.mobilewallpapers;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.play.core.review.ReviewInfo;
import com.google.android.play.core.review.ReviewManager;
import com.google.android.play.core.review.ReviewManagerFactory;
import com.google.android.play.core.tasks.Task;
import com.google.firebase.database.annotations.NotNull;
import com.shashank.sony.fancydialoglib.Animation;
import com.shashank.sony.fancydialoglib.FancyAlertDialog;
import com.shashank.sony.fancydialoglib.Icon;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import tbc.uncagedmist.mobilewallpapers.Common.MyApplicationClass;
import tbc.uncagedmist.mobilewallpapers.Fragments.CategoryFragment;
import tbc.uncagedmist.mobilewallpapers.Fragments.FavouriteFragment;
import tbc.uncagedmist.mobilewallpapers.Fragments.PopularFragment;
import tbc.uncagedmist.mobilewallpapers.Fragments.RecentFragment;
import tbc.uncagedmist.mobilewallpapers.Utility.CurvedBottomNavigationView;

public class HomeActivity extends AppCompatActivity {

    ReviewManager manager;
    ReviewInfo reviewInfo;

    private static final int PERMISSION_REQUEST_CODE = 31;

    CurvedBottomNavigationView curvedBottomNavigationView;

    FloatingActionButton fab;

    private FrameLayout adContainerView;
    private AdView adView;


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @NotNull String[] permissions,
                                           @NonNull @NotNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode)
        {
            case PERMISSION_REQUEST_CODE:
            {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)    {
                    Toast.makeText(this, "PERMISSION GRANTED..", Toast.LENGTH_SHORT).show();
                }
                else    {
                    Toast.makeText(this, "PERMISSION DENIED...", Toast.LENGTH_SHORT).show();
                }
            }
            break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            Window window = getWindow();
            window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        setContentView(R.layout.activity_home);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        manager = ReviewManagerFactory.create(HomeActivity.this);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED)   {
            requestPermissions(new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, PERMISSION_REQUEST_CODE);
        }

        curvedBottomNavigationView = findViewById(R.id.customBottomBar);
        fab = findViewById(R.id.fab);

        adContainerView = findViewById(R.id.ad_container);
        // Step 1 - Create an AdView and set the ad unit ID on it.
        adView = new AdView(this);
        adView.setAdUnitId(getString(R.string.BANNER_ID));
        adContainerView.addView(adView);

        if (MyApplicationClass.getInstance().isShowAds())   {
            loadBanner();
        }

        curvedBottomNavigationView.setSelectedItemId(R.id.action_category);

        curvedBottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
            Fragment fragment;

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if (item.getItemId() == R.id.action_category) {
                    getSupportActionBar().setTitle(R.string.app_name);
                    fragment = new CategoryFragment();
                    fab.setImageResource(R.drawable.ic_baseline_games_24);
                }
                else if (item.getItemId() == R.id.action_trending) {
                    getSupportActionBar().setTitle("Trending");
                    fragment = new PopularFragment(getApplicationContext());
                    fab.setImageResource(R.drawable.ic_baseline_stream_24);
                }
                else if (item.getItemId() == R.id.action_recent) {
                    getSupportActionBar().setTitle("Image History");
                    fragment = new RecentFragment(getApplicationContext());
                    fab.setImageResource(R.drawable.ic_baseline_history_edu_24);
                }
                else if (item.getItemId() == R.id.action_favourite) {
                    getSupportActionBar().setTitle("Favourites");
                    fragment = new FavouriteFragment(getApplicationContext());
                    fab.setImageResource(R.drawable.ic_baseline_favorite_border_24);
                }
                return loadFragment(fragment);
            }
        });

        loadFragment(CategoryFragment.getInstance());
        fab.setImageResource(R.drawable.ic_baseline_games_24);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED)   {
            requestPermissions(new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onBackPressed() {
        new FancyAlertDialog.Builder(HomeActivity.this)
                .setTitle("Resident Evil Village Wallpaper")
                .setBackgroundColor(Color.parseColor("#303F9F"))  //Don't pass R.color.colorvalue
                .setMessage("Support us by downloading our other apps!")
                .setNegativeBtnText("Don't")
                .setPositiveBtnBackground(Color.parseColor("#FF4081"))  //Don't pass R.color.colorvalue
                .setPositiveBtnText("Support")
                .setNegativeBtnBackground(Color.parseColor("#FFA9A7A8"))  //Don't pass R.color.colorvalue
                .setAnimation(Animation.POP)
                .isCancellable(true)
                .setIcon(R.drawable.ic_star_border_black_24dp, Icon.Visible)
                .OnPositiveClicked(() ->
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=tbc.uncagedmist.paidgameswallpapers"))))
                .OnNegativeClicked(() -> {
                })
                .build();
    }




    private void shareApp() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        String message = "Amazing Wallpaper in your way. Install Game Wallpaper App and Make your Display look Colourful! \n https://play.google.com/store/apps/details?id=tbc.uncagedmist.allgameswallpapers";
        intent.putExtra(Intent.EXTRA_TEXT, message);
        startActivity(Intent.createChooser(intent, "Share Game Wallpapers App Using"));
    }

    private void exit() {
        new FancyAlertDialog.Builder(HomeActivity.this)
                .setTitle("Good-Bye")
                .setBackgroundColor(Color.parseColor("#303F9F"))  //Don't pass R.color.colorvalue
                .setMessage("Do You Want to Step Out?")
                .setNegativeBtnText("Exit")
                .setPositiveBtnBackground(Color.parseColor("#FF4081"))  //Don't pass R.color.colorvalue
                .setPositiveBtnText("Rate US")
                .setNegativeBtnBackground(Color.parseColor("#FFA9A7A8"))  //Don't pass R.color.colorvalue
                .setAnimation(Animation.POP)
                .isCancellable(true)
                .setIcon(R.drawable.ic_star_border_black_24dp, Icon.Visible)
                .OnPositiveClicked(() ->
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=tbc.uncagedmist.allgameswallpapers"))))
                .OnNegativeClicked(() -> {
                    moveTaskToBack(true);
                    android.os.Process.killProcess(android.os.Process.myPid());
                    System.exit(1);
                })
                .build();
    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_frame, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    private void loadBanner() {

        AdRequest adRequest =
                new AdRequest.Builder()
                        .build();

        AdSize adSize = getAdSize();
        // Step 4 - Set the adaptive ad size on the ad view.
        adView.setAdSize(adSize);


        // Step 5 - Start loading the ad in the background.
        adView.loadAd(adRequest);
    }

    private AdSize getAdSize() {
        // Step 2 - Determine the screen width (less decorations) to use for the ad width.
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);

        float widthPixels = outMetrics.widthPixels;
        float density = outMetrics.density;

        int adWidth = (int) (widthPixels / density);

        // Step 3 - Get adaptive ad size and return for setting on the ad view.
        return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(this, adWidth);
    }
}
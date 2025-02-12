package io.vn.nguyenduck.blocktopograph.activity;

import static io.vn.nguyenduck.blocktopograph.Constants.MINECRAFT_APP_ID;
import static io.vn.nguyenduck.blocktopograph.utils.Utils.buildAndroidDataDir;
import static io.vn.nguyenduck.blocktopograph.utils.Utils.isAndroid11Up;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import io.vn.nguyenduck.blocktopograph.R;
import io.vn.nguyenduck.blocktopograph.file.BFile;

@SuppressLint("SdCardPath")
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation_view);

        Fragment contentView = getSupportFragmentManager().findFragmentById(R.id.content_view);
        assert contentView != null;
        NavController navController = NavHostFragment.findNavController(contentView);

        NavigationUI.setupWithNavController(bottomNavigationView, navController);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isAndroid11Up()) {
            BFile f = new BFile(buildAndroidDataDir(MINECRAFT_APP_ID) + "/games");
            f.copyTo(new BFile("/sdcard"));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isAndroid11Up()) {
            BFile f = new BFile("/sdcard/games");
            f.copyTo(new BFile(buildAndroidDataDir(MINECRAFT_APP_ID)));
        }
    }
}
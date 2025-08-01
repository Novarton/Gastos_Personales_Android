package com.example.gastospersonales.utils;

import android.app.Activity;
import android.content.Intent;
import android.view.MenuItem;

import com.example.gastospersonales.R;
import com.example.gastospersonales.activities.HomeActivity;
import com.example.gastospersonales.activities.ProfileActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class NavigationHelper {

    public static void setupBottomNavigation(BottomNavigationView bottomNavigationView, Activity currentActivity) {
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_home) {
                if (!(currentActivity instanceof HomeActivity)) {
                    currentActivity.startActivity(new Intent(currentActivity, HomeActivity.class));
                    currentActivity.overridePendingTransition(0, 0);
                    currentActivity.finish();
                }
                return true;
            } else if (itemId == R.id.nav_profile) {
                if (!(currentActivity instanceof ProfileActivity)) {
                    currentActivity.startActivity(new Intent(currentActivity, ProfileActivity.class));
                    currentActivity.overridePendingTransition(0, 0);
                    currentActivity.finish();
                }
                return true;
            }
            return false;
        });
    }
}

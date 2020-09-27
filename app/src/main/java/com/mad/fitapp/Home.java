package com.mad.fitapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Home extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private BottomNavigationView bottomNavigationView;
    private FrameLayout frameLayout;

    // fragments
    private Dashboard dashboard;
    private Exercise exercise;
    private Meal meal;
    private Disease disease;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        frameLayout = findViewById(R.id.main_frame);

        dashboard = new Dashboard();
        exercise = new Exercise();
        meal = new Meal();
        disease = new Disease();

        setFragment(dashboard);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        setFragment(dashboard);
                        return true;
                    case R.id.nav_exercises:
                        setFragment(exercise);
                        return true;
                    case R.id.nav_meals:
                        setFragment(meal);
                        return true;
                    case R.id.nav_disease:
                        setFragment(disease);
                        return true;
                    default:
                        return false;
                }
            }
        });
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_frame,fragment);
        transaction.commit();
    }
}
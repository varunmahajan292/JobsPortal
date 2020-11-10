package com.example.infinityjobportal;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    FirebaseAuth mAuth;

    AppBarConfiguration mAppBarConfiguration;
    DrawerLayout drawer;
    Toolbar toolbar;
    NavigationView navigationView;
    NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: has started.");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //set up toolbar as we are using noActionBarTheme
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mAuth = FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser() == null) {
            //User NOT logged In
            this.finish();
            startActivity(new Intent(getApplicationContext(), ClientLogin.class));
        }


        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);


        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.homeFragment, R.id.myJobsFragment, R.id.postJobFragment, R.id.postedJobsFragment, R.id.aboutUsFragment, R.id.myProfileFragment, R.id.queriesFragment, R.id.myMapFragment)
                .setDrawerLayout(drawer)
                .build();


        //By calling this method, the title in the Toolbar will automatically be updated when the destination changes (assuming there is a valid label).
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        Log.d(TAG, "onCreate: setupActionBarwithNavController is executed");
        NavigationUI.setupWithNavController(navigationView, navController);
        Log.d(TAG, "onCreate: setupWithNavController is executed");

        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
                int id = destination.getId();

                switch (id) {
                    case R.id.homeFragment:
                        break;

                    case R.id.myJobsFragment:
                        break;

                    case R.id.postJobFragment:
                        break;

                    case R.id.postedJobsFragment:
                        break;

                    case R.id.aboutUsFragment:
                        break;

                    case R.id.logout:
                        break;

                    default:
                        break;
                }
            }
        });


        Log.d(TAG, "onCreate: has ended.");

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);

        return true;
    }


    @Override
    public boolean onSupportNavigateUp() {
        Log.d(TAG, "onSupportNavigateUp: has started");
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


    public void logout(MenuItem item) {
        mAuth.signOut();
        startActivity(new Intent(getApplicationContext(), ClientLogin.class));
        finish();
    }

    public void searchActivity(MenuItem item) {
        startActivity(new Intent(getApplicationContext(), Filter.class));
    }
}
package com.example.authentification.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.authentification.R;
import com.example.authentification.presenter.LoginPresenter;
import com.google.android.material.navigation.NavigationView;

import java.util.Objects;

public class ProfileActivity extends AppCompatActivity{

    TextView userName, club, country;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity);

        String message = getIntent().getStringExtra("message");
        if(message != null) {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }
        userName = findViewById(R.id.profileName);
        club = findViewById(R.id.profileClub);
        country = findViewById(R.id.profileCountry);
        userName.setText(LoginPresenter.getName());
        club.setText(LoginPresenter.getClub());
        country.setText(LoginPresenter.getCountry());

        DrawerLayout drawerLayout = findViewById(R.id.profile_drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.navbar_open, R.string.navbar_tr_close);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        actionBarDrawerToggle.setDrawerSlideAnimationEnabled(true);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        NavigationView navigationView = findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(menuItem -> {
            int id = menuItem.getItemId();

            switch (id) {
                case R.id.tournaments:
                    //tournaments
                    break;
                case R.id.participants:
                    Intent intent = new Intent(ProfileActivity.this, ParticipantsActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    break;
                case R.id.log_out:
                    SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("email", "");
                    editor.putString("password", "");
                    editor.apply();
                    Intent loginActivity = new Intent(ProfileActivity.this, LoginActivity.class);
                    loginActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(loginActivity);
            }
            return true;
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.logout, menu);
        TextView name = findViewById(R.id.name_user);
        name.setText(LoginPresenter.getName());

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.log_out_bar) {
            SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("email", "");
            editor.putString("password", "");
            editor.apply();
            Intent loginActivity = new Intent(ProfileActivity.this, LoginActivity.class);
            loginActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(loginActivity);
        }
        return actionBarDrawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

}

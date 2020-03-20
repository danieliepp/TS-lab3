package com.example.authentification.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.authentification.R;
import com.example.authentification.adapters.ParticipantAdapter;
import com.example.authentification.data.Network;
import com.example.authentification.data.model.Participant;
import com.example.authentification.presenter.LoginPresenter;
import com.example.authentification.presenter.ParticipantPresenter;
import com.example.authentification.presenter.contract.IParticipantContract;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.List;
import java.util.Objects;

public class ParticipantsActivity extends AppCompatActivity implements IParticipantContract.View {

    private ActionBarDrawerToggle actionBarDrawerToggle;
    private FloatingActionButton addParticipant;
    private ParticipantPresenter participantPresenter;
    private ParticipantAdapter participantAdapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.participants_activity);
        addParticipant = findViewById(R.id.addParticipant);
        recyclerView = findViewById(R.id.rv);
        getParticipantsFromServer();
        addParticipant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ParticipantsActivity.this, AddParticipantActivity.class);
                startActivity(intent);
            }
        });

        DrawerLayout drawerLayout = findViewById(R.id.participants_drawer_layout);
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
                case R.id.profile:
                    Intent intent = new Intent(ParticipantsActivity.this, ProfileActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    break;
                case R.id.log_out:
                    SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("email", "");
                    editor.putString("password", "");
                    editor.apply();
                    Intent loginActivity = new Intent(ParticipantsActivity.this, LoginActivity.class);
                    loginActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(loginActivity);
            }
            return true;
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.search_logout, menu);
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
            Intent loginActivity = new Intent(ParticipantsActivity.this, LoginActivity.class);
            loginActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(loginActivity);
        }
        return actionBarDrawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    public void getParticipantsFromServer() {
        participantPresenter = new ParticipantPresenter(this);
        if(Network.isNetworkAvailable(this)){
            participantPresenter.getParticipants();
        }
    }

    private void setupAdapter(List<Participant> participantList) {
        participantAdapter = new ParticipantAdapter(participantList, new ParticipantAdapter.ItemParticipantOnClickListener() {
            @Override
            public void ItemOnClick(int position) {

            }

            @Override
            public void DeleteButtonOnClick(int position) {

            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.getRecycledViewPool().clear();
        recyclerView.setAdapter(participantAdapter);
        participantAdapter.notifyDataSetChanged();
    }

    @Override
    public void displayParticipants(List<Participant> participantList) {
        setupAdapter(participantList);
    }

    @Override
    public void displayError() {

    }

    @Override
    public void addParticipantSuccess() {

    }

    @Override
    public void addParticipantFailed(String message) {

    }
}

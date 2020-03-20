package com.example.authentification.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.authentification.R;
import com.example.authentification.data.Network;
import com.example.authentification.presenter.LoginPresenter;
import com.example.authentification.presenter.RegisterPresenter;
import com.example.authentification.presenter.contract.ILoginContract;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity implements ILoginContract.View {

    private TextView signUp;
    private TextInputEditText etEmail, etPassword;
    private Button signIn;
    private LoginPresenter loginPresenter;
    private View view;
    private CheckBox remeberMe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initializeWidgets();

        SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
        etEmail.setText(preferences.getString("email", ""));
        etPassword.setText(preferences.getString("password", ""));

        if(!etEmail.getText().toString().isEmpty() && !etPassword.getText().toString().isEmpty() ) {
            doLogin();
        } else {
            remeberMe.setChecked(false);
        }

        onRememberMeClick();
        onLoginClick();
        signUp = findViewById(R.id.signUp);

        if(getIntent().getStringExtra("message") != null) {
            String message = getIntent().getStringExtra("message");
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }

    public void initializeWidgets() {
        remeberMe = findViewById(R.id.rememberMe);
        signIn = findViewById(R.id.cirLoginButton);
        etEmail = findViewById(R.id.editTextEmail);
        etPassword = findViewById(R.id.editTextPassword);
    }

    public void onLoginClick() {
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    doLogin();
            }
        });
    }

    private void onRememberMeClick() {
        remeberMe.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.isChecked()) {

                    SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("email", etEmail.getText().toString().trim());
                    editor.putString("password", etPassword.getText().toString().trim());

                    editor.apply();

                } else {

                    SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("email", "");
                    editor.putString("password", "");
                    editor.apply();

                }
            }
        });
    }

    public void doLogin() {
        if(Network.isNetworkAvailable(this)){
            loginPresenter = new LoginPresenter(this);
            HashMap<String, String> map = new HashMap<>();
            map.put("email", etEmail.getText().toString());
            map.put("password", etPassword.getText().toString());

            loginPresenter.doLogin(map);
        }
    }

    @Override
    public void onLoginSuccess() {
            Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.putExtra("message", "Welcome " + LoginPresenter.getName());
            startActivity(intent);
    }

    @Override
    public void onLoginFailed() {
        Toast.makeText(this, "Email or password is incorrect", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onInternetError() {

    }
}

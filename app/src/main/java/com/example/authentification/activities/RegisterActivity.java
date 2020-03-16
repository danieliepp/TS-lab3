package com.example.authentification.activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.authentification.R;
import com.example.authentification.Utils.Utils;
import com.example.authentification.data.Network;
import com.example.authentification.data.model.Country;
import com.example.authentification.presenter.CountryPresenter;
import com.example.authentification.presenter.RegisterPresenter;
import com.example.authentification.presenter.contract.ICountryContract;
import com.example.authentification.presenter.contract.IRegisterContract;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.HashMap;
import java.util.List;

public class RegisterActivity extends AppCompatActivity implements IRegisterContract.View, ICountryContract.View {

    private TextView signIn;
    private RegisterPresenter registerPresenter;
    private TextInputEditText usernameEditText;
    private TextInputEditText firstNameEditText;
    private TextInputEditText lastNameEditText;
    private TextInputEditText emailEditText;
    private TextInputEditText countryEditText;
    private TextInputEditText clubEditText;
    private TextInputEditText passwordEditText;
    private TextInputEditText confirmPasswordEditText;
    private TextInputLayout usernameInputLayout;
    private TextInputLayout firstNameInputLayout;
    private TextInputLayout lastNameInputLayout;
    private TextInputLayout emailInputLayout;
    private TextInputLayout countryInputLayout;
    private TextInputLayout clubInputLayout;
    private TextInputLayout passwordInputLayout;
    private TextInputLayout confirmPasswordInputLayout;
    private MaterialButton confirmButton;
    private Country country;
    private Button btnRegister;
    private CountryPresenter countryPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initializeWidgets();
        onRegisterClick();
        setupWidgets();
        countryPresenter = new CountryPresenter(this);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }

    public void initializeWidgets() {
        signIn = findViewById(R.id.signIn);
        usernameEditText = findViewById(R.id.etUsername);
        firstNameEditText = findViewById(R.id.etFirstName);
        lastNameEditText = findViewById(R.id.etLastName);
        emailEditText = findViewById(R.id.etEmail);
        countryEditText = findViewById(R.id.etCountry);
        clubEditText = findViewById(R.id.etClub);
        passwordEditText = findViewById(R.id.etPassword);
        confirmPasswordEditText = findViewById(R.id.etConfirmPassword);
        usernameInputLayout = findViewById(R.id.tillUsername);
        firstNameInputLayout = findViewById(R.id.tillFirstName);
        lastNameInputLayout = findViewById(R.id.tillLastName);
        emailInputLayout = findViewById(R.id.tillEmail);
        countryInputLayout = findViewById(R.id.tillCountry);
        clubInputLayout = findViewById(R.id.tillClub);
        passwordInputLayout = findViewById(R.id.tillPassword);
        confirmPasswordInputLayout = findViewById(R.id.tillConfirmPassword);
        btnRegister = findViewById(R.id.btnRegister);
    }

    public void onRegisterClick() {
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(verifyAll()) {
                    registerNewUser();
                }
            }
        });
    }

    private void getCountryFromServer() {
        countryPresenter.getCountries();
    }

    public void setupWidgets() {
        countryEditText.setOnFocusChangeListener((view, b) -> {
            if (b) {
                view.performClick();
            }
        });

        countryEditText.setOnClickListener(view -> {
            countryEditText.setEnabled(false);
            getCountryFromServer();
            countryEditText.clearFocus();
        });

        usernameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                usernameInputLayout.setBoxStrokeColor(getResources().getColor(R.color.colorPrimary));
                usernameInputLayout.setHintTextColor(getResources().getColorStateList(R.color.colorPrimary));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        firstNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                firstNameInputLayout.setBoxStrokeColor(getResources().getColor(R.color.colorPrimary));
                firstNameInputLayout.setHintTextColor(getResources().getColorStateList(R.color.colorPrimary));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        lastNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                lastNameInputLayout.setBoxStrokeColor(getResources().getColor(R.color.colorPrimary));
                lastNameInputLayout.setHintTextColor(getResources().getColorStateList(R.color.colorPrimary));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        emailEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                emailInputLayout.setBoxStrokeColor(getResources().getColor(R.color.colorPrimary));
                emailInputLayout.setHintTextColor(getResources().getColorStateList(R.color.colorPrimary));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        countryEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                countryInputLayout.setBoxStrokeColor(getResources().getColor(R.color.colorPrimary));
                countryInputLayout.setHintTextColor(getResources().getColorStateList(R.color.colorPrimary));
                countryEditText.setError(null);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        clubEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                clubInputLayout.setBoxStrokeColor(getResources().getColor(R.color.colorPrimary));
                clubInputLayout.setHintTextColor(getResources().getColorStateList(R.color.colorPrimary));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        passwordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                passwordInputLayout.setBoxStrokeColor(getResources().getColor(R.color.colorPrimary));
                passwordInputLayout.setHintTextColor(getResources().getColorStateList(R.color.colorPrimary));
                passwordInputLayout.setPasswordVisibilityToggleEnabled(true);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        confirmPasswordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                confirmPasswordInputLayout.setBoxStrokeColor(getResources().getColor(R.color.colorPrimary));
                confirmPasswordInputLayout.setHintTextColor(getResources().getColorStateList(R.color.colorPrimary));
                confirmPasswordInputLayout.setPasswordVisibilityToggleEnabled(true);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });



    }

    public boolean verifyAll() {
        boolean result = true;

        if (usernameEditText.getText().toString().isEmpty()) {
            result = false;
            usernameInputLayout.setBoxStrokeColor(getResources().getColor(R.color.red));
            usernameInputLayout.setHintTextColor(getResources().getColorStateList(R.color.red));
            usernameEditText.setError(getText(R.string.username_error_empty));
        } else if (Utils.isUsernameValid(usernameEditText.getText().toString().trim())) {
            result = false;
            usernameInputLayout.setBoxStrokeColor(getResources().getColor(R.color.red));
            usernameInputLayout.setHintTextColor(getResources().getColorStateList(R.color.red));
            usernameEditText.setError(getText(R.string.username_error));
        }

        if (firstNameEditText.getText().toString().isEmpty()) {
            result = false;
            firstNameInputLayout.setBoxStrokeColor(getResources().getColor(R.color.red));
            firstNameInputLayout.setHintTextColor(getResources().getColorStateList(R.color.red));
            firstNameEditText.setError(getText(R.string.first_name_error_empty));
        } else if (Utils.isNameStringsValid(firstNameEditText.getText().toString().trim())) {
            result = false;
            firstNameInputLayout.setBoxStrokeColor(getResources().getColor(R.color.red));
            firstNameInputLayout.setHintTextColor(getResources().getColorStateList(R.color.red));
            firstNameEditText.setError(getText(R.string.first_name_error));
        }

        if (lastNameEditText.getText().toString().isEmpty()) {
            result = false;
            lastNameInputLayout.setBoxStrokeColor(getResources().getColor(R.color.red));
            lastNameInputLayout.setHintTextColor(getResources().getColorStateList(R.color.red));
            lastNameEditText.setError(getText(R.string.last_name_error_empty));
        } else if (Utils.isNameStringsValid(lastNameEditText.getText().toString().trim())) {
            result = false;
            lastNameInputLayout.setBoxStrokeColor(getResources().getColor(R.color.red));
            lastNameInputLayout.setHintTextColor(getResources().getColorStateList(R.color.red));
            lastNameEditText.setError(getText(R.string.last_name_error));
        }

        if (emailEditText.getText().toString().isEmpty()) {
            result = false;
            emailInputLayout.setBoxStrokeColor(getResources().getColor(R.color.red));
            emailInputLayout.setHintTextColor(getResources().getColorStateList(R.color.red));
            emailEditText.setError(getText(R.string.email_error_empty));
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailEditText.getText().toString()).matches()) {
            result = false;
            emailInputLayout.setBoxStrokeColor(getResources().getColor(R.color.red));
            emailInputLayout.setHintTextColor(getResources().getColorStateList(R.color.red));
            emailEditText.setError(getText(R.string.email_error));
        }

        if (clubEditText.getText().toString().isEmpty()) {
            result = false;
            clubInputLayout.setBoxStrokeColor(getResources().getColor(R.color.red));
            clubInputLayout.setHintTextColor(getResources().getColorStateList(R.color.red));
            clubEditText.setError(getText(R.string.club_error_empty));
        } else if (Utils.isClubValid(clubEditText.getText().toString().trim())) {
            result = false;
            clubInputLayout.setBoxStrokeColor(getResources().getColor(R.color.red));
            clubInputLayout.setHintTextColor(getResources().getColorStateList(R.color.red));
            clubEditText.setError(getText(R.string.club_error));
        }

        if (!isCountryValid()) {
            result = false;
            countryInputLayout.setBoxStrokeColor(getResources().getColor(R.color.red));
            countryInputLayout.setHintTextColor(getResources().getColorStateList(R.color.red));
            countryEditText.setError(getText(R.string.country_error));
        }

        if (Utils.isPasswordValid(passwordEditText.getText().toString().trim())) {
            result = false;
            passwordInputLayout.setPasswordVisibilityToggleEnabled(false);
            passwordInputLayout.setBoxStrokeColor(getResources().getColor(R.color.red));
            passwordInputLayout.setHintTextColor(getResources().getColorStateList(R.color.red));
            passwordEditText.setError(getText(R.string.password_upper_digital_symbols));
        }

        if (!matchPasswordValidation() || confirmPasswordEditText.getText().toString().length() < 8) {
            result = false;
            confirmPasswordInputLayout.setPasswordVisibilityToggleEnabled(false);
            confirmPasswordInputLayout.setBoxStrokeColor(getResources().getColor(R.color.red));
            confirmPasswordInputLayout.setHintTextColor(getResources().getColorStateList(R.color.red));
            confirmPasswordEditText.setError(getText(R.string.confirm_password_error));
        }

        return result;
    }

    private boolean matchPasswordValidation() {
        return passwordEditText.getText().toString().trim()
                .equals(confirmPasswordEditText.getText().toString().trim());
    }

    public void registerNewUser() {
        if(Network.isNetworkAvailable(this)){
            registerPresenter = new RegisterPresenter(this);
            HashMap<String, String> map = new HashMap<>();
            map.put("username", usernameEditText.getText().toString().trim());
            map.put("first_name", firstNameEditText.getText().toString().trim());
            map.put("last_name", lastNameEditText.getText().toString().trim());
            map.put("email", emailEditText.getText().toString().trim());
            map.put("country", countryEditText.getText().toString().trim());
            map.put("club", clubEditText.getText().toString().trim());
            map.put("password", passwordEditText.getText().toString().trim());
            map.put("confirm_password", confirmPasswordEditText.getText().toString().trim());
            registerPresenter.registerNewUser(map);
        }
    }

    @Override
    public void registerNewUserMessage() {
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra("message", "Registered successfully!");
        startActivity(intent);
    }

    private boolean isCountryValid() {
        if (country != null)
            return country.getCountryId() != null;
        return false;
    }

    @Override
    public void displayError() {
        Toast.makeText(this, "ERROR!!! Something went wrong!!! Try Again", Toast.LENGTH_LONG).show();
    }

    @Override
    public void displayErrorUserEmailOrUsername() {

    }

    @Override
    public void displayCountriesSuccess(List<Country> countryList) {
        new AlertDialog.Builder(this)
                .setCancelable(false)
                .setNegativeButton(R.string.cancel, (dialogInterface, i) -> dialogInterface.dismiss())
                .setTitle(R.string.choose_country)
                .setAdapter(new ArrayAdapter<>(this,
                        R.layout.display_country_item,
                        countryList), (dialogInterface, i) -> {
                    country = countryList.get(i);
                    countryEditText.setText(country.toString());
                    countryInputLayout.setError(null);
                }).show();
        countryEditText.setEnabled(true);
    }

    @Override
    public void displayCountriesError() {

    }
}

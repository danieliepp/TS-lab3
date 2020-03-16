package com.example.authentification.activities;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.authentification.R;
import com.example.authentification.Utils.Utils;
import com.example.authentification.data.RetrofitClient;
import com.example.authentification.data.model.Coach;
import com.example.authentification.data.model.Country;
import com.example.authentification.data.model.Participant;
import com.example.authentification.presenter.CoachPresenter;
import com.example.authentification.presenter.CountryPresenter;
import com.example.authentification.presenter.LoginPresenter;
import com.example.authentification.presenter.ParticipantPresenter;
import com.example.authentification.presenter.contract.ICoachContract;
import com.example.authentification.presenter.contract.ICountryContract;
import com.example.authentification.presenter.contract.IParticipantContract;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialAutoCompleteTextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class AddParticipantActivity extends AppCompatActivity implements IParticipantContract.View, ICountryContract.View, ICoachContract.View{

    private ActionBarDrawerToggle actionBarDrawerToggle;
    private TextInputEditText etParticipantFirstName;
    private TextInputEditText etParticipantLastName;
    private TextInputEditText etParticipantCoachName;
    private TextInputEditText etParticipantCountry;
    private TextInputEditText etParticipantBirthDay;
    private TextInputEditText etParticipantWeight;
    private TextInputEditText etParticipantAge;
    private TextInputEditText etParticipantClub;

    private TextInputLayout participantFirstNameLayout;
    private TextInputLayout participantLastNameLayout;
    private TextInputLayout participantCoachNameLayout;
    private TextInputLayout participantCountryLayout;
    private TextInputLayout participantBirthdayLayout;
    private TextInputLayout participantGenderLayout;
    private TextInputLayout participantWeightLayout;
    private TextInputLayout participantAgeLayout;
    private TextInputLayout participantClubLayout;

    private MaterialAutoCompleteTextView etGender;
    private ParticipantPresenter participantPresenter;
    private FloatingActionButton fabAddParticipant;
    private String[] chooseGenderType;
    private CountryPresenter countryPresenter;
    private Country country;
    private boolean dateError = true;

    private SimpleDateFormat birtdayDateFormatt;
    private Date birthdayDate;
    private Integer age;

    private CoachPresenter coachPresenter;
    private Coach coach;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_participant_activity);
        initWidgets();
        participantPresenter = new ParticipantPresenter(this);
        listenersToWidgets();
        listenersOnTextChange();
        countryPresenter = new CountryPresenter(this);
        coachPresenter = new CoachPresenter(this);
        DrawerLayout drawerLayout = findViewById(R.id.AddParticipantsActivity);
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
                    Intent intent = new Intent(AddParticipantActivity.this, ParticipantsActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    break;
                case R.id.profile:
                    Intent profileIntent = new Intent(AddParticipantActivity.this, ProfileActivity.class);
                    profileIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(profileIntent);
                    break;
                case R.id.log_out:
                    Intent loginActivity = new Intent(AddParticipantActivity.this, LoginActivity.class);
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
            Intent loginActivity = new Intent(AddParticipantActivity.this, LoginActivity.class);
            startActivity(loginActivity);
        }
        return actionBarDrawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    private void listenersToWidgets() {
        fabAddParticipant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(verifyAll()) {
                    addParticipant();
                } else {
                    Toast.makeText(AddParticipantActivity.this, "Fields has to be filled", Toast.LENGTH_SHORT).show();
                }
            }
        });

        chooseGenderType = getResources().getStringArray(R.array.sex_list);
        etGender.setAdapter(new ArrayAdapter<>(this, R.layout.display_spinner_item, chooseGenderType));

        etGender.setOnFocusChangeListener((view, b) -> {
            if (b) {
                view.performClick();
            }
        });

        etGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etGender.showDropDown();
                etGender.clearFocus();
                etGender.setError(null);
            }
        });

        etParticipantBirthDay.setOnFocusChangeListener((view, b) -> {
            if (b) {
                view.performClick();
            }
        });

        etParticipantBirthDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setupDatePicker(etParticipantBirthDay);
            }
        });

        etParticipantCountry.setOnFocusChangeListener((view, b) -> {
            if (b) {
                view.performClick();
            }
        });

        etParticipantCountry.setOnClickListener(view -> {
            getCountryFromServer();
            etParticipantCountry.clearFocus();
        });

        etParticipantCoachName.setOnFocusChangeListener((view, b) -> {
            if (b) {
                view.performClick();
            }
        });

        etParticipantCoachName.setOnClickListener(view -> {
            getCoachesFromServer();
            etParticipantCoachName.clearFocus();
        });
    }

    private void getCoachesFromServer() {
        coachPresenter.getCoaches();
    }

    private void getCountryFromServer() {
        countryPresenter.getCountries();
    }

    private void addParticipant() {
       Participant participant = new Participant.Builder()
                .setParticipantName(etParticipantFirstName.getText().toString() + " " + etParticipantLastName.getText().toString().trim())
                .setParticipantCountry(etParticipantCountry.getText().toString().trim())
                .setClub(etParticipantClub.getText().toString().trim())
                .setBirthday(etParticipantBirthDay.getText().toString().trim())
                .setCoachName(etParticipantCoachName.getText().toString().trim())
                .setAge(etParticipantAge.getText().toString().trim().trim())
                .setWeight(etParticipantWeight.getText().toString().trim())
                .setSex(etGender.getText().toString().trim())
                .create();
       participantPresenter.addParticipant(participant);
    }

    private void setupDatePicker(EditText dateInput) {
        Calendar currentTime = Calendar.getInstance();
        Calendar compareTimeToday = Calendar.getInstance();
        Calendar minParticipantAge = Calendar.getInstance();
        minParticipantAge.set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR) - 5);
        DatePickerDialog datePickerDialog
                = new DatePickerDialog(this,
                (view, year, month, dayOfMonth) -> {

                    currentTime.set(Calendar.YEAR, year);
                    currentTime.set(Calendar.MONTH, month);
                    currentTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(currentTime.getTime());

                    dateInput.setText(date);
                    if (compareTimeToday.before(currentTime) || minParticipantAge.before(currentTime)) {
                        dateError = true;
                        showDateError();
                    } else {
                        dateError = false;
                        hideDateError();
                    }
                    birtdayDateFormatt = new SimpleDateFormat("yyyy-MM-dd");
                    try {
                        birthdayDate = birtdayDateFormatt.parse(date);
                        age = Utils.calculateAge(birthdayDate);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    etParticipantAge.setText(age.toString());
                }, currentTime.get(Calendar.YEAR),
                currentTime.get(Calendar.MONTH),
                currentTime.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.setOnDismissListener(dialogInterface -> {
            dateInput.clearFocus();
            enableFields();

        });
        datePickerDialog.setCancelable(false);
        datePickerDialog.show();
        disableFields();
    }

    public void showDateError() {
        participantAgeLayout.setBoxStrokeColor(getResources().getColor(R.color.red));
        participantAgeLayout.setHintTextColor(getResources().getColorStateList(R.color.red));
        etParticipantAge.setError(getText(R.string.age_error));
    }

    public void hideDateError() {
        participantAgeLayout.setBoxStrokeColor(getResources().getColor(R.color.colorPrimary));
        participantAgeLayout.setHintTextColor(getResources().getColorStateList(R.color.colorPrimary));
        etParticipantAge.setError(null);
    }

    private void initWidgets() {
        etGender = findViewById(R.id.etParticipantGender);
        etParticipantAge = findViewById(R.id.etAge);
        etParticipantBirthDay = findViewById(R.id.etParticipantBirthday);
        etParticipantCoachName = findViewById(R.id.etParticipantCoachName);
        etParticipantCountry = findViewById(R.id.etParticipantCountry);
        etParticipantFirstName = findViewById(R.id.etParticipantFirstName);
        etParticipantLastName = findViewById(R.id.etParticipantLastName);
        etParticipantWeight = findViewById(R.id.etParticipantWeight);
        etParticipantClub = findViewById(R.id.etParticipantClub);
        fabAddParticipant = findViewById(R.id.btnAddParticipant);

        participantCoachNameLayout = findViewById(R.id.tillParticipantCoachName);
        participantCountryLayout = findViewById(R.id.tillParticipantCountry);
        participantBirthdayLayout = findViewById(R.id.tillParticipantBirthday);
        participantGenderLayout = findViewById(R.id.tillParticipantGender);
        participantWeightLayout = findViewById(R.id.tillParticipantWeight);
        participantFirstNameLayout = findViewById(R.id.tillParticipantFirstName);
        participantLastNameLayout = findViewById(R.id.tillParticipantLastName);
        participantAgeLayout = findViewById(R.id.tillAge);
        participantClubLayout = findViewById(R.id.tillParticipantClub);

    }

    public void listenersOnTextChange() {

        etParticipantFirstName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                participantFirstNameLayout.setBoxStrokeColor(getResources().getColor(R.color.colorPrimary));
                participantFirstNameLayout.setHintTextColor(getResources().getColorStateList(R.color.colorPrimary));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        etParticipantLastName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                participantLastNameLayout.setBoxStrokeColor(getResources().getColor(R.color.colorPrimary));
                participantLastNameLayout.setHintTextColor(getResources().getColorStateList(R.color.colorPrimary));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        etParticipantClub.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                participantClubLayout.setBoxStrokeColor(getResources().getColor(R.color.colorPrimary));
                participantClubLayout.setHintTextColor(getResources().getColorStateList(R.color.colorPrimary));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        etParticipantWeight.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                participantWeightLayout.setBoxStrokeColor(getResources().getColor(R.color.colorPrimary));
                participantWeightLayout.setHintTextColor(getResources().getColorStateList(R.color.colorPrimary));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });



        etParticipantBirthDay.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!editable.toString().isEmpty()){
                    etParticipantBirthDay.setError(null);
                }
            }
        });
    }

    public boolean verifyAll() {
        boolean result = true;
        if (etParticipantFirstName.getText().toString().isEmpty()) {
            result = false;
            participantFirstNameLayout.setBoxStrokeColor(getResources().getColor(R.color.red));
            participantFirstNameLayout.setHintTextColor(getResources().getColorStateList(R.color.red));
            etParticipantFirstName.setError(getText(R.string.name_empty));
        } else if (Utils.isNameStringsValid(etParticipantFirstName.getText().toString().trim())) {
            result = false;
            participantFirstNameLayout.setBoxStrokeColor(getResources().getColor(R.color.red));
            participantFirstNameLayout.setHintTextColor(getResources().getColorStateList(R.color.red));
            etParticipantFirstName.setError(getText(R.string.name_error));
        }

        if (etParticipantLastName.getText().toString().isEmpty()) {
            result = false;
            participantLastNameLayout.setBoxStrokeColor(getResources().getColor(R.color.red));
            participantLastNameLayout.setHintTextColor(getResources().getColorStateList(R.color.red));
            etParticipantLastName.setError(getText(R.string.name_empty));
        } else if (Utils.isNameStringsValid(etParticipantLastName.getText().toString().trim())) {
            result = false;
            participantLastNameLayout.setBoxStrokeColor(getResources().getColor(R.color.red));
            participantLastNameLayout.setHintTextColor(getResources().getColorStateList(R.color.red));
            etParticipantLastName.setError(getText(R.string.name_error));
        }

        if (etParticipantClub.getText().toString().isEmpty()) {
            result = false;
            participantClubLayout.setBoxStrokeColor(getResources().getColor(R.color.red));
            participantClubLayout.setHintTextColor(getResources().getColorStateList(R.color.red));
            etParticipantClub.setError(getText(R.string.club_error_empty));
        } else if (Utils.isClubValid(etParticipantClub.getText().toString().trim())) {
            result = false;
            participantClubLayout.setBoxStrokeColor(getResources().getColor(R.color.red));
            participantClubLayout.setHintTextColor(getResources().getColorStateList(R.color.red));
            etParticipantClub.setError(getText(R.string.club_error));
        }

        if (etParticipantCoachName.getText().toString().isEmpty()) {
            result = false;
            participantCoachNameLayout.setBoxStrokeColor(getResources().getColor(R.color.red));
            participantCoachNameLayout.setHintTextColor(getResources().getColorStateList(R.color.red));
            etParticipantCoachName.setError(getText(R.string.empty_field_error));
        } else if(Utils.isNameStringsValid(etParticipantCoachName.getText().toString().trim())) {
            result = false;
            participantCoachNameLayout.setBoxStrokeColor(getResources().getColor(R.color.red));
            participantCoachNameLayout.setHintTextColor(getResources().getColorStateList(R.color.red));
            etParticipantCoachName.setError(getText(R.string.coach_name_error));
        }

        if (etParticipantCountry.getText().toString().isEmpty()) {
            result = false;
            participantCountryLayout.setBoxStrokeColor(getResources().getColor(R.color.red));
            participantCountryLayout.setHintTextColor(getResources().getColorStateList(R.color.red));
            etParticipantCountry.setError(getText(R.string.empty_field_error));
        }

        if (etGender.getText().toString().isEmpty()) {
            result = false;
            participantGenderLayout.setBoxStrokeColor(getResources().getColor(R.color.red));
            participantGenderLayout.setHintTextColor(getResources().getColorStateList(R.color.red));
            etGender.setError(getText(R.string.empty_field_error));
        }

        if (etParticipantBirthDay.getText().toString().isEmpty()) {
            result = false;
            participantBirthdayLayout.setBoxStrokeColor(getResources().getColor(R.color.red));
            participantBirthdayLayout.setHintTextColor(getResources().getColorStateList(R.color.red));
            etParticipantBirthDay.setError(getText(R.string.empty_field_error));
        }

        if (etParticipantWeight.getText().toString().isEmpty()) {
            result = false;
            participantWeightLayout.setBoxStrokeColor(getResources().getColor(R.color.red));
            participantWeightLayout.setHintTextColor(getResources().getColorStateList(R.color.red));
            etParticipantWeight.setError(getText(R.string.empty_field_error));
        } else if (Integer.parseInt(etParticipantWeight.getText().toString()) > 100) {
            result = false;
            participantWeightLayout.setBoxStrokeColor(getResources().getColor(R.color.red));
            participantWeightLayout.setHintTextColor(getResources().getColorStateList(R.color.red));
            etParticipantWeight.setError(getText(R.string.weight_error));
        }

        if(dateError){
            result = false;
            participantAgeLayout.setBoxStrokeColor(getResources().getColor(R.color.red));
            participantAgeLayout.setHintTextColor(getResources().getColorStateList(R.color.red));
            etParticipantAge.setError(getText(R.string.age_error));
        }

        return result;
    }

    public void disableFields() {
        etParticipantFirstName.setEnabled(false);
        etParticipantLastName.setEnabled(false);
        etParticipantCoachName.setEnabled(false);
        etParticipantBirthDay.setEnabled(false);
        etParticipantCountry.setEnabled(false);
        etParticipantWeight.setEnabled(false);
        etGender.setEnabled(false);
    }

    public void enableFields() {
        etParticipantFirstName.setEnabled(true);
        etParticipantLastName.setEnabled(true);
        etParticipantCoachName.setEnabled(true);
        etParticipantBirthDay.setEnabled(true);
        etParticipantCountry.setEnabled(true);
        etParticipantWeight.setEnabled(true);
        etGender.setEnabled(true);
    }

    @Override
    public void displayParticipants(List<Participant> participantList) {

    }

    @Override
    public void displayError() {

    }

    @Override
    public void addParticipantSuccess() {
        new AlertDialog.Builder(AddParticipantActivity.this)
                .setTitle("Success")
                .setMessage("Participant was created")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(AddParticipantActivity.this, ParticipantsActivity.class);
                        startActivity(intent);
                    }
                })
                .show();
    }

    @Override
    public void addParticipantFailed(String message) {
        if(message.contains("exists")){
            participantFirstNameLayout.setBoxStrokeColor(getResources().getColor(R.color.red));
            participantFirstNameLayout.setHintTextColor(getResources().getColorStateList(R.color.red));
            etParticipantFirstName.setError(getText(R.string.name_error));
            participantLastNameLayout.setBoxStrokeColor(getResources().getColor(R.color.red));
            participantLastNameLayout.setHintTextColor(getResources().getColorStateList(R.color.red));
            etParticipantLastName.setError(getText(R.string.name_error));
        } else {
            String[] strings = message.split("\"");
            new AlertDialog.Builder(AddParticipantActivity.this)
                    .setTitle("Participant create failed")
                    .setMessage(strings[3])
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    })
                    .show();
        }
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
                    etParticipantCountry.setText(country.toString());
                    etParticipantCountry.setError(null);
                }).show();
        etParticipantCountry.setEnabled(true);
    }

    @Override
    public void displayCountriesError() {

    }

    @Override
    public void displayCoachesSuccess(List<Coach> coachList) {
        new AlertDialog.Builder(this)
                .setCancelable(false)
                .setNegativeButton(R.string.cancel, (dialogInterface, i) -> dialogInterface.dismiss())
                .setTitle(R.string.choose_country)
                .setAdapter(new ArrayAdapter<>(this,
                        R.layout.display_coach_item,
                        coachList), (dialogInterface, i) -> {
                    coach = coachList.get(i);
                    etParticipantCoachName.setText(coach.getCoachName());
                    etParticipantCoachName.setError(null);
                }).show();
        etParticipantCoachName.setEnabled(true);
    }

    @Override
    public void displayCoachesError() {

    }
}

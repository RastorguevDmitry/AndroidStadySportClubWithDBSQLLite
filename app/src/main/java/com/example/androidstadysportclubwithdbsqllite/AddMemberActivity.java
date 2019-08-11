package com.example.androidstadysportclubwithdbsqllite;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import com.example.androidstadysportclubwithdbsqllite.data.ClubOlimpusContract;

import java.util.ArrayList;

public class AddMemberActivity extends AppCompatActivity {

    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private EditText sportEditText;
    private Spinner genderSpiner;
    private int gender = 0; //0 - нет данных, 1 - м, 2 - Ж
    private ArrayAdapter spinerAdapter;
    private ArrayList spinnerArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_member);

        firstNameEditText = findViewById(R.id.firstNameEditText);
        lastNameEditText = findViewById(R.id.lastNameEditText);
        sportEditText = findViewById(R.id.sportEditText);
        genderSpiner = findViewById(R.id.genderSpiner);


        spinnerArrayList = new ArrayList();
        spinnerArrayList.add("Unknown");
        spinnerArrayList.add("Male");
        spinnerArrayList.add("Female");

        spinerAdapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, spinnerArrayList);
        spinerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpiner.setAdapter(spinerAdapter);

        genderSpiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedGender = (String) adapterView.getItemAtPosition(i);
                if (!TextUtils.isEmpty(selectedGender)) {
                    if (selectedGender.equals("Male")) {
                        gender = ClubOlimpusContract.MemberEntry.GENDER_MALE;
                    } else if (selectedGender.equals("Female")) {
                        gender = ClubOlimpusContract.MemberEntry.GENDER_FEMAIL;
                    } else gender = ClubOlimpusContract.MemberEntry.GENDER_UNKNOWN;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                gender = 0;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_member_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_member:
                insertMember();
                return true;

            case R.id.delit_member:
                return true;
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void insertMember() {
        String firstName = firstNameEditText.getText().toString().trim();
        String LastName = lastNameEditText.getText().toString().trim();
        String sport = sportEditText.getText().toString().trim();


        ContentValues contentValues = new ContentValues();
        contentValues.put(ClubOlimpusContract.MemberEntry.COLUMN_FIRST_NAME, firstName);
        contentValues.put(ClubOlimpusContract.MemberEntry.COLUMN_LAST_NAME, LastName);
        contentValues.put(ClubOlimpusContract.MemberEntry.COLUMN_SPORTS, sport);
        contentValues.put(ClubOlimpusContract.MemberEntry.COLUMN_GENDER, gender);

        ContentResolver contentResolver = getContentResolver();
        Uri uri = contentResolver.insert(ClubOlimpusContract.MemberEntry.CONTENT_URI, contentValues);

        if (uri == null) {
            Toast.makeText(this, "Insertion in the tsble faleed for " + uri, Toast.LENGTH_LONG).show();
        } else  Toast.makeText(this, "Data save ", Toast.LENGTH_LONG).show();
    }

}

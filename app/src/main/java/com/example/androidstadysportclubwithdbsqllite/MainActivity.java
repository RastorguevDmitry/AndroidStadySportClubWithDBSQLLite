package com.example.androidstadysportclubwithdbsqllite;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidstadysportclubwithdbsqllite.data.ClubOlimpusContract.MemberEntry;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    TextView dataTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataTextView = findViewById(R.id.dataTextView);

        FloatingActionButton floatingActionButton = findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddMemberActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        displayData();
    }

    private void displayData() {
        String[] projection = {
                MemberEntry._ID,
                MemberEntry.COLUMN_FIRST_NAME,
                MemberEntry.COLUMN_LAST_NAME,
                MemberEntry.COLUMN_GENDER,
                MemberEntry.COLUMN_SPORTS
        };

        Cursor cursor = getContentResolver().query(
                MemberEntry.CONTENT_URI,
                projection,
                null,
                null,
                null
        );

        dataTextView.setText("All members\n\n");
        dataTextView.append(MemberEntry._ID + " " +
                MemberEntry.COLUMN_FIRST_NAME + " " +
                MemberEntry.COLUMN_LAST_NAME + " " +
                MemberEntry.COLUMN_GENDER + " " +
                MemberEntry.COLUMN_SPORTS);

        int idСolumnIndex = cursor.getColumnIndex(MemberEntry._ID);
        int firstNameСolumnIndex = cursor.getColumnIndex(MemberEntry.COLUMN_FIRST_NAME);
        int lastNameСolumnIndex = cursor.getColumnIndex(MemberEntry.COLUMN_LAST_NAME);
        int genderСolumnIndex = cursor.getColumnIndex(MemberEntry.COLUMN_GENDER);
        int sportСolumnIndex = cursor.getColumnIndex(MemberEntry.COLUMN_SPORTS);

        while (cursor.moveToNext()) {
            int curentId = cursor.getInt(idСolumnIndex);
            String curentFirstName = cursor.getString(firstNameСolumnIndex);
            String curentLastName = cursor.getString(lastNameСolumnIndex);
            int curentGender = cursor.getInt(genderСolumnIndex);
            String curentSport = cursor.getString(sportСolumnIndex);
            dataTextView.append("\n" +
                    curentId + " " +
                    curentFirstName + " " +
                    curentLastName + " " +
                    curentGender + " " +
                    curentSport);
        }
        cursor.close();

    }

}

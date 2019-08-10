package com.example.androidstadysportclubwithdbsqllite.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.androidstadysportclubwithdbsqllite.data.ClubOlimpusContract.MemberEntry;

public class OlimpusDBHelper extends SQLiteOpenHelper {


    public OlimpusDBHelper(Context context) {
        super(context, ClubOlimpusContract.DATABASE_NAME, null, ClubOlimpusContract.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_MEMBERS_TABLE = "CREATE TABLE " + MemberEntry.TABLE_NAAME + "(" +
                MemberEntry.KEY_ID + " INTEGER PRIMARY KEY," +
                MemberEntry.KEY_FIRST_NAME + " TEXT," +
                MemberEntry.KEY_GENDER + " INTEGER NOT NULL," +
                MemberEntry.KEY_SPORTS + " TEXT)";

        sqLiteDatabase.execSQL(CREATE_MEMBERS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ClubOlimpusContract.DATABASE_NAME);
        onCreate(sqLiteDatabase);
    }
}

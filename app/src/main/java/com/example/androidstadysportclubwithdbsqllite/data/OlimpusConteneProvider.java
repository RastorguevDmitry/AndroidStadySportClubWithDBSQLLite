package com.example.androidstadysportclubwithdbsqllite.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import com.example.androidstadysportclubwithdbsqllite.data.ClubOlimpusContract.MemberEntry;

public class OlimpusConteneProvider extends ContentProvider {
    OlimpusDBHelper olimpusDBHelper;
    private static final int MEMBERS = 111;
    private static final int MEMBER_ID = 222;


    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        uriMatcher.addURI(ClubOlimpusContract.AUTHORITY, ClubOlimpusContract.PATH_MEMBERS, MEMBERS);
        uriMatcher.addURI(ClubOlimpusContract.AUTHORITY, ClubOlimpusContract.PATH_MEMBERS + "/#", MEMBER_ID);
    }


    @Override
    public boolean onCreate() {
        olimpusDBHelper = new OlimpusDBHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = olimpusDBHelper.getReadableDatabase();
        Cursor cursor;
        int match = uriMatcher.match(uri);

        switch (match) {
            case MEMBERS:
                cursor = db.query(MemberEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case MEMBER_ID:
                selection = MemberEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = db.query(MemberEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("can`t query incorrect uri" + uri);

        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {

        String firstName = contentValues.getAsString(MemberEntry.COLUMN_FIRST_NAME);
        if (firstName == null) {
            throw new IllegalArgumentException("You hate to input first name");
        }
        String lastName = contentValues.getAsString(MemberEntry.COLUMN_LAST_NAME);
        if (lastName == null) {
            throw new IllegalArgumentException("You hate to input last name");
        }
        Integer gender = contentValues.getAsInteger(MemberEntry.COLUMN_GENDER);
        if (gender == null || !(gender == MemberEntry.GENDER_UNKNOWN || gender == MemberEntry.GENDER_FEMAIL || gender == MemberEntry.GENDER_MALE)) {
            throw new IllegalArgumentException("You hate to chose gender");
        }
        String sport = contentValues.getAsString(MemberEntry.COLUMN_SPORTS);
        if (sport == null) {
            throw new IllegalArgumentException("You hate to inputsport");
        }

        SQLiteDatabase db = olimpusDBHelper.getWritableDatabase();
        int match = uriMatcher.match(uri);


        switch (match) {
            case MEMBERS:
                long id = db.insert(MemberEntry.TABLE_NAME, null, contentValues);
                if (id == -1) {
                    Log.e("Insert metod", "Insertion in the tsble faleed for " + uri);
                    return null;
                }

                getContext().getContentResolver().notifyChange(uri, null);
                return ContentUris.withAppendedId(uri, id);

            default:
                throw new IllegalArgumentException("Insertion in the tsble faleed for " + uri);
        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = olimpusDBHelper.getWritableDatabase();
        int match = uriMatcher.match(uri);
        int rowsDeleted;
        switch (match) {
            case MEMBERS:
                rowsDeleted = db.delete(MemberEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case MEMBER_ID:
                selection = MemberEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                rowsDeleted = db.delete(MemberEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("can`t delete this uri" + uri);
        }

        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {

        if (contentValues.containsKey(MemberEntry.COLUMN_FIRST_NAME)) {
            String firstName = contentValues.getAsString(MemberEntry.COLUMN_FIRST_NAME);
            if (firstName == null) {
                throw new IllegalArgumentException("You hate to input first name");
            }
        }

        if (contentValues.containsKey(MemberEntry.COLUMN_LAST_NAME)) {
            String lastName = contentValues.getAsString(MemberEntry.COLUMN_LAST_NAME);
            if (lastName == null) {
                throw new IllegalArgumentException("You hate to input last name");
            }
        }

        if (contentValues.containsKey(MemberEntry.COLUMN_GENDER)) {
            Integer gender = contentValues.getAsInteger(MemberEntry.COLUMN_GENDER);
            if (gender == null || !(gender == MemberEntry.GENDER_UNKNOWN || gender == MemberEntry.GENDER_FEMAIL || gender == MemberEntry.GENDER_MALE)) {
                throw new IllegalArgumentException("You hate to chose gender");
            }
        }

        if (contentValues.containsKey(MemberEntry.COLUMN_SPORTS)) {
            String sport = contentValues.getAsString(MemberEntry.COLUMN_SPORTS);
            if (sport == null) {
                throw new IllegalArgumentException("You hate to inputsport");
            }
        }

        SQLiteDatabase db = olimpusDBHelper.getWritableDatabase();
        int match = uriMatcher.match(uri);
        int rowsUpdated;
        switch (match) {
            case MEMBERS:
                rowsUpdated = db.update(MemberEntry.TABLE_NAME, contentValues, selection, selectionArgs);
                break;
            case MEMBER_ID:
                selection = MemberEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                rowsUpdated = db.update(MemberEntry.TABLE_NAME, contentValues, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("can`t update this uri" + uri);
        }
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }

    @Override
    public String getType(Uri uri) {

        int match = uriMatcher.match(uri);

        switch (match) {
            case MEMBERS:
                return MemberEntry.CONTENT_MULTIPLE_ITEMS;
            case MEMBER_ID:
                return MemberEntry.CONTENT_SINGLE_ITEMS;
            default:
                throw new IllegalArgumentException("unknown uri" + uri);
        }

    }
}

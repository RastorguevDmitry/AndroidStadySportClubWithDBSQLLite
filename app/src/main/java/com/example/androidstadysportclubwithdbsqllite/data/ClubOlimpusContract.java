package com.example.androidstadysportclubwithdbsqllite.data;

import android.net.Uri;
import android.provider.BaseColumns;
import android.provider.Telephony;

public final class ClubOlimpusContract {

    private ClubOlimpusContract() {

    }

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "olympys";

    public static final String SCHEME = "content://";
    public static final String AUTHORITY = "com.example.androidstadysportclubwithdbsqllite";
    public static final String PATH_MEMBERS = "members";

    public static final Uri BASE_CONTENT_URI = Uri.parse(SCHEME + AUTHORITY);

    public final static class MemberEntry implements Telephony.BaseMmsColumns {

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_MEMBERS);

        public static final String TABLE_NAME = "members";
        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_FIRST_NAME = "firstName";
        public static final String COLUMN_LAST_NAME = "lastName";
        public static final String COLUMN_GENDER = "gender";
        public static final String COLUMN_SPORTS = "sport";

        public static final int GENDER_UNKNOWN = 0;
        public static final int GENDER_MALE = 1;
        public static final int GENDER_FEMAIL = 2;
    }

}

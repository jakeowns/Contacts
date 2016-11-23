package com.example.jake.contacts;

import android.provider.BaseColumns;

/**
 * Created by Jake on 11/21/2016.
 */

public final class ContactContract {
    private ContactContract() {}

    public static class ContactEntry implements BaseColumns {
        public static final String TABLE_NAME = "contacts";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_PHONE = "phone";
        public static final String COLUMN_EMAIL = "email";
        public static final String COLUMN_ADDRESS = "address";
        public static final String COLUMN_NOTES = "notes";
    }
}

package com.example.jake.contacts;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CreateContactActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_contact);
        Intent intent = getIntent();
        final String contactId = intent.getStringExtra(ContactContract.ContactEntry._ID) != null
                ? intent.getStringExtra(ContactContract.ContactEntry._ID) : null;

        // set db
        final ContactDbHelper contactDbHelper = new ContactDbHelper(this);

        // set text fields
        final EditText name = (EditText) findViewById(R.id.contact_name);
        final EditText phone = (EditText) findViewById(R.id.contact_phone_number);
        final EditText email = (EditText) findViewById(R.id.contact_email);
        final EditText address = (EditText) findViewById(R.id.contact_address);
        final EditText notes = (EditText) findViewById(R.id.contact_notes);

        // buttons
        final Button saveButton = (Button) findViewById(R.id.save_button);
        final Button deleteButton = (Button) findViewById(R.id.delete_button);

        if (contactId != null) {
            Cursor c = contactDbHelper.getContact(contactId);
            c.moveToFirst();
            name.setText(c.getString(c.getColumnIndex(ContactContract.ContactEntry.COLUMN_NAME)));
            phone.setText(c.getString(c.getColumnIndex(ContactContract.ContactEntry.COLUMN_PHONE)));
            email.setText(c.getString(c.getColumnIndex(ContactContract.ContactEntry.COLUMN_EMAIL)));
            address.setText(c.getString(c.getColumnIndex(ContactContract.ContactEntry.COLUMN_ADDRESS)));
            notes.setText(c.getString(c.getColumnIndex(ContactContract.ContactEntry.COLUMN_NOTES)));
            deleteButton.setVisibility(View.VISIBLE);
        }

        // saveButton click listener
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentValues values = new ContentValues();
                values.put(ContactContract.ContactEntry.COLUMN_NAME, name.getText().toString());
                values.put(ContactContract.ContactEntry.COLUMN_PHONE, phone.getText().toString());
                values.put(ContactContract.ContactEntry.COLUMN_EMAIL, email.getText().toString());
                values.put(ContactContract.ContactEntry.COLUMN_ADDRESS, address.getText().toString());
                values.put(ContactContract.ContactEntry.COLUMN_NOTES, notes.getText().toString());
                if (contactId != null) {
                    contactDbHelper.updateContact(contactId, values);
                } else {
                    contactDbHelper.insertContact(values);
                }
                finish();
            }
        });
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contactDbHelper.deleteContact(contactId);
                finish();
            }
        });
    }
}
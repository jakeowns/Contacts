package com.example.jake.contacts;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // call create contact activity
                Intent intent = new Intent(MainActivity.this, CreateContactActivity.class);
                startActivity(intent);
            }
        });
        loadContacts();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadContacts();
    }

    private void loadContacts() {
        // populate listview from db
        ListView listview = (ListView) findViewById(R.id.list_item);
        final ContactDbHelper contactDbHelper = new ContactDbHelper(this);
        Cursor c = contactDbHelper.getAllContacts();
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
                R.layout.contact_listview,
                c,
                new String[]{
                        ContactContract.ContactEntry.COLUMN_NAME,
                        ContactContract.ContactEntry.COLUMN_PHONE
                },
                new int[]{
                        R.id.contact_listview_name,
                        R.id.contact_listview_phone
                },
                0);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, CreateContactActivity.class);
                intent.putExtra(ContactContract.ContactEntry._ID, String.valueOf(id));
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

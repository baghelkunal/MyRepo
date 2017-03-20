package com.example.login.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.login.R;
import com.example.login.adapters.UsersRecyclerAdapter;
import com.example.login.model.User;
import com.example.login.sql.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kunal on 20-Mar-17.
 */

public class UsersListActivity extends AppCompatActivity {

    private AppCompatActivity activity = UsersListActivity.this;
    private AppCompatTextView textViewName;
    private RecyclerView recyclerViewUsers;
    private List<User> listUsers;
    private UsersRecyclerAdapter usersRecyclerAdapter;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_list);
        getSupportActionBar().setTitle("");
        initViews();
        initObjects();

    }



    /**
     * This method is to initialize objects to be used
     */

    private void initObjects() {
        listUsers = new ArrayList<>();
        usersRecyclerAdapter = new UsersRecyclerAdapter(listUsers);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewUsers.setLayoutManager(layoutManager);
        recyclerViewUsers.setItemAnimator(new DefaultItemAnimator());
        recyclerViewUsers.setHasFixedSize(true);
        recyclerViewUsers.setAdapter(usersRecyclerAdapter);
        dbHelper = new DatabaseHelper(activity);

        String emailFromIntent = getIntent().getStringExtra("EMAIL");  // same as get attribute and set attribute
        textViewName.setText(emailFromIntent);

        getDataFromSQLite();


    }


    /**
     * This method is to initialize views
     */
    private void initViews() {
        textViewName = (AppCompatTextView) findViewById(R.id.textViewName);
        recyclerViewUsers = (RecyclerView) findViewById(R.id.recyclerViewUsers);
    }


    public void getDataFromSQLite() {
        // AsyncTask is used that SQLite operation not blocks the UI Thread.
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                listUsers.clear();
                listUsers.addAll(dbHelper.getAllUser());

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                usersRecyclerAdapter.notifyDataSetChanged();
            }
        }.execute();

    }
}

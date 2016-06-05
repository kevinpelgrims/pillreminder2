package com.kevinpelgrims.pillreminder2.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kevinpelgrims.pillreminder2.R;
import com.kevinpelgrims.pillreminder2.models.Reminder;
import com.kevinpelgrims.pillreminder2.views.adapters.ReminderAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.reminders_list) RecyclerView remindersList;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference database;

    private List<Reminder> reminders = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        firebaseAuth = FirebaseAuth.getInstance();

        if (checkUser()) {
            database = FirebaseDatabase.getInstance().getReference();
            initRemindersList();
        }
    }

    @OnClick(R.id.reminders_add)
    public void addReminder() {
        startActivity(new Intent(MainActivity.this, ReminderActivity.class));
    }

    private boolean checkUser() {
        if (firebaseAuth.getCurrentUser() == null) {
            //TODO: startForResult and deal with successful sign in
            startActivity(new Intent(this, SignInActivity.class));
            return false;
        }
        else {
            return true;
        }
    }

    private void initRemindersList() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        remindersList.setLayoutManager(layoutManager);

        RecyclerView.Adapter adapter = new ReminderAdapter(reminders);
        remindersList.setAdapter(adapter);

        loadReminders();
    }

    private void loadReminders() {
        final String userId = firebaseAuth.getCurrentUser().getUid();
        database.child("users/" + userId + "/reminders/").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Reminder> reminders = new ArrayList<Reminder>();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    reminders.add(snapshot.getValue(Reminder.class));
                }

                addDataToList(reminders);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //TODO
            }
        });
    }

    private void addDataToList(List<Reminder> reminders) {
        this.reminders.addAll(reminders);
        remindersList.getAdapter().notifyDataSetChanged();
    }
}

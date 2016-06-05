package com.kevinpelgrims.pillreminder2.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kevinpelgrims.pillreminder2.R;
import com.kevinpelgrims.pillreminder2.models.Reminder;

import java.util.ArrayList;
import java.util.List;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        firebaseAuth = FirebaseAuth.getInstance();

        if (checkUser()) {
            database = FirebaseDatabase.getInstance().getReference();
            loadReminders();
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
    }
}

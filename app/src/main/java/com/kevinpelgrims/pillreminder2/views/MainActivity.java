package com.kevinpelgrims.pillreminder2.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.kevinpelgrims.pillreminder2.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        checkUser();
    }

    @OnClick(R.id.reminders_add)
    public void addReminder() {
        startActivity(new Intent(MainActivity.this, ReminderActivity.class));
    }

    private void checkUser() {
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            startActivity(new Intent(this, SignInActivity.class));
        }
    }
}

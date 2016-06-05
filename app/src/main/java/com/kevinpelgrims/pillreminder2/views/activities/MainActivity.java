package com.kevinpelgrims.pillreminder2.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.kevinpelgrims.pillreminder2.PillReminderApplication;
import com.kevinpelgrims.pillreminder2.R;
import com.kevinpelgrims.pillreminder2.models.Reminder;
import com.kevinpelgrims.pillreminder2.repositories.RemindersRepository;
import com.kevinpelgrims.pillreminder2.repositories.RepositoryCallback;
import com.kevinpelgrims.pillreminder2.repositories.UsersRepository;
import com.kevinpelgrims.pillreminder2.views.adapters.ReminderAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_SIGN_IN = 100;

    @Inject UsersRepository usersRepository;
    @Inject RemindersRepository remindersRepository;

    @BindView(R.id.reminders_list) RecyclerView remindersList;

    private List<Reminder> reminders = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        PillReminderApplication.getComponent(this).inject(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        if (checkUser()) {
            initRemindersList();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_SIGN_IN && resultCode == RESULT_OK) {
            initRemindersList();
        }
    }

    @OnClick(R.id.reminders_add)
    public void addReminder() {
        startActivity(new Intent(MainActivity.this, ReminderActivity.class));
    }

    private boolean checkUser() {
        if (!usersRepository.isUserSignedIn()) {
            startActivityForResult(new Intent(this, SignInActivity.class), REQUEST_CODE_SIGN_IN);
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
        final String userId = usersRepository.getCurrentUserId();
        remindersRepository.getReminders(userId, new RepositoryCallback<List<Reminder>>() {
            @Override
            public void success(List<Reminder> reminders) {
                addDataToList(reminders);
            }

            @Override
            public void failure(Exception error) {
                //TODO: Failure
            }
        });
    }

    private void addDataToList(List<Reminder> reminders) {
        this.reminders.addAll(reminders);
        remindersList.getAdapter().notifyDataSetChanged();
    }
}

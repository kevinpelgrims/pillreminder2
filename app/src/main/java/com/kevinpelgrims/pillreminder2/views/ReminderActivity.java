package com.kevinpelgrims.pillreminder2.views;

import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kevinpelgrims.pillreminder2.R;
import com.kevinpelgrims.pillreminder2.models.Reminder;

import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ReminderActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {
    private static final String ARG_HOUR = "hour";
    private static final String ARG_MINUTE = "minute";

    @BindView(R.id.add_reminder_alarm_time) TextView alarmTimeText;
    @BindView(R.id.add_reminder_pill_name) TextView pillNameText;
    @BindView(R.id.add_reminder_note) TextView noteText;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference database;

    private int selectedHour;
    private int selectedMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);
        ButterKnife.bind(this);

        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference().child("reminders");

        if (savedInstanceState != null
                && savedInstanceState.containsKey(ARG_HOUR)
                && savedInstanceState.containsKey(ARG_MINUTE)) {
            selectedHour = savedInstanceState.getInt(ARG_HOUR);
            selectedMinute = savedInstanceState.getInt(ARG_MINUTE);
        }
        else {
            selectedHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
            selectedMinute = 0;
            showTimePickerDialog();
        }

        updateAlarmTimeText();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(ARG_HOUR, selectedHour);
        outState.putInt(ARG_MINUTE, selectedMinute);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        selectedHour = hourOfDay;
        selectedMinute = minute;
        updateAlarmTimeText();
    }

    @OnClick(R.id.add_reminder_alarm_time)
    void changeAlarmTime() {
        showTimePickerDialog();
    }

    @OnClick(R.id.add_reminder_save)
    void saveReminder() {
        final String userId = firebaseAuth.getCurrentUser().getUid();
        final Reminder reminder = new Reminder(userId, selectedHour, selectedMinute, pillNameText.getText().toString(), noteText.getText().toString());

        database.push().setValue(reminder, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError == null) {
                    // Success!
                    finish();
                }
            }
        });
    }

    private void showTimePickerDialog() {
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, this, selectedHour, selectedMinute, true);
        timePickerDialog.show();
    }

    private void updateAlarmTimeText() {
        alarmTimeText.setText(String.format(Locale.getDefault(), "%02d:%02d", selectedHour, selectedMinute));
    }
}
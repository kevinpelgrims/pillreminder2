package com.kevinpelgrims.pillreminder2.views;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.TimePicker;

import com.kevinpelgrims.pillreminder2.PillReminderApplication;
import com.kevinpelgrims.pillreminder2.R;
import com.kevinpelgrims.pillreminder2.helpers.FormattingHelper;
import com.kevinpelgrims.pillreminder2.models.Reminder;
import com.kevinpelgrims.pillreminder2.repositories.RemindersRepository;
import com.kevinpelgrims.pillreminder2.repositories.RepositoryCallback;
import com.kevinpelgrims.pillreminder2.repositories.UsersRepository;

import java.util.Calendar;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ReminderActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {
    private static final String ARG_HOUR = "hour";
    private static final String ARG_MINUTE = "minute";

    @Inject UsersRepository usersRepository;
    @Inject RemindersRepository remindersRepository;

    @BindView(R.id.add_reminder_alarm_time) TextView alarmTimeText;
    @BindView(R.id.add_reminder_pill_name) TextView pillNameText;
    @BindView(R.id.add_reminder_note) TextView noteText;

    private int selectedHour;
    private int selectedMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        PillReminderApplication.getComponent(this).inject(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);
        ButterKnife.bind(this);

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
        final String userId = usersRepository.getCurrentUserId();
        final Reminder reminder = new Reminder(userId, selectedHour, selectedMinute, pillNameText.getText().toString(), noteText.getText().toString());

        remindersRepository.saveReminder(reminder, userId, new RepositoryCallback<Reminder>() {
            @Override
            public void success(Reminder reminder) {
                finish();
            }

            @Override
            public void failure(Exception error) {
                //TODO: Failed to save reminder
            }
        });
    }

    private void showTimePickerDialog() {
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, this, selectedHour, selectedMinute, true);
        timePickerDialog.show();
    }

    private void updateAlarmTimeText() {
        alarmTimeText.setText(FormattingHelper.formatTime(selectedHour, selectedMinute));
    }
}

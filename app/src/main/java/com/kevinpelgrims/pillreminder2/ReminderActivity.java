package com.kevinpelgrims.pillreminder2;

import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ReminderActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {
    @BindView(R.id.add_reminder_alarm_time) TextView alarmTimeText;

    private int selectedHour;
    private int selectedMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);
        ButterKnife.bind(this);

        selectedHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        selectedMinute = 0;
        updateAlarmTimeText();
        showTimePickerDialog();
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        selectedHour = hourOfDay;
        selectedMinute = minute;
        updateAlarmTimeText();
    }

    @OnClick(R.id.add_reminder_alarm_time)
    public void changeAlarmTime() {
        showTimePickerDialog();
    }

    private void showTimePickerDialog() {
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, this, selectedHour, selectedMinute, true);
        timePickerDialog.show();
    }

    private void updateAlarmTimeText() {
        alarmTimeText.setText(String.format(Locale.getDefault(), "%02d:%02d", selectedHour, selectedMinute));
    }
}

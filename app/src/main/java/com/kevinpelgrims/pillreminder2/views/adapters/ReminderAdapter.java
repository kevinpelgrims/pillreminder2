package com.kevinpelgrims.pillreminder2.views.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kevinpelgrims.pillreminder2.R;
import com.kevinpelgrims.pillreminder2.helpers.FormattingHelper;
import com.kevinpelgrims.pillreminder2.models.Reminder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReminderAdapter extends RecyclerView.Adapter<ReminderAdapter.ViewHolder> {
    private List<Reminder> reminders;

    public ReminderAdapter(List<Reminder> reminders) {
        this.reminders = reminders != null ? reminders : new ArrayList<Reminder>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_reminder, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Reminder reminder = reminders.get(position);

        holder.name.setText(reminder.getName());
        holder.time.setText(FormattingHelper.formatTime(reminder.getHour(), reminder.getMinute()));
        holder.note.setText(reminder.getNote());
    }

    @Override
    public int getItemCount() {
        return reminders.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.reminder_name) TextView name;
        @BindView(R.id.reminder_time) TextView time;
        @BindView(R.id.reminder_note) TextView note;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

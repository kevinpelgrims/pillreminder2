package com.kevinpelgrims.pillreminder2.repositories;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kevinpelgrims.pillreminder2.models.Reminder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RemindersRepository {
    private DatabaseReference database;

    public RemindersRepository() {
        database = FirebaseDatabase.getInstance().getReference();
    }

    public void getReminders(String userId, final RepositoryCallback<List<Reminder>> callback) {
        database.child("users/" + userId + "/reminders/").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Reminder> reminders = new ArrayList<Reminder>();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    reminders.add(snapshot.getValue(Reminder.class));
                }

                callback.success(reminders);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.failure(databaseError.toException());
            }
        });
    }

    public void saveReminder(final Reminder reminder, String userId, final RepositoryCallback<Reminder> callback) {
        String key = database.child("reminders").push().getKey();
        Map<String, Object> reminderValues = reminder.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/reminders/" + key, reminderValues);
        childUpdates.put("/users/" + userId + "/reminders/" + key, reminderValues);

        database.updateChildren(childUpdates, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError == null) {
                    callback.success(reminder);
                }
                else {
                    callback.failure(databaseError.toException());
                }
            }
        });
    }
}

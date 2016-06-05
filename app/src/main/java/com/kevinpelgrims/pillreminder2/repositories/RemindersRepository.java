package com.kevinpelgrims.pillreminder2.repositories;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kevinpelgrims.pillreminder2.models.Reminder;

import java.util.HashMap;
import java.util.Map;

public class RemindersRepository {
    private DatabaseReference database;

    public RemindersRepository() {
        database = FirebaseDatabase.getInstance().getReference();
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

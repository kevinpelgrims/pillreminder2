package com.kevinpelgrims.pillreminder2.repositories;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kevinpelgrims.pillreminder2.models.User;

public class UsersRepository {
    private FirebaseAuth firebaseAuth;
    private DatabaseReference usersDatabase;

    public UsersRepository() {
        firebaseAuth = FirebaseAuth.getInstance();
        usersDatabase = FirebaseDatabase.getInstance().getReference().child("users");
    }

    public void signInWithGoogle(String token, final RepositoryCallback<Void> callback) {
        AuthCredential credential = GoogleAuthProvider.getCredential(token, null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        final FirebaseUser currentUser = firebaseAuth.getCurrentUser();

                        if (task.isSuccessful() && currentUser != null) {
                            // Sign in succeeded, store user info in the database (asynchronously)
                            String photoUrl = currentUser.getPhotoUrl() != null ? currentUser.getPhotoUrl().toString() : null;
                            User user = new User(currentUser.getEmail(), currentUser.getDisplayName(), photoUrl);
                            usersDatabase.child(currentUser.getUid()).setValue(user);

                            callback.success(null);
                        }
                        else {
                            // Sign in failed
                            callback.failure(task.getException());
                        }
                    }
                });
    }

    public boolean isUserSignedIn() {
        return firebaseAuth != null;
    }

    public String getCurrentUserId() {
        return firebaseAuth.getCurrentUser() != null ? firebaseAuth.getCurrentUser().getUid() : null;
    }
}

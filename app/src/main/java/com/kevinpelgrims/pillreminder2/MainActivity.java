package com.kevinpelgrims.pillreminder2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkUser();
    }

    private void checkUser() {
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            startActivity(new Intent(this, SignInActivity.class));
        }
    }
}

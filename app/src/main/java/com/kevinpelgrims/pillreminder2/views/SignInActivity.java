package com.kevinpelgrims.pillreminder2.views;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.kevinpelgrims.pillreminder2.PillReminderApplication;
import com.kevinpelgrims.pillreminder2.R;
import com.kevinpelgrims.pillreminder2.repositories.RepositoryCallback;
import com.kevinpelgrims.pillreminder2.repositories.UsersRepository;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignInActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_SIGN_IN = 100;

    @Inject UsersRepository usersRepository;

    private GoogleApiClient googleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        PillReminderApplication.getComponent(this).inject(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        ButterKnife.bind(this);

        setUpGoogleApiClient();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                final GoogleSignInAccount account = result.getSignInAccount();
                signInWithGoogleAccount(account);
            }
            else {
                //TODO
            }
        }
    }

    @OnClick(R.id.sign_in_button)
    void signInWithGoogle() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(signInIntent, REQUEST_CODE_SIGN_IN);
    }

    private void setUpGoogleApiClient() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        //TODO
                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    private void signInWithGoogleAccount(GoogleSignInAccount account) {
        usersRepository.signInWithGoogle(account.getIdToken(), new RepositoryCallback<Void>() {
            @Override
            public void success(Void result) {
                finish();
            }

            @Override
            public void failure(Exception error) {
                //TODO: Sign in failed
            }
        });
    }
}

package com.kevinpelgrims.pillreminder2.di;

import com.kevinpelgrims.pillreminder2.views.MainActivity;
import com.kevinpelgrims.pillreminder2.views.ReminderActivity;
import com.kevinpelgrims.pillreminder2.views.SignInActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = { RepositoriesModule.class })
public interface AppComponent {
    void inject(MainActivity activity);
    void inject(ReminderActivity activity);
    void inject(SignInActivity activity);
}

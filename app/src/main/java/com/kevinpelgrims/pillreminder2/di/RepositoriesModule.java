package com.kevinpelgrims.pillreminder2.di;

import com.kevinpelgrims.pillreminder2.repositories.RemindersRepository;
import com.kevinpelgrims.pillreminder2.repositories.UsersRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RepositoriesModule {
    @Provides @Singleton
    UsersRepository provideUsersRepository() {
        return new UsersRepository();
    }

    @Provides @Singleton
    RemindersRepository provideRemindersRepository() {
        return new RemindersRepository();
    }
}

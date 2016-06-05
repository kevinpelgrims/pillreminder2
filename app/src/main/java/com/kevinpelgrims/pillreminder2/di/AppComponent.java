package com.kevinpelgrims.pillreminder2.di;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = { RepositoriesModule.class })
public interface AppComponent {
}

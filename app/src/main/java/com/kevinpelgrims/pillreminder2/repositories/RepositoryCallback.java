package com.kevinpelgrims.pillreminder2.repositories;

public interface RepositoryCallback<T> {
    void success(T t);
    void failure(Exception error);
}

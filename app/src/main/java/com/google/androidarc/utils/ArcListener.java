package com.google.androidarc.utils;

public interface ArcListener<T> {
    void complete(T t);

    void onError(Throwable e);
}
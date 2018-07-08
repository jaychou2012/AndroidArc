package com.google.androidarc.utils;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class ArcObserver<T> implements Observer<T> {
    private ArcListener<T> arcListener;

    public ArcObserver(ArcListener<T> arcListener) {
        this.arcListener = arcListener;
    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(T t) {
        arcListener.complete(t);
    }

    @Override
    public void onError(Throwable e) {
        arcListener.onError(e);
    }

    @Override
    public void onComplete() {

    }
}

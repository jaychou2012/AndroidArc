package com.google.androidarc.utils;

import android.content.Context;
import android.widget.Toast;

import com.google.androidarc.database.AppDatabase;
import com.google.androidarc.database.User;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ArcUtils {

    private static <T> void subscribeOnobserveOn(Observable observable, Observer<T> observer) {
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(observer);
    }

    public static void getAll(final Context context, ArcObserver<List<User>> arcObserver) {
        Observable observable = Observable.create(new ObservableOnSubscribe<List<User>>() {

            @Override
            public void subscribe(ObservableEmitter<List<User>> emitter) throws Exception {
                emitter.onNext(AppDatabase.getInstance(context).userDao().getAll());
            }
        });
        subscribeOnobserveOn(observable, arcObserver);
    }

    public static void insertAll(final Context context, final User user, ArcObserver<User> arcObserver) {
        Observable observable = Observable.create(new ObservableOnSubscribe<User>() {

            @Override
            public void subscribe(ObservableEmitter<User> emitter) throws Exception {
                AppDatabase.getInstance(context).userDao().insertAll(user);
            }
        });
        subscribeOnobserveOn(observable, arcObserver);
    }

    public static void arcToast(final Context context, final String text) {
        Observable.create(new ObservableOnSubscribe<String>() {

            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {

            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
            }
        });
    }

}

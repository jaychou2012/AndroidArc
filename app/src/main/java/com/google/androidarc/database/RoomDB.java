package com.google.androidarc.database;

import android.arch.persistence.room.Room;
import android.content.Context;

public class RoomDB {

    public static AppDatabase init(Context context, String dbName) {
        AppDatabase db = Room.databaseBuilder(context,
                AppDatabase.class, dbName).build();
        return db;
    }
}

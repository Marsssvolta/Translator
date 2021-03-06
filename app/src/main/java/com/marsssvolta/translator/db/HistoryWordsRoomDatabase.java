package com.marsssvolta.translator.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.marsssvolta.translator.model.HistoryWords;

@Database(entities = {HistoryWords.class}, version = 1)
public abstract class HistoryWordsRoomDatabase extends RoomDatabase {

    public abstract HistoryWordsDao historyWordsDao();

    private static volatile HistoryWordsRoomDatabase INSTANCE;

    public static HistoryWordsRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (HistoryWordsRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            HistoryWordsRoomDatabase.class, "history_database")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}

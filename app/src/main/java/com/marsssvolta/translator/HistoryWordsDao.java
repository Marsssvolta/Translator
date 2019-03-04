package com.marsssvolta.translator;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface HistoryWordsDao {

    @Query("SELECT * FROM history_table")
    LiveData<List<HistoryWords>> getHistoryWords();

    @Insert
    void insert(HistoryWords historyWords);

    @Query("DELETE FROM history_table")
    void deleteAll();
}

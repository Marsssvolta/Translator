package com.marsssvolta.translator.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "history_table")
public class HistoryWords {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    @ColumnInfo(name = "source")
    private String mSource;

    @NonNull
    @ColumnInfo(name = "translated")
    private String mTranslated;

    public HistoryWords(@NonNull String source, @NonNull String translated) {
        this.mSource = source;
        this.mTranslated = translated;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getSource() {
        return this.mSource;
    }

    @NonNull
    public String getTranslated() {
        return this.mTranslated;
    }
}

package pl.dzielins42.illusivebaboon.android.data.db.dao;


import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Update;

interface BasicDao<T> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(T item);

    @Delete
    void delete(T... items);

    @Update
    void update(T... items);
}

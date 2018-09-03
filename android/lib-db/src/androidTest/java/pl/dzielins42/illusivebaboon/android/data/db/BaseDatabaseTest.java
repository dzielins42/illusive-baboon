package pl.dzielins42.illusivebaboon.android.data.db;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;

import java.io.IOException;

@RunWith(AndroidJUnit4.class)
public abstract class BaseDatabaseTest {
    protected AppDatabase mDb;

    @Before
    public void setUp() {
        Context context = InstrumentationRegistry.getTargetContext();
        mDb = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
    }

    @After
    public void tearDown() throws IOException {
        mDb.close();
    }
}
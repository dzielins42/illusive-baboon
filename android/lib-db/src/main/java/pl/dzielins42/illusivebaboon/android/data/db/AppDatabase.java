package pl.dzielins42.illusivebaboon.android.data.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import pl.dzielins42.illusivebaboon.android.data.db.dao.NodeDao;
import pl.dzielins42.illusivebaboon.android.data.db.dao.HistoryValueDao;
import pl.dzielins42.illusivebaboon.android.data.db.dao.ReferenceDao;
import pl.dzielins42.illusivebaboon.android.data.db.entity.Node;
import pl.dzielins42.illusivebaboon.android.data.db.entity.HistoryValue;
import pl.dzielins42.illusivebaboon.android.data.db.entity.Reference;

@Database(version = 1, entities = {
        Node.class,
        Reference.class,
        HistoryValue.class
})
public abstract class AppDatabase extends RoomDatabase {
    public abstract NodeDao nodeDao();

    public abstract ReferenceDao referenceDao();

    public abstract HistoryValueDao historyValueDao();
}

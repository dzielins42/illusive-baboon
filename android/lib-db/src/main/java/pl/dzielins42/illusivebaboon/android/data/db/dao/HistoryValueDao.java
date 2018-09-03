package pl.dzielins42.illusivebaboon.android.data.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;
import android.support.annotation.NonNull;

import java.util.List;

import pl.dzielins42.illusivebaboon.android.data.db.entity.HistoryValue;

@Dao
public interface HistoryValueDao extends BasicDao<HistoryValue> {
    @Query("SELECT * FROM history_values")
    List<HistoryValue> all();
    @Query("SELECT * FROM history_values WHERE EXISTS (SELECT * FROM hierarchy_references WHERE history_values.ref_id = hierarchy_references.id AND hierarchy_references.parent_id = :parentNodeId)")
    List<HistoryValue> byParentNodeId(@NonNull String parentNodeId);
}

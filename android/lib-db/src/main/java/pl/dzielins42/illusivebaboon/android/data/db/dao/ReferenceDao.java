package pl.dzielins42.illusivebaboon.android.data.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;
import android.support.annotation.NonNull;

import java.util.List;

import pl.dzielins42.illusivebaboon.android.data.db.entity.Reference;

@Dao
public interface ReferenceDao extends BasicDao<Reference> {
    @Query("SELECT * FROM hierarchy_references")
    List<Reference> all();

    @Query("SELECT * FROM hierarchy_references WHERE parent_id = :parentId")
    List<Reference> byParentNodeId(@NonNull String parentId);
}

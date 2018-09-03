package pl.dzielins42.illusivebaboon.android.data.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;
import android.support.annotation.NonNull;

import java.util.List;

import pl.dzielins42.illusivebaboon.android.data.db.entity.Node;

@Dao
public interface NodeDao extends BasicDao<Node> {
    @Query("SELECT * FROM hierarchy_nodes")
    List<Node> all();
    @Query("SELECT * FROM hierarchy_nodes WHERE parent_id = :parentId")
    List<Node> byParentId(@NonNull String parentId);
}

package pl.dzielins42.illusivebaboon.android.data.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Entity(tableName = "hierarchy_references", foreignKeys = {
        @ForeignKey(entity = Node.class, parentColumns = "id", childColumns = "parent_id", onDelete = ForeignKey.CASCADE)
})
public class Reference {

    @PrimaryKey
    @ColumnInfo(name = "id")
    @NonNull
    public String id;
    @ColumnInfo(name = "name")
    @NonNull
    public String name;
    @ColumnInfo(name = "parent_id")
    @NonNull
    public String parentId;
}

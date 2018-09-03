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
@Entity(tableName = "history_values", foreignKeys = {
        @ForeignKey(entity = Reference.class, parentColumns = "id", childColumns = "ref_id", onDelete = ForeignKey.CASCADE)
})
public class HistoryValue {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public long id;
    @ColumnInfo(name = "value")
    @NonNull
    public String value;
    @ColumnInfo(name = "ref_id")
    @NonNull
    public String referenceId;
    @ColumnInfo(name = "timestamp")
    public long timestamp;
}

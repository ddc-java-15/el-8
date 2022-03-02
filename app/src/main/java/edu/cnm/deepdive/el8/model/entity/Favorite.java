package edu.cnm.deepdive.el8.model.entity;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "favorite", foreignKeys = @ForeignKey(entity = User.class, parentColumns = "user_id", childColumns = "favorite", onDelete = ForeignKey.CASCADE))
public class Favorite {

  @NonNull
  @PrimaryKey(autoGenerate = true)
  @ColumnInfo(name = "favorite_id")
  @Ignore
  private long id;

  @NonNull
  @Ignore
  private User user;

  public long getId() {
    return id;
  }

  @NonNull
  public User getUser() {
    return user;
  }

  public void setUser(@NonNull User user) {
    this.user = user;
  }
}

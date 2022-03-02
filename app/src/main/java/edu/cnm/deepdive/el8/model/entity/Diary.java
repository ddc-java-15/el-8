package edu.cnm.deepdive.el8.model.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "diary", foreignKeys = @ForeignKey(entity= User.class, parentColumns ="user_id", childColumns = "diary",onDelete = ForeignKey.CASCADE))
public class Diary {


  @NonNull
  @PrimaryKey(autoGenerate = true)
  @ColumnInfo(name = "diary_id")
  @Ignore
  private long id;

  @NonNull
  private String entry;

  @NonNull
  @Ignore
  private User user;

  public long getId() {
    return id;
  }

  @NonNull
  public String getEntry() {
    return entry;
  }

  public void setEntry(@NonNull String entry) {
    this.entry = entry;
  }

  @NonNull
  public User getUser() {
    return user;
  }

  public void setUser(@NonNull User user) {
    this.user = user;
  }
}

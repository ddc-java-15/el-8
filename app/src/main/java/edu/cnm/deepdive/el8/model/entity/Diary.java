package edu.cnm.deepdive.el8.model.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import java.util.Date;

@Entity(
    tableName = "diary",
    foreignKeys = @ForeignKey(
        entity= User.class,
        parentColumns ="user_id", childColumns = "user_id",
        onDelete = ForeignKey.CASCADE))


public class Diary {

  /**
   * Encapsulates the property of the {@link Diary} entity class.
   */

  @PrimaryKey(autoGenerate = true)
  @ColumnInfo(name = "diary_id")
  private long id;

  @NonNull
  private Date created = new Date();

  @NonNull
  private String entry= "";

  @ColumnInfo(name= "user_id",index = true)
  private long userId;


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  @NonNull
  public Date getCreated() {
    return created;
  }

  public void setCreated(@NonNull Date created) {
    this.created = created;
  }

  @NonNull
  public String getEntry() {
    return entry;
  }

  public void setEntry(@NonNull String entry) {
    this.entry = entry;
  }

  public long getUserId() {
    return userId;
  }

  public void setUserId(long userId) {
    this.userId = userId;
  }
}

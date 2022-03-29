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

  /**
   * Returns the id of the specified {@link Diary}
   * @return
   */
  public long getId() {
    return id;
  }
  /**
   * Sets the id of the specified {@link Diary}
   * @param id
   */
  public void setId(long id) {
    this.id = id;
  }
  /**
   * Returns the date of creation of the specified {@link Diary}
   * @return
   */
  @NonNull
  public Date getCreated() {
    return created;
  }
  /**
   * Set the date of creation of the specified {@link Diary}
   * @param created
   */
  public void setCreated(@NonNull Date created) {
    this.created = created;
  }

  /**
   * Retrieves an {@code entry} specific to {@link Diary}
   * @return
   */
  @NonNull
  public String getEntry() {
    return entry;
  }

  /**
   * Set an {@code entry} specific to {@link Diary}
   * @param entry
   */
  public void setEntry(@NonNull String entry) {
    this.entry = entry;
  }
  /**
   * Retrieves the id of the {@link User} specific to {@link Diary}
   * @return
   */
  public long getUserId() {
    return userId;
  }
  /**
   * Sets the  id of the {@link User} specific to {@link Diary}
   * @param userId
   */
  public void setUserId(long userId) {
    this.userId = userId;
  }
}

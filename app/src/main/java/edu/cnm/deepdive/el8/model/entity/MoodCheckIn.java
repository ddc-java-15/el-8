package edu.cnm.deepdive.el8.model.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import java.util.Date;

@Entity(
    tableName = "mood_check_in",
    foreignKeys = @ForeignKey(
        entity = User.class,
        parentColumns = "user_id", childColumns = "user_id",
        onDelete = ForeignKey.CASCADE)
)

/**
 * Encapsulates the property of the {@link MoodCheckIn} entity class.
 */


public class MoodCheckIn {


  @PrimaryKey(autoGenerate = true)
  @ColumnInfo(name = "mood_check_in_id")
  private long id;

  @NonNull
  private Date created = new Date();

  private int rating;

  @ColumnInfo(name = "user_id",index = true)
  private long userId;

  /**
   * Returns the id of the specified {@link MoodCheckIn}
   * @return
   */
  public long getId() {
    return id;
  }
  /**
   * Sets the id of the specified {@link MoodCheckIn}
   * @param id
   */
  public void setId(long id) {
    this.id = id;
  }

  /**
   * Returns the date of creation of the specified {@link MoodCheckIn}
   * @return
   */
  @NonNull
  public Date getCreated() {
    return created;
  }

  /**
   * Set the date of creation of the specified {@link MoodCheckIn}
   * @param created
   */
  public void setCreated(@NonNull Date created) {
    this.created = created;
  }

  /**
   * Retrieves the rating of the specified {@link MoodCheckIn}
   * @return
   */
  public int getRating() {
    return rating;
  }

  /**
   * Retrieves the rating of the specified {@link MoodCheckIn}
   * @param rating
   */
  public void setRating(int rating) {
    this.rating = rating;
  }

  /**
   * Retrieves the  id of the {@link User} specific to {@link MoodCheckIn}
   * @return
   */
  public long getUserId() {
    return userId;
  }

  /**
   * Sets the  id of the {@link User} specific to {@link MoodCheckIn}
   * @param userId
   */
  public void setUserId(long userId) {
    this.userId = userId;
  }


}

package edu.cnm.deepdive.el8.model.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "mood_check_in",foreignKeys = @ForeignKey(entity = User.class, parentColumns = "user_id", childColumns = "mood_check_in",onDelete = ForeignKey.CASCADE))
public class MoodCheckIn {

  @NonNull
  @PrimaryKey(autoGenerate = true)
  @ColumnInfo(name = "mood_check_in_id")
  @Ignore
  private long id;

  @NonNull
  private int rating;

  @NonNull
  private String progressReport;

  @NonNull
  @Ignore
  private User user;

  public long getId() {
    return id;
  }

  public int getRating() {
    return rating;
  }

  public void setRating(int rating) {
    this.rating = rating;
  }

  @NonNull
  public String getProgressReport() {
    return progressReport;
  }

  public void setProgressReport(@NonNull String progressReport) {
    this.progressReport = progressReport;
  }

  @NonNull
  public User getUser() {
    return user;
  }

  public void setUser(@NonNull User user) {
    this.user = user;
  }
}

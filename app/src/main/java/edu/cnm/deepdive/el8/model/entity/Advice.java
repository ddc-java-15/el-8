package edu.cnm.deepdive.el8.model.entity;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;



@Entity(tableName = "advice", foreignKeys = @ForeignKey (entity = User.class , parentColumns = "user_id", childColumns = "advice",onDelete = ForeignKey.CASCADE))
public class Advice {

  @NonNull
  @PrimaryKey(autoGenerate = true)
  @ColumnInfo(name = "advice_id")
  @Ignore
  private long id;

  private String action;

  private String image;


  @NonNull
  @Ignore
  private User user;


  public long getId() {
    return id;
  }

  public String getAction() {
    return action;
  }

  public void setAction(String action) {
    this.action = action;
  }

  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }

  @NonNull
  public User getUser() {
    return user;
  }

  public void setUser(@NonNull User user) {
    this.user = user;
  }
}

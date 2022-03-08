package edu.cnm.deepdive.el8.model.entity;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;



@Entity(
    tableName = "advice",
    foreignKeys = @ForeignKey (
        entity = User.class ,
        parentColumns = "user_id", childColumns = "user_id",
        onDelete = ForeignKey.CASCADE))

public class Advice {

  @PrimaryKey(autoGenerate = true)
  @ColumnInfo(name = "advice_id")
  private long id;

  private String action = "";

  private String image = "";

  @ColumnInfo(index = true)
  private boolean favorite;

  @ColumnInfo(name = "user_id",index = true)
  private long userId;


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
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

  public boolean isFavorite() {
    return favorite;
  }

  public void setFavorite(boolean favorite) {
    this.favorite = favorite;
  }

  public long getUserId() {
    return userId;
  }

  public void setUserId(long userId) {
    this.userId = userId;
  }

}

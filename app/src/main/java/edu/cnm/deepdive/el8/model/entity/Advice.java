package edu.cnm.deepdive.el8.model.entity;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import java.util.Date;


@Entity(
    tableName = "advice",
    foreignKeys = @ForeignKey (
        entity = User.class ,
        parentColumns = "user_id", childColumns = "user_id",
        onDelete = ForeignKey.CASCADE))

/**
 * Encapsulates the property of the {@link Advice} entity class.
 */

public class Advice {

  @PrimaryKey(autoGenerate = true)
  @ColumnInfo(name = "advice_id")
  private long id;

  @NonNull
  @ColumnInfo(index = true)
  private Date created = new Date();

  private String action = "";

  private String image = "";

  @ColumnInfo(index = true)
  private boolean favorite;

  @ColumnInfo(name = "user_id",index = true)
  private long userId;
  /**
   * Returns the id of the specified {@link Advice}
   * @return
   */
  public long getId() {
    return id;
  }
  /**
   * Sets the id of the specified {@link Advice}
   * @param id
   */
  public void setId(long id) {
    this.id = id;
  }
  /**
   * Returns the date of creation of the specified {@link Advice}
   * @return
   */
  @NonNull
  public Date getCreated() {
    return created;
  }
  /**
   * Sets the date of creation of the specified {@link Advice}
   * @param created
   */
  public void setCreated(@NonNull Date created) {
    this.created = created;
  }

  /**
   * Retrieves the {@code action} of the specified {@link Advice}
   * @return
   */
  public String getAction() {
    return action;
  }

  /**
   * Sets the {@code action} of the specified {@link Advice}
   * @param action
   */
  public void setAction(String action) {
    this.action = action;
  }

  /**
   * Retrieves the {@code image} of the specified {@link Advice}
   * @return
   */
  public String getImage() {
    return image;
  }

  /**
   * Sets the {@code action} of the specified {@link Advice}
   * @param image
   */
  public void setImage(String image) {
    this.image = image;
  }

  /**
   * Sets a {@code favorite} specific to {@link Advice}
   * @return
   */
  public boolean isFavorite() {
    return favorite;
  }

  /**
   * Sets a {@code favorite} specific to {@link Advice}
   * @param favorite
   */
  public void setFavorite(boolean favorite) {
    this.favorite = favorite;
  }
  /**
   * Retrieves the  name of the {@link User} specific to {@link Advice}
   * @return
   */
  public long getUserId() {
    return userId;
  }
  /**
   * Sets the  name of the {@link User} specific to {@link Advice}
   * @param userId
   */
  public void setUserId(long userId) {
    this.userId = userId;
  }

}

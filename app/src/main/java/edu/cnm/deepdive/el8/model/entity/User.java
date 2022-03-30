package edu.cnm.deepdive.el8.model.entity;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
    tableName = "user",
    indices = {
        @Index(value = "oauth_key", unique = true)
    }
)
/**
 * Encapsulates the property of the {@link User} entity class.
 */

public class User {

  @PrimaryKey(autoGenerate = true)
  @ColumnInfo(name = "user_id")
  private long id;

  @ColumnInfo(name = "oauth_key")
  private String oauthKey;


  @NonNull
  @ColumnInfo(name = "name",index = true)
  private String name = "";

  private int age;

  @NonNull
  private String location = "";

  /**
   * Returns the id of the specified {@link User}
   * @return
   */
  public long getId() {
    return id;
  }

  /**
   * Sets the id of the specified {@link User}
   * @return
   */
  public void setId(long id) {
    this.id = id;
  }

  /**
   * Returns the Oauth Key of this specified {@link User}
   * @return
   */
  public String getOauthKey() {
    return oauthKey;
  }

  /**
   * Sets the Oauth Key of this specified {@link User}
   * @param oauthKey
   */
  public void setOauthKey(String oauthKey) {
    this.oauthKey = oauthKey;
  }

  /**
   * Retrieves the  name of the specified {@link User}
   * @return
   */
  @NonNull
  public String getName() {
    return name;
  }

  /**
   * Sets the  name of the specified {@link User}
   * @param name
   */
  public void setName(@NonNull String name) {
    this.name = name;
  }
  /**
   * Retrieves the age of the specified {@link User}
   * @return
   */
  public int getAge() {
    return age;
  }
  /**
   * Sets the age of the specified {@link User}
   * @param age
   */
  public void setAge(int age) {
    this.age = age;
  }

  /**
   * Retrieves the location of the specified {@link User}
   * @return
   */
  @NonNull
  public String getLocation() {
    return location;
  }
  /**
   * Sets the location of the specified {@link User}
   * @param location
   */
  public void setLocation(@NonNull String location) {
    this.location = location;
  }
}

package edu.cnm.deepdive.el8.model.entity;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "user")
public class User {

  @PrimaryKey(autoGenerate = true)
  @NonNull
  @ColumnInfo(name = "user_id")
  @Ignore
  private long id;

  @NonNull
  @ColumnInfo(name = "name")
  private String name;

  @NonNull
  private int age;

  @NonNull
  private String location;

  public long getId() {
    return id;
  }

  @NonNull
  public String getName() {
    return name;
  }

  public void setName(@NonNull String name) {
    this.name = name;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }

  @NonNull
  public String getLocation() {
    return location;
  }

  public void setLocation(@NonNull String location) {
    this.location = location;
  }
}

package edu.cnm.deepdive.el8.model.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import edu.cnm.deepdive.el8.model.entity.User;
import io.reactivex.rxjava3.core.Single;
import java.util.Collection;
import java.util.List;

@Dao
public interface UserDao {
  @Insert
  Single<Long> insert(User user);

  @Insert
  Single<List<Long>> insert(User... users);

  @Insert
  Single<List<Long>> insert(Collection<User> users);

  @Update
  Single<Integer> update(User user);

  @Update
  Single<Integer> update(User... users);

  @Update
  Single<Integer> update(Collection<User> users);

  @Delete
  Single<Integer> delete(User user);

  @Delete
  Single<Integer> delete(User... user);

  @Delete
  Single<Integer> delete(Collection<User> user);

  @Query("SELECT * FROM user WHERE user_id = :id")
  LiveData<User> select(long id);

  @Query("SELECT * FROM user ORDER BY name ASC")
  LiveData<List<User>> select();
}

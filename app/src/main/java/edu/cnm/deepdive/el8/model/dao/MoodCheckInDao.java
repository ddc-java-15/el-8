package edu.cnm.deepdive.el8.model.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import edu.cnm.deepdive.el8.model.entity.MoodCheckIn;
import edu.cnm.deepdive.el8.model.entity.User;
import io.reactivex.rxjava3.core.Single;
import java.util.Collection;
import java.util.List;

@Dao
public interface MoodCheckInDao {

  @Insert
  Single<Long> insert(MoodCheckIn moodCheckIn);

  @Insert
  Single<List<Long>> insert(MoodCheckIn... moodCheckIns);

  @Insert
  Single<List<Long>> insert(Collection<MoodCheckIn> moodCheckIns);

  @Update
  Single<Integer> update(MoodCheckIn moodCheckIn);

  @Update
  Single<Integer> update(MoodCheckIn... moodCheckIns);

  @Update
  Single<Integer> update(Collection<MoodCheckIn> moodCheckIns);

  @Delete
  Single<Integer> delete(MoodCheckIn moodCheckIn);

  @Delete
  Single<Integer> delete(MoodCheckIn... moodCheckIns);

  @Delete
  Single<Integer> delete(Collection<MoodCheckIn> moodCheckIns);

  @Query("SELECT * FROM mood_check_in WHERE mood_check_in_id = :id")
  LiveData<MoodCheckIn> select(long id);

  @Query("SELECT * FROM mood_check_in ORDER BY created ASC")
  LiveData<List<MoodCheckIn>> select();

}

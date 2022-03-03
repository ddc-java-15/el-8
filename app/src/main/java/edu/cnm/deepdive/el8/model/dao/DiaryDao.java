package edu.cnm.deepdive.el8.model.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import edu.cnm.deepdive.el8.model.entity.Diary;
import io.reactivex.rxjava3.core.Single;
import java.util.Collection;
import java.util.List;

@Dao
public interface DiaryDao {

  @Insert
  Single<Long> insert(Diary diary);

  @Insert
  Single<List<Long>> insert(Diary... diaries);

  @Insert
  Single<List<Long>> insert(Collection<Diary> diaries);

  @Update
  Single<Integer> update(Diary diary);

  @Update
  Single<Integer> update(Diary... diaries);

  @Update
  Single<Integer> update(Collection<Diary> diaries);

  @Delete
  Single<Integer> delete(Diary diary);

  @Delete
  Single<Integer> delete(Diary... diaries);

  @Delete
  Single<Integer> delete(Collection<Diary> diaries);

  @Query("SELECT * FROM diary WHERE diary_id = :id")
  LiveData<Diary> select(long id);

  @Query("SELECT * FROM diary ORDER BY created ASC")
  LiveData<List<Diary>> select();

}

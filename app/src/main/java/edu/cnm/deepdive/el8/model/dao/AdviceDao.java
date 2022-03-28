package edu.cnm.deepdive.el8.model.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import edu.cnm.deepdive.el8.model.entity.Advice;
import edu.cnm.deepdive.el8.model.entity.Diary;
import io.reactivex.rxjava3.core.Single;
import java.util.Collection;
import java.util.List;

/**
 * Interface  used to access, modify, delete and update  data of the {@link Advice} entity and to abstract
 * the retrieval of data through queries from the database.
 */
@Dao
public interface AdviceDao {



  @Insert
  Single<Long> insert(Advice advice);

  @Insert
  Single<List<Long>> insert(Advice... advices);

  @Insert
  Single<List<Long>> insert(Collection<Advice> advices);

  @Update
  Single<Integer> update(Advice advice);

  @Update
  Single<Integer> update(Advice...  advices);

  @Update
    Single<Integer> update(Collection<Advice> advices);

  @Delete
  Single<Integer> delete(Advice advice);

  @Delete
  Single<Integer> delete(Advice... advices);

  @Delete
  Single<Integer> delete(Collection<Advice> advices);

  @Query("SELECT * FROM advice WHERE advice_id = :id")
  LiveData<Advice> select(long id);

  @Query("SELECT * FROM advice ORDER BY `action` ASC")
  LiveData<List<Advice>> select();

  @Query("SELECT * FROM advice WHERE user_id = :userId ORDER BY `action` ASC")
  LiveData<List<Advice>> selectByUser(long userId);

  @Query("SELECT * FROM advice WHERE user_id = :userId AND favorite ORDER BY `action` ASC")
  LiveData<List<Advice>> selectFavoritesByUser(long userId);

}

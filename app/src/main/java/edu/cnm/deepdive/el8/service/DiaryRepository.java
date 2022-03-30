package edu.cnm.deepdive.el8.service;

import android.content.Context;
import androidx.lifecycle.LiveData;
import edu.cnm.deepdive.el8.model.dao.AdviceDao;
import edu.cnm.deepdive.el8.model.dao.DiaryDao;
import edu.cnm.deepdive.el8.model.entity.Advice;
import edu.cnm.deepdive.el8.model.entity.Diary;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import java.util.Date;
import java.util.List;
import edu.cnm.deepdive.el8.model.entity.User;

/**
 * Provides the core CRUD operations for the {@link Diary} entity.
 */
public class DiaryRepository {

  private final DiaryDao diaryDao;

  private final Context context;

  public DiaryRepository(Context context) {
    this.context = context;

    El8Database database = El8Database.getInstance();
    diaryDao = database.getDiaryDao();
  }

  /**
   * Returns a LiveData instance of {@code id} specific to {@link DiaryDao}
   * @param id
   * @return
   */
  public LiveData<Diary> get(long id) {

    return diaryDao.select(id);

  }

  /**
   * Retrieves a List of all instances of {@link Diary}
   * @return
   */
  public LiveData<List<Diary>> getAll() {

    return diaryDao.select();
  }

  /**
   * Saves an instance of  {@link Advice }
   * @param diary
   * @return
   */
  public Single<Diary> save(Diary diary) {

    return (
        (diary.getId() == 0)
            ? Single.fromCallable(() -> {
              diary.setCreated(new Date());
              return diary;
            })
            .flatMap(diaryDao::insert)
            .map((id) -> {
              diary.setId(id);
              return diary;
            })
            : diaryDao
                .update(diary)
                .map((count) -> diary)
    )
        .subscribeOn(Schedulers.io());

  }

  /**
   * Returns a completable upon completion of deletion of {@link Diary}
   * @param diary
   * @return
   */
  public Completable delete(Diary diary) {

    return (
        (diary.getId() == 0)
            ? Completable.complete()
            : diaryDao
                .delete(diary)
                .ignoreElement()
    )
        .subscribeOn(Schedulers.io());
  }

  /**
   * Returns all {@link Diary} instances of the specified {@link User}
   * @param userId
   * @return
   */
  public LiveData<List<Diary>> getAllByUser(long userId) {
    return diaryDao.selectByUser(userId);
  }

}

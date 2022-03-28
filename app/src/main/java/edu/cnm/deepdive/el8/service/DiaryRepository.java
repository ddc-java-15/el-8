package edu.cnm.deepdive.el8.service;

import android.content.Context;
import androidx.lifecycle.LiveData;
import edu.cnm.deepdive.el8.model.dao.DiaryDao;
import edu.cnm.deepdive.el8.model.entity.Advice;
import edu.cnm.deepdive.el8.model.entity.Diary;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import java.util.Date;
import java.util.List;

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

  public LiveData<Diary> get(long id) {

    return diaryDao.select(id);

  }

  public LiveData<List<Diary>> getAll() {

    return diaryDao.select();
  }

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
  public LiveData<List<Diary>> getAllByUser(long userId) {
    return diaryDao.selectByUser(userId);
  }

}

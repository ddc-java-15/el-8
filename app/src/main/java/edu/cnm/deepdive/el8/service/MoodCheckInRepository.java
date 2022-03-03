package edu.cnm.deepdive.el8.service;

import android.content.Context;
import androidx.lifecycle.LiveData;
import edu.cnm.deepdive.el8.model.dao.MoodCheckInDao;
import edu.cnm.deepdive.el8.model.entity.MoodCheckIn;
import edu.cnm.deepdive.el8.model.entity.User;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import java.util.List;

public class MoodCheckInRepository {

  private final Context context;

  private final MoodCheckInDao moodCheckInDao;

  public MoodCheckInRepository(Context context) {
    this.context = context;

    El8Database database = El8Database.getInstance();
    moodCheckInDao = database.getMoodCheckInDao();
  }

  public LiveData<MoodCheckIn> get(long id) {

    return moodCheckInDao.select(id);

  }

  public LiveData<List<MoodCheckIn>> getAll() {

    return moodCheckInDao.select();
  }

  public Single<MoodCheckIn> save(MoodCheckIn moodCheckIn) {

    return (
        (moodCheckIn.getId() == 0)
            ? moodCheckInDao
            .insert(moodCheckIn)
            .map((id) -> {
              moodCheckIn.setId(id);
              return moodCheckIn;
            })
            : moodCheckInDao
                .update(moodCheckIn)
                .map((count) -> moodCheckIn)
    )
        .subscribeOn(Schedulers.io());

  }

  public Completable delete(MoodCheckIn moodCheckIn) {

    return (
        (moodCheckIn.getId() == 0)
            ? Completable.complete()
            : moodCheckInDao
                .delete(moodCheckIn)
                .ignoreElement()
    )
        .subscribeOn(Schedulers.io());
  }
}
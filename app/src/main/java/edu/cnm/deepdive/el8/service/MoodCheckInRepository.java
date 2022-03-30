package edu.cnm.deepdive.el8.service;

import android.content.Context;
import androidx.lifecycle.LiveData;
import edu.cnm.deepdive.el8.model.dao.AdviceDao;
import edu.cnm.deepdive.el8.model.dao.MoodCheckInDao;
import edu.cnm.deepdive.el8.model.entity.Advice;
import edu.cnm.deepdive.el8.model.entity.MoodCheckIn;
import edu.cnm.deepdive.el8.model.entity.User;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import java.util.Date;
import java.util.List;


/**
 * Provides the core CRUD operations for the {@link MoodCheckIn} entity.
 */
public class MoodCheckInRepository {

  private final Context context;

  private final MoodCheckInDao moodCheckInDao;

  /**
   * Initializes this instance of {@link AdviceRepository} with the injected parameters below.
   * @param context
   */
  public MoodCheckInRepository(Context context) {
    this.context = context;
    El8Database database = El8Database.getInstance();
    moodCheckInDao = database.getMoodCheckInDao();
  }

  /**
   * Returns a LiveData instance of {@code id} specific to {@link MoodCheckIn}
   * @param id
   * @return
   */
  public LiveData<MoodCheckIn> get(long id) {

    return moodCheckInDao.select(id);

  }

  /**
   * Retrieves a List of all instancesof  {@link Advice}
   * @return
   */
  public LiveData<List<MoodCheckIn>> getAll() {

    return moodCheckInDao.select();
  }

  /**
   * Retrieves a list of all {@link MoodCheckIn} specific to an instance of {@link User}
   * @param userId specific to this userId
   * @return
   */
  public LiveData<List<MoodCheckIn>> getAllByUser(long userId) {
    return moodCheckInDao.selectByUser(userId);
  }

  /**
   * Saves an instance of  {@link Advice }
   * @param moodCheckIn
   * @return
   */
  public Single<MoodCheckIn> save(MoodCheckIn moodCheckIn) {

    return (
        (moodCheckIn.getId() == 0)
            ? Single.fromCallable(() -> {
              moodCheckIn.setCreated(new Date());
              return moodCheckIn;
            })
            .flatMap(moodCheckInDao::insert)
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

  /**
   * Returns a completable upon completion of deletion of {@link MoodCheckIn}
   * @param moodCheckIn
   * @return
   */
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

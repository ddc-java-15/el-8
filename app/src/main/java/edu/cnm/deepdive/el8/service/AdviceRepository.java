package edu.cnm.deepdive.el8.service;

import android.content.Context;
import androidx.lifecycle.LiveData;
import edu.cnm.deepdive.el8.model.dao.AdviceDao;
import edu.cnm.deepdive.el8.model.entity.Advice;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import java.util.List;
import edu.cnm.deepdive.el8.model.entity.User;

/**
 * Provides the core CRUD operations for the {@link Advice} entity.
 */
public class AdviceRepository {

  private final Context context;

  private final AdviceDao adviceDao;

  /**
   * Initializes this instance of {@link AdviceRepository} with the injected parameters below.
   * @param context
   */
  public AdviceRepository(Context context) {
    this.context = context;
    El8Database database = El8Database.getInstance();
    adviceDao = database.getAdviceDao();
  }

  /**
   * Returns a LiveData instance of {@code id} specific to {@link AdviceDao}
   * @param id
   * @return
   */
  public LiveData<Advice> get(long id) {

    return adviceDao.select(id);

  }

  /**
   * Retrieves a List of {@link Advice}
   * @return
   */
  public LiveData<List<Advice>> getAll() {

    return adviceDao.select();
  }

  /**
   * Retrieves a list of all {@link Advice} specific to an instance of {@link User}
   * @param userId specific to this userId
   * @param onlyFavorites parameterizes to only retrieve  the favorite instances.
   * @return
   */
  public LiveData<List<Advice>> getAllByUser(long userId, boolean onlyFavorites) {

    return onlyFavorites
        ? adviceDao.selectFavoritesByUser(userId)
        : adviceDao.selectByUser(userId);
  }


  public Single<Advice> save(Advice advice) {

    return (
        (advice.getId() == 0)
            ? adviceDao
            .insert(advice)
            .map((id) -> {
              advice.setId(id);
              return advice;
            })
            : adviceDao
                .update(advice)
                .map((count) -> advice)
    )
        .subscribeOn(Schedulers.io());

  }

  public Completable delete(Advice advice) {

    return (
        (advice.getId() == 0)
            ? Completable.complete()
            : adviceDao
                .delete(advice)
                .ignoreElement()
    )
        .subscribeOn(Schedulers.io());
  }

  public Single<Advice> generate(int moodRating, long userId) {
    Advice advice = new Advice();
    advice.setUserId(userId);
    advice.setAction("Yoga"); // FIXME based advice on mood rating.
    advice.setImage(""); // FIXME based on drawable resources and mood rating;
    return save(advice);

  }

}

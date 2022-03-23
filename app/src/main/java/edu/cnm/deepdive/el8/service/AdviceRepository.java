package edu.cnm.deepdive.el8.service;

import android.content.Context;
import androidx.lifecycle.LiveData;
import edu.cnm.deepdive.el8.model.dao.AdviceDao;
import edu.cnm.deepdive.el8.model.entity.Advice;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import java.util.List;

public class AdviceRepository {

  private final Context context;

  private final AdviceDao adviceDao;

  public AdviceRepository(Context context) {
    this.context = context;

    El8Database database = El8Database.getInstance();
    adviceDao = database.getAdviceDao();
  }

  public LiveData<Advice> get(long id) {

    return adviceDao.select(id);

  }

  public LiveData<List<Advice>> getAll() {

    return adviceDao.select();
  }

  public LiveData<List<Advice>> getAllByUser(long userId) {

    return adviceDao.selectByUser(userId);
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

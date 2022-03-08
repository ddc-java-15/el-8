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

}

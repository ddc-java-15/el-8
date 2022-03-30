package edu.cnm.deepdive.el8.viewmodel;

import android.app.Application;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import edu.cnm.deepdive.el8.controller.AdviceDetailsFragment;
import edu.cnm.deepdive.el8.controller.AdviceFragment;
import edu.cnm.deepdive.el8.model.entity.Advice;
import edu.cnm.deepdive.el8.model.entity.MoodCheckIn;
import edu.cnm.deepdive.el8.model.entity.User;
import edu.cnm.deepdive.el8.service.AdviceRepository;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import java.util.List;

/**
 *  Prepares and manages the data for the {@link AdviceFragment } and {@link AdviceDetailsFragment} fragment .
 *  Handles the communication of the {@link AdviceFragment } and {@link AdviceDetailsFragment} with the rest of the application.
 */
public class AdviceViewModel extends AndroidViewModel implements
    DefaultLifecycleObserver {

  private final AdviceRepository repository;

  private final LiveData<Advice> advice;

  private final MutableLiveData<Throwable> throwable;

  private final MutableLiveData<Long> adviceId;

  private final CompositeDisposable pending;

  private final MutableLiveData<Long> userId;

  private final LiveData<List<Advice>> advices;

  private final MutableLiveData<Boolean> onlyFavorites;

  /**
   *Initialize this instance of {@link AdviceViewModel} with the injected parameters.
   * @param application
   */
  public AdviceViewModel(@NonNull Application application) {
    super(application);
    repository = new AdviceRepository(application);
    adviceId = new MutableLiveData<>();
    advice = Transformations.switchMap(adviceId, (id) -> repository.get(id));
    userId = new MutableLiveData<>(0L);
    onlyFavorites = new MutableLiveData<>(false);
    AdviceFilterLiveData adviceFilterLiveData = new AdviceFilterLiveData(userId, onlyFavorites);
    advices = Transformations.switchMap(adviceFilterLiveData, (filter) ->
        repository.getAllByUser(filter.userId, filter.onlyFavorites));
    throwable = new MutableLiveData<>();
    pending = new CompositeDisposable();

  }

  @Override
  public void onStop(@NonNull LifecycleOwner owner) {
    DefaultLifecycleObserver.super.onStop(owner);
    pending.clear();
  }
  /**
   * Retrieves an instance of {@code Livedata} {@link  Advice}
   * @return
   */
  public LiveData<Advice> getAdvice() {
    return advice;
  }

  /**
   * Retrieves a {@code throwable} {@link  Advice}
   * @return
   */
  public LiveData<Throwable> getThrowable() {
    return throwable;
  }

  /**
   * Retrieves a List of {@code Livedata} {@link  Advice}
   * @return
   */
  public LiveData<List<Advice>> getAdvices() {
    return advices;
  }

  /**
   * Sets the  {@code id} of the {@link User} specific to {@link MoodCheckIn}
   * @param userId
   */
  public void setUserId(long userId) {
    this.userId.setValue(userId);
  }

  /**
   * Sets the instance or instances of {@code onlyFavorite}
   * @param onlyFavorites
   */
  public void setOnlyFavorites(boolean onlyFavorites) {
    this.onlyFavorites.setValue(onlyFavorites);
  }

  /**
   * Saves the specified instance of {@link Advice}
   * @param advice
   */
  public void save(Advice advice) {
    Disposable disposable = repository
        .save(advice)
        .subscribe(
            (adv) -> this.adviceId.postValue(adv.getId()),
            this::postThrowable
        );
    pending.add(disposable);
  }

  /**
   * Sets the instance of {@code favorites}
   * @param advice
   * @param favorite
   */
  public void setFavorite(Advice advice, boolean favorite) {
    advice.setFavorite(favorite);
    save(advice);
  }

  /**
   * Deletes the specified instance of {@link Advice}
   * @param advice
   */
  public void delete(Advice advice) {
    repository
        .delete(advice)
        .subscribe(
            () -> {
            },
            this::postThrowable);

  }

  /**
   * Sets the specified {@link Advice} id.
   * @param id
   */
  public void setAdviceId(long id) {
    adviceId.setValue(id);

  }

  private void postThrowable(Throwable throwable) {
    Log.e(getClass().getSimpleName(), throwable.getMessage(), throwable);
    this.throwable.postValue(throwable);

  }

  private static class AdviceFilter {
    private final long userId;
    private final boolean onlyFavorites;

    private AdviceFilter(long userId, boolean onlyFavorites) {
      this.userId = userId;
      this.onlyFavorites = onlyFavorites;
    }
  }

  private static class AdviceFilterLiveData extends MediatorLiveData <AdviceFilter> {

    @SuppressWarnings("ConstantConditions")
    public AdviceFilterLiveData(LiveData<Long> userId, LiveData<Boolean> onlyFavorites) {
      addSource(userId, (id) -> setValue(new AdviceFilter(id, onlyFavorites.getValue())));
      addSource(onlyFavorites, (favorites) -> setValue(new AdviceFilter(userId.getValue(), favorites)));
    }
  }
}


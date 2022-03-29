package edu.cnm.deepdive.el8.viewmodel;

import android.app.Application;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import edu.cnm.deepdive.el8.controller.MoodCheckInDetailsFragment;
import edu.cnm.deepdive.el8.controller.MoodFragment;
import edu.cnm.deepdive.el8.model.entity.MoodCheckIn;
import edu.cnm.deepdive.el8.model.entity.User;
import edu.cnm.deepdive.el8.service.MoodCheckInRepository;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import java.util.List;

/**
 *  Prepares and manages the data for the {@link MoodFragment } and {@link MoodCheckInDetailsFragment} fragment .
 *  Handles the communication of the {@link MoodFragment } and {@link MoodCheckInDetailsFragment} with the rest of the application.
 */
public class MoodViewModel extends AndroidViewModel implements DefaultLifecycleObserver {

  private final MoodCheckInRepository repository;

  private final LiveData<MoodCheckIn> moodCheckIn;

  private final MutableLiveData<Throwable> throwable;

  private final MutableLiveData<Long> moodId;

  private final MutableLiveData<Long> userId;

  private final LiveData<List<MoodCheckIn>> moods;

  private final CompositeDisposable pending;

  /**
   *Initialize this instance of {@link MoodViewModel} with the injected parameters.
   * @param application
   */
  public MoodViewModel(@NonNull Application application) {
    super(application);
    repository = new MoodCheckInRepository(application);
    moodId = new MutableLiveData<>();
    moodCheckIn = Transformations.switchMap(moodId, (id) -> repository.get(id));
    userId = new MutableLiveData<>();
    moods = Transformations.switchMap(userId, repository::getAllByUser);
    throwable = new MutableLiveData<>();
    pending = new CompositeDisposable();

  }

  @Override
  public void onStop(@NonNull LifecycleOwner owner) {
    DefaultLifecycleObserver.super.onStop(owner);
    pending.clear();
  }

  /**
   * Retrieves an instance of {@code Livedata} {@link  MoodCheckIn}
   * @return
   */
  public LiveData<MoodCheckIn> getMoodCheckIn() {
    return moodCheckIn;
  }

  /**
   * Retrieves a {@code throwable} {@link  MoodCheckIn}
   * @return
   */
  public LiveData<Throwable> getThrowable() {
    return throwable;
  }

  /**
   * Retrieves a List of {@code Livedata} {@link  MoodCheckIn}
   * @return
   */
  public LiveData<List<MoodCheckIn>> getMoods() {
    return moods;
  }


   /**
   * Sets the  {@code id} of the {@link User} specific to {@link MoodCheckIn}
   * @param userId
   */
  public void setUserId(long userId) {
    this.userId.setValue(userId);
  }

  /**
   * Saves the specified instance of {@link MoodCheckIn}
   * @param moodCheckIn
   */
  public void save(MoodCheckIn moodCheckIn) {
    Disposable disposable = repository
        .save(moodCheckIn)
        .subscribe(
            (c) -> {
            },
            this::postThrowable
        );
    pending.add(disposable);
  }

  /**
   * Deletes the specified instance of {@link MoodCheckIn}
   * @param moodCheckIn
   */
  public void delete(MoodCheckIn moodCheckIn) {
    repository
        .delete(moodCheckIn)
        .subscribe(
            () -> {
            },
            this::postThrowable);

  }

  /**
   * Sets the specified id of {@link MoodCheckIn}
   * @param id
   */
  public void setMoodCheckInId(long id) {
    moodId.setValue(id);

  }

  private void postThrowable(Throwable throwable) {
    Log.e(getClass().getSimpleName(), throwable.getMessage(), throwable);
    this.throwable.postValue(throwable);
  }

}

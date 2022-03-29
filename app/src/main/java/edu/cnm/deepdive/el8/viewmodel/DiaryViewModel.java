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
import edu.cnm.deepdive.el8.controller.DiaryFragment;
import edu.cnm.deepdive.el8.controller.DiaryDetailsFragment;
import edu.cnm.deepdive.el8.model.entity.Diary;
import edu.cnm.deepdive.el8.model.entity.MoodCheckIn;
import edu.cnm.deepdive.el8.model.entity.User;
import edu.cnm.deepdive.el8.service.DiaryRepository;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import java.util.List;

/**
 *  Prepares and manages the data for the {@link DiaryFragment } and {@link DiaryDetailsFragment} fragment .
 *  Handles the communication of {@link DiaryFragment } and {@link DiaryDetailsFragment}  with the rest of the application.
 */
public class DiaryViewModel extends AndroidViewModel implements DefaultLifecycleObserver {

  private final DiaryRepository repository;

  private final LiveData<Diary> diary;

  private final MutableLiveData<Throwable> throwable;

  private final MutableLiveData<Long> diaryId;

  private final MutableLiveData<Long> userId;

  private final LiveData<List<Diary>> diaries;


  private final CompositeDisposable pending;

  /**
   *Initialize this instance of {@link DiaryViewModel} with the injected parameters.
   * @param application
   */
  public DiaryViewModel(@NonNull Application application) {
    super(application);
    repository = new DiaryRepository(application);
    diaryId = new MutableLiveData<>();
    diary = Transformations.switchMap(diaryId, (id) -> repository.get(id));
    userId = new MutableLiveData<>();
    diaries = Transformations.switchMap(userId, (id) -> repository.getAllByUser(id));
    throwable = new MutableLiveData<>();
    pending = new CompositeDisposable();

  }

  @Override
  public void onStop(@NonNull LifecycleOwner owner) {
    DefaultLifecycleObserver.super.onStop(owner);
    pending.clear();
  }

  /**
   * Retrieves an instance of {@code Livedata} {@link  Diary}
   * @return
   */
  public LiveData<Diary> getDiary() {
    return diary;
  }

  /**
   * Retrieves an {@code throwable} {@link Diary}
   * @return
   */
  public LiveData<Throwable> getThrowable() {
    return throwable;
  }
  /**
   * Retrieves a List of {@code Livedata} {@link  Diary}
   * @return
   */
  public LiveData<List<Diary>> getDiaries() {
    return diaries;
  }
  /**
   * Sets the  {@code id} of the {@link User} specific to {@link Diary}
   * @param userId
   */
  public void setUserId(long userId) {
    this.userId.setValue(userId);
  }

  /**
   * Saves the specified instance of {@link Diary}
   * @param diary
   */
  public void save(Diary diary) {
    Disposable disposable = repository
        .save(diary)
        .subscribe(
            (c) -> {
            },
            this::postThrowable
        );
    pending.add(disposable);
  }

  /**
   * Deletes the specified instance of {@link Diary}
   * @param diary
   */
  public void delete(Diary diary) {
    repository
        .delete(diary)
        .subscribe(
            () -> {
            },
            this::postThrowable);

  }

  /**
   * Sets the specified id of {@link MoodCheckIn}
   * @param id
   */
  public void setDiaryId(long id) {
    diaryId.setValue(id);

  }

  private void postThrowable(Throwable throwable) {
    Log.e(getClass().getSimpleName(), throwable.getMessage(), throwable);
    this.throwable.postValue(throwable);
  }

}




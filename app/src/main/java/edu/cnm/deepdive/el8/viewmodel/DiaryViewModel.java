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
import edu.cnm.deepdive.el8.model.entity.Diary;
import edu.cnm.deepdive.el8.service.DiaryRepository;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import java.util.List;

public class DiaryViewModel extends AndroidViewModel implements DefaultLifecycleObserver {

  private final DiaryRepository repository;

  private final LiveData<Diary> diary;

  private final MutableLiveData<Throwable> throwable;

  private final MutableLiveData<Long> diaryId;

  private final MutableLiveData<Long> userId;


  private final CompositeDisposable pending;

  public DiaryViewModel(@NonNull Application application) {
    super(application);
    repository = new DiaryRepository(application);
    diaryId = new MutableLiveData<>();
    diary = Transformations.switchMap(diaryId, (id) -> repository.get(id));
    userId = new MutableLiveData<>();
    throwable = new MutableLiveData<>();
    pending = new CompositeDisposable();

  }

  @Override
  public void onStop(@NonNull LifecycleOwner owner) {
    DefaultLifecycleObserver.super.onStop(owner);
    pending.clear();
  }

  public LiveData<Diary> getDiary() {
    return diary;
  }

  public LiveData<Throwable> getThrowable() {
    return throwable;
  }

  public LiveData<List<Diary>> getDiaries() {
    return repository.getAll();
  }

  public void setUserId(long userId) {
    this.userId.setValue(userId);
  }

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

  public void delete(Diary diary) {
    repository
        .delete(diary)
        .subscribe(
            () -> {
            },
            this::postThrowable);

  }

  public void setDiaryId(long id) {
    diaryId.setValue(id);

  }

  private void postThrowable(Throwable throwable) {
    Log.e(getClass().getSimpleName(), throwable.getMessage(), throwable);
    this.throwable.postValue(throwable);
  }

}




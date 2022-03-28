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
import edu.cnm.deepdive.el8.model.entity.Advice;
import edu.cnm.deepdive.el8.service.AdviceRepository;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import java.util.List;


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

  public LiveData<Advice> getAdvice() {
    return advice;
  }

  public LiveData<Throwable> getThrowable() {
    return throwable;
  }

  public LiveData<List<Advice>> getAdvices() {
    return advices;
  }

  public void setUserId(long userId) {
    this.userId.setValue(userId);
  }

  public void setOnlyFavorites(boolean onlyFavorites) {
    this.onlyFavorites.setValue(onlyFavorites);
  }
  public void save(Advice advice) {
    Disposable disposable = repository
        .save(advice)
        .subscribe(
            (adv) -> this.adviceId.postValue(adv.getId()),
            this::postThrowable
        );
    pending.add(disposable);
  }

  public void setFavorite(Advice advice, boolean favorite) {
    advice.setFavorite(favorite);
    save(advice);
  }

  public void delete(Advice advice) {
    repository
        .delete(advice)
        .subscribe(
            () -> {
            },
            this::postThrowable);

  }

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


package edu.cnm.deepdive.el8.service;

import android.content.Context;
import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import edu.cnm.deepdive.el8.model.dao.UserDao;
import edu.cnm.deepdive.el8.model.entity.Advice;
import edu.cnm.deepdive.el8.model.entity.User;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import java.util.List;

/**
 * Provides the core CRUD operations for the {@link User} entity.
 */
public class UserRepository {

  private final Context context;

  private final UserDao userDao;

  private final GoogleSignInService signInService;

  private final MutableLiveData<User> user;

  /**
   *
   * Initializes this instance with of {@link UserRepository}
   * @param context
   */
  public UserRepository(Context context) {
    this.context = context;
    El8Database database = El8Database.getInstance();
    userDao = database.getUserDao();
    signInService = GoogleSignInService.getInstance();
    user = new MutableLiveData<>();
    getOrCreate()
        .subscribe(
            (user) -> this.user.postValue(user),
            (throwable) -> Log.e(getClass().getSimpleName(), throwable.getMessage(),throwable)
        );
  }

  /**
   * Returns a Livedata instance of {@link User}
   * @return
   */
  public LiveData<User> getUser() {
    return user;
  }

  /**
   * Returns a LiveData instance of {@code id} specific to {@link User}
   * @param id
   * @return
   */
  public LiveData<User> get(long id) {

    return userDao.select(id);

  }

  /**
   * Retrieves a List of all instances of  {@link Advice}
   * @return
   */
  public LiveData<List<User>> getAll() {

    return userDao.select();
  }

  /**
   * Saves an instance of  {@link User }
   * @param user
   * @return
   */
  public Single<User> save(User user) {

    return (
        (user.getId() == 0)
            ? userDao
            .insert(user)
            .map((id) -> {
              user.setId(id);
              return user;
            })
            : userDao
                .update(user)
                .map((count) -> user)
    )
        .subscribeOn(Schedulers.io());

  }

  /**
   * Returns a completable upon completion of deletion of {@link User}
   * @param user
   * @return
   */
  public Completable delete(User user) {

    return (
        (user.getId() == 0)
            ? Completable.complete()
            : userDao
                .delete(user)
                .ignoreElement()
    )
        .subscribeOn(Schedulers.io());
  }

  /**
   * Returns a single instance of {@link User} sign-in.
   * @return
   */
  public Single<User> getOrCreate() {
    return signInService
        .refresh()
        .flatMap((account) -> userDao.getByOauthKey(account.getId())
            .switchIfEmpty(
                Single
                    .fromCallable(() -> {
                      User user = new User();
                      user.setOauthKey(account.getId());
                      //noinspection ConstantConditions
                      user.setName(account.getDisplayName());
                      return user;
                    })
                    .flatMap(this::save)
            )
        )
        .subscribeOn(Schedulers.io());



  }
}

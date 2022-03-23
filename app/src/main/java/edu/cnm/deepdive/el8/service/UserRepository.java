package edu.cnm.deepdive.el8.service;

import android.content.Context;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import edu.cnm.deepdive.el8.model.dao.UserDao;
import edu.cnm.deepdive.el8.model.entity.User;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import java.util.List;

public class UserRepository {

  private final Context context;

  private final UserDao userDao;

  private final GoogleSignInService signInService;

  private final MutableLiveData<User> user;


  public UserRepository(Context context) {
    this.context = context;

    El8Database database = El8Database.getInstance();
    userDao = database.getUserDao();
    signInService = GoogleSignInService.getInstance();
    user = new MutableLiveData<>();
    getOrCreate().subscribe();
  }

  public LiveData<User> getUser() {
    return user;
  }

  public LiveData<User> get(long id) {

    return userDao.select(id);

  }

  public LiveData<List<User>> getAll() {

    return userDao.select();
  }

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
        .doOnSuccess(this.user::postValue)
        .subscribeOn(Schedulers.io());



  }
}

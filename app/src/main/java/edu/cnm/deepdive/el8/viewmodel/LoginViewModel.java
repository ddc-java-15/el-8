package edu.cnm.deepdive.el8.viewmodel;

import android.app.Application;
import android.content.Intent;
import android.util.Log;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import edu.cnm.deepdive.el8.controller.LoginActivity;
import edu.cnm.deepdive.el8.model.entity.MoodCheckIn;
import edu.cnm.deepdive.el8.model.entity.User;
import edu.cnm.deepdive.el8.service.GoogleSignInService;
import edu.cnm.deepdive.el8.service.UserRepository;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;

/**
 *  Prepares and manages the data for the {@link LoginActivity }  fragment .
 *  Handles the communication of the {@link LoginActivity }  fragment with the rest of the application.
 */
public class LoginViewModel extends AndroidViewModel implements DefaultLifecycleObserver {

  private final GoogleSignInService signInService;
  private final MutableLiveData<GoogleSignInAccount> account;
  private final MutableLiveData<Throwable> throwable;
  private final CompositeDisposable pending;
  private final UserRepository userRepository;

  /**
   *Initialize this instance of {@link LoginViewModel} with the injected parameters.
   * @param application
   */
  public LoginViewModel(@NonNull Application application) {
    super(application);
    signInService = GoogleSignInService.getInstance();
    account = new MutableLiveData<>();
    throwable = new MutableLiveData<>();
    pending = new CompositeDisposable();
    userRepository= new UserRepository(application);
    refresh();
  }

  /**
   * Retrieves an instance of {@code Livedata} {@link  User}
   * @return
   */
  public LiveData<User> getUser() {
    return userRepository.getUser();
  }

  /**
   * Retrieves an instance of {@code Livedata} {@link  GoogleSignInAccount}
   * @return
   */
  public LiveData<GoogleSignInAccount> getAccount() {
    return account;
  }

  /**
   * Retrieves a {@code throwable}
   * @return
   */
  public LiveData<Throwable> getThrowable() {
    return throwable;
  }

  /**
   * Refresh the current instance {@link GoogleSignInAccount}
   */
  public void refresh() {
    throwable.setValue(null);
    Disposable disposable = signInService
        .refresh()
        .subscribe(
            account::postValue,
            (throwable) -> account.postValue(null)

        );
    pending.add(disposable);
  }

  /**
   * Completes the Google sign in process.
   * @param result
   */
  public void completeSignIn(ActivityResult result) {
    throwable.setValue(null);
    Disposable disposable = signInService
        .completeSignIn(result)
        .subscribe(
            account::postValue,
            this::postThrowable
        );
    pending.add(disposable);
  }

  /**
   * Starts the Google sign in process.
   * @param launcher
   */
  public void startSignIn(ActivityResultLauncher<Intent> launcher) {
    signInService.startSignIn(launcher);
  }

  /**
   * Executes the Google sign out process
   */
  public void signOut() {
    throwable.setValue(null);
    Disposable disposable = signInService
        .signOut()
        .doFinally(() -> account.postValue(null))
        .subscribe(
            () -> {
            },
            this::postThrowable
        );
    pending.add(disposable);
  }

  @Override
  public void onStop(@NonNull LifecycleOwner owner) {
    DefaultLifecycleObserver.super.onStop(owner);
    pending.clear();
  }

  private void postThrowable(Throwable throwable) {
    Log.e(getClass().getSimpleName(), throwable.getMessage(), throwable);
    this.throwable.postValue(throwable);
  }
}
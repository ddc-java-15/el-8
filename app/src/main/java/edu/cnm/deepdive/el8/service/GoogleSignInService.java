package edu.cnm.deepdive.el8.service;

import android.app.Application;
import android.content.Intent;
import android.util.Log;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleEmitter;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * Handles the Google sign in process.
 */
public class GoogleSignInService {

  private static final String BEARER_TOKEN_FORMAT = "Bearer %s";

  private static Application context;

  private final GoogleSignInClient client;

  private GoogleSignInService() {

    GoogleSignInOptions options = new GoogleSignInOptions.Builder()
        .requestEmail()
        .requestId()
        .requestProfile()
        .build();

    client = GoogleSignIn.getClient(context, options);

  }

  /**
   * Sets the context of this Google sign in instance.
   * @param context
   */
  public static void setContext(Application context) {
    GoogleSignInService.context = context;
  }

  /**
   * Gets an instance of the {@link GoogleSignInService}
   * @return
   */
  public static GoogleSignInService getInstance() {
    return InstanceHolder.INSTANCE;
  }

  /**
   * Refreshes the specified instance if {@link  GoogleSignInService}
   * @return
   */
  public Single<GoogleSignInAccount> refresh() {
    return Single
        .create((SingleEmitter<GoogleSignInAccount> emitter) ->
            client
                .silentSignIn()
                .addOnSuccessListener((this::logAccount) )
                .addOnSuccessListener(t -> emitter.onSuccess(t))
                .addOnFailureListener(emitter::onError)

        )
        .observeOn(Schedulers.io());
  }

  /**
   * Refreshes the beare token.
   * @return
   */
  public Single<String> refreshBearerToken() {
    return refresh()
        .map(this::getBearerToken);
  }

  @NonNull
  private String getBearerToken(GoogleSignInAccount account) {
    return String.format(BEARER_TOKEN_FORMAT, account.getIdToken());
  }

  private void logAccount(GoogleSignInAccount account) {

    if (account != null ) {
      Log.d(getClass().getSimpleName(), (account.getIdToken() != null ? getBearerToken(account): "(none)" ));

    }

  }

  /**
   * Starts the specified {@link GoogleSignInService} with the launcher parameter.
   * @param launcher
   */
  public void startSignIn(ActivityResultLauncher<Intent> launcher) {
    launcher.launch(client.getSignInIntent());

  }

  /**
   * Completes the specified instance {@link  GoogleSignInService}
   * @param result
   * @return
   */
  public Single<GoogleSignInAccount> completeSignIn(ActivityResult result) {
    return Single
        .create((SingleEmitter<GoogleSignInAccount> emitter) -> {
          try {
            Task<GoogleSignInAccount> task =
                GoogleSignIn.getSignedInAccountFromIntent(result.getData());
            GoogleSignInAccount account = task.getResult(ApiException.class);
            logAccount(account);
            emitter.onSuccess(account);
          } catch (ApiException e) {
            emitter.onError(e);
          }
        })
        .observeOn(Schedulers.io());
  }

  /**
   * Returns a {@code completable} of the specified instance of the sign-out process.
   * @return
   */
  public Completable signOut() {
    return Completable
        .create((emitter) ->
            client
                .signOut()
                .addOnSuccessListener((ignored) -> emitter.onComplete())
                .addOnFailureListener(emitter::onError)
        )
        .subscribeOn(Schedulers.io());
  }

  private static class InstanceHolder {

    private static final GoogleSignInService INSTANCE = new GoogleSignInService();
  }

}

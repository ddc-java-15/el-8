package edu.cnm.deepdive.el8;

import android.app.Application;
import com.facebook.stetho.Stetho;
import edu.cnm.deepdive.el8.service.El8Database;
import edu.cnm.deepdive.el8.service.GoogleSignInService;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * Initializes (in the {@link #onCreate()} method) application-level resources. This class
 * <strong>must</strong> be referenced in {@code AndroidManifest.xml}, or it will not be loaded and
 * used by the Android system.
 */
public class El8Application extends Application {

  @Override
  public void onCreate() {
    super.onCreate();
    GoogleSignInService.setContext(this);
    Stetho.initializeWithDefaults(this);
    El8Database.setContext(this);
    El8Database
        .getInstance()
        .getUserDao()
        .delete()
        .subscribeOn(Schedulers.io())
        .subscribe();
  }

}

package edu.cnm.deepdive.el8.service;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;
import edu.cnm.deepdive.el8.model.dao.AdviceDao;
import edu.cnm.deepdive.el8.model.dao.DiaryDao;
import edu.cnm.deepdive.el8.model.dao.MoodCheckInDao;
import edu.cnm.deepdive.el8.model.dao.UserDao;
import edu.cnm.deepdive.el8.model.entity.Advice;
import edu.cnm.deepdive.el8.model.entity.Diary;
import edu.cnm.deepdive.el8.model.entity.MoodCheckIn;
import edu.cnm.deepdive.el8.model.entity.User;
import edu.cnm.deepdive.el8.service.El8Database.Converters;
import io.reactivex.rxjava3.schedulers.Schedulers;
import java.util.Date;

/**
 * Database of the EL8 application that extends the room database.
 */
@Database(
    entities = {User.class, Diary.class, MoodCheckIn.class, Advice.class},
    version = 1
)
@TypeConverters({Converters.class})
public abstract class El8Database extends RoomDatabase {

  private static final String DB_NAME = "el8-db";

  private static Application context;

  /**
   * Sets the context of the database.
   * @param context
   */
  public static void setContext(Application context) {
    El8Database.context = context;
  }

  /**
   * Retrieves an instance of the {@link El8Database}
   * @return
   */
  public static El8Database getInstance() {
    return InstanceHolder.INSTANCE;
  }

  /**
   * Retrieves the {@link UserDao}
   * @return
   */
  public abstract UserDao getUserDao();

  /**
   * Retrieves the {@link AdviceDao}
   * @return
   */
  public abstract AdviceDao getAdviceDao();

  /**
   * Retrieves the {@link DiaryDao}
   * @return
   */
  public abstract DiaryDao getDiaryDao();

  /**
   * Retrieves the {@link MoodCheckInDao}
   * @return
   */
  public abstract MoodCheckInDao getMoodCheckInDao();

  private static class InstanceHolder {

    private static final El8Database INSTANCE = Room
        .databaseBuilder(context, El8Database.class, DB_NAME)
        .addCallback(new El8Database.Callback())
        .build();
  }

  private static class Callback extends RoomDatabase.Callback {

    @Override
    public void onCreate(@NonNull SupportSQLiteDatabase db) {
      super.onCreate(db);
      User user = new User();
      user.setName("Test name");
      getInstance()
          .getUserDao()
          .insert(user)
          .subscribeOn(Schedulers.io())
          .subscribe();
    }

  }

  /**
   * Date and time converters
   */
  public static class Converters {

    @TypeConverter
    public static Long toLong(Date value) {
      return (value != null) ? value.getTime() : null;
    }

    @TypeConverter
    public static Date toDate(Long value) {
      return (value != null) ? new Date(value) : null;
    }
  }
}

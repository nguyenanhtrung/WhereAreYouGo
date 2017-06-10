package com.example.android.whereareyougo.ui.di.module;

import android.app.Application;
import android.content.Context;
import com.example.android.whereareyougo.ui.data.database.model.AppDatabaseHelper;
import com.example.android.whereareyougo.ui.data.database.model.DatabaseHelper;
import com.example.android.whereareyougo.ui.data.manager.AppDataManager;
import com.example.android.whereareyougo.ui.data.manager.DataManager;
import com.example.android.whereareyougo.ui.di.ApplicationContext;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

/**
 * Created by nguyenanhtrung on 09/06/2017.
 */

@Module
public class ApplicationModule {
  private final Application application;

  public ApplicationModule(Application application) {
    this.application = application;
  }

  @Provides
  @ApplicationContext
  Context provideContext(){
    return application;
  }

  @Provides
  Application provideApplication(){
    return application;
  }

  @Provides
  @Singleton
  DataManager provideDataManager(AppDataManager appDataManager){
    return appDataManager;
  }

  @Provides
  @Singleton
  DatabaseHelper provideDatabaseHelper(AppDatabaseHelper databaseHelper){
    return databaseHelper;
  }










}

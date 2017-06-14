package com.example.android.whereareyougo.ui.di.module;

import android.app.Application;
import android.content.Context;
import com.example.android.whereareyougo.ui.data.database.model.AppDatabaseHelper;
import com.example.android.whereareyougo.ui.data.database.model.DatabaseHelper;
import com.example.android.whereareyougo.ui.data.manager.AppDataManager;
import com.example.android.whereareyougo.ui.data.manager.DataManager;
import com.example.android.whereareyougo.ui.data.pref.AppPreferencesHelper;
import com.example.android.whereareyougo.ui.data.pref.PreferencesHelper;
import com.example.android.whereareyougo.ui.di.ApplicationContext;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
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

  @Provides
  @Singleton
  FirebaseAuth provideFirebaseAuth(){
    return FirebaseAuth.getInstance();
  }

  @Provides
  @Singleton
  DatabaseReference provideDatabaseReference(){
    return FirebaseDatabase.getInstance().getReference();
  }

  @Provides
  @Singleton
  PreferencesHelper providePreferencesHelper(AppPreferencesHelper preferencesHelper){
    return preferencesHelper;
  }

  @Provides
  @Singleton
  FirebaseStorage provideFirebaseStorage(){
    return FirebaseStorage.getInstance();
  }








}

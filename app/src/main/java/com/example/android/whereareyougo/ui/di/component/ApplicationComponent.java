package com.example.android.whereareyougo.ui.di.component;

import android.app.Application;
import android.content.Context;
import com.example.android.whereareyougo.ui.MyApplication;
import com.example.android.whereareyougo.ui.data.manager.DataManager;
import com.example.android.whereareyougo.ui.di.ApplicationContext;
import com.example.android.whereareyougo.ui.di.module.ApplicationModule;
import dagger.Component;
import javax.inject.Singleton;

/**
 * Created by nguyenanhtrung on 09/06/2017.
 */

@Singleton
@Component(modules= ApplicationModule.class)
public interface ApplicationComponent {
  void inject(MyApplication myApplication);

  @ApplicationContext
  Context context();
  Application getApplication();
  DataManager getDataManager();
}
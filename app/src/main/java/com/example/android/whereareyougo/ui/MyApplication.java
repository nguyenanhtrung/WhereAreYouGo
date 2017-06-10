package com.example.android.whereareyougo.ui;

import android.app.Application;
import com.example.android.whereareyougo.ui.data.manager.DataManager;
import com.example.android.whereareyougo.ui.di.component.ApplicationComponent;
import com.example.android.whereareyougo.ui.di.component.DaggerApplicationComponent;
import com.example.android.whereareyougo.ui.di.module.ApplicationModule;
import javax.inject.Inject;

/**
 * Created by nguyenanhtrung on 09/06/2017.
 */

public class MyApplication extends Application {
  @Inject
  DataManager dataManager;

  private ApplicationComponent applicationComponent;

  @Override
  public void onCreate() {
    super.onCreate();

    applicationComponent = DaggerApplicationComponent.builder()
        .applicationModule(new ApplicationModule(this)).build();

    applicationComponent.inject(this);



  }

  public ApplicationComponent getComponent(){
    return applicationComponent;
  }

  public void setComponent(ApplicationComponent applicationComponent) {
    this.applicationComponent = applicationComponent;
  }
}

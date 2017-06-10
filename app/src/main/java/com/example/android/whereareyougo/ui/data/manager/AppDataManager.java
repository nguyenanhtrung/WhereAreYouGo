package com.example.android.whereareyougo.ui.data.manager;

import android.content.Context;
import com.example.android.whereareyougo.ui.data.database.model.DatabaseHelper;
import com.example.android.whereareyougo.ui.di.ApplicationContext;
import javax.inject.Inject;

/**
 * Created by nguyenanhtrung on 09/06/2017.
 */

public class AppDataManager implements DataManager {

  private Context context;
  private DatabaseHelper databaseHelper;

  @Inject
  public AppDataManager(@ApplicationContext Context context) {
    this.context = context;

  }
}

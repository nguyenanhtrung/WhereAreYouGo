package com.example.android.whereareyougo.ui.data.manager;

import android.content.Context;
import com.example.android.whereareyougo.ui.data.database.model.DatabaseHelper;
import com.example.android.whereareyougo.ui.di.ApplicationContext;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.ProviderQueryResult;
import javax.inject.Inject;

/**
 * Created by nguyenanhtrung on 09/06/2017.
 */

public class AppDataManager implements DataManager {

  private Context context;
  private DatabaseHelper databaseHelper;

  @Inject
  public AppDataManager(@ApplicationContext Context context, DatabaseHelper databaseHelper) {
    this.context = context;
    this.databaseHelper = databaseHelper;
  }

  @Override
  public Task<ProviderQueryResult> getProviderForEmail(String email) {
    return databaseHelper.getProviderForEmail(email);
  }

  @Override
  public Task<AuthResult> createUserWithEmailAndPassword(String email, String password) {
    return databaseHelper.createUserWithEmailAndPassword(email,password);
  }

  @Override
  public void writeNewUser(String userId, String email, String password, String name) {
    databaseHelper.writeNewUser(userId,email,password,name);
  }
}

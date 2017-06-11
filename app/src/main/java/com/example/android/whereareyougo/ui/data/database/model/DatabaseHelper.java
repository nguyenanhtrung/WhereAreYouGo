package com.example.android.whereareyougo.ui.data.database.model;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.ProviderQueryResult;

/**
 * Created by nguyenanhtrung on 09/06/2017.
 */

public interface DatabaseHelper {
  public Task<ProviderQueryResult> getProviderForEmail(String email);
}

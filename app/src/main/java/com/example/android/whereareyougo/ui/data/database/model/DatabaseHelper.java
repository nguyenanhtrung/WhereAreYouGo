package com.example.android.whereareyougo.ui.data.database.model;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.ProviderQueryResult;

/**
 * Created by nguyenanhtrung on 09/06/2017.
 */

public interface DatabaseHelper {

  Task<ProviderQueryResult> getProviderForEmail(String email);

  Task<AuthResult> createUserWithEmailAndPassword(String email, String password);

  void writeNewUser(String userId, String email, String password, String name);

  Task<AuthResult> signInWithEmailAndPassworđ(String email, String password);
}

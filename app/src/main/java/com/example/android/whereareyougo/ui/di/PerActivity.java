package com.example.android.whereareyougo.ui.di;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import javax.inject.Qualifier;
import javax.inject.Scope;

/**
 * Created by nguyenanhtrung on 09/06/2017.
 */

@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface PerActivity {

}

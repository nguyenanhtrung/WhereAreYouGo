package com.example.android.whereareyougo.ui.ui.base;

/**
 * Created by nguyenanhtrung on 09/06/2017.
 */

public interface MvpPresenter<V extends MvpView> {

  void onAttach(V view);

  void onDetach();


}

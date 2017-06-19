package com.example.android.whereareyougo.ui.ui.map;

import com.example.android.whereareyougo.ui.data.manager.DataManager;
import com.example.android.whereareyougo.ui.ui.base.BasePresenter;
import com.example.android.whereareyougo.ui.utils.MyKey;
import javax.inject.Inject;

/**
 * Created by nguyenanhtrung on 15/06/2017.
 */

public class MapPresenter<V extends MapMvpView> extends BasePresenter<V> implements
    MapMvpPresenter<V>{

  @Inject
  public MapPresenter(DataManager dataManager) {
    super(dataManager);
  }


  @Override
  public int getMapTypeChoose(String[] maptype, String choose) {
    if (choose.equals(maptype[0])){
      return MyKey.MAP_TYPE_NORMAL;
    }

    if (choose.equals(maptype[1])){
      return MyKey.MAP_TYPE_HYBRID;
    }

    if (choose.equals(maptype[2])){
      return MyKey.MAP_TYPE_SATELLITE;
    }

    if (choose.equals(maptype[3])){
      return MyKey.MAP_TYPE_TERRAIN;
    }

    return MyKey.MAP_TYPE_NONE;
  }

  @Override
  public void onClickFloatButtonMapType() {
    getMvpView().showDialogChooseMapType();
  }
}

package com.example.android.whereareyougo.ui.data.database.entity;

/**
 * Created by nguyenanhtrung on 06/07/2017.
 */

public class Friend {
    private String id;
    private boolean permissionFollow = false;

    public Friend() {

    }

    public Friend(String id, boolean permissionFollow) {
        this.id = id;
        this.permissionFollow = permissionFollow;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isPermissionFollow() {
        return permissionFollow;
    }

    public void setPermissionFollow(boolean permissionFollow) {
        this.permissionFollow = permissionFollow;
    }
}

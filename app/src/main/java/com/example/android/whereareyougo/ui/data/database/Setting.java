package com.example.android.whereareyougo.ui.data.database;

/**
 * Created by nguyenanhtrung on 13/08/2017.
 */

public class Setting {
    private String settingName;
    private int settingIconId;
    private String settingContent;

    public Setting(String settingName, int settingIconId, String settingContent) {
        this.settingName = settingName;
        this.settingIconId = settingIconId;
        this.settingContent = settingContent;
    }

    public Setting() {
    }

    public String getSettingName() {
        return settingName;
    }

    public void setSettingName(String settingName) {
        this.settingName = settingName;
    }

    public int getSettingIconId() {
        return settingIconId;
    }

    public void setSettingIconId(int settingIconId) {
        this.settingIconId = settingIconId;
    }

    public String getSettingContent() {
        return settingContent;
    }

    public void setSettingContent(String settingContent) {
        this.settingContent = settingContent;
    }
}

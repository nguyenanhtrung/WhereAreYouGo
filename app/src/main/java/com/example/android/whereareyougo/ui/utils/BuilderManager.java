package com.example.android.whereareyougo.ui.utils;

import com.nightonke.boommenu.BoomButtons.TextOutsideCircleButton;

/**
 * Created by nguyenanhtrung on 09/07/2017.
 */

public class BuilderManager {
    public static TextOutsideCircleButton.Builder getTextOutsideCircleButtonBuilder(int textId, int imageId) {
        return new TextOutsideCircleButton.Builder()
                .normalImageRes(imageId)
                .normalTextRes(textId);
    }
}

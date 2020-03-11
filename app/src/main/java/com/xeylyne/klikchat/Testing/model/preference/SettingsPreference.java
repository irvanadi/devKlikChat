package com.xeylyne.klikchat.Testing.model.preference;

import android.graphics.Color;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;

/**
 * Created by m-hasan on 02/12/18 with love.
 */
public class SettingsPreference extends Preference {

    @DrawableRes
    private int image;

    private String status;
    @ColorInt
    private int colorStatus;

    public SettingsPreference(@NonNull String key, String title, String summary, @DrawableRes int image, String status) {
        this.type = TYPE.GENERAL;
        this.key = key;
        this.title = title;
//        if (contact.getCategoryOrder() == ContactCategory.CATEGORY_PROFILE) {
//            this.title = KlikChatApp.getContext().getResources().getString(R.string.you);
//        }
        this.summary = summary;
        this.image = image;
        this.colorStatus = Color.parseColor("#22995C");
        this.status = status;
    }

    public int getColorStatus() {
        return colorStatus;
    }

    public void setColorStatus(int colorStatus) {
        this.colorStatus = colorStatus;
    }

    public int getImage() {
        return image;
    }

    public String getStatus() {
        return status;
    }
}

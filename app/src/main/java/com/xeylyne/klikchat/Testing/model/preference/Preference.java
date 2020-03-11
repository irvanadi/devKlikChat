package com.xeylyne.klikchat.Testing.model.preference;

import android.graphics.Color;
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;

import com.xeylyne.klikchat.R;

/**
 * Created by m-hasan on 02/12/18 with love.
 */
public class Preference {
    enum TYPE {MENU, SWITCH, MEDIA, GENERAL, MENU_WITH_BUTTON}

    protected TYPE type;
    protected String key;
    protected String categoryKey;
    protected String title;
    protected String summary;
    @DrawableRes
    protected int icon;
    @ColorInt
    private int colorTitle;
    @ColorInt
    private int colorIcon;
    private OnClickListener clickListener;
    private boolean enabled = true;

    Preference() {
        this.key = "";
        this.type = TYPE.MENU;
        this.title = null;
        this.summary = null;
        this.colorTitle = Color.BLACK;
        this.colorIcon = Color.parseColor("#FF939598");
        this.icon = R.drawable.drawable_null;
        this.clickListener = null;
    }

    public Preference(String title, @DrawableRes int icon) {
        this();
        this.title = title;
        this.icon = icon;
    }

    public Preference(String title, String summary, @DrawableRes int icon) {
        this(title, icon);
        this.summary = summary;
    }

    public Preference(String key, String title, String summary, @DrawableRes int icon) {
        this(title, summary, icon);
        this.key = key;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setColor(@ColorInt int color) {
        this.colorTitle = color;
        this.colorIcon = color;
    }

    public String getCategoryKey() {
        return categoryKey;
    }

    void setCategoryKey(String categoryKey) {
        this.categoryKey = categoryKey;
    }

    @ColorInt
    public int getColorTitle() {
        return colorTitle;
    }

    public void setColorTitle(@ColorInt int colorTitle) {
        this.colorTitle = colorTitle;
    }

    @ColorInt
    public int getColorIcon() {
        return colorIcon;
    }

    public void setColorIcon(@ColorInt int colorIcon) {
        this.colorIcon = colorIcon;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public View.OnClickListener getClickListener() {
        if (clickListener == null) return null;
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onClick(Preference.this);
            }
        };
    }

    public void setClickListener(OnClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public TYPE getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }

    public String getSummary() {
        return summary;
    }

    @DrawableRes
    public int getIcon() {
        return icon;
    }

    public interface OnClickListener {
        void onClick(Preference preference);
    }
}

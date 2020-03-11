package com.xeylyne.klikchat.Testing.model.preference;

import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by m-hasan on 02/12/18 with love.
 */
public class ExtraButtonPreference extends Preference {
    private List<Button> extraButton = new ArrayList<>();
    private int limitButton = 3;

    public ExtraButtonPreference() {
        this.type = TYPE.MENU_WITH_BUTTON;
    }

    public ExtraButtonPreference(String key, String title, String summary, int icon, int limitButton) {
        super(key, title, summary, icon);
        this.limitButton = limitButton;
    }

    public ExtraButtonPreference(String title, String summary, int icon, int limitButton) {
        super(title, summary, icon);
        this.limitButton = limitButton;
    }

    public ExtraButtonPreference(int limitButton) {
        this.type = TYPE.MENU_WITH_BUTTON;
        this.limitButton = limitButton;
    }

    public void addButton(Button button) {
        if (extraButton.size() == limitButton) return;
        extraButton.add(button);
    }

    @Nullable
    public Button getButton(int index) {
        if (index > extraButton.size() - 1) return null;
        return extraButton.get(index);
    }

    public int getButtonCount() {
        return extraButton.size();
    }

    public static final class Button {
        @DrawableRes
        private int icon;
        private View.OnClickListener clickListener;

        public Button(@DrawableRes int icon, View.OnClickListener clickListener) {
            this.icon = icon;
            this.clickListener = clickListener;
        }

        public void setIcon(@DrawableRes int icon) {
            this.icon = icon;
        }

        public void setClickListener(View.OnClickListener clickListener) {
            this.clickListener = clickListener;
        }

        @DrawableRes
        public int getIcon() {
            return icon;
        }

        public View.OnClickListener getClickListener() {
            return clickListener;
        }
    }
}

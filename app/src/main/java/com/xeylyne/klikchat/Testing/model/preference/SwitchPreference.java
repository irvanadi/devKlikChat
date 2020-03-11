package com.xeylyne.klikchat.Testing.model.preference;

import android.view.View;

import androidx.appcompat.widget.SwitchCompat;

/**
 * Created by m-hasan on 02/12/18 with love.
 */
public class SwitchPreference extends Preference {
    private boolean state = false;
    private OnSwitchClickListener switchClickListener;
    private boolean mergeSwitchClick = false;
    private boolean switchEnabled = true;

    public SwitchPreference() {
        this.type = TYPE.SWITCH;
    }

    public SwitchPreference(String key, String title, String summary, int icon) {
        super(key, title, summary, icon);
        this.type = TYPE.SWITCH;
    }

    public SwitchPreference(String key, String title, String summary, int icon, boolean state) {
        this(key, title, summary, icon);
        this.state = state;
    }

    public SwitchPreference(boolean state) {
        this.type = TYPE.MENU_WITH_BUTTON;
        this.state = state;
    }

    public boolean isSwitchEnabled() {
        return switchEnabled;
    }

    public void setSwitchEnabled(boolean switchEnabled) {
        this.switchEnabled = switchEnabled;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public boolean getState() {
        return state;
    }

    @Override
    public View.OnClickListener getClickListener() {
        final View.OnClickListener switchListener = super.getClickListener();
        if (switchListener != null) {
            return new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (v instanceof SwitchCompat) {
                        SwitchCompat switchCompat = (SwitchCompat) v;
                        state = switchCompat.isChecked();
                    }
                    switchListener.onClick(v);
                }
            };
        }

        return null;
    }

    public View.OnClickListener getSwitchClickListener() {
        if (switchClickListener == null && getClickListener() == null) return null;
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (switchClickListener != null) {
                    switchClickListener.onClick(SwitchPreference.this, (SwitchCompat) v);
                    if (!mergeSwitchClick) return;
                }

                if (getClickListener() != null) {
                    getClickListener().onClick(v);
                }
            }
        };
    }

    public void setSwitchClickListener(OnSwitchClickListener switchClickListener) {
        setSwitchClickListener(switchClickListener, false);
    }

    public void setSwitchClickListener(OnSwitchClickListener switchClickListener, boolean mergeSwitchClick) {
        this.switchClickListener = switchClickListener;
        this.mergeSwitchClick = mergeSwitchClick;
    }

    public interface OnSwitchClickListener {
        void onClick(SwitchPreference preference, SwitchCompat switchCompat);
    }
}

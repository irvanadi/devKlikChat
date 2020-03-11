package com.xeylyne.klikchat.Testing.model.preference;

import androidx.annotation.NonNull;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by m-hasan on 02/12/18 with love.
 */
public class CategoryPreference {
    public enum TYPE {STANDARD, MEDIA, SEARCH, WITH_SUMMARY}

    protected TYPE type = TYPE.STANDARD;
    private String key;
    private String title;
    private List<Preference> preferences;
    private String summary;

    public CategoryPreference(String key) {
        this.key = key;
        this.title = null;
        this.preferences = new ArrayList<>();
    }

    public CategoryPreference(String key, String title) {
        this(key);
        this.title = title;
        this.preferences = new ArrayList<>();
    }

    public CategoryPreference(String key, String title, int mediaCount) {
        this(key, title);
        this.type = TYPE.MEDIA;
        this.summary = String.valueOf(mediaCount);
    }

    public CategoryPreference(String key, String title, String summary) {
        this(key, title);
        this.type = TYPE.WITH_SUMMARY;
        this.summary = summary;
    }

    public CategoryPreference(String key, @NonNull List<Preference> preferences) {
        this(key);
        addAllPreferences(preferences);
    }

    public CategoryPreference(String key, String title, List<Preference> preferences) {
        this(key, preferences);
        this.title = title;
    }

    public CategoryPreference(String key, String title, @NonNull List<Preference> preferences, int mediaCount) {
        this(key, title, mediaCount);
        addAllPreferences(preferences);
    }

    public CategoryPreference(String key, String title, @NonNull List<Preference> preferences, String summary) {
        this(key, title, summary);
        addAllPreferences(preferences);
    }

    private void addAllPreferences(@NonNull List<Preference> preferences) {
        for (Preference preference : preferences) addPreference(preference);
    }

    private boolean wrongType(Preference preference) {
        return (type == TYPE.STANDARD && preference instanceof MediaPreference) ||
                (type == TYPE.SEARCH && preference instanceof MediaPreference) ||
                (type == TYPE.WITH_SUMMARY && preference instanceof MediaPreference) ||
                (type == TYPE.MEDIA && !(preference instanceof MediaPreference));
    }

    public void appendPreferenceFirst(Preference preference) {
        if (wrongType(preference)) return;

        preference.setCategoryKey(key);
        preferences.add(0, preference);
    }

    public void addPreference(Preference preference) {
        if (wrongType(preference)) return;

        preference.setCategoryKey(key);
        preferences.add(preference);
    }

    public void setType(TYPE type) {
        this.type = type;

        ArrayList<Preference> beRemoved = new ArrayList<>();
        for (Preference preference : preferences) {
            if (wrongType(preference)) beRemoved.add(preference);
        }

        preferences.removeAll(beRemoved);
    }

    public String getKey() {
        return key;
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

    public List<Preference> getPreferences() {
        return preferences;
    }
}

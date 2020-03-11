package com.xeylyne.klikchat.Testing.model.preference;

/**
 * Created by m-hasan on 02/12/18 with love.
 */
public class MediaPreference extends Preference {
    private String image = null;

    public MediaPreference(String image) {
        this.type = TYPE.MEDIA;
        this.image = image;
    }

    public String getImage() {
        return image;
    }
}

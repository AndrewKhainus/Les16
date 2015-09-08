package com.radomar.les16.models;

import java.net.URL;

/**
 * Created by Radomar on 01.09.2015.
 */
public class SpaceItem {

    private String mDescription;
    private URL mImageUrl;

    public SpaceItem(URL imageUrl, String description) {
        this.mImageUrl = imageUrl;
        this.mDescription = description;
    }

    public String getDescription() {
        return mDescription;
    }

    public URL getImageUrl() {
        return mImageUrl;
    }

}

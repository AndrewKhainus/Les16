package com.radomar.les16.models;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Radomar on 01.09.2015.
 */
public final class CometModel {

    public String name;
    public String timePeriod;
    public String imgUrl;

    public String getDescription() {
        String descrition = "Name: " + name + '\n' +
                "Time period: " + timePeriod + "years";
        return descrition;
    }

    public URL getImageUrl() {
        try {
            return new URL(imgUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }
}

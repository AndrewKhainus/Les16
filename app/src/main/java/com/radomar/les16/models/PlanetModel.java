package com.radomar.les16.models;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Radomar on 8/28/2015.
 */
public final class PlanetModel {

    public String place;
    public String type;
    public String name;
    public String mass;
    public String orbitalSpeed;
    public String imgUrl;

    public String getDescription() {
        String descrition = "Name: " + name + '\n' +
                "Place: " + place + '\n' +
                "Type: " + type + '\n' +
                "Mass: " + mass + '\n' +
                "Orbital Speed: " + orbitalSpeed;
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

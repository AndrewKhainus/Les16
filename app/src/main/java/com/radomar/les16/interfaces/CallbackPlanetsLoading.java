package com.radomar.les16.interfaces;

import com.radomar.les16.models.SolarSystemModel;

/**
 * Created by Radomar on 30.08.2015.
 */
public interface CallbackPlanetsLoading {
    void onSuccess(SolarSystemModel solarSystemModel);
    void onFailure(String errorMessage);
}

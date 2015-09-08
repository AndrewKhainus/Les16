package com.radomar.les16.interfaces;

import com.radomar.les16.models.BugIdModel;

/**
 * Created by Radomar on 30.08.2015.
 */
public interface CallbackCommentsLoading {
    void onSuccess(BugIdModel bugIdModel);
    void onFailure(String errorMessage);
}

package com.radomar.les16.tasks;

import android.content.Context;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.radomar.les16.interfaces.CallbackPlanetsLoading;
import com.radomar.les16.models.SolarSystemModel;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Radomar on 01.09.2015.
 */
public class AsyncTaskDescriptionLoader extends AsyncTask<Void, Void, SolarSystemModel> {

    private CallbackPlanetsLoading mCallbackPlanetsLoading;
    private Context mContext;

    public AsyncTaskDescriptionLoader(CallbackPlanetsLoading callbackPlanetsLoading, Context context) {
        this.mCallbackPlanetsLoading = callbackPlanetsLoading;
        this.mContext = context;
    }

    @Override
    protected SolarSystemModel doInBackground(Void... params) {
        SolarSystemModel solarSystemModel = null;

        try {
            solarSystemModel = loadData();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return solarSystemModel;
    }

    @Override
    protected void onPostExecute(SolarSystemModel solarSystemModel) {
        super.onPostExecute(solarSystemModel);
        if (solarSystemModel != null){
            mCallbackPlanetsLoading.onSuccess(solarSystemModel);
        } else mCallbackPlanetsLoading.onFailure("Error parsing");
    }

    private SolarSystemModel loadData() throws IOException, JSONException {

        final String jsonString = getJsonString();

        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();

        SolarSystemModel solarSystemModel = gson.fromJson(jsonString, SolarSystemModel.class);

        return solarSystemModel;
    }

    private String getJsonString() throws IOException {
        InputStream is = mContext.getAssets().open("solar_system.txt");
        final BufferedReader br = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null){
            sb.append(line);
        }
        return sb.toString();
    }
}

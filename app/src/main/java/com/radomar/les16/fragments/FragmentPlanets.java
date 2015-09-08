package com.radomar.les16.fragments;

import android.app.Fragment;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.radomar.les16.R;
import com.radomar.les16.adapters.PlanetsExpListAdapter;
import com.radomar.les16.interfaces.CallbackPlanetsLoading;
import com.radomar.les16.models.CometModel;
import com.radomar.les16.models.PlanetModel;
import com.radomar.les16.models.SolarSystemModel;
import com.radomar.les16.models.SpaceItem;
import com.radomar.les16.models.SpotModel;
import com.radomar.les16.models.TransNeptunianObjectModel;
import com.radomar.les16.tasks.AsyncTaskDescriptionLoader;

import java.util.ArrayList;

/**
 * Created by Radomar on 30.08.2015.
 */
public class FragmentPlanets extends Fragment implements CallbackPlanetsLoading {

    private ExpandableListView mExpListView;
    private PlanetsExpListAdapter mAdapter;
    private SolarSystemModel mSolarSystemModel;
    private TextView mSpotHeader;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_planets, container, false);
        mExpListView = (ExpandableListView)view.findViewById(R.id.exListView_FP);
        mSpotHeader     = (TextView) view.findViewById(R.id.tvSpot_FP);

        if (isNetworkAvailable()) {
            new AsyncTaskDescriptionLoader(FragmentPlanets.this, getActivity()).execute();
        }

        return view;
    }

    @Override
    public void onSuccess(SolarSystemModel solarSystemModel) {
        mSolarSystemModel = solarSystemModel;
        setSolarSystemModel(solarSystemModel.spot);

        initExpListAdapter();
        mExpListView.setAdapter(mAdapter);
    }

    @Override
    public void onFailure(String errorMessage) {
        Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT).show();
    }

    private void setSolarSystemModel(SpotModel model) {

        mSpotHeader.setText(
                "Name: " + model.name + '\n' +
                        "Age: " + model.age + '\n' +
                        "Mass: " + model.mass + '\n' +
                        "Planets: " + model.planetsCount + '\n' +
                        "Orbital Speed: " + model.orbitalSpeed
        );
    }

    private void initExpListAdapter() {
        ArrayList<ArrayList<SpaceItem>> groups = new ArrayList<>();
        ArrayList<SpaceItem> planets = new ArrayList<>();
        ArrayList<SpaceItem> transNeptunianObjects = new ArrayList<>();
        ArrayList<SpaceItem> comets = new ArrayList<>();
        ArrayList<SpaceItem> UFO = new ArrayList<>();
        ArrayList<SpaceItem> Juvbaku = new ArrayList<>();

        for (PlanetModel pm: mSolarSystemModel.planets) {
            planets.add(new SpaceItem(pm.getImageUrl(), pm.getDescription()));
        }
        for (CometModel cm: mSolarSystemModel.comets) {
            comets.add(new SpaceItem(cm.getImageUrl(), cm.getDescription()));
        }
        for (TransNeptunianObjectModel tnom: mSolarSystemModel.transNeptunianObjects) {
            transNeptunianObjects.add(new SpaceItem(tnom.getImageUrl(), tnom.getDescription()));
        }

        groups.add(planets);
        groups.add(UFO);
        groups.add(Juvbaku);

        mAdapter = new PlanetsExpListAdapter(getActivity(), groups);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}

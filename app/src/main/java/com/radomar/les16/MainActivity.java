package com.radomar.les16;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.radomar.les16.fragments.FragmentComments;
import com.radomar.les16.fragments.FragmentPlanets;

public class MainActivity extends Activity implements View.OnClickListener{

    private Button mOpenFragmentPlanets;
    private Button mOpenFragmentComments;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();
        setListener();
    }

    private void findViews() {
        mOpenFragmentPlanets = (Button) findViewById(R.id.btPlanets_AM);
        mOpenFragmentComments  = (Button) findViewById(R.id.btComents_AM);
    }

    private void setListener() {
        mOpenFragmentPlanets.setOnClickListener(this);
        mOpenFragmentComments.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btPlanets_AM:
                openFragmentPlanets();
                break;
            case R.id.btComents_AM:
                openFragmentComments();
                break;
        }
    }

    private void openFragmentPlanets() {
        FragmentPlanets fragmentPlanets = new FragmentPlanets();

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

        fragmentTransaction.replace(R.id.rlForFragment_AM, fragmentPlanets);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commit();
    }

    private void openFragmentComments() {
        FragmentComments fragmentComments = new FragmentComments();

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

        fragmentTransaction.replace(R.id.rlForFragment_AM, fragmentComments);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commit();
    }
}

package com.radomar.les16.fragments;

import android.app.Fragment;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.radomar.les16.R;
import com.radomar.les16.adapters.CommentsRecyclerAdapter;
import com.radomar.les16.interfaces.CallbackCommentsLoading;
import com.radomar.les16.models.BugIdModel;
import com.radomar.les16.models.CommentModel;
import com.radomar.les16.tasks.AsyncJsonCommentsLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Radomar on 29.08.2015.
 */
public class FragmentComments extends Fragment implements CallbackCommentsLoading {

    private RecyclerView mRecyclerView;
    private CommentsRecyclerAdapter mAdapter;
    private List<CommentModel> mData = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_comments, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.rvList_FC);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        if (isNetworkAvailable()) {
            new AsyncJsonCommentsLoader(FragmentComments.this).execute();
        }

        return view;
    }

    @Override
    public void onSuccess(BugIdModel bugIdModel) {

        for (int i = 0; i < bugIdModel.comments.size(); i++) {
            mData = bugIdModel.comments;
        }
        mAdapter = new CommentsRecyclerAdapter(getActivity(), mData);
        mRecyclerView.setAdapter(mAdapter);

    }

    @Override
    public void onFailure(String errorMessage) {
        Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT).show();
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}

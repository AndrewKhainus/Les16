package com.radomar.les16.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.radomar.les16.R;
import com.radomar.les16.models.SpaceItem;
import com.radomar.les16.tasks.AsyncTaskImageLoader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Radomar on 30.08.2015.
 */
public class PlanetsExpListAdapter extends BaseExpandableListAdapter {

    private ArrayList<ArrayList<SpaceItem>> mGroups;
    private Context mContext;

    public PlanetsExpListAdapter (Context context,ArrayList<ArrayList<SpaceItem>> groups){
        mContext = context;
        mGroups = groups;
    }

    @Override
    public int getGroupCount() {
        return mGroups.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mGroups.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mGroups.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mGroups.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView,
                             ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.exp_list_group_view, null);
        }

        TextView textGroup = (TextView) convertView.findViewById(R.id.textGroup);
        switch (groupPosition) {
            case 0:
                textGroup.setText("Planets");
                break;
            case 1:
                textGroup.setText("Trans-Neptunian objects");
                break;
            case 2:
                textGroup.setText("Comets");
                break;
        }

        return convertView;

    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
                             View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.exp_list_child_view, null);
        }

        TextView description = (TextView)convertView.findViewById(R.id.tvDescription_CV);
        final ImageView image = (ImageView) convertView.findViewById(R.id.ivSpaceObject_CV);

        description.setText(mGroups.get(groupPosition).get(childPosition).getDescription());

        URL imageUrl = mGroups.get(groupPosition).get(childPosition).getImageUrl();
        final String fileName = imageUrl.toString().substring(imageUrl.toString().lastIndexOf('/') + 1);
        File imageFile = new File(mContext.getCacheDir(), fileName);

        if (imageFile.exists()) {
            image.setImageBitmap(BitmapFactory.decodeFile(imageFile.getAbsolutePath()));
        } else {
            new AsyncTaskImageLoader() {
                @Override
                protected void onPostExecute(Bitmap bitmap) {
                    saveImage(bitmap, fileName);
                    image.setImageBitmap(bitmap);
                }
            }.execute(imageUrl);
        }

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    private void saveImage(Bitmap bmImg, String fileName) {

        File ImageFile;
        try{
            ImageFile = new File(mContext.getCacheDir(), fileName);

            FileOutputStream out = new FileOutputStream(ImageFile);
            bmImg.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
package com.radomar.les16.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.radomar.les16.R;
import com.radomar.les16.models.CommentModel;

import java.util.List;

/**
 * Created by Radomar on 30.08.2015.
 */
public class CommentsRecyclerAdapter extends RecyclerView.Adapter<CommentsRecyclerAdapter.CustomViewHolder> {

    private final Context mContext;
    private List<CommentModel> mData;

    public CommentsRecyclerAdapter(Context context, List<CommentModel> data) {
        mContext = context;
        mData = data;
    }


    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.comments_list_item, viewGroup, false);
        return new CustomViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder customViewHolder, int i) {
        customViewHolder.onBind();
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {

        TextView text;

        public CustomViewHolder(View itemView) {
            super(itemView);

            text = (TextView)itemView.findViewById(R.id.tvText_CLI);
        }

        public void onBind() {
            CommentModel commentModel = mData.get(getAdapterPosition());
            setText(commentModel);
        }

        private void setText(CommentModel commentModel) {
            text.setText("attachment_id....." + commentModel.attachment_id + '\n' +
                            "author............." + commentModel.author + '\n' +
                            "bug_id............." + commentModel.bug_id + '\n' +
                            "creation_time..." + commentModel.creation_time + '\n' +
                            commentModel.text + '\n' +
                            "time..............." + commentModel.time);
        }
    }
}
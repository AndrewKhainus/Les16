package com.radomar.les16.tasks;

import android.os.AsyncTask;

import com.radomar.les16.interfaces.CallbackCommentsLoading;
import com.radomar.les16.models.BugIdModel;
import com.radomar.les16.models.CommentModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Radomar on 30.08.2015.
 */
public class AsyncJsonCommentsLoader extends AsyncTask<Void, Void, BugIdModel> {

    private static final String STRING_URL = "https://bugzilla.mozilla.org/rest/bug/707428/comment";
    private CallbackCommentsLoading mCallbackCommentsLoading;

    public AsyncJsonCommentsLoader(CallbackCommentsLoading callbackCommentsLoading) {
        this.mCallbackCommentsLoading = callbackCommentsLoading;
    }

    @Override
    protected BugIdModel doInBackground(Void... params) {
        BugIdModel bugIdModel = null;
            try {
                bugIdModel = loadData();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        return bugIdModel;
    }

    @Override
    protected void onPostExecute(BugIdModel bugIdModel) {
        super.onPostExecute(bugIdModel);
        if (bugIdModel != null) {
            mCallbackCommentsLoading.onSuccess(bugIdModel);
        } else {
            mCallbackCommentsLoading.onFailure("Error parsing");
        }
    }

    private BugIdModel loadData() throws IOException, JSONException {
        BugIdModel bugIdModel = new BugIdModel();
        bugIdModel.comments = new ArrayList<>();

        final String jsonString = getJsonString(STRING_URL);

        JSONArray jsonComments = new JSONObject(jsonString)
                                .getJSONObject("bugs")
                                .getJSONObject("707428")
                                .getJSONArray("comments");

        for (int i = 0; i < jsonComments.length(); i++) {
            CommentModel commentModel = getComment(jsonComments.getJSONObject(i));
            bugIdModel.comments.add(commentModel);
        }

        return bugIdModel;
    }

    private CommentModel getComment(JSONObject jPlanet) throws JSONException, IOException {
        CommentModel commentModel = new CommentModel();

        commentModel.attachment_id = jPlanet.getString("attachment_id");
        commentModel.author         = jPlanet.getString("author");
        commentModel.bug_id         = jPlanet.getString("bug_id");
        commentModel.creation_time  = jPlanet.getString("creation_time");
        commentModel.creator        = jPlanet.getString("creator");
        commentModel.id             = jPlanet.getString("id");
        commentModel.is_private     = jPlanet.getString("is_private");
        commentModel.raw_text       = jPlanet.getString("raw_text");
        commentModel.tags           = jPlanet.getString("tags");
        commentModel.text           = jPlanet.getString("text");
        commentModel.time           = jPlanet.getString("time");

        return commentModel;
    }

    private String getJsonString(String url) {

        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(new URL(url).openStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                sb.append(inputLine);
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sb.toString();
    }
}

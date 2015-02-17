package fr.ecp.sio.piopio.loaders;

import android.content.Context;


import java.util.List;

import fr.ecp.sio.piopio.model.ApiClient;
import fr.ecp.sio.piopio.model.Talk;

import android.support.v4.content.AsyncTaskLoader;

/**
 * Created by Diana on 05/12/2014.
 */
public class TalksLoader extends AsyncTaskLoader<List<Talk>> {
    private List<Talk> result;

    private String userId;

    public TalksLoader(Context context, String userId) {
        super(context);
        this.userId = userId;
    }

    //network opperations
    @Override
    public List<Talk> loadInBackground() {
       ApiClient client = new ApiClient();
        return client.getUserTweets(userId);
    }


    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        if (result != null)
            deliverResult(result);
        if (takeContentChanged() || result == null) forceLoad();
    }

    @Override
    protected void onStopLoading() {
        super.onStopLoading();
        cancelLoad();
    }

    @Override
    public void deliverResult(List<Talk> data) {
        result = data;
        super.deliverResult(data);
    }
}

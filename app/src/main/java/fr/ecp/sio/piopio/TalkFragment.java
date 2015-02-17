package fr.ecp.sio.piopio;

import android.os.Bundle;
import android.support.annotation.Nullable;


import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.List;

import fr.ecp.sio.piopio.TalkActivity;
import fr.ecp.sio.piopio.adapters.InterfaceDataComunication;
import fr.ecp.sio.piopio.adapters.TalksAdapter;
import fr.ecp.sio.piopio.loaders.TalksLoader;
import fr.ecp.sio.piopio.model.Talk;
import fr.ecp.sio.piopio.model.User;

/**
 * Created by Diana on 05/12/2014.
 */
public class TalkFragment extends ListFragment implements LoaderManager.LoaderCallbacks<List<Talk>>, InterfaceDataComunication {

    private static final int LOADER_TALKS = 1000;
    private static final String ARGS_USER = "user";

    private ListView mListView;
    private boolean showOnlyBestFriends;
    ListAdapter mListAdapter;

    private User mUser;

    public static Bundle newArguments(User user) {
        Bundle args = new Bundle();
        args.putParcelable(ARGS_USER, user);
        return args;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        mListAdapter = new TalksAdapter();
        getListView().setDividerHeight(0);

    }


    @Override
    public void onStart() {
        super.onStart();
        getLoaderManager().initLoader(LOADER_TALKS, null, this);
    }


    @Override
    public Loader<List<Talk>> onCreateLoader(int i, Bundle bundle) {
        User user = getArguments().getParcelable(ARGS_USER);// userID
        return new TalksLoader(getActivity(), user.getId());

    }

    @Override
    public void onLoadFinished(Loader<List<Talk>> objectLoader, List<Talk> talks) {
        ((TalksAdapter) mListAdapter).setmData(talks);
        setListAdapter(mListAdapter);

    }

    @Override
    public void onLoaderReset(Loader<List<Talk>> loader) {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUser = getArguments().getParcelable(ARGS_USER);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getActivity() instanceof TalkActivity) {
            getActivity().setTitle(mUser.getHandle());
        }
    }


    @Override
    public void refreshData() {
        getLoaderManager().restartLoader(getActivity().getTaskId(), getArguments(), this);
        Log.i("aaa","refreshing data");
        ((TalksAdapter) mListAdapter).notifyDataSetChanged();
    }
}

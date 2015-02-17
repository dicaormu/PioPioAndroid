package fr.ecp.sio.piopio;

import fr.ecp.sio.piopio.adapters.UsersAdapter;
import fr.ecp.sio.piopio.loaders.UserLoader;
import fr.ecp.sio.piopio.model.User;
import fr.ecp.sio.piopio.adapters.InterfaceDataComunication;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v4.app.Fragment;


import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * Created by Diana on 05/12/2014.
 */
public class UsersFragment extends ListFragment implements LoaderManager.LoaderCallbacks<List<User>>, InterfaceDataComunication // el caso general es el ListFragment, porque contiene una lista a condicion aue en el xml se cambie el id a @android:id/list{
{
    private static final int LOADER_USER=0;
    public static final int REQUEST_LOGIN_FOR_POST=2;
    public static final int REQUEST_ADDUSER_FOR_POST=3;
    public static final String FOLLOW_MODE="followMode";
    public static final String SCREEN_USER="screenUser";
    public static final int MODE_FOLLOWERS=1;
    public static final int MODE_FOLLOWINGS=2;

    ListAdapter mListAdapter;
    private boolean mIsMasterDetailedMode;
    public User localUser;
    private int mode;
    UserLoader loader;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mIsMasterDetailedMode=getActivity().findViewById(R.id.tweets_content)==null;

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view,savedInstanceState);

        mListAdapter = new UsersAdapter();
        setListAdapter(mListAdapter);

        getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);//para seleccionar solo un elemento a la vez (solo para la version tablet)


        view.findViewById(R.id.listTweets).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showTalks(localUser);
            }
        });

        view.findViewById(R.id.followUser).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addUser();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.users_fragment,container,false);
    }

    @Override
    public void onStart() {
        super.onStart();
        getLoaderManager().initLoader(LOADER_USER,null,this);


    }

    @Override
    public Loader<List<User>> onCreateLoader(int i, Bundle bundle) {

        localUser=getArguments().getParcelable(SCREEN_USER);
        int met=getArguments().getInt(FOLLOW_MODE);
        mode=met;
        loader=new UserLoader(getActivity(),localUser.getId(),UserLoader.UserMethods.fromInt(met));
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<List<User>> objectLoader, List<User> users) {

        ((UsersAdapter)mListAdapter).setmData(users);
    }

    @Override
    public void onLoaderReset(Loader<List<User>> objectLoader) {

    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        User user= (User) mListAdapter.getItem(position);
        showTalks(user);
    }

    private void showTalks(User user){
        if(mIsMasterDetailedMode){//Portrait view
            Intent intent=new Intent(getActivity(),TalkActivity.class);
            intent.putExtras(TalkFragment.newArguments(user));
            startActivity(intent);
        }
        else{
            Fragment talkfragment = new TalkFragment();//Landscape view
            Bundle args = new Bundle();
            Log.i("information","user "+user.getId());
            args.putString(TalkActivity.EXTRA_USR_ID, user.getId());
            talkfragment.setArguments(TalkFragment.newArguments(user));

            getFragmentManager().
                    beginTransaction().replace(R.id.tweets_content, talkfragment).commit();
        }
    }

    private void post(){


            Intent intent=new Intent(getActivity(),PostActivity.class);
            startActivity(intent);


    }

    private void addUser(){


            Intent intent=new Intent(getActivity(),AddUserActivity.class);
            startActivityForResult(intent, 1);



    }


   /* private void loginRequest(){

        LoginFragment  fragment = new LoginFragment();
        fragment.setTargetFragment(this,REQUEST_LOGIN_FOR_POST);
        fragment.show(getFragmentManager(),"idLoginFragment");
    }*/

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==REQUEST_LOGIN_FOR_POST && resultCode == PostActivity.RESULT_OK){
            post();
        }
        if(requestCode==REQUEST_ADDUSER_FOR_POST && resultCode == PostActivity.RESULT_OK){
          
            refreshData();
        }

    }

    @Override
    public void refreshData() {
        getLoaderManager().restartLoader(getActivity().getTaskId(),getArguments(),this);

    }
}

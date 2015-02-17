package fr.ecp.sio.piopio;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;


import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.IOException;

import fr.ecp.sio.piopio.model.ApiClient;
import fr.ecp.sio.piopio.model.User;
import fr.ecp.sio.piopio.adapters.InterfaceDataComunication;

/**
 * Created by Diana on 05/12/2014.
 *
 */
public class TalkActivity extends ActionBarActivity {
    public static final String EXTRA_USR_ID = "userId";
    public static final int POST_TALK_ACTIVITY=3;
    private InterfaceDataComunication activity;//Interface for the comunication for refreshing the data

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.talk_activity);
        if (savedInstanceState == null) {
            Fragment talkFragment = new TalkFragment();
            talkFragment.setArguments(getIntent().getExtras());
            getSupportFragmentManager().beginTransaction()
                    .add(android.R.id.content, talkFragment)
                    .commit();
            activity=(InterfaceDataComunication)talkFragment;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        User user =getIntent().getExtras().getParcelable("user");
        String localId = AccountManager.getUserId(TalkActivity.this);
        if(localId.equals(user.getId())){//the user is the owner of the account
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_talk, menu);
        }
        else {//im consulting the tweets of someone else
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_user, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        User user= getIntent().getExtras().getParcelable("user");
        Bundle args = new Bundle();

        switch (item.getItemId()) {
            case R.id.action_delete:

                new AlertDialog.Builder(TalkActivity.this)

                        .setTitle(R.string.unfollow_user)
                        .setMessage(getString(R.string.delete_confirm, user.getHandle()))
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                unfollowSomeone();
                            }
                        }).setNegativeButton(android.R.string.no, null).show();
                Toast.makeText(this, "Refresh selected", Toast.LENGTH_SHORT)
                        .show();
                break;
            case R.id.action_post_talk:
                Intent intent=new Intent(TalkActivity.this,PostActivity.class);
                startActivityForResult(intent, POST_TALK_ACTIVITY);//waits response for refresing data
                break;
            case R.id.action_show_followers:
             args.putInt(UsersFragment.FOLLOW_MODE,
                        UsersFragment.MODE_FOLLOWERS);
                args.putParcelable(UsersFragment.SCREEN_USER,user);
                showFollow(args);

                break;
            case R.id.action_show_followings:

                args.putInt(UsersFragment.FOLLOW_MODE,
                        UsersFragment.MODE_FOLLOWINGS);
                args.putParcelable(UsersFragment.SCREEN_USER,user);
                showFollow(args);

                break;

        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Show the Activity of the followers ou followings
     * @param args
     */
    private void showFollow(Bundle args){
        finish();
        Intent intentFollow=new Intent(TalkActivity.this,FollowxxActivity.class);
        intentFollow.putExtras(args);
        startActivity(intentFollow);
    }

    /**
     *
     * @param fragment
     */
    @Override
    public void onAttachFragment(android.app.Fragment fragment) {
        super.onAttachFragment(fragment);
        try {
            activity = (InterfaceDataComunication) fragment;

        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement TextClicked");
        }
    }

    /**
     * implementation for un follow someone
     */
    public void unfollowSomeone(){
        User user= TalkActivity.this.getIntent().getExtras().getParcelable("user");

        new AsyncTask<String, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(String... params) {

                try {
                    String handle = AccountManager.getUserId(TalkActivity.this);
                    new ApiClient().unFollowSomeone(handle, params[0]);
                    activity.refreshData();

                    return true;
                } catch (IOException e) {
                    Log.e(TalkActivity.class.getName(), "delete failed", e);
                    return false;
                }
            }

            @Override
            protected void onPostExecute(Boolean success) {
                if (success) {
                    finish();
                    Toast.makeText(TalkActivity.this, R.string.post_success, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(TalkActivity.this, R.string.post_error, Toast.LENGTH_SHORT).show();
                }
            }
        }.execute(user.getId());
    }

    /**
     * result of the activities called
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==POST_TALK_ACTIVITY)
            if(resultCode==PostActivity.RESULT_OK){
                activity.refreshData();
            }
    }
}

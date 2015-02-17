package fr.ecp.sio.piopio;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import fr.ecp.sio.piopio.model.User;

/**
 * Created by Diana on 25/01/2015.
 * Show followers or followings of the selected user
 */
public class FollowxxActivity extends ActionBarActivity {

    public User user = null;
    int mode=1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_followxx);

        mode=getIntent().getExtras().getInt(UsersFragment.FOLLOW_MODE);
        user=getIntent().getExtras().getParcelable(UsersFragment.SCREEN_USER);
        if (AccountManager.isConnected(this)) {
            setTitle( user.getHandle());
        }

        followxx();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
      switch (item.getItemId()) {
            case R.id.action_show_followers:
                mode=UsersFragment.MODE_FOLLOWERS;
                break;
            case R.id.action_show_followings:
                mode=UsersFragment.MODE_FOLLOWINGS;
                break;

        }
        followxx();
        return super.onOptionsItemSelected(item);
    }

    /**
     * Show the fragment of the followers or followings
     */
    private void followxx(){
        Fragment fragment = new UsersFragment();
        Bundle args = new Bundle();
        args.putInt(UsersFragment.FOLLOW_MODE,
                mode);
        args.putParcelable(UsersFragment.SCREEN_USER,
                user);
        fragment.setArguments(args);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content, fragment).commit();
    }


}

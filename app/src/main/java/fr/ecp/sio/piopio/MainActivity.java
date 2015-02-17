package fr.ecp.sio.piopio;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import fr.ecp.sio.piopio.model.User;


public class MainActivity extends ActionBarActivity implements
        ActionBar.TabListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        post(savedInstanceState);
        if(AccountManager.isConnected(this)){
            setTitle(R.string.app_name);
        }

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // for each of the sections in the app, add a tab to the action bar.
        actionBar.addTab(actionBar.newTab().setText(R.string.my_followers)
                .setTabListener(this));
        actionBar.addTab(actionBar.newTab().setText(R.string.my_followings)
                .setTabListener(this));

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(this,SettingsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, show the tab contents in the
        // container view.
        User localUser=new User();
        localUser.setHandle(AccountManager.getUserHandle(this));
        localUser.setId(AccountManager.getUserId(this));

        Fragment fragment = new UsersFragment();
        Bundle args = new Bundle();
        args.putInt(UsersFragment.FOLLOW_MODE,
                tab.getPosition() + 1);
        args.putParcelable(UsersFragment.SCREEN_USER,localUser);
        fragment.setArguments(args);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content, fragment).commit();
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }

    private void post(Bundle bundle){
        if(!AccountManager.isConnected(this)){
            loginRequest(bundle);
        }
    }

    private void loginRequest(Bundle bundle){
        LoginFragment  fragment = new LoginFragment();
        //fragment.setTargetFragment(this.getSupportFragmentManager().getFragment(),2);
        fragment.show(getSupportFragmentManager(),"idLoginFragment");
    }

}

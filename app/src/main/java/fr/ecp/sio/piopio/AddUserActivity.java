package fr.ecp.sio.piopio;



import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
//import android.support.v4.app.LoaderManager;


import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import fr.ecp.sio.piopio.loaders.UserLoader;
import fr.ecp.sio.piopio.model.ApiClient;
import fr.ecp.sio.piopio.model.User;
import fr.ecp.sio.piopio.adapters.SearchUserAdapter;

public class AddUserActivity extends FragmentActivity implements LoaderManager.LoaderCallbacks<List<User>>{

    List<User> users;
    SearchUserAdapter adapter;
    User userAdd=null;

	/** Called when the activity is first created. */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_user_activity);
        adapter=new SearchUserAdapter(this,4,new ArrayList());
        findViewById(R.id.add_user_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFollower();
            }
        });

        AutoCompleteTextView textView = (AutoCompleteTextView) findViewById(R.id.autocomplete_user);

        // Set the adapter for the AutoCompleteTextView
        textView.setAdapter(adapter);

        textView.setOnItemClickListener(new OnItemClickListener() {

            // Display a Toast Message when the user clicks on an item in the AutoCompleteTextView
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {

                Toast.makeText(getApplicationContext(), ""+ arg0.getAdapter().getItem(arg2), Toast.LENGTH_SHORT).show();
                userAdd=null;
                if(arg2>-1)
                    userAdd=(User)arg0.getAdapter().getItem(arg2);
                if(userAdd!=null){
                    ImageView profilePictureView = (ImageView) findViewById(R.id.profile_picture_selected);
                    Picasso.with(arg1.getContext()).load(userAdd.getProfilePicture()).into(profilePictureView);
                    TextView textView=(TextView)findViewById(R.id.handle_selected);
                    textView.setText(userAdd.getHandle());
                }


            }
        });
		// Get a reference to the AutoCompleteTextView
        getSupportLoaderManager().initLoader(0, null, this);
        // Create an ArrayAdapter containing  names

	}

    @Override
    protected void onStart() {
        super.onStart();
   }



    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {

        return super.onCreateView(parent, name, context, attrs);
    }

    @Override
    public Loader<List<User>> onCreateLoader(int id, Bundle args) {
        UserLoader loader=new UserLoader(this,AccountManager.getUserId(this), UserLoader.UserMethods.GET_USERS);
        return loader;


    }

    @Override
    public void onLoadFinished(Loader<List<User>> loader, List<User> data) {
        if(data!=null) {
            users=data;
            adapter.addAll(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<User>> loader) {

    }

    /**
     * Adds a follower for the logged user
     */
    public void addFollower(){
        String content=null;
        if(userAdd!= null)
            content=userAdd.getId();
        new AsyncTask<String, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(String... params) {
                try {
                    String handle = AccountManager.getUserId(AddUserActivity.this);
                    new ApiClient().followSomeone(handle, params[0]);
                    setResult(PostActivity.RESULT_OK);
                    return true;
                } catch (IOException e) {
                    Log.e(PostActivity.class.getName(), "follow failed", e);
                    return false;
                }
            }

            @Override
            protected void onPostExecute(Boolean success) {
                if (success) {
                    finish();//finish activity
                    Toast.makeText(AddUserActivity.this, R.string.post_success, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AddUserActivity.this, R.string.post_error, Toast.LENGTH_SHORT).show();
                }
            }
        }.execute(content);
    }
}
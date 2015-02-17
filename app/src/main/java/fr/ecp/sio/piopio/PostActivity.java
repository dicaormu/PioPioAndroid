package fr.ecp.sio.piopio;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

import fr.ecp.sio.piopio.model.ApiClient;

/**
 * Activity for posting a tweet
 * Created by Diana on 12/12/2014.
 */
public class PostActivity extends ActionBarActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_activity);

    }

    public void post(View view) {
        EditText contentView = (EditText) findViewById(R.id.content);
        String content = contentView.getText().toString();
        if (content.isEmpty()) {
            contentView.setError(getString(R.string.required));
            contentView.requestFocus();
            return;
        }
        new AsyncTask<String, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(String... params) {
                try {
                    String handle = AccountManager.getUserId(PostActivity.this);
                    new ApiClient().postTweet(handle, params[0]);
                    setResult(RESULT_OK);
                    return true;
                } catch (IOException e) {
                    Log.e(PostActivity.class.getName(), "Post failed", e);
                    return false;
                }
            }

            @Override
            protected void onPostExecute(Boolean success) {
                if (success) {
                    finish();
                    Toast.makeText(PostActivity.this, R.string.post_success, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(PostActivity.this, R.string.post_error, Toast.LENGTH_SHORT).show();
                }
            }
        }.execute(content);
    }
}

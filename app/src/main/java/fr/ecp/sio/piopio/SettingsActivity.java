package fr.ecp.sio.piopio;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;

/**
 * Created by Diana on 12/12/2014.
 */
public class SettingsActivity extends PreferenceActivity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

        Preference pref = findPreference("logout");
        pref.setEnabled(AccountManager.isConnected(this));
        pref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(final Preference preference) {
                new AlertDialog.Builder(SettingsActivity.this)
                        .setTitle(R.string.logout)
                        .setMessage(R.string.logout_confirm)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                AccountManager.clearUserToken(SettingsActivity.this);
                                preference.setEnabled(false);

                                System.runFinalizersOnExit(true);
                                finish();
                                System.exit(0);

                            }
                        }).setNegativeButton(android.R.string.no, null).show();

                return true;
            }
        });
    }
}

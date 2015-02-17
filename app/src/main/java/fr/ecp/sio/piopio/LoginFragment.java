package fr.ecp.sio.piopio;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;


import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import fr.ecp.sio.piopio.model.ApiClient;

/**
 * Created by Diana on 12/12/2014.
 */
public class LoginFragment extends DialogFragment implements DialogInterface.OnShowListener {

    private EditText mHandleLogin;
    private EditText mHandlePassword;


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstance){
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.login_fragment, null);
        mHandleLogin=(EditText)view.findViewById(R.id.handle);
        mHandlePassword=(EditText)view.findViewById(R.id.password);
        Dialog dialog = new AlertDialog.Builder(getActivity())
                .setTitle(R.string.login)
                .setView(view)
                .setPositiveButton(android.R.string.ok, null)
                //.setNegativeButton(android.R.string.cancel, null)
                .create();
        dialog.setOnShowListener(this);
        return dialog;
    }

    @Override
    public void onShow(DialogInterface dialog) {
        ((AlertDialog)dialog).getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    private void login(){
        String login=null;
        if (mHandleLogin!= null)
            login=mHandleLogin.getText().toString();
        String password=null;
        if (mHandlePassword!= null)
            password=mHandlePassword.getText().toString();

        if( login.isEmpty()){
            mHandleLogin.setError(getString(R.string.required));
            mHandleLogin.requestFocus();
            return;
        }
        if(password.isEmpty()){
            mHandlePassword.setError(getString(R.string.required));
            mHandlePassword.requestFocus();
            return;
        }

        new AsyncTask<String,Void, String>(){
            String finalLoginId ;
            String finalLoginHandle;

            @Override
            protected String doInBackground(String... params) {
                try {
                    finalLoginId=new ApiClient().login(params[0], params[1]);
                    finalLoginHandle=params[0];
                    return finalLoginId;
                } catch (Exception e) {
                    Log.e(LoginFragment.class.getName(),"Login failed",e);
                    return null;
                }
            }

            @Override
            protected void onPostExecute(String s) {

                if(s !=null){
                    Fragment target=getTargetFragment();
                    if(target!= null){
                        target.onActivityResult(getTargetRequestCode(), Activity.RESULT_OK,null);
                        LoginFragment.this.dismiss();
                    }
                    AccountManager.login(getActivity(), s, finalLoginHandle,finalLoginId);
                    getActivity().setTitle(R.string.app_name);
                    dismiss();

                    Toast.makeText(getActivity(), R.string.login_success, Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(getActivity(), R.string.login_error_Conn, Toast.LENGTH_SHORT).show();
                }

            }
        }.execute(login, password);


    }
}

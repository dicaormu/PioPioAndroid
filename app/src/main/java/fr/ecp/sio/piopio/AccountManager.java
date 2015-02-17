package fr.ecp.sio.piopio;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Diana on 12/12/2014.
 * this class allows me to know the user data when the user is logged into the system
 */
public class AccountManager
{

    private static final String PREF_API_TOKEN = "apiToken";
    private static final String PREF_API_HANDLE = "apiHandle";
    private static final String PREF_API_ID = "apiId";
    public static boolean isConnected(Context context){
        return getUserToken(context)!= null;

    }

    public static String getUserToken(Context context){
        SharedPreferences pref= PreferenceManager.getDefaultSharedPreferences(context);
        return pref.getString(PREF_API_TOKEN,null) ;
    }

    public static String getUserHandle(Context context){
        SharedPreferences pref= PreferenceManager.getDefaultSharedPreferences(context);
        return pref.getString(PREF_API_HANDLE,null) ;
    }

    public static String getUserId(Context context){
        SharedPreferences pref= PreferenceManager.getDefaultSharedPreferences(context);
        return pref.getString(PREF_API_ID,null) ;
    }

    /**
     * To register user credentials when is a successful login
     * */
    public static void login(Context context, String token, String handle, String id){
        SharedPreferences pref=PreferenceManager.getDefaultSharedPreferences(context);
         pref.edit()
                 .putString(PREF_API_TOKEN, token)
                 .putString(PREF_API_HANDLE, handle)
                 .putString(PREF_API_ID,id)
                 .apply();
        //pref.edit().commit();
    }

    /**
     * To logout
     *
     * */
    public static void clearUserToken(Context context){


        SharedPreferences pref=PreferenceManager.getDefaultSharedPreferences(context);
        pref.edit().remove(PREF_API_TOKEN).apply();
        //pref.edit().commit();
    }

}

package fr.ecp.sio.piopio.loaders;

import android.content.Context;

import android.support.v4.content.AsyncTaskLoader;

import java.util.List;

import fr.ecp.sio.piopio.model.ApiClient;
import fr.ecp.sio.piopio.model.User;

/**
 * Created by Diana on 05/12/2014.
 */
public class UserLoader extends AsyncTaskLoader<List<User>> {
    private List<User> result;
    String userId;
    UserMethods method;

    public static enum UserMethods {
        GET_FOLLOWERS(1) {
            @Override
            public List<User> executeOperation(String idUsuario) {
                ApiClient api = new ApiClient();
                return api.getUserFollowers(idUsuario);
            }
        },
        GET_FOLLOWINGS(2) {
            @Override
            public List<User> executeOperation(String idUsuario) {
                ApiClient api = new ApiClient();
                return api.getUserFollowings(idUsuario);
            }
        },
        GET_USERS(3) {
            @Override
            public List<User> executeOperation(String idUsuario) {
                ApiClient api = new ApiClient();
                return api.getUsers(idUsuario);
            }
        };

        int value;

        UserMethods(int value) {
            this.value = value;
        }

        public static UserMethods fromInt(int val){
            for (UserMethods m : values()) {
                if(m.value==val)
                    return m;
            }
            return null;
        }

        public abstract List<User> executeOperation(String idUsuario);
    };


    public UserLoader(Context context, String userId, UserMethods method) {
        super(context);
        this.userId = userId;
        this.method=method;
    }

    //operaciones de tipo red
    @Override
    public List<User> loadInBackground() {
        return method.executeOperation(userId);
    }


    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        if (result != null)
            deliverResult(result);
        if (takeContentChanged() || result == null) forceLoad();
    }

    @Override
    protected void onStopLoading() {
        super.onStopLoading();
        cancelLoad();
    }

    @Override
    public void deliverResult(List<User> data) {
        result = data;
        if (isStarted())
            super.deliverResult(data);
    }
}

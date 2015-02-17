package fr.ecp.sio.piopio.model;

import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.MalformedURLException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import fr.ecp.sio.piopio.RestApiConsumer;

/**
 * Created by Diana on 12/12/2014.
 * This class comuniques the app with the rest interface
 */
public class ApiClient {


    private static final String API_BASE = "http://54.148.37.7:8080";//ip of the services



    /**
     * calls login from api rest
     * @param handle name of the user
     * @param passwd token of the user
     * */
    public String login(String handle, String passwd) throws Exception {

        String url = API_BASE + "/sessions";
        RestApiConsumer restApi = new RestApiConsumer();
        restApi.execute(restApi.POST, url, null);

        Gson gson = new Gson();
        Object resp = gson.fromJson(restApi.getResponse(), Object.class);
        if (resp == null)
            return null;
        String user = ((Map) resp).get("to").toString();

        if (user.isEmpty())
            return null;
        return user;
    }

    /**
    * get user followers
     * @param userId id of the user
    * */
    public List<User> getUserFollowers(String userId) {
        String url = new String(API_BASE + "/" + userId + "/followers");
        RestApiConsumer consumer = new RestApiConsumer();

        try {
            consumer.execute(consumer.GET, url, null);

            Gson gson = new Gson();
            User[] users = gson.fromJson(consumer.getResponse(), User[].class);

            return Arrays.asList(users);
        } catch (Exception e) {
            Log.e("error!!!", e.toString());
            return null;
        }
    }

    /**
     * get user followings
     * @param userId id of the user
     * */
    public List<User> getUserFollowings(String userId) {
        String url = new String(API_BASE + "/" + userId + "/followings");
        RestApiConsumer consumer = new RestApiConsumer();

        try {
            consumer.execute(consumer.GET, url, null);

            Gson gson = new Gson();
            User[] users = gson.fromJson(consumer.getResponse(), User[].class);

            return Arrays.asList(users);
        } catch (Exception e) {
            Log.e("error!!!", e.toString());
            return null;
        }
    }

    /**
     * get list of user of the system
     * @param userId id of the user
     * */
    public List<User> getUsers(String userId) {

        String url = new String(API_BASE +"/"+userId+ "/users");
        RestApiConsumer consumer = new RestApiConsumer();

        try {
            consumer.execute(consumer.GET, url, null);

            Gson gson = new Gson();
            User[] users = gson.fromJson(consumer.getResponse(), User[].class);

            return Arrays.asList(users);
        } catch (Exception e) {
            Log.e("error!!!", e.toString());
            return new ArrayList();
        }
    }

    /**
     * get user tweets
     * @param userId id of the user
     * */
    public List<Talk> getUserTweets(String userId) {
        try {

            RestApiConsumer consumer = new RestApiConsumer();
            String url = API_BASE + "/" + userId + "/tweets";
            consumer.execute(consumer.GET, url, null);

            Talk[] tweets = new Gson().fromJson(consumer.getResponse(), Talk[].class);

            List<Talk> list = Arrays.asList(tweets);

            return list;
        } catch (MalformedURLException e) {
            Log.e("error",e.toString());
        } catch (Exception e) {
            Log.e("error",e.toString());
        }
        return null;
    }

    /**
     * post a tweet
     * @param handle id of the user (id)
     * @param content tweet
     * */
    public void postTweet(String handle, String content) throws IOException {

        String url = API_BASE + "/" + handle + "/tweets";

        RestApiConsumer consumer = new RestApiConsumer();
        TweetModel mod = new TweetModel();
        mod.setContent(content);
        String json = new Gson().toJson(mod);
        try {
            consumer.execute(consumer.POST, url, json);
        } catch (Exception e) {
            Log.e("error!!!", e.toString());
        }

    }

    /**
     * follows someone
     * @param idUser user tht is doing the
     * */
    public void followSomeone(String idUser, String idFollowing) throws IOException {

        String url = API_BASE + "/" + idUser + "/followings";

        RestApiConsumer consumer = new RestApiConsumer();
        UserAddModel mod = new UserAddModel();
        mod.setFollow(idFollowing);
        String json = new Gson().toJson(mod);
        try {
            consumer.execute(consumer.POST, url, json);
        } catch (Exception e) {
            Log.e("error!!!", e.toString());
        }

    }

    /**
     * Stop following someone
     * @param idUser this user
     * @param  idFollowing user i dont wanr to follow
     * */
    public void unFollowSomeone(String idUser, String idFollowing) throws IOException {

        String url = API_BASE + "/" + idUser + "/followings/"+idFollowing;

        RestApiConsumer consumer = new RestApiConsumer();
        UserAddModel mod = new UserAddModel();
        mod.setFollow(idFollowing);
        String json = new Gson().toJson(mod);
        Log.i("url delete ", json);
        try {
            consumer.execute(consumer.DELETE, url, null);
        } catch (Exception e) {
            Log.e("error!!!", e.toString());
        }

    }


}

/**
 * Model for generating Json of the tweet (pio)
 * */
class TweetModel {

    private String tweet;


    public String getContent() {
        return tweet;
    }

    public void setContent(String content) {
        this.tweet = content;
    }
}

/**
 * Model for generating Json of the user
 * */
class UserAddModel {

    private String follow;


    public String getFollow() {
        return follow;
    }

    public void setFollow(String content) {
        this.follow = content;
    }
}
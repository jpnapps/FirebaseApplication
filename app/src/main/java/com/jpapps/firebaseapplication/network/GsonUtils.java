package com.jpapps.firebaseapplication.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.jpapps.firebaseapplication.itest.model.MItem;
import com.jpapps.firebaseapplication.LogUtils;
import com.jpapps.firebaseapplication.model.Mretailer;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;


/**
 * Created by ceino on 12/9/15.
 */
public class GsonUtils<T> {
    static GsonUtils gson;

    public static GsonUtils getInstance() {
        if(gson==null)
             gson = new GsonUtils();
        return gson;
    }

    public ArrayList<MItem> gsonToMItems(JSONArray json) {

        ArrayList<MItem> posts= null;
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String jsonString =  json.toString();
            Type listType = new TypeToken<ArrayList<MItem>>(){}.getType();
            posts = (ArrayList<MItem>) gson.fromJson(jsonString, listType);
            // users = gson.fromJson(jsonString, MJobs.class);

        } catch (Exception e){
            LogUtils.LOGD("exception", "NS  JSONArray MItem " + e.getMessage());
            //Crashlytics.logException(e);
            e.printStackTrace();
        }
        return posts;
    }
    public Mretailer gsonToMretailer(JSONObject json) {

        Mretailer users = null;
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String jsonString =  json.toString();
            users = gson.fromJson(jsonString, Mretailer.class);

        } catch (Exception e){
            LogUtils.LOGD("exception", "NS JSONObject MAgencyRoot " + e.getMessage());
            //Crashlytics.logException(e);
            e.printStackTrace();
        }
        return users;
    }
/*
    public MLaunchRoot gsonToMLaunchRoot(JSONObject json) {

        MLaunchRoot users = null;
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String jsonString =  json.toString();
            users = gson.fromJson(jsonString, MLaunchRoot.class);

        } catch (Exception e){
            LogUtils.LOGD("exception", "NS JSONObject MLaunchRoot " + e.getMessage());
            //Crashlytics.logException(e);
            e.printStackTrace();
        }
        return users;
    }
    public MMissionRoot gsonToMMissionRoot(JSONObject json) {

        MMissionRoot users = null;
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String jsonString =  json.toString();
            users = gson.fromJson(jsonString, MMissionRoot.class);

        } catch (Exception e){
            LogUtils.LOGD("exception", "NS JSONObject MMissionRoot " + e.getMessage());
            //Crashlytics.logException(e);
            e.printStackTrace();
        }
        return users;
    }
    public MPadRoot gsonToMPadRoot(JSONObject json) {

        MPadRoot users = null;
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String jsonString =  json.toString();
            users = gson.fromJson(jsonString, MPadRoot.class);

        } catch (Exception e){
            LogUtils.LOGD("exception", "NS JSONObject MPadRoot " + e.getMessage());
            //Crashlytics.logException(e);
            e.printStackTrace();
        }
        return users;
    }

    public MRocketsRoot gsonToMRocketsRoot(JSONObject json) {

        MRocketsRoot users = null;
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String jsonString =  json.toString();
            users = gson.fromJson(jsonString, MRocketsRoot.class);

        } catch (Exception e){
            LogUtils.LOGD("exception", "NS JSONObject MRocketsRoot " + e.getMessage());
            //Crashlytics.logException(e);
            e.printStackTrace();
        }
        return users;
    }

*//*    public MJobs gsonToMJobs(JSONObject json) {
        MJobs users = null;
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String jsonString =  json.toString();
            users = gson.fromJson(jsonString, MJobs.class);
        } catch (Exception e){
            LogUtils.LOGD("exception", "NS JSONObject MJobs " + e.getMessage());
            //Crashlytics.logException(e);
            e.printStackTrace();
        }
        return users;
    }*//*

 */


/*
*
*
*
*
atlas-android-jan
  git checkout 0.3.0  -0.22.0
  git checkut 0.2.9  -0.20.4
*/

}
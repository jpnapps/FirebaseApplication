package com.jpapps.firebaseapplication.network;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;

import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.jpapps.firebaseapplication.model.Mretailer;

import org.json.JSONObject;


/**
 * Created by ceino on 5/12/16.
 */

public class NetworkService extends IntentService {



    public  static Mretailer mretailerroot;
    public  static Mretailer mretailerrootpaged;
    /** dev*/

    public static boolean isRelease=true;
    public static final String GLOBALURL = "https://launchlibrary.net/1.3/";
    public static String GLOBAL_IMAGE_URL = "https://s3.amazonaws.com/topschoolbetatest/";




    public static final String ACTION_ALL = "action.all";

    public static final String ACTION_page = "action.page";
    public static final String SKIP_PARAM = "SKIP";
    public static final String LIMIT_PARAM = "LIMIT";
    public static final String STRING_PARAM = "Strings";


    public NetworkService() {

        super("uNetworkService");

    }

    public static void startActionAll(Context context) {
        //LogUtils.LOGD("jithish", "NS startActionAll");
        Intent intent = new Intent(context, NetworkService.class);
        intent.setAction(ACTION_ALL);
        context.startService(intent);
    }
    public static void startActionAll(Context context,int skip) {
        //LogUtils.LOGD("jithish", "NS startActionAll");
        Intent intent = new Intent(context, NetworkService.class);
        intent.setAction(ACTION_ALL);
        context.startService(intent);
    }
    public static void startActionAll(Context context,String skip) {
        //LogUtils.LOGD("jithish", "NS startActionAll");
        Intent intent = new Intent(context, NetworkService.class);
        intent.setAction(ACTION_page);
        intent.putExtra(STRING_PARAM,skip);
        context.startService(intent);
    }
    @Override
    protected void onHandleIntent(Intent intent) {
        if(intent.getAction().equalsIgnoreCase(ACTION_ALL)) {
            getRetailers();
        }
        else if(intent.getAction().equalsIgnoreCase(ACTION_page)) {
            String url=  intent.getStringExtra(STRING_PARAM);
            getRetailers(url);
        }


    }

    private void getRetailers() {
        try{
            AjaxCallback<JSONObject> cb = new AjaxCallback<JSONObject>() {
                @Override
                public void callback(String url, JSONObject json, AjaxStatus status) {
                    if (json == null) {
                       // ToastHandler.newInstance(PacifyerActivity.this).showToast("Please Try again");
                       // LogUtils.LOGD("call", "declineSessionWeb json null ");
                    } else {


                        try {

                            mretailerroot = GsonUtils.getInstance().gsonToMretailer(json);
                            mretailerrootpaged = null;
                            PrefManager.getInstance(getApplicationContext()).saveMretailer(mretailerroot);
                            postEventOnMainThread(new NotifyEvent(true, false));
                        /*    if (skip == 0) {
                                mretailerroot = GsonUtils.getInstance().gsonToMretailer(json);
                                mretailerrootpaged = null;
                                postEventOnMainThread(new NotifyEvent(true, false));
                            } else {
                                if (mretailerroot != null) {
                                    mretailerrootpaged = GsonUtils.getInstance().gsonToMretailer(json);
                                    postEventOnMainThread(new NotifyEvent(true, true));

                                } else {
                                    mretailerroot = GsonUtils.getInstance().gsonToMretailer(json);
                                    mretailerrootpaged = null;
                                    postEventOnMainThread(new NotifyEvent(true, false));
                                }
                            }*/
                        } catch (Exception e) {
                            //Crashlytics.logException(e);
                            mretailerroot = new Mretailer();
                           // postEventOnMainThread(new NotifyEvent(false, e));
                          //  LogUtils.LOGD("exception", "NS list customers  top " + e.getMessage());
                            e.printStackTrace();
                        }



                     /*   try {
                            LogUtils.LOGD("call", "declineSessionWeb json  " + json);
                        } catch (Exception e){
                            ToastHandler.newInstance(PacifyerActivity.this).showToast("Please Try again");
                            LogUtils.LOGD("call", "declineSessionWeb exception : " + e.getMessage());
                        }*/
                    }
                }
            };
          NetworkApi.getInstance().getwithoutheaderWeb(getApplicationContext(),"https://www.beeone.co.uk/api/retailers",cb);
         //  NetworkApi.getInstance().
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }



    private void getRetailers(String url) {
        try{
            AjaxCallback<JSONObject> cb = new AjaxCallback<JSONObject>() {
                @Override
                public void callback(String url, JSONObject json, AjaxStatus status) {
                    if (json == null) {
                        // ToastHandler.newInstance(PacifyerActivity.this).showToast("Please Try again");
                        // LogUtils.LOGD("call", "declineSessionWeb json null ");
                    } else {


                        try {

                            mretailerrootpaged = GsonUtils.getInstance().gsonToMretailer(json);
                           // mretailerrootpaged = null;
                            PrefManager.getInstance(getApplicationContext()).saveMretailer(mretailerroot);
                            postEventOnMainThread(new NotifyEvent(true, true));
                        /*    if (skip == 0) {
                                mretailerroot = GsonUtils.getInstance().gsonToMretailer(json);
                                mretailerrootpaged = null;
                                postEventOnMainThread(new NotifyEvent(true, false));
                            } else {
                                if (mretailerroot != null) {
                                    mretailerrootpaged = GsonUtils.getInstance().gsonToMretailer(json);
                                    postEventOnMainThread(new NotifyEvent(true, true));

                                } else {
                                    mretailerroot = GsonUtils.getInstance().gsonToMretailer(json);
                                    mretailerrootpaged = null;
                                    postEventOnMainThread(new NotifyEvent(true, false));
                                }
                            }*/
                        } catch (Exception e) {
                            //Crashlytics.logException(e);
                            mretailerroot = new Mretailer();
                            // postEventOnMainThread(new NotifyEvent(false, e));
                            //  LogUtils.LOGD("exception", "NS list customers  top " + e.getMessage());
                            e.printStackTrace();
                        }



                     /*   try {
                            LogUtils.LOGD("call", "declineSessionWeb json  " + json);
                        } catch (Exception e){
                            ToastHandler.newInstance(PacifyerActivity.this).showToast("Please Try again");
                            LogUtils.LOGD("call", "declineSessionWeb exception : " + e.getMessage());
                        }*/
                    }
                }
            };
            NetworkApi.getInstance().getwithoutheaderWeb(getApplicationContext(),url,cb);
            //  NetworkApi.getInstance().
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    void postEventOnMainThread(final Object event) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                BusProvider.getInstance().post(event);
                // bus.post(event);
            }
        });

    }

    public Boolean isValid(String text) {
        if (text != null)
            if (!text.trim().equalsIgnoreCase(""))
                return true;
        return false;

    }

    public Boolean isValid(Object object) {
        if (object != null)
            return true;
        return false;

    }




  /*  void postEventOnMainThread(final Object event) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                BaseBusProvider.getInstance().post(event);
                // bus.post(event);
            }
        });
    }*/
}
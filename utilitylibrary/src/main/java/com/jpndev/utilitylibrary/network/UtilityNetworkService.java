package com.jpndev.utilitylibrary.network;

import android.app.IntentService;
import android.content.Intent;


/**
 * Created by ceino on 5/12/16.
 */

public class UtilityNetworkService extends IntentService {




    /** dev*/

    public static boolean isRelease=true;
    public static final String GLOBALURL = "https://jobs.github.com/positions.json?";
    public static String GLOBAL_IMAGE_URL = "https://s3.amazonaws.com/topschoolbetatest/";




    public static final String ACTION_ALL = "action.all";


    public static final String SKIP_PARAM = "SKIP";
    public static final String LIMIT_PARAM = "LIMIT";
    public static final String STRING_PARAM = "Strings";


    public UtilityNetworkService() {

        super("uNetworkService");

    }



    @Override
    protected void onHandleIntent(Intent intent) {
        if(intent.getAction().equalsIgnoreCase(ACTION_ALL)) {

        }


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

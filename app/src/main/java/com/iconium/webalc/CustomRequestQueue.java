package com.iconium.webalc;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by ogie on 12/4/2017.
 */

public class CustomRequestQueue {

    private static Context mContext;
    private static CustomRequestQueue customRequestQueueInstance;
    private static RequestQueue customRequestQueue;

    public CustomRequestQueue(Context context) {
        mContext = context;
        customRequestQueue  = getRequestQueue();

    }

    public static synchronized CustomRequestQueue getInstance(Context context){
        if(customRequestQueueInstance == null){
            customRequestQueueInstance = new CustomRequestQueue(context);
        }
        return customRequestQueueInstance;
    }


    public RequestQueue getRequestQueue(){

        if(customRequestQueue == null){
            customRequestQueue = Volley.newRequestQueue(mContext.getApplicationContext());
        }

        return customRequestQueue;

    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

}

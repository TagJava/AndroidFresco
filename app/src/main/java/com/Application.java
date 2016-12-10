package com;


import android.content.Context;
import android.support.multidex.MultiDexApplication;

import com.facebook.drawee.backends.pipeline.Fresco;


public class Application extends MultiDexApplication {
    /******************** release key ****************************/
    public static final String APP_ID = "2882303761517320717";
    public static final String APP_KEY = "5641732060717";
    /******************** release key end ****************************/

    /******************** debug key ****************************/
    public static final String DEBUG_APP_ID = "2882303761517339062";
    public static final String DEBUG_APP_KEY = "5851733941062";
    /******************** debug key end ****************************/
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
//        OkHttpClient okHttpClient = null;
//        ImagePipelineConfig config = OkHttpImagePipelineConfigFactory
//                .newBuilder(mContext, okHttpClient).build();
        Fresco.initialize(mContext);

//        ImagePipelineConfig config = ImagePipelineConfig.newBuilder()
//                .setNetworkFetcher(myNetworkFetcher);
//        .build();
//        Fresco.initialize(context, config);
    }
}

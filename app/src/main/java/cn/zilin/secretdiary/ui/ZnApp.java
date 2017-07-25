package cn.zilin.secretdiary.ui;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Process;

import com.bumptech.glide.Glide;
import com.tencent.bugly.crashreport.CrashReport;

public class ZnApp extends Application {
    private static Context mContext;
    private static Handler mMainThreadHandler;
    private static int mMainThreadId;
    private static ZnApp mInstance;

    public static ZnApp getInstance() {
        return mInstance;
    }

    public static Handler getMainThreadHandler() {
        return mMainThreadHandler;
    }

    public static int getMainThreadId() {
        return mMainThreadId;
    }

    public static Context getAppContext() {
        return mContext;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        mContext = base;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //CrashReport.initCrashReport(getApplicationContext(), "c87f27e612", false);
        mInstance = this;
        mContext = this;
        mMainThreadHandler = new Handler();
        mMainThreadId = Process.myTid();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        Glide.get(this).trimMemory(level);
        //System.gc();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Glide.get(this).clearMemory();
    }
}

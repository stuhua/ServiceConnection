package com.stuhua.serviceconnection;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by liulh on 2017/3/23.
 */

public class DownloadService extends Service {
    public static final int MAX_PROGRESS = 100;
    private int mProgressCur;
    private OnProgressListener mOnProgressListener;

    public int getProgressCur() {
        return mProgressCur;
    }

    public void setProgressCur(int progressCur) {
        mProgressCur = progressCur;
    }

    /**
     * 注册回调接口的方法
     *
     * @param listener
     */
    public void setOnProgressListener(OnProgressListener listener) {
        this.mOnProgressListener = listener;
    }

    /**
     * 开启一个线程模拟下载
     */
    public void startDownload() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (mProgressCur < MAX_PROGRESS) {
                    mProgressCur += 5;
                    if (mOnProgressListener != null) {
                        mOnProgressListener.onProgress(mProgressCur);
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }


    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }

    public class MyBinder extends Binder {
        public DownloadService getService() {
            return DownloadService.this;
        }
    }
}

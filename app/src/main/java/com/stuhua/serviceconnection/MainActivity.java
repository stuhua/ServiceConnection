package com.stuhua.serviceconnection;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.logging.Logger;

public class MainActivity extends AppCompatActivity {
    private DownloadService mDownloadService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent(MainActivity.this, DownloadService.class);
        bindService(intent, conn, Context.BIND_AUTO_CREATE);
    }

    public void startDownload(View view) {
        mDownloadService.startDownload();
    }

    ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mDownloadService = ((DownloadService.MyBinder) (service)).getService();
            mDownloadService.setOnProgressListener(new OnProgressListener() {
                @Override
                public void onProgress(int progress) {
                    //更新界面
                    com.orhanobut.logger.Logger.d(progress);
                }
            });
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mDownloadService = null;
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(conn);
    }
}

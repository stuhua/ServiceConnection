package com.stuhua.serviceconnection;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.logging.Logger;

public class MainActivity extends AppCompatActivity {
    private DownloadService mDownloadService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mDownloadService = ((DownloadService.MyBinder) (service)).getService();
            mDownloadService.setOnProgressListener(new OnProgressListener() {
                @Override
                public void onProgress(int progress) {
                    //更新界面

                }
            });
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
}

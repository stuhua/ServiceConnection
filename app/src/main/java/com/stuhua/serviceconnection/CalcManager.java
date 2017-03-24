package com.stuhua.serviceconnection;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;

import com.stuhua.serviceconnection.aidl.IMyAidlInterface;

/**
 * Created by liulh on 2017/3/24.
 */
public class CalcManager {
    private IMyAidlInterface mAidlInterface;

    private static CalcManager ourInstance = new CalcManager();

    public static CalcManager getInstance() {
        return ourInstance;
    }

    private CalcManager() {
    }

    public void bindService(Context ctx) {
        Intent intent = new Intent();
        ctx.bindService(intent, conn, Context.BIND_AUTO_CREATE);
    }

    public void unBindService(Context ctx) {
        ctx.unbindService(conn);
    }

    /**
     * 方法二，服务端和客户端通过Binder通讯
     */
    ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder binder) {
            mAidlInterface = IMyAidlInterface.Stub.asInterface(binder);
            try {
                binder.linkToDeath(recipient, 0);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
    private IBinder.DeathRecipient recipient = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            if (mAidlInterface == null) {
                //重新绑定
            }
        }
    };

    public void add(int x, int y) {
        try {
            mAidlInterface.add(x, y);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void min(int x, int y) {
        try {
            mAidlInterface.min(x, y);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}

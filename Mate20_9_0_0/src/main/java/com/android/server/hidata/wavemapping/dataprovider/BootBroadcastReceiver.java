package com.android.server.hidata.wavemapping.dataprovider;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import com.android.server.hidata.wavemapping.cons.ContextManager;
import com.android.server.hidata.wavemapping.util.LogUtil;

public class BootBroadcastReceiver extends BroadcastReceiver {
    public static final String TAG;
    private Handler bootHandler;
    private Context mCtx = ContextManager.getInstance().getContext();

    static {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("WMapping.");
        stringBuilder.append(BootBroadcastReceiver.class.getSimpleName());
        TAG = stringBuilder.toString();
    }

    public BootBroadcastReceiver(Handler handler) {
        this.bootHandler = handler;
    }

    public void onReceive(Context context, Intent intent) {
        LogUtil.i("Boot this system , BootBroadcastReceiver onReceive()");
        if (intent == null) {
            LogUtil.e("Intent is null, so exiting");
            return;
        }
        String action = intent.getAction();
        if (action != null) {
            Message bootMsg;
            if (action.equals("android.intent.action.BOOT_COMPLETED") || action.equals("android.intent.action.LOCKED_BOOT_COMPLETED")) {
                LogUtil.d("BootBroadcastReceiver onReceive(), Boot Complete");
                bootMsg = Message.obtain(this.bootHandler, 1);
                if (this.bootHandler == null) {
                    LogUtil.w("BootBroadcastReceiver onReceive(),null == this.bootHandler");
                    return;
                }
                this.bootHandler.sendMessage(bootMsg);
            } else if (action.equals("android.intent.action.ACTION_SHUTDOWN")) {
                LogUtil.d("BootBroadcastReceiver onReceive(), Shut Down");
                bootMsg = Message.obtain(this.bootHandler, 2);
                if (this.bootHandler == null) {
                    LogUtil.w("BootBroadcastReceiver onReceive(),null == this.bootHandler");
                    return;
                }
                this.bootHandler.sendMessage(bootMsg);
            }
        }
    }
}

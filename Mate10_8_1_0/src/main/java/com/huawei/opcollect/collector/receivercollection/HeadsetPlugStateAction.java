package com.huawei.opcollect.collector.receivercollection;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.huawei.opcollect.odmf.OdmfCollectScheduler;
import com.huawei.opcollect.strategy.Action;
import com.huawei.opcollect.utils.OPCollectLog;
import com.huawei.opcollect.utils.OPCollectUtils;
import java.io.PrintWriter;

public class HeadsetPlugStateAction extends Action {
    private static final int HEADSET_PLUGGED = 1;
    private static final int HEADSET_UNPLUGGED = 0;
    private static final String TAG = "HeadsetPlugStateAction";
    private static HeadsetPlugStateAction sInstance = null;
    private HeadSetStateReceiver mReceiver = null;
    private int mState = -1;

    class HeadSetStateReceiver extends BroadcastReceiver {
        HeadSetStateReceiver() {
        }

        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                String action = intent.getAction();
                if (action != null) {
                    OPCollectLog.r("HeadsetPlugStateAction", "onReceive action: " + action);
                    if ("android.intent.action.HEADSET_PLUG".equals(action) && intent.hasExtra("state")) {
                        HeadsetPlugStateAction.this.mState = intent.getIntExtra("state", -1);
                        HeadsetPlugStateAction.this.perform();
                    }
                }
            }
        }
    }

    public static synchronized HeadsetPlugStateAction getInstance(Context context) {
        HeadsetPlugStateAction headsetPlugStateAction;
        synchronized (HeadsetPlugStateAction.class) {
            if (sInstance == null) {
                sInstance = new HeadsetPlugStateAction(context, "HeadsetPlugStateAction");
            }
            headsetPlugStateAction = sInstance;
        }
        return headsetPlugStateAction;
    }

    private HeadsetPlugStateAction(Context context, String name) {
        super(context, name);
        setDailyRecordNum(SysEventUtil.querySysEventDailyCount(SysEventUtil.EVENT_HEADSET_PLUG) + SysEventUtil.querySysEventDailyCount(SysEventUtil.EVENT_HEADSET_UNPLUG));
        OPCollectLog.r("HeadsetPlugStateAction", "HeadsetPlugStateAction");
    }

    public void enable() {
        super.enable();
        if (this.mReceiver == null && this.mContext != null) {
            this.mReceiver = new HeadSetStateReceiver();
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.intent.action.HEADSET_PLUG");
            this.mContext.registerReceiver(this.mReceiver, intentFilter, OPCollectUtils.OPCOLLECT_PERMISSION, OdmfCollectScheduler.getInstance().getCtrlHandler());
            OPCollectLog.r("HeadsetPlugStateAction", "enabled");
        }
    }

    protected boolean execute() {
        if (1 == this.mState) {
            SysEventUtil.collectSysEventData(SysEventUtil.EVENT_HEADSET_PLUG);
        } else if (this.mState == 0) {
            SysEventUtil.collectSysEventData(SysEventUtil.EVENT_HEADSET_UNPLUG);
        }
        this.mState = -1;
        return true;
    }

    public boolean perform() {
        return super.perform();
    }

    public void disable() {
        super.disable();
        if (this.mReceiver != null && this.mContext != null) {
            this.mContext.unregisterReceiver(this.mReceiver);
            this.mReceiver = null;
        }
    }

    public boolean destroy() {
        super.destroy();
        destroyInstance();
        return true;
    }

    private static synchronized void destroyInstance() {
        synchronized (HeadsetPlugStateAction.class) {
            sInstance = null;
        }
    }

    public void dump(int indentNum, PrintWriter pw) {
        super.dump(indentNum, pw);
        if (pw != null) {
            String indent = String.format("%" + indentNum + "s\\-", new Object[]{" "});
            if (this.mReceiver == null) {
                pw.println(indent + "receiver is null");
            } else {
                pw.println(indent + "receiver not null");
            }
        }
    }
}

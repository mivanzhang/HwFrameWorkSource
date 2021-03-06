package com.android.internal.telephony.vsim.process;

import android.os.AsyncResult;
import android.os.Message;
import com.android.internal.telephony.vsim.HwVSimConstants;
import com.android.internal.telephony.vsim.HwVSimController;
import com.android.internal.telephony.vsim.HwVSimController.WorkModeParam;
import com.android.internal.telephony.vsim.HwVSimEventReport.VSimEventInfoUtils;
import com.android.internal.telephony.vsim.HwVSimLog;
import com.android.internal.telephony.vsim.HwVSimModemAdapter;
import com.android.internal.telephony.vsim.HwVSimRequest;

public class HwVSimSReadyProcessor extends HwVSimEReadyProcessor {
    public static final String LOG_TAG = "HwVSimSReadyProcessor";

    public HwVSimSReadyProcessor(HwVSimController controller, HwVSimModemAdapter modemAdapter, HwVSimRequest request) {
        super(controller, modemAdapter, request);
    }

    protected void logd(String s) {
        HwVSimLog.VSimLogD(LOG_TAG, s);
    }

    public boolean processMessage(Message msg) {
        int i = msg.what;
        if (i == 50) {
            onNetworkConnected();
            return true;
        } else if (i == 60) {
            onSwitchWorkModeFinish();
            return true;
        } else if (i == 65) {
            onPlmnSelInfoDone(msg);
            return true;
        } else if (i != 71) {
            switch (i) {
                case HwVSimConstants.EVENT_CARD_POWER_ON_DONE /*45*/:
                    onCardPowerOnDone(msg);
                    return true;
                case HwVSimConstants.EVENT_RADIO_POWER_ON_DONE /*46*/:
                    onRadioPowerOnDone(msg);
                    return true;
                default:
                    return false;
            }
        } else {
            onNetworkConnectTimeout();
            return true;
        }
    }

    protected void afterNetwork() {
        logd("afterNetwork");
        if (this.mHasEnterAfterNetwork) {
            logd("can not enter more than once.");
            return;
        }
        this.mHasEnterAfterNetwork = true;
        int mainSlot = this.mRequest.getMainSlot();
        int slaveSlot = mainSlot == 0 ? 1 : 0;
        HwVSimRequest slaveRequest = this.mRequest.clone();
        if (slaveRequest != null) {
            slaveRequest.setPowerOnOffMark(slaveSlot, true);
            slaveRequest.setCardOnOffMark(slaveSlot, true);
            this.mModemAdapter.cardPowerOn(this, slaveRequest, slaveSlot, 1);
        }
        int mainIndex = 1;
        if (mainSlot == 2) {
            mainIndex = 11;
        }
        HwVSimRequest mainRequest = this.mRequest.clone();
        if (mainRequest != null) {
            mainRequest.setPowerOnOffMark(mainSlot, true);
            mainRequest.setCardOnOffMark(mainSlot, true);
            this.mModemAdapter.cardPowerOn(this, mainRequest, mainSlot, mainIndex);
        }
    }

    protected void onCardPowerOnDone(Message msg) {
        logd("onCardPowerOnDone");
        VSimEventInfoUtils.setCauseType(this.mVSimController.mEventInfo, 7);
        AsyncResult ar = msg.obj;
        if (isAsyncResultValidNoProcessException(ar)) {
            HwVSimRequest request = ar.userObj;
            int subId = request.mSubId;
            int mainSlot = request.getMainSlot();
            request.setCardOnOffMark(subId, false);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("onCardPowerOnDone subId = ");
            stringBuilder.append(subId);
            stringBuilder.append(" mainSlot = ");
            stringBuilder.append(mainSlot);
            logd(stringBuilder.toString());
            if (subId == mainSlot) {
                WorkModeParam param = getWorkModeParam(this.mRequest);
                if (param == null) {
                    doSwitchModeProcessException(null, this.mRequest);
                    return;
                } else if (param.workMode != 2) {
                    request.setPowerOnOffMark(subId, false);
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("m2 subId = ");
                    stringBuilder.append(subId);
                    stringBuilder.append("set radio power off");
                    logd(stringBuilder.toString());
                    this.mVSimController.getPhoneBySub(subId).getServiceStateTracker().setDesiredPowerState(false);
                } else if (ar.exception != null) {
                    this.mModemAdapter.radioPowerOn(null, null, subId);
                    request.setPowerOnOffMark(subId, false);
                } else {
                    this.mModemAdapter.radioPowerOn(this, request, subId);
                }
            } else if (ar.exception != null) {
                this.mModemAdapter.radioPowerOn(null, null, subId);
                request.setPowerOnOffMark(subId, false);
            } else {
                this.mModemAdapter.radioPowerOn(this, request, subId);
            }
            if (isAllMarkClear(request)) {
                switchWorkModeFinish();
            }
        }
    }

    protected void onRadioPowerOnDone(Message msg) {
        logd("onRadioPowerOnDone");
        VSimEventInfoUtils.setCauseType(this.mVSimController.mEventInfo, 11);
        AsyncResult ar = msg.obj;
        if (isAsyncResultValidForRequestNotSupport(ar)) {
            HwVSimRequest request = ar.userObj;
            int subId = request.mSubId;
            request.setPowerOnOffMark(subId, false);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("onRadioPowerOnDone subId = ");
            stringBuilder.append(subId);
            logd(stringBuilder.toString());
            if (isAllMarkClear(request)) {
                switchWorkModeFinish();
            }
        }
    }

    private void onSwitchWorkModeFinish() {
        logd("onSwitchWorkModeFinish");
        VSimEventInfoUtils.setCauseType(this.mVSimController.mEventInfo, 1);
        transitionToState(0);
    }

    private void switchWorkModeFinish() {
        logd("switchWorkModeFinish");
        if (isAllMarkClear(this.mRequest)) {
            Message onCompleted = this.mVSimController.obtainMessage(60, this.mRequest);
            AsyncResult.forMessage(onCompleted);
            onCompleted.sendToTarget();
        }
    }

    public void doProcessException(AsyncResult ar, HwVSimRequest request) {
        doSwitchModeProcessException(ar, request);
    }

    private WorkModeParam getWorkModeParam(HwVSimRequest request) {
        if (this.mVSimController == null) {
            return null;
        }
        return this.mVSimController.getWorkModeParam(request);
    }
}

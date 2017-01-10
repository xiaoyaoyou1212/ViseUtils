package com.vise.utils.receiver;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;

import com.vise.log.ViseLog;

import java.util.List;

/**
 * Call requires API level 4
 * <uses-permission android:name="android.permission.RECEIVE_SMS"/>
 * <p/>
 * action: android.provider.Telephony.SMS_RECEIVED
 */
public class SmsReceiver extends BroadcastReceiver {
    private SmsListener smsListener;

    public SmsReceiver() {

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            ViseLog.i("收到广播：" + intent.getAction());
            Bundle bundle = intent.getExtras();
            for (String key : bundle.keySet()) {
                ViseLog.i(key + " : " + bundle.get(key));
            }
            Object[] pdus = (Object[]) intent.getExtras().get("pdus");
            String fromAddress = null;
            String serviceCenterAddress = null;
            if (pdus != null) {
                String msgBody = "";
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.DONUT) {
                    for (Object obj : pdus) {
                        SmsMessage sms = SmsMessage.createFromPdu((byte[]) obj);
                        msgBody += sms.getMessageBody();
                        fromAddress = sms.getOriginatingAddress();
                        serviceCenterAddress = sms.getServiceCenterAddress();

                        if (smsListener != null) {
                            smsListener.onMessage(sms);
                        }
                        ViseLog.i("getDisplayMessageBody：" + sms.getDisplayMessageBody());
                        ViseLog.i("getDisplayOriginatingAddress：" + sms.getDisplayOriginatingAddress());
                        ViseLog.i("getEmailBody：" + sms.getEmailBody());
                        ViseLog.i("getEmailFrom：" + sms.getEmailFrom());
                        ViseLog.i("getMessageBody：" + sms.getMessageBody());
                        ViseLog.i("getOriginatingAddress：" + sms.getOriginatingAddress());
                        ViseLog.i("getPseudoSubject：" + sms.getPseudoSubject());
                        ViseLog.i("getServiceCenterAddress：" + sms.getServiceCenterAddress());
                        ViseLog.i("getIndexOnIcc：" + sms.getIndexOnIcc());
                        ViseLog.i("getMessageClass：" + sms.getMessageClass());
                        ViseLog.i("getUserData：" + new String(sms.getUserData()));
                    }
                }
                if (smsListener != null) {
                    smsListener.onMessage(msgBody, fromAddress, serviceCenterAddress);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void registerSmsReceiver(Context context, SmsListener smsListener) {
        try {
            this.smsListener = smsListener;
            IntentFilter filter = new IntentFilter();
            filter.addAction("android.provider.Telephony.SMS_RECEIVED");
            filter.setPriority(Integer.MAX_VALUE);
            context.registerReceiver(this, filter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void unRegisterSmsReceiver(Context context) {
        try {
            context.unregisterReceiver(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static abstract class SmsListener {
        public abstract void onMessage(String msg, String fromAddress, String serviceCenterAddress);

        public void onMessage(SmsMessage msg) {
        }
    }

    /**
     * Call requires API level 4
     * <uses-permission android:name="android.permission.SEND_SMS"/>
     *
     * @param phone
     * @param msg
     */
    public static void sendMsgToPhone(String phone, String msg) {
        ViseLog.i("发送手机：" + phone + " ,内容： " + msg);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.DONUT) {
            SmsManager manager = SmsManager.getDefault();
            List<String> texts = manager.divideMessage(msg);
            for (String txt : texts) {
                manager.sendTextMessage(phone, null, txt, null, null);
            }
        } else {
            ViseLog.e("发送失败，系统版本低于DONUT，" + phone + " ,内容： " + msg);
        }

    }

    /**
     * Call requires API level 4
     * <uses-permission android:name="android.permission.WRITE_SMS"/>
     *
     * @param context
     * @param phone
     * @param msg
     */
    public static void saveMsgToSystem(Context context, String phone, String msg) {
        ContentValues values = new ContentValues();
        values.put("date", System.currentTimeMillis());
        //阅读状态 
        values.put("read", 0);
        //1为收 2为发  
        values.put("type", 2);
        values.put("address", phone);
        values.put("body", msg);
        context.getContentResolver().insert(Uri.parse("content://sms/inbox"), values);
    }

}

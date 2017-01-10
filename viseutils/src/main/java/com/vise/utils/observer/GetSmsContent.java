package com.vise.utils.observer;

import android.app.Activity;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GetSmsContent extends ContentObserver {
    public final String SMS_URI_INBOX = "content://sms/inbox";
    private Activity activity = null;
    private String smsContent = "";
    private EditText verifyText = null;

    private String SMS_ADDRESS_PRNUMBER = "106901161102782";

    public GetSmsContent(Activity activity, Handler handler, EditText verifyText) {
        super(handler);
        this.activity = activity;
        this.verifyText = verifyText;
    }

    @Override
    public void onChange(boolean selfChange) {
        super.onChange(selfChange);
        Cursor cursor = null;
        cursor = activity.managedQuery(Uri.parse(SMS_URI_INBOX), new String[]{
                        "_id", "address", "body", "read"}, "address=? and read=?",
                new String[]{SMS_ADDRESS_PRNUMBER, "0"}, "date desc");
        if (cursor != null) {
            cursor.moveToFirst();
            if (cursor.moveToFirst()) {
                String smsbody = cursor
                        .getString(cursor.getColumnIndex("body"));
                String regEx = "[^0-9]";
                Pattern p = Pattern.compile(regEx);
                Matcher m = p.matcher(smsbody.toString());
                smsContent = m.replaceAll("").trim().toString();
                if (verifyText != null && null != smsContent
                        && !"".equals(smsContent)) {
                    smsContent = smsContent.substring(0,
                            smsContent.length() - 2);
                    verifyText.setText(smsContent);
                    verifyText.setSelection(smsContent.length());
                }
            }
        }
    }
}
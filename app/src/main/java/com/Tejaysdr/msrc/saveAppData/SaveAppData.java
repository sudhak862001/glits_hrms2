package com.Tejaysdr.msrc.saveAppData;

import android.content.Context;
import android.content.SharedPreferences;

import com.Tejaysdr.msrc.MSRCApplication;
import com.Tejaysdr.msrc.dataModel.OperatorCode;
import com.Tejaysdr.msrc.dataModel.OperatorLoginData;
import com.google.gson.Gson;

/**
 * Created by sridhar on 2/8/2017.
 */

public class SaveAppData {
    private static SaveAppData sessionData;
    public static String DIGITAL_APP = "DIGITALApp";
    public static String DIGITAL_APP_tOKEN = "token";
    public static String SHPREF_KEY_OPERATOR_CODE="OPERATOR_CODE";
    public static String SHPREF_KEY_OPERATOR_LOGIN="OPERATOR_LOGIN";
    private SaveAppData() {}
    public static SaveAppData getSessionDataInstance() {
        if (sessionData == null) {
            sessionData = new SaveAppData();
        }
        return sessionData;
    }

    public static void saveOperatorData(OperatorCode operatorCode) {
        SharedPreferences.Editor e = MSRCApplication.getAppContext().getSharedPreferences(DIGITAL_APP, Context.MODE_PRIVATE).edit();
        if (operatorCode != null) {
            Gson gson = new Gson();
            String json = gson.toJson(operatorCode);
            e.putString(SHPREF_KEY_OPERATOR_CODE, json);
        } else {
            e.putString(SHPREF_KEY_OPERATOR_CODE, null);
        }
        e.commit();
    }
    public static OperatorCode getOperatorData() {
        Gson gson = new Gson();
        String json = MSRCApplication.getAppContext().getSharedPreferences(DIGITAL_APP, Context.MODE_PRIVATE).getString(SHPREF_KEY_OPERATOR_CODE, null);
        OperatorCode operatorCode = gson.fromJson(json, OperatorCode.class);
        return operatorCode;
    }

    public static void saveOperatorLoginData(OperatorLoginData operatorlogin) {
        SharedPreferences.Editor e = MSRCApplication.getAppContext().getSharedPreferences(DIGITAL_APP, Context.MODE_PRIVATE).edit();
        if (operatorlogin != null) {
            Gson gson = new Gson();
            String json = gson.toJson(operatorlogin);
            e.putString(SHPREF_KEY_OPERATOR_LOGIN, json);
        } else {
            e.putString(SHPREF_KEY_OPERATOR_LOGIN, null);
        }
        e.apply();
    }
    public static OperatorLoginData getOperatorLoginData() {
        Gson gson = new Gson();
        String json = MSRCApplication.getAppContext().getSharedPreferences(DIGITAL_APP, Context.MODE_PRIVATE).getString(SHPREF_KEY_OPERATOR_LOGIN, null);
        OperatorLoginData operatorLogin = gson.fromJson(json, OperatorLoginData.class);
        return operatorLogin;
    }
    public static void setusename(String usename) {
        SharedPreferences.Editor e = MSRCApplication.getAppContext().getSharedPreferences(DIGITAL_APP_tOKEN, Context.MODE_PRIVATE).edit();
        e.putString("usename", usename).commit();
    }

    public static String getusename() {
        String usename = MSRCApplication.getAppContext().getSharedPreferences(DIGITAL_APP_tOKEN, Context.MODE_PRIVATE).getString("usename","");
        return usename;
    }
}

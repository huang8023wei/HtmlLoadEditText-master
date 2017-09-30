package com.htmlgap.htmlgap;

import java.util.ArrayList;

/**
 * Created by hzp on 2017/9/14.
 * 接口传值
 */

public class CallBackListenerImp {
    public interface  CallbackListener{
        void callBack(ArrayList<String> mInputList);
    }
    public static CallbackListener mCallbackLisntener;
    public static void getCallbackListener(CallbackListener callbackLisntener){
        mCallbackLisntener = callbackLisntener;
    }
}

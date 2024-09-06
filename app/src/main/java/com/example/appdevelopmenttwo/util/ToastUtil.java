package com.example.appdevelopmenttwo.util;

import android.content.Context;
import android.os.Looper;
import android.widget.Toast;

import java.util.ArrayList;

public class ToastUtil {
    private static ArrayList<Toast> toastList = new ArrayList<Toast>();

    public static void newToast(Context context, String content) {
        cancelAll();
        Toast toast = Toast.makeText(context,content,Toast.LENGTH_SHORT);
        toastList.add(toast);
        toast.show();
    }

    public static void cancelAll() {
        if (!toastList.isEmpty()){
            for (Toast t : toastList) {
                t.cancel();
            }
            toastList.clear();
        }
    }

    public static void toastShort(Context context, String msg){
        try {
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            //解决在子线程中调用Toast的异常情况处理
            Looper.prepare();
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
            Looper.loop();
        }
    }

    public static void toastLong(Context context,String msg){
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }
}

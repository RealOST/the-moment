package com.example.appdevelopmenttwo.bean;

import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class CDTime {
    private TextView tv_hours;
    private TextView tv_colon1;
    private TextView tv_minutes;
    private TextView tv_colon2;
    private TextView tv_seconds;

    private long mDay;// 天
    private long mHour;//小时,
    private long mMin;//分钟,
    private long mSecond;//秒
    private Date endDate = null;
    private long endTime;
    private Timer mTimer = new Timer();

    public CDTime calcTime(){
        //s1.获取当前系统时间
        Date nowDate = new Date();
        long nowTime = nowDate.getTime();
        //s2.计算剩余时间
        long allTime = (endTime - nowTime) / 1000;
        //s3.剩余时间转换成天数、小时、分钟、秒
        mDay = allTime / 3600 / 24;
        mHour = allTime / 3600 % 24;
        mMin = allTime / 60 % 60;
        mSecond = allTime % 60;

        //Log.i(TAG,"mDay="+mDay + "mHour=" + mHour + "mMin=" + mMin + "mSecond=" + mSecond);
        return this;
    }
    public CDTime initEndTime(String endTimeStr){
        SimpleDateFormat format=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        //设置要读取的时间字符串格式
        try {
            endDate = format.parse(endTimeStr);
            endTime = endDate.getTime();
            //Log.i(TAG,"endtime="+endTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return this;
    }

    private Handler timeHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                computeTime();
                if (mSecond == 0 &&  mDay == 0 && mHour == 0 && mMin == 0 ) {
                    mTimer.cancel();
                }
            }
        }
    };

    private String getTv(long l){
        if(l>=10){
            return l+"";
        }else{
            return "0"+l;//小于10,,前面补位一个"0"
        }
    }

    /**
     * 倒计时计算
     */
    private void computeTime() {
        mSecond--;
        if (mSecond < 0) {
            mMin--;
            mSecond = 59;
            if (mMin < 0) {
                mMin = 59;
                mHour--;
                if (mHour < 0) {
                    // 倒计时结束
                    mHour = 23;
                    mDay--;
                    if(mDay < 0){
                        // 倒计时结束
                        mDay = 0;
                        mHour= 0;
                        mMin = 0;
                        mSecond = 0;
                    }
                }
            }
        }
    }

    /**
     * 开启倒计时
     *  //time为Date类型：在指定时间执行一次。
     *        timer.schedule(task, time);
     *  //firstTime为Date类型,period为long，表示从firstTime时刻开始，每隔period毫秒执行一次。
     *       timer.schedule(task, firstTime,period);
     *  //delay 为long类型：从现在起过delay毫秒执行一次。
     *       timer.schedule(task, delay);
     *  //delay为long,period为long：从现在起过delay毫秒以后，每隔period毫秒执行一次。
     *       timer.schedule(task, delay,period);
     */
    public CDTime startRun() {
        TimerTask mTimerTask = new TimerTask() {
            @Override
            public void run() {
                Message message = Message.obtain();
                message.what = 1;
                timeHandler.sendMessage(message);
            }
        };
        mTimer.schedule(mTimerTask,0,1000);
        return this;
    }


    public long getmDay() {
        return mDay;
    }

}

package com.mall.app;

import android.app.Activity;
import android.app.Application;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/29.
 */

public class MallAppcation extends Application {

    public static MallAppcation mallAppcation;

    private List<Activity> activities = new ArrayList<Activity>();

    @Override
    public void onCreate() {
        super.onCreate();
        mallAppcation = this;
    }


    /**
     * 新建了一个activity
     * @param activity
     */
    public void addActivity(Activity activity){
        activities.add(activity);
    }
    /**
     *  结束指定的Activity
     * @param activity
     */
    public void finishActivity(Activity activity){
        if (activity!=null) {
            this.activities.remove(activity);
            activity.finish();
            activity = null;
        }
    }
    /**
     * 应用退出，结束所有的activity
     */
    public void exit(){
        for (Activity activity : activities) {
            if (activity!=null) {
                activity.finish();
            }
        }
        System.exit(0);
    }
    /**
     * 关闭Activity列表中的所有Activity*/
    public void finishActivity(){
        for (Activity activity : activities) {
            if (null != activity) {
                activity.finish();
            }
        }
        //杀死该应用进程
        android.os.Process.killProcess(android.os.Process.myPid());
    }


}

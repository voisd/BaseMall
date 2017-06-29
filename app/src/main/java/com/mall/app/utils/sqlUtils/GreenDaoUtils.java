package com.mall.app.utils.sqlUtils;

import android.database.sqlite.SQLiteDatabase;

import com.mall.app.MallAppcation;
import com.mall.app.sqlbeen.DaoMaster;
import com.mall.app.sqlbeen.DaoSession;


/**
 * Created by Administrator on 2017/5/18.
 */

public class GreenDaoUtils {

    private DaoMaster.DevOpenHelper mHelper;
    private SQLiteDatabase db;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;

    private static GreenDaoUtils greenDaoUtils;

    private GreenDaoUtils(){}

    public static GreenDaoUtils getSingleTon(){
        if (greenDaoUtils==null){
            greenDaoUtils=new GreenDaoUtils();
        }
        return greenDaoUtils;
    }

    private void initGreenDao(){
        mHelper=new DaoMaster.DevOpenHelper(MallAppcation.mallAppcation,"sh.db",null);
        db=mHelper.getWritableDatabase();
        mDaoMaster=new DaoMaster(db);
        mDaoSession=mDaoMaster.newSession();
    }

    public DaoSession getmDaoSession() {
        if (mDaoMaster==null){
            initGreenDao();
        }
        return mDaoSession;
    }

    public SQLiteDatabase getDb() {
        if (db==null){
            initGreenDao();
        }
        return db;
    }

}

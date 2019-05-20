package com.example.ikiler.transport2019.DB;

import android.content.Context;

import com.example.ikiler.transport2019.bean.DaoMaster;
import com.example.ikiler.transport2019.bean.DaoSession;

/**
 * Created by ikiler on 19-5-13.
 */

public class SensorDB  extends DaoMaster.DevOpenHelper{

    DaoMaster daoMaster;
    DaoSession session;
    public SensorDB(Context context) {
        super(context, "sensor");
        daoMaster = new DaoMaster(getWritableDb());
    }

    public DaoSession getSession() {
        if (session == null)
            session = daoMaster.newSession();
        return session;
    }
}

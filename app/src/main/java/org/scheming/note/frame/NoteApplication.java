package org.scheming.note.frame;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.scheming.greendao.dao.DaoMaster;
import org.scheming.greendao.dao.DaoSession;
import org.scheming.greendao.dao.NoteDao;
import org.scheming.note.R;

import java.lang.reflect.Field;

/**
 * Created by Scheming on 2015/5/29.
 */
public class NoteApplication extends Application {
    private static NoteApplication instance;
    private DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static NoteApplication getInstance() {
        return instance;
    }

    public DaoSession getDaoSession() {
        if (daoSession == null) {
            DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "note_db", null);
            SQLiteDatabase database = helper.getWritableDatabase();
            DaoMaster daoMaster = new DaoMaster(database);
            daoSession = daoMaster.newSession();
        }
        return daoSession;
    }

    public int getStatusBarHeight() {
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0;
        int height;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            height = getResources().getDimensionPixelSize(x);
            Log.d("status_bar_height", height + "");
            return height;
        } catch (Exception e1) {
            Log.d("status_bar_height", "fail");
            e1.printStackTrace();
            return 75;
        }
    }
}

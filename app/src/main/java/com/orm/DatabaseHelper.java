package com.orm;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.bean.PersonanlBean;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;


/**
 * 数据库工具类
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
    private static final String TAG = DatabaseHelper.class.getSimpleName();
    private static final String TABLE_NAME = "ormtable.db";
    private static final int DATABASE_VERSION = 11;

    public DatabaseHelper(Context context) {
        super(context, TABLE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, PersonanlBean.class);   //根据PersonalBean来进行创建操作
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, PersonanlBean.class, true); //如果版本有更新则会执行onUpgrade方法，
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 用单例来生成DaoHelp对象
    private static DatabaseHelper databaseHelper;

    public static synchronized DatabaseHelper getDatabaseHelper(Context context) {
        if (databaseHelper == null) {
            synchronized (DatabaseHelper.class) {
                if (databaseHelper == null) databaseHelper = new DatabaseHelper((context));
            }
        }
        return databaseHelper;
    }

    //利用生成的daoHelp对象来生成Dao对象，该对象是处理数据库的关键要素
    private Dao<PersonanlBean, Integer> databaseDao;

    public Dao<PersonanlBean, Integer> getDatabaseDao() throws SQLException {
        if (databaseDao == null) {
            databaseDao = getDao(PersonanlBean.class);
        }
        return databaseDao;
    }

    @Override
    public void close() {
        super.close();
        databaseDao = null;
    }

}

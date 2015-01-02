package com.vector.onetodo.db.gen;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import de.greenrobot.dao.AbstractDaoMaster;
import de.greenrobot.dao.identityscope.IdentityScopeType;

import com.vector.onetodo.db.gen.CheckListDao;
import com.vector.onetodo.db.gen.AssignDao;
import com.vector.onetodo.db.gen.ShareDao;
import com.vector.onetodo.db.gen.FriendsDao;
import com.vector.onetodo.db.gen.ReminderDao;
import com.vector.onetodo.db.gen.RepeatDao;
import com.vector.onetodo.db.gen.LabelDao;
import com.vector.onetodo.db.gen.AttachDao;
import com.vector.onetodo.db.gen.CommentDao;
import com.vector.onetodo.db.gen.ToDoDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * Master of DAO (schema version 1): knows all DAOs.
*/
public class DaoMaster extends AbstractDaoMaster {
    public static final int SCHEMA_VERSION = 1;

    /** Creates underlying database table using DAOs. */
    public static void createAllTables(SQLiteDatabase db, boolean ifNotExists) {
        CheckListDao.createTable(db, ifNotExists);
        AssignDao.createTable(db, ifNotExists);
        ShareDao.createTable(db, ifNotExists);
        FriendsDao.createTable(db, ifNotExists);
        ReminderDao.createTable(db, ifNotExists);
        RepeatDao.createTable(db, ifNotExists);
        LabelDao.createTable(db, ifNotExists);
        AttachDao.createTable(db, ifNotExists);
        CommentDao.createTable(db, ifNotExists);
        ToDoDao.createTable(db, ifNotExists);
    }
    
    /** Drops underlying database table using DAOs. */
    public static void dropAllTables(SQLiteDatabase db, boolean ifExists) {
        CheckListDao.dropTable(db, ifExists);
        AssignDao.dropTable(db, ifExists);
        ShareDao.dropTable(db, ifExists);
        FriendsDao.dropTable(db, ifExists);
        ReminderDao.dropTable(db, ifExists);
        RepeatDao.dropTable(db, ifExists);
        LabelDao.dropTable(db, ifExists);
        AttachDao.dropTable(db, ifExists);
        CommentDao.dropTable(db, ifExists);
        ToDoDao.dropTable(db, ifExists);
    }
    
    public static abstract class OpenHelper extends SQLiteOpenHelper {

        public OpenHelper(Context context, String name, CursorFactory factory) {
            super(context, name, factory, SCHEMA_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.i("greenDAO", "Creating tables for schema version " + SCHEMA_VERSION);
            createAllTables(db, false);
        }
    }
    
    /** WARNING: Drops all table on Upgrade! Use only during development. */
    public static class DevOpenHelper extends OpenHelper {
        public DevOpenHelper(Context context, String name, CursorFactory factory) {
            super(context, name, factory);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.i("greenDAO", "Upgrading schema from version " + oldVersion + " to " + newVersion + " by dropping all tables");
            dropAllTables(db, true);
            onCreate(db);
        }
    }

    public DaoMaster(SQLiteDatabase db) {
        super(db, SCHEMA_VERSION);
        registerDaoClass(CheckListDao.class);
        registerDaoClass(AssignDao.class);
        registerDaoClass(ShareDao.class);
        registerDaoClass(FriendsDao.class);
        registerDaoClass(ReminderDao.class);
        registerDaoClass(RepeatDao.class);
        registerDaoClass(LabelDao.class);
        registerDaoClass(AttachDao.class);
        registerDaoClass(CommentDao.class);
        registerDaoClass(ToDoDao.class);
    }
    
    public DaoSession newSession() {
        return new DaoSession(db, IdentityScopeType.Session, daoConfigMap);
    }
    
    public DaoSession newSession(IdentityScopeType type) {
        return new DaoSession(db, type, daoConfigMap);
    }
    
}

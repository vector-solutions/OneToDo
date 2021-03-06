package com.vector.onetodo.db.gen;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import com.vector.onetodo.db.gen.Reminder;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table REMINDER.
*/
public class ReminderDao extends AbstractDao<Reminder, Long> {

    public static final String TABLENAME = "REMINDER";

    /**
     * Properties of entity Reminder.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Is_time_location = new Property(1, Boolean.class, "is_time_location", false, "IS_TIME_LOCATION");
        public final static Property Is_alertNotification = new Property(2, Boolean.class, "is_alertNotification", false, "IS_ALERT_NOTIFICATION");
        public final static Property Is_alertEmail = new Property(3, Boolean.class, "is_alertEmail", false, "IS_ALERT_EMAIL");
        public final static Property Location = new Property(4, String.class, "location", false, "LOCATION");
        public final static Property Location_tag = new Property(5, String.class, "location_tag", false, "LOCATION_TAG");
        public final static Property Location_type = new Property(6, Integer.class, "location_type", false, "LOCATION_TYPE");
        public final static Property Time = new Property(7, Long.class, "time", false, "TIME");
    };


    public ReminderDao(DaoConfig config) {
        super(config);
    }
    
    public ReminderDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'REMINDER' (" + //
                "'_id' INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "'IS_TIME_LOCATION' INTEGER," + // 1: is_time_location
                "'IS_ALERT_NOTIFICATION' INTEGER," + // 2: is_alertNotification
                "'IS_ALERT_EMAIL' INTEGER," + // 3: is_alertEmail
                "'LOCATION' TEXT," + // 4: location
                "'LOCATION_TAG' TEXT," + // 5: location_tag
                "'LOCATION_TYPE' INTEGER," + // 6: location_type
                "'TIME' INTEGER);"); // 7: time
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'REMINDER'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, Reminder entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        Boolean is_time_location = entity.getIs_time_location();
        if (is_time_location != null) {
            stmt.bindLong(2, is_time_location ? 1l: 0l);
        }
 
        Boolean is_alertNotification = entity.getIs_alertNotification();
        if (is_alertNotification != null) {
            stmt.bindLong(3, is_alertNotification ? 1l: 0l);
        }
 
        Boolean is_alertEmail = entity.getIs_alertEmail();
        if (is_alertEmail != null) {
            stmt.bindLong(4, is_alertEmail ? 1l: 0l);
        }
 
        String location = entity.getLocation();
        if (location != null) {
            stmt.bindString(5, location);
        }
 
        String location_tag = entity.getLocation_tag();
        if (location_tag != null) {
            stmt.bindString(6, location_tag);
        }
 
        Integer location_type = entity.getLocation_type();
        if (location_type != null) {
            stmt.bindLong(7, location_type);
        }
 
        Long time = entity.getTime();
        if (time != null) {
            stmt.bindLong(8, time);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public Reminder readEntity(Cursor cursor, int offset) {
        Reminder entity = new Reminder( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getShort(offset + 1) != 0, // is_time_location
            cursor.isNull(offset + 2) ? null : cursor.getShort(offset + 2) != 0, // is_alertNotification
            cursor.isNull(offset + 3) ? null : cursor.getShort(offset + 3) != 0, // is_alertEmail
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // location
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // location_tag
            cursor.isNull(offset + 6) ? null : cursor.getInt(offset + 6), // location_type
            cursor.isNull(offset + 7) ? null : cursor.getLong(offset + 7) // time
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, Reminder entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setIs_time_location(cursor.isNull(offset + 1) ? null : cursor.getShort(offset + 1) != 0);
        entity.setIs_alertNotification(cursor.isNull(offset + 2) ? null : cursor.getShort(offset + 2) != 0);
        entity.setIs_alertEmail(cursor.isNull(offset + 3) ? null : cursor.getShort(offset + 3) != 0);
        entity.setLocation(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setLocation_tag(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setLocation_type(cursor.isNull(offset + 6) ? null : cursor.getInt(offset + 6));
        entity.setTime(cursor.isNull(offset + 7) ? null : cursor.getLong(offset + 7));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(Reminder entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(Reminder entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
}

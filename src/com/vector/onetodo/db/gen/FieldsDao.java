package com.vector.onetodo.db.gen;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table FIELDS.
*/
public class FieldsDao extends AbstractDao<Fields, Long> {

    public static final String TABLENAME = "FIELDS";

    /**
     * Properties of entity Fields.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Field_name = new Property(1, String.class, "field_name", false, "FIELD_NAME");
        public final static Property Ischecked = new Property(2, Boolean.class, "ischecked", false, "ISCHECKED");
    };


    public FieldsDao(DaoConfig config) {
        super(config);
    }
    
    public FieldsDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'FIELDS' (" + //
                "'_id' INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "'FIELD_NAME' TEXT," + // 1: field_name
                "'ISCHECKED' INTEGER);"); // 2: ischecked
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'FIELDS'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, Fields entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String field_name = entity.getField_name();
        if (field_name != null) {
            stmt.bindString(2, field_name);
        }
 
        Boolean ischecked = entity.getIschecked();
        if (ischecked != null) {
            stmt.bindLong(3, ischecked ? 1l: 0l);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public Fields readEntity(Cursor cursor, int offset) {
        Fields entity = new Fields( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // field_name
            cursor.isNull(offset + 2) ? null : cursor.getShort(offset + 2) != 0 // ischecked
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, Fields entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setField_name(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setIschecked(cursor.isNull(offset + 2) ? null : cursor.getShort(offset + 2) != 0);
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(Fields entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(Fields entity) {
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

package com.vector.onetodo.db.gen;

import java.util.Map;

import android.database.sqlite.SQLiteDatabase;
import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see de.greenrobot.dao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig fieldsDaoConfig;
    private final DaoConfig checkListDaoConfig;
    private final DaoConfig assignDaoConfig;
    private final DaoConfig shareDaoConfig;
    private final DaoConfig friendsDaoConfig;
    private final DaoConfig reminderDaoConfig;
    private final DaoConfig labelDaoConfig;
    private final DaoConfig labelNameDaoConfig;
    private final DaoConfig toDoDaoConfig;

    private final FieldsDao fieldsDao;
    private final CheckListDao checkListDao;
    private final AssignDao assignDao;
    private final ShareDao shareDao;
    private final FriendsDao friendsDao;
    private final ReminderDao reminderDao;
    private final LabelDao labelDao;
    private final LabelNameDao labelNameDao;
    private final ToDoDao toDoDao;

    public DaoSession(SQLiteDatabase db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        fieldsDaoConfig = daoConfigMap.get(FieldsDao.class).clone();
        fieldsDaoConfig.initIdentityScope(type);

        checkListDaoConfig = daoConfigMap.get(CheckListDao.class).clone();
        checkListDaoConfig.initIdentityScope(type);

        assignDaoConfig = daoConfigMap.get(AssignDao.class).clone();
        assignDaoConfig.initIdentityScope(type);

        shareDaoConfig = daoConfigMap.get(ShareDao.class).clone();
        shareDaoConfig.initIdentityScope(type);

        friendsDaoConfig = daoConfigMap.get(FriendsDao.class).clone();
        friendsDaoConfig.initIdentityScope(type);

        reminderDaoConfig = daoConfigMap.get(ReminderDao.class).clone();
        reminderDaoConfig.initIdentityScope(type);

        labelDaoConfig = daoConfigMap.get(LabelDao.class).clone();
        labelDaoConfig.initIdentityScope(type);

        labelNameDaoConfig = daoConfigMap.get(LabelNameDao.class).clone();
        labelNameDaoConfig.initIdentityScope(type);

        toDoDaoConfig = daoConfigMap.get(ToDoDao.class).clone();
        toDoDaoConfig.initIdentityScope(type);

        fieldsDao = new FieldsDao(fieldsDaoConfig, this);
        checkListDao = new CheckListDao(checkListDaoConfig, this);
        assignDao = new AssignDao(assignDaoConfig, this);
        shareDao = new ShareDao(shareDaoConfig, this);
        friendsDao = new FriendsDao(friendsDaoConfig, this);
        reminderDao = new ReminderDao(reminderDaoConfig, this);
        labelDao = new LabelDao(labelDaoConfig, this);
        labelNameDao = new LabelNameDao(labelNameDaoConfig, this);
        toDoDao = new ToDoDao(toDoDaoConfig, this);

        registerDao(Fields.class, fieldsDao);
        registerDao(CheckList.class, checkListDao);
        registerDao(Assign.class, assignDao);
        registerDao(Share.class, shareDao);
        registerDao(Friends.class, friendsDao);
        registerDao(Reminder.class, reminderDao);
        registerDao(Label.class, labelDao);
        registerDao(LabelName.class, labelNameDao);
        registerDao(ToDo.class, toDoDao);
    }
    
    public void clear() {
        fieldsDaoConfig.getIdentityScope().clear();
        checkListDaoConfig.getIdentityScope().clear();
        assignDaoConfig.getIdentityScope().clear();
        shareDaoConfig.getIdentityScope().clear();
        friendsDaoConfig.getIdentityScope().clear();
        reminderDaoConfig.getIdentityScope().clear();
        labelDaoConfig.getIdentityScope().clear();
        labelNameDaoConfig.getIdentityScope().clear();
        toDoDaoConfig.getIdentityScope().clear();
    }

    public FieldsDao getFieldsDao() {
        return fieldsDao;
    }

    public CheckListDao getCheckListDao() {
        return checkListDao;
    }

    public AssignDao getAssignDao() {
        return assignDao;
    }

    public ShareDao getShareDao() {
        return shareDao;
    }

    public FriendsDao getFriendsDao() {
        return friendsDao;
    }

    public ReminderDao getReminderDao() {
        return reminderDao;
    }

    public LabelDao getLabelDao() {
        return labelDao;
    }

    public LabelNameDao getLabelNameDao() {
        return labelNameDao;
    }

    public ToDoDao getToDoDao() {
        return toDoDao;
    }

}
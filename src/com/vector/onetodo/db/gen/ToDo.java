package com.vector.onetodo.db.gen;

import com.vector.onetodo.db.gen.DaoSession;
import de.greenrobot.dao.DaoException;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table TO_DO.
 */
public class ToDo {

    private Long id;
    private Integer user_id;
    private Integer todo_type_id;
    private Integer parent;
    private String title;
    private Boolean is_allday;
    private Long start_date;
    private Long end_date;
    private String location;
    private Integer priority;
    private String notes;
    private long todo_id;

    /** Used to resolve relations */
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    private transient ToDoDao myDao;

    private CheckList checkList;
    private Long checkList__resolvedKey;

    private Assign assign;
    private Long assign__resolvedKey;

    private Share share;
    private Long share__resolvedKey;

    private Label label;
    private Long label__resolvedKey;

    private Reminder reminder;
    private Long reminder__resolvedKey;

    private Repeat repeat;
    private Long repeat__resolvedKey;


    public ToDo() {
    }

    public ToDo(Long id) {
        this.id = id;
    }

    public ToDo(Long id, Integer user_id, Integer todo_type_id, Integer parent, String title, Boolean is_allday, Long start_date, Long end_date, String location, Integer priority, String notes, long todo_id) {
        this.id = id;
        this.user_id = user_id;
        this.todo_type_id = todo_type_id;
        this.parent = parent;
        this.title = title;
        this.is_allday = is_allday;
        this.start_date = start_date;
        this.end_date = end_date;
        this.location = location;
        this.priority = priority;
        this.notes = notes;
        this.todo_id = todo_id;
    }

    /** called by internal mechanisms, do not call yourself. */
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getToDoDao() : null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getTodo_type_id() {
        return todo_type_id;
    }

    public void setTodo_type_id(Integer todo_type_id) {
        this.todo_type_id = todo_type_id;
    }

    public Integer getParent() {
        return parent;
    }

    public void setParent(Integer parent) {
        this.parent = parent;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getIs_allday() {
        return is_allday;
    }

    public void setIs_allday(Boolean is_allday) {
        this.is_allday = is_allday;
    }

    public Long getStart_date() {
        return start_date;
    }

    public void setStart_date(Long start_date) {
        this.start_date = start_date;
    }

    public Long getEnd_date() {
        return end_date;
    }

    public void setEnd_date(Long end_date) {
        this.end_date = end_date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public long getTodo_id() {
        return todo_id;
    }

    public void setTodo_id(long todo_id) {
        this.todo_id = todo_id;
    }

    /** To-one relationship, resolved on first access. */
    public CheckList getCheckList() {
        long __key = this.todo_id;
        if (checkList__resolvedKey == null || !checkList__resolvedKey.equals(__key)) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            CheckListDao targetDao = daoSession.getCheckListDao();
            CheckList checkListNew = targetDao.load(__key);
            synchronized (this) {
                checkList = checkListNew;
            	checkList__resolvedKey = __key;
            }
        }
        return checkList;
    }

    public void setCheckList(CheckList checkList) {
        if (checkList == null) {
            throw new DaoException("To-one property 'todo_id' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.checkList = checkList;
            todo_id = checkList.getId();
            checkList__resolvedKey = todo_id;
        }
    }

    /** To-one relationship, resolved on first access. */
    public Assign getAssign() {
        long __key = this.todo_id;
        if (assign__resolvedKey == null || !assign__resolvedKey.equals(__key)) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            AssignDao targetDao = daoSession.getAssignDao();
            Assign assignNew = targetDao.load(__key);
            synchronized (this) {
                assign = assignNew;
            	assign__resolvedKey = __key;
            }
        }
        return assign;
    }

    public void setAssign(Assign assign) {
        if (assign == null) {
            throw new DaoException("To-one property 'todo_id' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.assign = assign;
            todo_id = assign.getId();
            assign__resolvedKey = todo_id;
        }
    }

    /** To-one relationship, resolved on first access. */
    public Share getShare() {
        long __key = this.todo_id;
        if (share__resolvedKey == null || !share__resolvedKey.equals(__key)) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            ShareDao targetDao = daoSession.getShareDao();
            Share shareNew = targetDao.load(__key);
            synchronized (this) {
                share = shareNew;
            	share__resolvedKey = __key;
            }
        }
        return share;
    }

    public void setShare(Share share) {
        if (share == null) {
            throw new DaoException("To-one property 'todo_id' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.share = share;
            todo_id = share.getId();
            share__resolvedKey = todo_id;
        }
    }

    /** To-one relationship, resolved on first access. */
    public Label getLabel() {
        long __key = this.todo_id;
        if (label__resolvedKey == null || !label__resolvedKey.equals(__key)) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            LabelDao targetDao = daoSession.getLabelDao();
            Label labelNew = targetDao.load(__key);
            synchronized (this) {
                label = labelNew;
            	label__resolvedKey = __key;
            }
        }
        return label;
    }

    public void setLabel(Label label) {
        if (label == null) {
            throw new DaoException("To-one property 'todo_id' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.label = label;
            todo_id = label.getId();
            label__resolvedKey = todo_id;
        }
    }

    /** To-one relationship, resolved on first access. */
    public Reminder getReminder() {
        long __key = this.todo_id;
        if (reminder__resolvedKey == null || !reminder__resolvedKey.equals(__key)) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            ReminderDao targetDao = daoSession.getReminderDao();
            Reminder reminderNew = targetDao.load(__key);
            synchronized (this) {
                reminder = reminderNew;
            	reminder__resolvedKey = __key;
            }
        }
        return reminder;
    }

    public void setReminder(Reminder reminder) {
        if (reminder == null) {
            throw new DaoException("To-one property 'todo_id' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.reminder = reminder;
            todo_id = reminder.getId();
            reminder__resolvedKey = todo_id;
        }
    }

    /** To-one relationship, resolved on first access. */
    public Repeat getRepeat() {
        long __key = this.todo_id;
        if (repeat__resolvedKey == null || !repeat__resolvedKey.equals(__key)) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            RepeatDao targetDao = daoSession.getRepeatDao();
            Repeat repeatNew = targetDao.load(__key);
            synchronized (this) {
                repeat = repeatNew;
            	repeat__resolvedKey = __key;
            }
        }
        return repeat;
    }

    public void setRepeat(Repeat repeat) {
        if (repeat == null) {
            throw new DaoException("To-one property 'todo_id' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.repeat = repeat;
            todo_id = repeat.getId();
            repeat__resolvedKey = todo_id;
        }
    }

    /** Convenient call for {@link AbstractDao#delete(Object)}. Entity must attached to an entity context. */
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.delete(this);
    }

    /** Convenient call for {@link AbstractDao#update(Object)}. Entity must attached to an entity context. */
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.update(this);
    }

    /** Convenient call for {@link AbstractDao#refresh(Object)}. Entity must attached to an entity context. */
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.refresh(this);
    }

}

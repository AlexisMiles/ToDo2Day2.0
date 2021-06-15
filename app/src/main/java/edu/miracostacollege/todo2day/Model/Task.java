package edu.miracostacollege.todo2day.Model;

public class Task {

    //Member Variables
    private long mId;
    private String mDescription;
    private boolean mIsDone;

    //Constructors
    public Task(long id, String description, boolean isDone) {
        mId = id;
        mDescription = description;
        mIsDone = isDone;
    }

    public Task(String description) {
        this(-1, description, false);
    }

    //Accessors/Mutators
    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public boolean isDone() {
        return mIsDone;
    }

    public void setDone(boolean done) {
        mIsDone = done;
    }

    //toString
    @Override
    public String toString() {
        return "Task{" +
                "Id=" + mId +
                ", Description='" + mDescription + '\'' +
                ", IsDone=" + mIsDone +
                '}';
    }
}

package edu.miracostacollege.todo2day.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    public static final String TAG = DBHelper.class.getSimpleName();

    //STEP 1) Define all the database info in CONSTANTS
    public static final String DATABASE_NAME = "ToDo2Day";
    public static final String DATABASE_TABLE = "Tasks";
    public static final String FIELD_PRIMARY_KEY = "_id";
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_IS_DONE = "is_done";

    //Constructor for DBHelper
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Create all of the database tables
        //1) Determine whether to read or write the database (CREATE = write)
        //Opens a writable connection to the ToDo2Day database, remember to close!!
        db = getWritableDatabase();

        //Execute our create table statement
        String sql = "CREATE TABLE IF NOT EXISTS " + DATABASE_TABLE + "("
                    + FIELD_PRIMARY_KEY + " INTEGER PRIMARY KEY, "
                    + FIELD_DESCRIPTION + " TEXT,"
                    + FIELD_IS_DONE + " INTEGER" + ")";

        //Log the SQL String
        Log.i(TAG, sql);

        db.execSQL(sql); //Execute

        db.close();
    }

    //Method to add a task to the database
    //Corresponds to an inset operation
    public void addTask(Task task){
        //Do not grab ID, let the database assign an ID
        String description = task.getDescription();
        boolean isDone = task.isDone();

        //Get a reference to the database (writable)
        SQLiteDatabase db = getWritableDatabase();

        //ContentValues is a key/value mapping
        ContentValues values = new ContentValues();
        values.put(FIELD_DESCRIPTION, description);
        values.put(FIELD_IS_DONE, isDone ? 1 : 0);//Remember to change boolean to INTEGER when sending to SQL
        long id = db.insert(DATABASE_TABLE, null, values);
        //After adding the task, set its ID to the one generate by the database
        task.setId(id);


        db.insert(DATABASE_TABLE, null, values); //Code is weird for second parameter, always just put in null

        //Don't forget to close the database when done
        db.close();
    }

    //Method to get all the Task existing in the database
    public List<Task> getAllTasks(){
        //Construct empty list
        List<Task> allTasks = new ArrayList<>();

        //Fill it from the database
        SQLiteDatabase db = getReadableDatabase();
        //Make a query to extract everything
        //Query results in SQLite are called Cursor objects
        Cursor cursor = db.query(DATABASE_TABLE,
                new String[]{FIELD_PRIMARY_KEY, FIELD_DESCRIPTION, FIELD_IS_DONE},
                null,
                null,
                null,
                null,
                null);

        //Loop through the cursor results, one at a time
        //Create Task objects and add them to the List
        //First determine if there are results
        if(cursor.moveToFirst()){
            do{
                Task task = new Task(cursor.getLong(0),
                        cursor.getString(1),
                        cursor.getInt(2)==1);//To make it a boolean
                allTasks.add(task);
            }while(cursor.moveToNext());
        }
        //Remember to close the cursor first and then the database
        cursor.close();
        db.close();

        //Return the filled list
        return allTasks;
    }

    //Method to delete all tasks
    public void clearAllTasks(){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(DATABASE_TABLE, null, null);
        //Table is cleared
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

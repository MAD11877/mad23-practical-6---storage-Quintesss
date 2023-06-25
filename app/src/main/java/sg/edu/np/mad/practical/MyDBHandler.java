package sg.edu.np.mad.practical;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.service.autofill.UserData;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Random;

public class MyDBHandler extends SQLiteOpenHelper {
    String title = "MyDBHandler";

    public static String DATABASE_NAME = "accountDB.db";
    public static int DATABASE_VERSION = 1;
    public static String ACCOUNTS = "accounts"; //Table name
    public static String COLUMN_ID = "UserID";
    public static String COLUMN_USERNAME = "Username";
    public static String COLUMN_DESC = "Description";
    public static String COLUMN_FOLLOW = "Follow";

    public MyDBHandler( Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
        Log.i(title, "DB constructor");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_COMMAND = "CREATE TABLE " + ACCOUNTS + "(" + COLUMN_ID + " INTEGER, " + COLUMN_USERNAME + " TEXT, " + COLUMN_DESC + " TEXT, " + COLUMN_FOLLOW + " INTEGER)";
        Log.i(title, CREATE_TABLE_COMMAND);
        db.execSQL(CREATE_TABLE_COMMAND);

        //populate db with 20 users
        for(int i=0; i<20; i++){
            Random ran = new Random();
            int myRandomNumber = ran.nextInt(999999999);
            int randomDesc =ran.nextInt(999999999);
            int randomBool = ran.nextInt(2); //random value 0-1

            ContentValues values = new ContentValues();
            values.put(COLUMN_ID, i);
            values.put(COLUMN_USERNAME, "Name"+myRandomNumber);
            values.put(COLUMN_DESC, "Description "+randomDesc);
            values.put(COLUMN_FOLLOW, randomBool);

            Log.i(title, "Adding User "+values);
            db.insert(ACCOUNTS, null, values);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ACCOUNTS);
        Log.i(title, "Drop and Create new DB");
        onCreate(db);
    }

    public void addUser(User user){
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, user.getName());
        values.put(COLUMN_DESC, user.getDescription());

        int followStat;
        if(user.getFollowed()){
            followStat = 1;
        }else {followStat = 0;}
        values.put(COLUMN_FOLLOW, followStat);

        SQLiteDatabase db = this.getWritableDatabase();
        Log.i(title, "Adding User "+values);
        db.insert(ACCOUNTS, null, values);
        db.close();
    }

    // return list of user
    public ArrayList<User> getUsers(){
        String QUERY = "SELECT * FROM " + ACCOUNTS;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(QUERY, null);

        String Username, Desc;
        int UserId, FollowedInt;
        Boolean Followed;
        ArrayList<User> userArrayList = new ArrayList<>();
        while(cursor.moveToNext()){
            UserId = cursor.getInt(0);
            Username = cursor.getString(1);
            Desc = cursor.getString(2);
            FollowedInt = cursor.getInt(3);

            if (FollowedInt==0){Followed=Boolean.FALSE;}
            else{Followed=Boolean.TRUE;}

            User user = new User(Username, Desc, UserId, Followed);
            userArrayList.add(user);
        }

        return userArrayList;
    }

    //update follow status
    public void updateUser(String username){
        String QUERY = "SELECT * FROM " + ACCOUNTS + " WHERE " + COLUMN_USERNAME + "=\"" + username + "\"";
        int follow_status = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(QUERY, null);

        if(cursor.moveToFirst()){
            if(cursor.getInt(3) == 0){
                follow_status = 1;
            }else{follow_status=0;}
        }

        String QUERY_UPDATE = "UPDATE " + ACCOUNTS + " SET " + COLUMN_FOLLOW + "=" + follow_status + " WHERE " + COLUMN_USERNAME + "=\"" + username + "\"";
        Log.i(title, QUERY_UPDATE);
        db.execSQL(QUERY_UPDATE);
    } //db updates, but value not reflected during the same session!

    /*
    public User findUser(String username){
        String QUERY = "SELECT * FROM " + ACCOUNTS + "WHERE " + COLUMN_USERNAME + "=\"" + username + "\"";
        Log.i(title, "Find with command " + QUERY);

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(QUERY, null);

        User queryResult = new User();

        if(cursor.moveToFirst()){
            queryResult.setName(cursor.getString(0));
            queryResult.setDescription(cursor.getString(1));
            cursor.close();
        }else{
            queryResult=null;
        }
        return queryResult;
    }
    */
}

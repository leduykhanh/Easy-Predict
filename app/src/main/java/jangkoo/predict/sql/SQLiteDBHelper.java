package jangkoo.predict.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

//Basic Database
public class SQLiteDBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Articles.db";
    private String SQL_CREATE_QUERY = "CREATE TABLE bet_history (id integer primary key AUTOINCREMENT, " +
                                        "time TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                                        "amount INTEGER DEFAULT 100, " +
                                        "bet_id INTEGER DEFAULT 1, " +
                                        "team1 TEXT DEFAULT 'Barca',team2 TEXT DEFAULT 'Arsenal',"+
                                        "result INTEGER  DEFAULT 0, " +
                                        "your_bet INTEGER DEFAULT 100)" ;
    private String SQL_INSERT_DUMMY_DATA = "INSERT INTO bet_history(time,amount,team1,team2,result,your_bet,bet_id)" +
            "VALUES('2016-01-01',100,'Barca','Arsenal',0,1,1);";
    private String SQL_DELETE_QUERY = "DROP TABLE IF EXISTS bet_history";
    public SQLiteDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_QUERY);
        db.execSQL(SQL_INSERT_DUMMY_DATA);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(SQL_DELETE_QUERY);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
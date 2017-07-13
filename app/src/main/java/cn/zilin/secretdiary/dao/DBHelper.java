package cn.zilin.secretdiary.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper{
	
	private final static String DBNAME = "secret_diary";
	private final static int VERSION = 1;
	private static final String DIARY_CREATE = "create table diary (_id integer primary key autoincrement, title text, content text, mood text, sign text, tape text, time text)";

	public DBHelper(Context context) {
		super(context, DBNAME, null, VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(DIARY_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS diary");
		onCreate(db);
	}

}
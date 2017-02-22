package com.example.everyday;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseSqlite extends SQLiteOpenHelper{

	public DatabaseSqlite(Context context) {
		super(context, "db", null, 1);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL("CREATE TABLE daygram(_id INTEGER PRIMARY KEY AUTOINCREMENT," +
				"week TEXT,year INTEGER,month TEXT," +
				"date INTEGER,content TEXT)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}

}

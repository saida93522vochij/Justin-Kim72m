package jp.android.fukuoka.scorecaster.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "data";
	private static final int DATABASE_VERSION = 1;

	private static final String CREATE_MESSAGE_TABLE_SQL = "create table score "
		+ "(rowid integer primary key autoincrement, "
		+ "score1 integer not null, "
		+ "score2 integer not null)";

	private static final String DROP_MESSAGE_TABLE_SQL = "drop table if exists message";

	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(CREATE_MESSAGE_TABLE_SQL);
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL(DROP_MESSAGE_TABLE_SQL);
		onCreate(db);
	}
}

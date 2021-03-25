package jp.android.fukuoka.scorecaster.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class ScoreDao {

	private static final String TABLE_NAME="score";
	private static final String COLUMN_ID = "rowid";
	private static final String COLUMN_SCORE1 = "score1";
	private static final String COLUMN_SCORE2 = "score2";
	private static final String[] COLUMNS =
	{COLUMN_ID, COLUMN_SCORE1, COLUMN_SCORE2};

	private SQLiteDatabase db;

	public ScoreDao(SQLiteDatabase db) {
		this.db = db;
	}

	public long insert(Score score) {
		ContentValues values = new ContentValues();
		values.put(COLUMN_SCORE1, score.getScore_team1());
		values.put(COLUMN_SCORE2, score.getScore_team2());
		return db.insert(TABLE_NAME, null, values);
	}
	
	public int update(Score score) {
		ContentValues values = new ContentValues();
		values.put(COLUMN_SCORE1, score.getScore_team1());
		values.put(COLUMN_SCORE2, score.getScore_team2());
		String whereClause = "rowid = " + score.getRowid();
		return db.update(TABLE_NAME, values, whereClause, null);
	}

	public List<Score> findAll() {
		List<Score> scoreList = new ArrayList<Score>();
		Cursor cursor = db.query(TABLE_NAME, COLUMNS, null, null, null, null, COLUMN_ID);
		while(cursor.moveToNext()) {
			Score score = new Score(cursor.getInt(0), cursor.getInt(1), cursor.getInt(2));
			scoreList.add(score);
		}
		return scoreList;
	}
	
	public int delete(int rowId) {
		return db.delete(TABLE_NAME, "rowid = " + rowId, null);
	}
	
}

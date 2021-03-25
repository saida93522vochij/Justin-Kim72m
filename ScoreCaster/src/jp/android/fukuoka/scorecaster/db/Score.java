package jp.android.fukuoka.scorecaster.db;

public class Score {
	
	private int rowid;
	private int score_team1;
	private int score_team2;
	
	public Score(int score1, int score2) {
		this.rowid = Integer.MIN_VALUE;
		this.score_team1 = score1;
		this.score_team2 = score2;
	}

	public Score(int _rowid, int score1, int score2) {
		this.rowid = _rowid;
		this.score_team1 = score1;
		this.score_team2 = score2;
	}

	public int getScore_team1() {
		return score_team1;
	}

	public int getScore_team2() {
		return score_team2;
	}

	public int getRowid() {
		return rowid;
	}
	
}

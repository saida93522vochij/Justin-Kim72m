package jp.android.fukuoka.scorecaster;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;

public class SettingActivity extends PreferenceActivity {
	public static final String PREFS_NAME = "PrefsFile";

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.layout.setting);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
        
        // Restore preferences
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        String time = ((Integer)R.id.ListKey).toString();
        editor.putString("time", time);
        String twitterid = ((Integer)R.id.twitter_id).toString();
        editor.putString("twitterId", twitterid);
        String twitterpswd = ((Integer)R.id.twitter_pswd).toString();
        editor.putString("twitterPswd", twitterpswd);

        // Don't forget to commit your edits!!!
        editor.commit();		
	}
}
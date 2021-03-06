package jp.jagfukuoka.sodefuri.server.twitter;

import java.util.ArrayList;
import java.util.List;

import jp.jagfukuoka.sodefuri.preference.TwitterPreferences;
import twitter4j.ProfileImage;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;
import twitter4j.http.AccessToken;
import twitter4j.http.RequestToken;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class TwitterRequest {
	
	private static final String TWITTER_REQUEST = "TWITTER_REQUEST";
	
	/**
	 * 認証ページのURLを取得する。
	 * @param context
	 * @return
	 */
	public static String getAuthUrl(Context context){
			
		ConfigurationBuilder builder = new ConfigurationBuilder();
		Configuration conf = builder.setDebugEnabled(true)
		.setOAuthConsumerKey(TwitterPreferences.CONSUMER_KEY)
		.setOAuthConsumerSecret(TwitterPreferences.CONSUMER_SERCRET)
		.build();
		Twitter twitter = new TwitterFactory(conf).getInstance();
		String authorizationURL = "";
		try {
			RequestToken requestToken = twitter.getOAuthRequestToken();
			authorizationURL = requestToken.getAuthorizationURL();
			TwitterPreferences.storeRequestToken(context, requestToken.getToken(), requestToken.getTokenSecret());
		} catch (TwitterException e) {
			e.printStackTrace();
		}
		return authorizationURL;
	}
	
	/**
	 * pincodeを設定してtwitterの認証を完了する。
	 * @param context
	 * @param pincode
	 */
	public static void setPinCode(Context context, String pincode){
		Twitter twitter = new TwitterFactory().getInstance();
		twitter.setOAuthConsumer(TwitterPreferences.CONSUMER_KEY, TwitterPreferences.CONSUMER_SERCRET);
		RequestToken requestToken = new RequestToken(TwitterPreferences.getRequestToken(context), TwitterPreferences.getRequestTokenSercret(context));
		try {
			AccessToken oAuthAccessToken = twitter.getOAuthAccessToken(requestToken, pincode);
			TwitterPreferences.storeAccessToken(context, oAuthAccessToken.getToken(), oAuthAccessToken.getTokenSecret());
			TwitterPreferences.storeScreenName(context, twitter.getScreenName());
		} catch (TwitterException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 指定したユーザーのプロフィール画像URLを取得する。
	 * @param context
	 * @param screenName
	 * @return
	 */
	public static String getImageUrl(Context context, String screenName){
		String image = "";
		ConfigurationBuilder builder = new ConfigurationBuilder();
		Configuration conf = builder
				.setOAuthAccessToken(TwitterPreferences.getAccessToken(context))
				.setOAuthAccessTokenSecret(TwitterPreferences.getAccessTokenSercret(context))
				.setOAuthConsumerKey(TwitterPreferences.CONSUMER_KEY)
				.setOAuthConsumerSecret(TwitterPreferences.CONSUMER_SERCRET)
				.setDebugEnabled(true).build();
		Twitter twitter = new TwitterFactory(conf).getInstance();
		try {
			ProfileImage profileImage = twitter.getProfileImage(screenName, ProfileImage.NORMAL);
			image = profileImage.getURL();
			Log.d("image", image);
		} catch (TwitterException e) {
			e.printStackTrace();
		}
		return image;
	}

	/**
	 * 指定したユーザーのタイムラインを取得
	 * 
	 * @param screenName
	 * @return
	 */
	public static List<String> getUserTimeline(String screenName) {
		List<String> result = new ArrayList<String>();
		Twitter twitter = new TwitterFactory().getInstance();
		try {
			ResponseList<Status> userTimeline = twitter.getUserTimeline(screenName);
			for (Status status : userTimeline) {
				result.add(status.getText());
			}
		} catch (TwitterException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 指定したユーザーのプロフィールを取得する。
	 * @param context
	 * @param screenName
	 * @return
	 */
	public static String getProfile(Context context, String screenName) {
		String description = "";
		ConfigurationBuilder builder = new ConfigurationBuilder();
		Configuration conf = builder
				.setOAuthAccessToken(TwitterPreferences.getAccessToken(context))
				.setOAuthAccessTokenSecret(TwitterPreferences.getAccessTokenSercret(context))
				.setOAuthConsumerKey(TwitterPreferences.CONSUMER_KEY)
				.setOAuthConsumerSecret(TwitterPreferences.CONSUMER_SERCRET)
				.setDebugEnabled(true).build();
		Twitter twitter = new TwitterFactory(conf).getInstance();
		try {
			User user = twitter.showUser(screenName);
			description = user.getDescription();
			Log.d(TWITTER_REQUEST, description);
		} catch (TwitterException e) {
			e.printStackTrace();
		}
		return description;
	}

	/**
	 * 指定したユーザーをフォローする
	 * @param context
	 * @param screenName
	 */
	public static void createFriendship(Context context, String screenName) {
		ConfigurationBuilder builder = new ConfigurationBuilder();
		Configuration conf = builder.setOAuthAccessToken(TwitterPreferences.getAccessToken(context))
		.setOAuthAccessTokenSecret(TwitterPreferences.getAccessTokenSercret(context))
		.setOAuthConsumerKey(TwitterPreferences.CONSUMER_KEY)
		.setOAuthConsumerSecret(TwitterPreferences.CONSUMER_SERCRET)
		.setDebugEnabled(true)
		.build();
		Twitter twitter = new TwitterFactory(conf).getInstance();
		try {
			twitter.createFriendship(screenName);
			Toast.makeText(context, "フォローしました。", Toast.LENGTH_LONG).show();
		} catch (TwitterException e) {
			Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
			e.printStackTrace();
		}
	}

	/**
	 * 自分のスクリーンネームを取得する。
	 * @return
	 */
	public static String getMyScreenName(Context context) {
		ConfigurationBuilder builder = new ConfigurationBuilder();
		Configuration conf = builder.setOAuthAccessToken(TwitterPreferences.getAccessToken(context))
		.setOAuthAccessTokenSecret(TwitterPreferences.getAccessTokenSercret(context))
		.setOAuthConsumerKey(TwitterPreferences.CONSUMER_KEY)
		.setOAuthConsumerSecret(TwitterPreferences.CONSUMER_SERCRET)
		.setDebugEnabled(true)
		.build();
		Twitter twitter = new TwitterFactory(conf).getInstance();		
		String screenName = "";
		try {
			screenName = twitter.getScreenName();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (TwitterException e) {
			e.printStackTrace();
		}
		return screenName;
	}

}

package com.codepath.apps.basictwitter.data;

import com.codepath.apps.basictwitter.models.User;

import android.content.Context;
import android.content.SharedPreferences;

public class UserPreferences {

	private static final String PREFKEY = "userdata";
	private SharedPreferences userPrefs;
	
	public UserPreferences(Context context) {
		userPrefs = context.getSharedPreferences(PREFKEY, Context.MODE_PRIVATE);
	}
	
	public void updateUserData(User user) {
		SharedPreferences.Editor editor = userPrefs.edit();
		editor.putString("name", user.getName());
		editor.putString("screen_name", user.getScreenName());
		editor.putLong("uid", user.getUid());
		editor.putString("profile_image_url", user.getProfileImageUrl());
		editor.commit();
	}
	
	public User getUserData() {
		User user = new User();
		user.setName(userPrefs.getString("name", null));
		user.setScreenName(userPrefs.getString("screen_name", ""));
		user.setUid(userPrefs.getLong("uid", 0));
		user.setProfileImageUrl(userPrefs.getString("profile_image_url", ""));
		return user;
	}
}

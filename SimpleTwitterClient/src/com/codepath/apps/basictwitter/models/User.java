package com.codepath.apps.basictwitter.models;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

public class User {

	private String name;
	private long uid;
	private String screenName;
	private String profileImageUrl;
	
	
	public static User fromJSON(JSONObject json) {
		User user = new User();
		
		//extract values from json to populate member variables
		try {
			user.name = json.getString("name");
			user.screenName = json.getString("screen_name");
			user.uid = json.getLong("id");
			user.profileImageUrl = json.getString("profile_image_url");
			// once we have the user object populated with json object, add to shared preferences
			
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}		
		return user;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}

	public void setProfileImageUrl(String profileImageUrl) {
		this.profileImageUrl = profileImageUrl;
	}

	public String getName() {
		return name;
	}

	public long getUid() {
		return uid;
	}

	public String getScreenName() {
		return screenName;
	}

	public String getProfileImageUrl() {
		return profileImageUrl;
	}

}

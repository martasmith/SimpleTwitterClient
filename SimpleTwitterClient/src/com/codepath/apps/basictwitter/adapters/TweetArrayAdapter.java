package com.codepath.apps.basictwitter.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.basictwitter.R;
import com.codepath.apps.basictwitter.models.Tweet;
import com.nostra13.universalimageloader.core.ImageLoader;

public class TweetArrayAdapter extends ArrayAdapter<Tweet> {

	public TweetArrayAdapter(Context context, List<Tweet> tweets) {
		super(context, 0, tweets);	
	}
	
	// viewholder class 
	private static class ViewHolder {
		ImageView ivProfileImage;
		TextView tvUserName,tvBody,tvFullName,tvRelativeDate;
	}
	
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Tweet currentTweet = getItem(position);
		ViewHolder viewHolder;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.tweet_item, parent, false);
			viewHolder.ivProfileImage = (ImageView) convertView.findViewById(R.id.ivProfileImage);
			viewHolder.tvUserName = (TextView) convertView.findViewById(R.id.tvUserName);
			viewHolder.tvFullName = (TextView) convertView.findViewById(R.id.tvFullName);
			viewHolder.tvBody = (TextView) convertView.findViewById(R.id.tvBody);
			viewHolder.tvRelativeDate = (TextView) convertView.findViewById(R.id.tvRelativeDate);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		//populate the data into the template view using the data object
		ImageLoader imageLoader = ImageLoader.getInstance();
		viewHolder.tvFullName.setText(currentTweet.getUser().getName());
		viewHolder.tvUserName.setText("@"+ currentTweet.getUser().getScreenName());
		viewHolder.tvBody.setText(currentTweet.getBody());
		viewHolder.tvRelativeDate.setText(currentTweet.getRelativeTimeAgo());
		viewHolder.ivProfileImage.setImageResource(android.R.color.transparent);
		imageLoader.displayImage(currentTweet.getUser().getProfileImageUrl(), viewHolder.ivProfileImage);
		return convertView;
	}

}

/*
    Copyright 2012 Faisal Mooraby

    This file is part of RTwicudroid.

    RTwicudroid is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    RTwicudroid is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with RTwicudroid.  If not, see <http://www.gnu.org/licenses/>
*/
package com.QualbSoft.RTwicudroid;

import winterwell.jtwitter.OAuthSignpostClient;
import winterwell.jtwitter.Twitter;
import winterwell.jtwitter.Status;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class RTwicudroid_DisplayRTMenu extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Bundle passed_b = getIntent().getExtras();
		
		String tweet_string = passed_b.getString("RT_String");
		String s_user = passed_b.getString("s_user");
		String s_key_token_1 = passed_b.getString("s_key_token_1");
		String s_key_token_2 = passed_b.getString("s_key_token_2");
		
		winterwell.jtwitter.OAuthSignpostClient oauthClient = new OAuthSignpostClient(RTwicudroid_STATIC.RTwicudroid_OAUTHKEY, 
				RTwicudroid_STATIC.RTwicudroid_OAUTHSECRET, s_key_token_1, s_key_token_2);
    	
		final Twitter twitter = new Twitter(s_user, oauthClient);
		
		final Status rtstatus=RTwicudroid_STATIC.getGLOB_TWEETSET();
		
		int start_of_tweet_only_string = tweet_string.indexOf(":");
		final String tweet_only_string = tweet_string.substring(
				start_of_tweet_only_string + 3, tweet_string.length() - 19);
		final String tweet_user = tweet_string.substring(0,
				start_of_tweet_only_string);

		setContentView(com.QualbSoft.RTwicudroid.R.layout.retweetit);
		TextView RT_TextView = (TextView) findViewById(com.QualbSoft.RTwicudroid.R.id.TextViewTweet_in_RTView);
		RT_TextView.setText(tweet_string);

		Button RT_button = (Button) findViewById(com.QualbSoft.RTwicudroid.R.id.ButtonRT);
		Button Return_button = (Button) findViewById(com.QualbSoft.RTwicudroid.R.id.ButtonReturn);

		Return_button.setOnClickListener(new OnClickListener() {

			//@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}

		});

		RT_button.setOnClickListener(new OnClickListener() {

			//@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String RT_string_or = "RT @" + tweet_user + " "
						+ tweet_only_string;
				String RT_string_nw = "";

				if (RT_string_or.length() > 140) {
					RT_string_nw = RT_string_or.substring(0, 135) + " ...";
				}else {
					RT_string_nw = RT_string_or;
				}
				//twitter.updateStatus(RT_string_nw);
				twitter.retweet(rtstatus);
				
				finish();
			}

		});

	}
}

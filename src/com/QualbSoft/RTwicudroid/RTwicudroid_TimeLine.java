/*
    Copyright 2012 Faisal Mooraby

    This file is part of RTwicudroid.

    RTwicudroid is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    Foobar is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with Foobar.  If not, see <http://www.gnu.org/licenses/>
*/
package com.QualbSoft.RTwicudroid;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.QualbSoft.RTwicudroid.RTwicudroid_FUNC.TweetSet;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import winterwell.jtwitter.Twitter;
import winterwell.jtwitter.TwitterException;
import winterwell.jtwitter.Status;

public class RTwicudroid_TimeLine extends Activity {

	private String s_wotd;
	private String s_definition;
	private String s_user;
	private String s_key_token_1;
	private String s_key_token_2;
	private Bundle b_initial;
	private TweetSet tweetlist ;
	private Twitter twitter;
	
	/** Called when the activity is first created. */

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		b_initial = getIntent().getExtras();

		s_wotd = b_initial.getString("s_wotd_word");
		s_definition = b_initial.getString("s_wotd_def");
		s_user = b_initial.getString("s_user");
		s_key_token_1 = b_initial.getString("s_key_token_1");
		s_key_token_2 = b_initial.getString("s_key_token_2");

		
		twitter = new Twitter();
		
		tweetlist = TwitterWordSearch(twitter, s_wotd);

		setContentView(com.QualbSoft.RTwicudroid.R.layout.tllistview);
		final ListView listView = (ListView) findViewById(com.QualbSoft.RTwicudroid.R.id.ListViewTimeLine);
		listView.setAdapter(new ArrayAdapter(this,
				com.QualbSoft.RTwicudroid.R.layout.timeline_row,
				com.QualbSoft.RTwicudroid.R.id.TextView_tweet, tweetlist.twittertext));
		// listView.setAdapter(new
		// ArrayAdapter(this,com.QualbSoft.RTwicudroid.R.layout.timeline_row,com.QualbSoft.RTwicudroid.R.id.TextView_name_date,tweetlist));

		final Context contextactivity = this;
		Button refresh_button = (Button) findViewById(com.QualbSoft.RTwicudroid.R.id.ButtonTimeLine);
		refresh_button.setOnClickListener(new OnClickListener() {

			//@Override
			public void onClick(View v) {
				tweetlist = TwitterWordSearch(twitter, s_wotd);
				listView.setAdapter(new ArrayAdapter(contextactivity,
						com.QualbSoft.RTwicudroid.R.layout.timeline_row,
						com.QualbSoft.RTwicudroid.R.id.TextView_tweet,
						tweetlist.twittertext));

			}

		});

		listView.setOnItemClickListener(new OnItemClickListener() {

			//@Override
			public void onItemClick(AdapterView<?> adapterView, View view,
					int index, long arg3) {
				// TODO Auto-generated method stub
				String selected_string;
				selected_string = (String) listView.getAdapter().getItem(index);

				Bundle b_RT = new Bundle();
				b_RT.putString("RT_String", selected_string);
				b_RT.putString("s_user", s_user);
				b_RT.putString("s_key_token_1", s_key_token_1);
				b_RT.putString("s_key_token_2", s_key_token_2);
				

				RTwicudroid_STATIC.setGLOB_TWEETSET(tweetlist.twitterstatus[index]);

				Intent RT_intent = new Intent().setClass(contextactivity,
						RTwicudroid_DisplayRTMenu.class);
				RT_intent.putExtras(b_RT);
				startActivity(RT_intent);
			}

		});

	}
	
	@Override public void onBackPressed() { this.getParent().onBackPressed();
	}

	public TweetSet TwitterWordSearch(Twitter twitter, String s_wotd) {
		List<Status> tweetlists = twitter.search(s_wotd+" artwiculate");
		TweetSet tweetset = new TweetSet(tweetlists);
		
		return tweetset;
		
	}
	public String[] TwitterWordSearch2(Twitter twitter, String s_wotd) {

		List tweetlists = twitter.search(s_wotd+" artwiculate");
		Iterator itrtr = tweetlists.iterator();
		String screenname;
		String tweeps;
		int tweethr;
		int tweetmin;
		int tweetsec;
		String tweettime_s;
		String[] tweetlist = new String[tweetlists.size()];

		DecimalFormat dbldigit = new DecimalFormat("00");

		int numtweets = 0;
		while (itrtr.hasNext()) {

			Status tstatus = (Status) itrtr.next();
			screenname = tstatus.user.screenName;
			tweeps = tstatus.text;
			tweethr = tstatus.createdAt.getHours();
			tweetmin = tstatus.createdAt.getMinutes();
			tweetsec = tstatus.createdAt.getSeconds();

			tweettime_s = dbldigit.format(tweethr) + ":"
					+ dbldigit.format(tweetmin) + ":"
					+ dbldigit.format(tweetsec);

			String toreplace = "&quot";
			String replacewith = "'";

			if (tweeps.startsWith("RT @", 0) == false) {
				tweetlist[numtweets] = screenname + ": \n" + tweeps
						+ "\nposted at " + tweettime_s;
				tweetlist[numtweets].replaceAll(toreplace, replacewith);
				numtweets += 1;
			}
		}

		String[] tweetsreturn = new String[numtweets];
		int i;
		for (i = 0; i < numtweets; i++) {
			tweetsreturn[i] = tweetlist[i];
		}
		return tweetsreturn;

	}
	

}

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
import java.util.Iterator;
import java.util.List;

import winterwell.jtwitter.Twitter;
import winterwell.jtwitter.Status;

public class RTwicudroid_FUNC {

public static class TweetSet{
	public Status[] twitterstatus;
	public String[] twittertext;
	
	public TweetSet(List<Status> ts_tweetlist){
		Status[] tstatuses=new Status[ts_tweetlist.size()];
		
		Iterator itrtr = ts_tweetlist.iterator();
		String screenname;
		String tweeps;
		int tweethr;
		int tweetmin;
		int tweetsec;
		String tweettime_s;
		String[] tweetlist = new String[ts_tweetlist.size()];

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
				tweetlist[numtweets] = screenname + ": \n" + tweeps + "\nposted at " + tweettime_s;
				tweetlist[numtweets].replaceAll(toreplace, replacewith);
				tstatuses[numtweets]=tstatus;
				numtweets += 1;
			}
		}

		twittertext = new String[numtweets];
		twitterstatus = new Status[numtweets];
		int i;
		for (i = 0; i < numtweets; i++) {
			twittertext[i] = tweetlist[i];
			twitterstatus[i]=tstatuses[i];
		}

	}
	public TweetSet(List<Status> ts_tweetlist, String s_user){
		Status[] tstatuses=new Status[ts_tweetlist.size()];
		
		Iterator itrtr = ts_tweetlist.iterator();
		String screenname;
		String tweeps;
		int tweethr;
		int tweetmin;
		int tweetsec;
		String tweettime_s;
		String[] tweetlist = new String[ts_tweetlist.size()];

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
				if(screenname.equals(s_user)){
					tweetlist[numtweets] = screenname + ": \n" + tweeps + "\nposted at " + tweettime_s;
					tweetlist[numtweets].replaceAll(toreplace, replacewith);
					tstatuses[numtweets]=tstatus;
					numtweets += 1;
				}
			}
		}

		if (numtweets==0){
			twittertext=null;
			twitterstatus=null;
		}else{
			twittertext = new String[numtweets];
			twitterstatus = new Status[numtweets];
			int i;
			for (i = 0; i < numtweets; i++) {
				twittertext[i] = tweetlist[i];
				twitterstatus[i]=tstatuses[i];
			}
		}
	}
}


}

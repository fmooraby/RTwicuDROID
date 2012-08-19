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

import winterwell.jtwitter.Twitter;
import winterwell.jtwitter.Status;
import winterwell.jtwitter.OAuthSignpostClient;

import com.QualbSoft.RTwicudroid.RTwicudroid_FUNC.TweetSet;


public class RTwicudroid_STATIC {
	public static Status GLOB_TWEETSET=null;
	
	// IF YOU ALREADY HAVE YOUR OWN KEY AND SECRET, ENTER THEM HERE:
	// public static String RTwicudroid_OAUTHKEY="YOUR-OAUTH-KEY-HERE";
	// public static String RTwicudroid_OAUTHSECRET="YOUR-OAUTH-SECRET-HERE";
    
	// OTHERWISE, YOU CAN USE THE ONE FROM JTWITTER BUT IT'S BEST TO USE YOUR OWN!:
	public static String RTwicudroid_OAUTHKEY=OAuthSignpostClient.JTWITTER_OAUTH_KEY;
	public static String RTwicudroid_OAUTHSECRET=OAuthSignpostClient.JTWITTER_OAUTH_SECRET;

	
	public static void setGLOB_TWEETSET(Status tweetstatus){
		GLOB_TWEETSET=tweetstatus;
	}
	
	public static void clearGLOB_TWEETSET(){
		GLOB_TWEETSET=null;
	}
	
	public static Status getGLOB_TWEETSET(){
		return GLOB_TWEETSET;
	}
}

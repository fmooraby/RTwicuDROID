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

import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.List;

import com.QualbSoft.RTwicudroid.RTwicudroid_FUNC.TweetSet;

import android.R;
import android.app.Activity;
import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import winterwell.jtwitter.Twitter;
import winterwell.jtwitter.Status;
import winterwell.jtwitter.TwitterException;



public class RTwicudroid_Indv extends Activity {
    
    private String s_wotd;
    private String s_definition;
    private String s_user;
    private String s_key_token_1;
    private String s_key_token_2;
    private Bundle b_initial;
    private TweetSet tweetlistupdate;
    private String s_tweetlistupdate[];
    
    
	/** Called when the activity is first created. */
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        b_initial = getIntent().getExtras();
    	
        s_wotd = b_initial.getString("s_wotd_word");
        s_definition = b_initial.getString("s__wotd_def");
        s_user = b_initial.getString("s_user");
        s_key_token_1 = b_initial.getString("s_key_token_1");
        s_key_token_2 = b_initial.getString("s_key_token_2");
    	
        String[] tweetnotify = new String [1];
        
        tweetnotify[0]="Enter twitter Screen name of artwiculate user and click button";
        

        setContentView(com.QualbSoft.RTwicudroid.R.layout.indiv_listview);
        final ListView listView = (ListView) findViewById(com.QualbSoft.RTwicudroid.R.id.ListViewIndv);
        listView.setAdapter(new ArrayAdapter(this,com.QualbSoft.RTwicudroid.R.layout.timeline_row,com.QualbSoft.RTwicudroid.R.id.TextView_tweet,tweetnotify));
        //listView.setAdapter(new ArrayAdapter(this,com.QualbSoft.RTwicudroid.R.layout.timeline_row,com.QualbSoft.RTwicudroid.R.id.TextView_name_date,tweetlist));

        Button search_user_tweets_button = (Button) findViewById(com.QualbSoft.RTwicudroid.R.id.ButtonIndv);
        final Context contextactivity= this;
        search_user_tweets_button.setOnClickListener(new OnClickListener() {

			//@Override
			public void onClick(View v) {
				
				EditText user_EditText = (EditText) findViewById(com.QualbSoft.RTwicudroid.R.id.EditTextIndv);
				
				String string_user=user_EditText.getText().toString();
				tweetlistupdate=TwitterWordSearchforUser(string_user, s_wotd);
		        
				if(tweetlistupdate.twitterstatus==null){
					s_tweetlistupdate=new String[1];
					s_tweetlistupdate[0]="No Artwiculate tweets found for this user ("+string_user+")";
				}else{
					s_tweetlistupdate=tweetlistupdate.twittertext;
				}
				
				listView.setAdapter(new ArrayAdapter(contextactivity,com.QualbSoft.RTwicudroid.R.layout.timeline_row,com.QualbSoft.RTwicudroid.R.id.TextView_tweet,s_tweetlistupdate));
		        
			}
        	
        });
        
        
        listView.setOnItemClickListener(new OnItemClickListener(){

			//@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int index,
					long arg3) {
				// TODO Auto-generated method stub
				String selected_string;
				
				selected_string=(String) listView.getAdapter().getItem(index);
				
				Bundle b_RT = new Bundle();
				b_RT.putString("RT_String", selected_string);
				b_RT.putString("s_user", s_user);
				b_RT.putString("s_key_token_1", s_key_token_1);
				b_RT.putString("s_key_token_2", s_key_token_2);
				
				RTwicudroid_STATIC.setGLOB_TWEETSET(tweetlistupdate.twitterstatus[index]);

				Intent RT_intent=new Intent().setClass(contextactivity, RTwicudroid_DisplayRTMenu.class);
				RT_intent.putExtras(b_RT);
				startActivity(RT_intent);
			}
        	
        });
     
    }
	
	@Override public void onBackPressed() { this.getParent().onBackPressed();
	}
	
	public TweetSet TwitterWordSearchforUser(String s_user, String s_wotd){
		Twitter twitter=new Twitter();
		List tweetlists = twitter.search(s_user+" "+s_wotd);
		TweetSet tweetset=new TweetSet(tweetlists, s_user);
		
		return tweetset;
	}
	public String[] TwitterWordSearchforUser2(String s_user, String s_wotd){

		Twitter twitter=new Twitter();
		
		

		List tweetlists = twitter.search(s_user+" "+s_wotd);
        Iterator itrtr = tweetlists.iterator();
        String screenname;
        String tweeps;
        int tweethr;
        int tweetmin;
        int tweetsec;
        String tweettime_s;
        String[] tweetlist = new String [tweetlists.size()];
            
        DecimalFormat dbldigit= new DecimalFormat("00"); 
        
        int numtweets=0;
        while(itrtr.hasNext()){
        
        	Status tstatus = (Status) itrtr.next();
            screenname=tstatus.user.screenName;
            tweeps=tstatus.text;
            tweethr=tstatus.createdAt.getHours();
            tweetmin=tstatus.createdAt.getMinutes();
            tweetsec=tstatus.createdAt.getSeconds();

            tweettime_s=dbldigit.format(tweethr)+":"+dbldigit.format(tweetmin)+":"+dbldigit.format(tweetsec);

            String toreplace="&quot";
            String replacewith="'";

            if(tweeps.startsWith("RT @", 0)==false){
            	if(screenname.equals(s_user)){
                    tweetlist[numtweets]=screenname+": \n"+tweeps+"\nposted at "+tweettime_s;
            		tweetlist[numtweets].replaceAll(toreplace, replacewith);
            		numtweets+=1;
            	}
            }
        }
        
        if (numtweets==0){
        	String[] tweetnolist=new String [1];
        	tweetnolist[0]="No artwiculate tweets found for user"+s_user;
        	return tweetnolist;
        }else{
        	int i;
        	String[] tweetnolist=new String [1];
        	tweetnolist[0]=numtweets+" artwiculate tweets found for user "+s_user+": \n"+tweetlist[0];
        	//return tweetnolist;
        	String[] tweetsreturn= new String [numtweets];
        	
        	for(i=0;i<numtweets;i++){
        		tweetsreturn[i]=tweetlist[i];
        	}
        	tweetnolist[0]="Expected array size is "+numtweets+" but returned array size is "+tweetsreturn.length;
        	return tweetsreturn;
        	//return tweetnolist;
        }
	}
}

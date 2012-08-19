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

import android.app.Activity;
import android.app.TabActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;

import winterwell.jtwitter.OAuthSignpostClient;
import winterwell.jtwitter.Status;
import winterwell.jtwitter.Twitter;
import winterwell.jtwitter.TwitterException;


public class RTwicudroid_Tweetit extends Activity {
    /** Called when the activity is first created. */
    
	@Override
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final String s_wotd;
        String s_definition;
        String s_user;
        String s_key_token_1;
        String s_key_token_2;
        
        Bundle b_initial;
        
        b_initial = getIntent().getExtras();
		
        s_wotd = b_initial.getString("s_wotd_word");
        s_definition = b_initial.getString("s_wotd_def");
        s_user = b_initial.getString("s_user");
        s_key_token_1 = b_initial.getString("s_key_token_1");
        s_key_token_2 = b_initial.getString("s_key_token_2");
		
        
        setContentView(R.layout.tweetit);
        Button tweetit_button = (Button)findViewById(R.id.ButtonTweetIt);
        final EditText tweetit_edittext = (EditText) findViewById(R.id.EditTextTweetit);
        final TextView mystatus_textview=(TextView) findViewById(R.id.TextViewlastupdate);
        tweetit_edittext.setText(s_wotd);
        
        try{    
        	winterwell.jtwitter.OAuthSignpostClient oauthClient = new OAuthSignpostClient(RTwicudroid_STATIC.RTwicudroid_OAUTHKEY, 
    				RTwicudroid_STATIC.RTwicudroid_OAUTHSECRET, s_key_token_1, s_key_token_2);
        	final Twitter twitter=new Twitter(s_user, oauthClient);
        //twitter.setSource("RTwicudroid");
    	
        
        tweetit_button.setOnClickListener(new OnClickListener() {
    		
			//@Override
			public void onClick(View v) {
			      // do something when the button is clicked
					String mytweet_string;
					String mylaststatus_string;
					
					Status twitterstatus= twitter.getStatus();
					mylaststatus_string = twitterstatus.text;
					
					mytweet_string = tweetit_edittext.getText().toString();
			    	twitter.setStatus(mytweet_string);
			    	twitterstatus= twitter.getStatus();
					
			    	mylaststatus_string = "- "+twitter.getStatus().text+"\n\n- "+mylaststatus_string;
			    	mystatus_textview.setText(mylaststatus_string);
			    	tweetit_edittext.setText(s_wotd);
			    }
		});
        }catch (TwitterException e) {
			// TODO Auto-generated catch block
			
			mystatus_textview.setText("Twitter Issue: "+e);
	
		}catch (Exception e) {
			// TODO Auto-generated catch block
			
			mystatus_textview.setText("Other Issues: "+e);
	
		}
    }
	@Override public void onBackPressed() { this.getParent().onBackPressed();
	}

}

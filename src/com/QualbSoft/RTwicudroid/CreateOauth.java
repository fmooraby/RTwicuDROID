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

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URLConnection;

import winterwell.jtwitter.OAuthSignpostClient;
import winterwell.jtwitter.Twitter;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.app.AlertDialog;
import android.content.DialogInterface;

import java.net.URL;
import java.net.URLConnection;

import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;

import com.QualbSoft.RTwicudroid.RTwicudroid_STATIC;
import android.graphics.Bitmap;

public class CreateOauth extends Activity {
	static String S_USER="";
	static String S_PIN="";
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
	
		final winterwell.jtwitter.OAuthSignpostClient oauthClient=new OAuthSignpostClient(RTwicudroid_STATIC.RTwicudroid_OAUTHKEY, 
				RTwicudroid_STATIC.RTwicudroid_OAUTHSECRET, "oob");
		
		
		URI url = oauthClient.authorizeUrl();
		
		//--------------------------------------------------------
		final Context myApp = this;  
		class MyJavaScriptInterface{
			
			@SuppressWarnings("unused")  
			public void showHTML(String html)  
		    {  
		        
				int int_ERR=0;
				HtmlCleaner parser = new HtmlCleaner();
				TagNode node = parser.clean(html);
				
				TagNode node_name = node.findElementByAttValue("class", "name", true, true);		// Look for all DIV Tag with attribute 'id'= 'the_word' where the Word of the Day is
				String twitter_id = "";
				
				if (node_name!=null){
					twitter_id=node_name.getText().toString();
					
				}
				else{
					int_ERR=1;
				}
				
				TagNode node_pin=node.findElementByName("code", true);		
				String str_pin = "";
				if (node_pin!=null){
					str_pin = node_pin.getText().toString();					
				}
				else{
					int_ERR=1;
				}
				
				if (int_ERR==0){
					/*
					new AlertDialog.Builder(myApp)  
		            .setTitle("HTML")  
		            .setMessage("NAME: "+twitter_id+"\nPIN: "+str_pin) 
		            .setPositiveButton(android.R.string.ok, null)  
		            .setCancelable(false)  
		            .create()  
		            .show();
		            */
					S_USER=twitter_id;
					S_PIN=str_pin;
				}
		    }  
		}
		
		//---------------------------------------------------------
		setContentView(R.layout.oauth_signin);
		final WebView webOauth = (WebView)findViewById(R.id.webview_twitteroauth);
		
		//---------------------------------------------------------
		webOauth.getSettings().setJavaScriptEnabled(true);  
		  
		/* Register a new JavaScript interface called HTMLOUT */  
		webOauth.addJavascriptInterface(new MyJavaScriptInterface(), "HTMLOUT");
		//----------------------------------------------------------
		
		webOauth.setWebViewClient(new WebViewClient(){
			
			
			public void onPageFinished (WebView view, String url_load){
				super.onPageFinished(webOauth, url_load);
				
				
				if (url_load.matches("https://twitter.com/oauth/authorize")){
					webOauth.loadUrl("javascript:window.HTMLOUT.showHTML('<head>'+document.getElementsByTagName('html')[0].innerHTML+'</head>');");  
				}
			}
		});
		
		
		webOauth.loadUrl(url.toString());
		
		
		Button btn_oauth = (Button)findViewById(R.id.btn_Oauth);
		
		
		webOauth.setOnTouchListener(new View.OnTouchListener() { 
		    //@Override
		    public boolean onTouch(View arg0, MotionEvent event) {
		        switch (event.getAction()) { 
		                   case MotionEvent.ACTION_DOWN: 
		                   case MotionEvent.ACTION_UP: 
		                       if (!arg0.hasFocus()) { 
		                           arg0.requestFocus(); 
		                       } 
		                       break; 
		               } 
		               return false; 
		            }
		    });

		
		btn_oauth.setOnClickListener(new OnClickListener(){
			
			public void onClick(View arg0) {
				String[] accessToken;
				String str_oauth_pin = S_PIN;
				String s_user = S_USER;
				
				
				oauthClient.setAuthorizationCode(str_oauth_pin);
				accessToken = oauthClient.getAccessToken();
				
				
				
				
				// TEST THE TOKENS BEFORE MOVING ON
				
				winterwell.jtwitter.OAuthSignpostClient oauthClient = new OAuthSignpostClient(RTwicudroid_STATIC.RTwicudroid_OAUTHKEY, 
	    				RTwicudroid_STATIC.RTwicudroid_OAUTHSECRET, accessToken[0], accessToken[1]);
	        	Twitter twitter;
				try {
					twitter = new Twitter(s_user, oauthClient);
					if (twitter==null){
		        	}
					else{
						
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
		        	
				}
	        	
	        	
				
	        	
				Bundle b_oauth = getIntent().getExtras();
				b_oauth.putString("s_user", s_user);
				b_oauth.putString("s_key_token_1", accessToken[0]);
				b_oauth.putString("s_key_token_2", accessToken[1]);
				
				//UPDATE VALUE IN DATABASE
		    	SQLiteDatabase UserDB = openOrCreateDatabase("RTwicudroidDB", MODE_PRIVATE, null);//.openDatabase("RTwicudroidDB", null, MODE_PRIVATE);		    	
		    	UserDB.execSQL("INSERT INTO LOGINS (USERNAME, OAUTHKEY1, OAUTHKEY2) VALUES(\""+s_user+"\",\""+accessToken[0]+"\",\""+accessToken[1]+"\")");
		    	UserDB.close();
		    	
		    	// PASS ON INFORMATION (WORD OF THE DAY) TO NEXT ACTIVITY
		    	
		    	// START NEW INTENT
		    	Intent nextactivity = new Intent(arg0.getContext(), RTwicudroid_TAB.class);
		    	
		    	finish();
				nextactivity.putExtras(b_oauth);
				startActivity(nextactivity);
			}
		});
		
		
	}
}



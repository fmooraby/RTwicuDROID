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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.ShortBufferException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;

import org.htmlcleaner.*;
import winterwell.jtwitter.*;
import winterwell.jtwitter.android.*;
import winterwell.jtwitter.ecosystem.*;
import winterwell.jtwitter.guts.*;
import winterwell.jtwitter.OAuthSignpostClient;

//import oauth.signpost.*;
//import oauth.signpost.basic.*;
//import oauth.signpost.exception.*;
//import oauth.signpost.http.*;
//import oauth.signpost.signature.*;

public class RTwicudroidActivity extends Activity {
    /** Called when the activity is first created. */
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // LOAD THE INTRO SCREEN
        setContentView(R.layout.introwin);
       
        // INITIALISE VARIABLES (BUTTONS, TEXTVIEW, ...)
        Button session_button = (Button)findViewById(R.id.btn_session);
        Button register_button = (Button)findViewById(R.id.btn_register);
        Button about_button = (Button)findViewById(R.id.btn_about);
        Button exit_button = (Button)findViewById(R.id.btn_exit);
        
       
        
        // SESSION PRESSING BUTTON
        session_button.setOnClickListener(new OnClickListener(){

			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				 
				try {
					 // GET WORD OF THE DAY AND DEFINITION
					String[] s_wotd= getwotd();
					
					// LOAD OR CREATE USER DATABASE
					loadconfigfile();
					// CHECK SAVED USER INFORMATION (BOOLEAN RETURN - FALSE IF NO USER DATA OR KEY SAVED)
					boolean b_saved_user_data = check_user_data();


					// IF NO USER DATA SAVED:
					if (!b_saved_user_data){
						create_user_data_and_start(arg0, s_wotd);
					}
					else{
					// ELSE - DO NOTHING
						// start_session
						// OPEN DATABASE TO GET USER AND KEY INFORMATION
						String s_user=get_user_data();
						String[] s_keys = get_user_tokens();
						
						// PUT REUSED VALUES IN BUNDLE
						Bundle b_global = new Bundle();
						b_global.putString("s_user", s_user);
						b_global.putString("s_key_token_1", s_keys[0]);
						b_global.putString("s_key_token_2", s_keys[1]);
						b_global.putString("s_wotd_word", s_wotd[0]);
						b_global.putString("s_wotd_def", s_wotd[1]);
						
						//START NEW INTENT AND CLOSE THIS VIEW
						Intent nextactivity=new Intent(arg0.getContext(), RTwicudroid_TAB.class);
						nextactivity.putExtras(b_global);
						
						finish();
						startActivity(nextactivity
								.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
   	 
        				//startActivity(nextactivity);
						
						
					}
										
				
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvalidKeyException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (BadPaddingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalBlockSizeException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoSuchAlgorithmException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoSuchPaddingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ShortBufferException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Throwable e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
	
        });
        
        
        
        register_button.setOnClickListener(new OnClickListener(){

			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				// START LOAD REGISTER TWITTER LAYOUT AND INTENT
				Intent twitterRegister_Intent = new Intent(arg0.getContext(), TwitterRegister.class);
		    	startActivity(twitterRegister_Intent);
		    	
			}
        	
        });
        
        
        about_button.setOnClickListener(new OnClickListener(){

			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				// LOAD ABOUT DIALOG WITH OK BUTTON AT THE END
				AlertDialog.Builder alertDialog = new AlertDialog.Builder(arg0.getContext());
				
				alertDialog.setTitle("About RTwicuDROID");
				String s_about = "";
				// TEXT INCLUDES:
				
				// VERSION NUMBER
				
				String app_ver=""; 
				try
				{
				    app_ver = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
				}
				catch (NameNotFoundException e)
				{
				    //Log.v(tag, e.getMessage());
				}
				s_about = s_about+"Version Number: "+app_ver;
				
				// PUBLISHER
				s_about = s_about+"\n\nPublisher: Qualbsoft";
				
				// PURPOSE OF THE GAME
				s_about = s_about+"\n\nRTwicudroid is an Android interface to the popular Twitter game Artwiculate.";
				s_about = s_about+"\nArtwiculate is a game through which users tweet a dedicated Word of the Day in a sentence or phrase.";
				s_about = s_about+" Users can also vote for their favourite Artwiculate quotes by retweeting.";
				
				// THIS IS ONLY THE INTERFACE ON ANDROID
				s_about = s_about+"\n\nQualbsoft is by no mean claiming ownership of Arwticulate. RTwicudroid is a mean to make the game more interactive on Android.";
				s_about = s_about+"\nVisit www.artwiculate.com for more details on the game.";
				
				
				// BUILT ON ENGINES
				s_about = s_about+"\n\nRTwicudroid uses the following engines:\n   - JTwitter\n   - HTMLCleaner";
				// BRIEF EXPLANATION OF ENGINES
				s_about = s_about+"\n\nJTwitter is an Open Source Twitter API for Java under the LGPL license.";
				s_about = s_about+"\nJtwitter is maintained by Winterwell and a copy can be obtained at";
				s_about = s_about+"\nwinterwell.com/software/jtwitter.php";
				s_about = s_about+"\n\nHTMLCleaner is an Open Source HTML Parser API under the BSD license.";
				s_about = s_about+"\nHTMLCleaner can be obtained at:";
				s_about = s_about+"\nhttp://htmlcleaner.sourceforge.net/";
				alertDialog.setMessage(s_about);
				
				alertDialog.setPositiveButton("Ok",
					    new DialogInterface.OnClickListener() {
					        public void onClick(DialogInterface dialog, int which) {
					          //dismiss the dialog  
					        }
					    });
				
				alertDialog.show();
				
			}
        	
        });
        
        
        
        exit_button.setOnClickListener(new OnClickListener(){

			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				finish();
			}
        	
        });
        
    }
    
    
    // LOAD CONFIG FILE (SQL DATABASE WITH USER AND ENCRYPTION INFORMATION)
    private void loadconfigfile() throws FileNotFoundException, NameNotFoundException {
    	
    	SQLiteDatabase UserDB;
    	UserDB=openOrCreateDatabase("RTwicudroidDB", MODE_PRIVATE, null);
    	
    	Calendar c = Calendar.getInstance();
    	
    	String currentDateTimeString = c.get(Calendar.YEAR) +""+c.get(Calendar.MONTH)+""+c.get(Calendar.DAY_OF_MONTH);

    	SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        String strInstallDate = sharedPreferences.getString("InstallDate", "");
        
        String strVersion = sharedPreferences.getString("Version", "");
        String app_ver=""; 
		app_ver = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
		
		if (!app_ver.contentEquals(strVersion)){
        	
        	UserDB.execSQL("DROP TABLE IF EXISTS LOGINS");
        	
        }
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("InstallDate", currentDateTimeString);
        editor.putString("Version", app_ver);
        
        editor.commit();
        
        
    	UserDB.execSQL("CREATE TABLE IF NOT EXISTS LOGINS (ID INTEGER PRIMARY KEY, USERNAME TEXT, OAUTHKEY1 TEXT, OAUTHKEY2 TEXT);");
    	
    	//******** UserDB.execSQL("CREATE TABLE IF NOT EXISTS SETTINGS (ID INTEGER PRIMARY KEY, USERID INTEGER, AUTOLOGON BOOLEAN);");
    	//******** UserDB.execSQL("CREATE TABLE IF NOT EXISTS FAVS (SCREENNAME TEXT);");
    	
    	
    	/// FOR TESTING
    	//UserDB.execSQL("INSERT INTO LOGINS (USERNAME, ENCPASS) VALUES('faisal___','Mohamud*0786')");
    	//UserDB.execSQL("INSERT INTO LOGINS (USERNAME, ENCPASS) VALUES('fusiongo','Mohamud*0786')");
    	//UserDB.execSQL("INSERT INTO LOGINS (USERNAME, ENCPASS) VALUES('rubbishex','somerubishhere')");
    	UserDB.close();
    	
    	 
    }
    
    // LOAD USER DETAILS
    private String get_user_data(){
		String s_User= new String();
		SQLiteDatabase configdoc;
		int i=1;
		
		
		configdoc=openOrCreateDatabase("RTwicudroidDB", MODE_PRIVATE, null);//.openDatabase("RTwicudroidDB", null, MODE_PRIVATE);
		
		s_User=null;
		
		Cursor c=configdoc.rawQuery("SELECT USERNAME FROM LOGINS", null);		
		int colindex=c.getColumnIndex("USERNAME");
		if (c !=null){
			int rows=c.getCount();
			while (i<=rows){
				c.moveToNext();
				s_User=c.getString(colindex);
				//ArrayListUsers.add(s_user);
				i++;
			}
		}
		
		c.close();
		configdoc.close();
		
		//return ArrayListUsers;
		if (s_User==null){
			s_User="";
		}
		return s_User;
	}
    
    // LOAD ENCRYPTION KEYS
    private String[] get_user_tokens() throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, NoSuchPaddingException, ShortBufferException, IOException, Throwable{
		
    	ArrayList<String> ArrayListKeys= new ArrayList();
		SQLiteDatabase configdoc;
		String UserName;
		String[] s_user_tokens = new String[]{"", ""};
		
		int i=1;
		int i_flag=0;
		
		configdoc=openOrCreateDatabase("RTwicudroidDB", MODE_PRIVATE, null);//.openDatabase("RTwicudroidDB", null, MODE_PRIVATE);
		//configdoc=SQLiteDatabase.openDatabase("RTwicudroidDB", null, configdoc.OPEN_READONLY);
		
		Cursor c=configdoc.rawQuery("SELECT OAUTHKEY1, OAUTHKEY2 FROM LOGINS", null);		
		
		//startManagingCursor(c);
		int colindex1=c.getColumnIndex("OAUTHKEY1");
		int colindex2=c.getColumnIndex("OAUTHKEY2");
		
		if (c !=null){
			int rows=c.getCount();
				
			while (i<=rows){
				c.moveToNext();
				String s1 = c.getString(colindex1);
				String s2 = c.getString(colindex2);

				s_user_tokens[0]=s1;
				s_user_tokens[1]=s2;
				/*String s1 = c.getString(colindex1);
				String s2 = c.getString(colindex2);
				s_user_tokens[0]=s1;
				s_user_tokens[1]=s2;
				Log.d("key_check","COL1 data: "+s1+",   COL1 data length: "+c.getString(colindex1).length()+"   Is Col1 Null?: "+c.isNull(colindex1));
				*/
				if (c.getString(colindex1).contentEquals("null") || c.getString(colindex2).contentEquals("null") || c.isNull(colindex1) || c.isNull(colindex2)){
					i_flag=1;
					s_user_tokens=null;
					
				}else{
					//i_flag=0;
						//s_pass=superdecrypt(s_pass,UserName.get(i-1).toString());
						//ArrayListPasswords.add(s_pass);
					//ArrayListKeys.add(c.getString(colindex1));
					//ArrayListKeys.add(c.getString(colindex2));
					//s_user_tokens[0]=s1;
					//s_user_tokens[1]=s2;
					///
					///
				}				
				
				
				i++;
			}
		}
		
		c.close();
		if (i_flag==1){
			configdoc.execSQL("DELETE FROM LOGINS");
	    	
		}
		
		
		//Log.d("key_check","Key Check: Number of keys="+ArrayListKeys.size());
		
		configdoc.close();
		
		
		//return ArrayListKeys;
		return s_user_tokens;
	}
    
    private boolean check_user_data() throws Throwable {
		// TODO Auto-generated method stub
    	String s_user = get_user_data();
		
    	
		if (s_user==null || s_user.length()==0){
			return false;
		}
		else{
			String[] s_tokens=get_user_tokens();
			if (s_tokens==null){
				return false;
			}
			else{
				return true;
			}
			
		}
	}
    
    
    
    
    
    private void create_user_data_and_start(View v, String[] s_wotd) {
		// TODO Auto-generated method stub
    	////// String s_user, s_key1,  s_key2;
    	////// SQLiteDatabase UserDB;
    	
    	
    	// LOAD OAUTH VIEW & INSERT CREATED USER DATA IN DATABASE (in CreateOauth activity)
    	// ALSO PASS ON THE WOTD AND DEFINITION TO THE NEW INTENT
    	Bundle b_global=new Bundle();
    	b_global.putString("s_wotd_word", s_wotd[0]);
    	b_global.putString("s_wotd_def", s_wotd[1]);
    	
    	
    	Intent createOauth_Intent = new Intent(v.getContext(), CreateOauth.class);
    	createOauth_Intent.putExtras(b_global);
    	startActivity(createOauth_Intent);
    	
    	// CLOSE THIS ACTIVITY
    	finish();
    }
    
  
    /*
private void saveuserstoconfigfile(String s_user, String s_key1, String s_key2, SQLiteDatabase UserDB) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, NoSuchPaddingException, ShortBufferException, IOException{
		
		//UPDATE VALUE IN DATABASE
		UserDB=openOrCreateDatabase("RTwicudroidDB", MODE_PRIVATE, null);//.openDatabase("RTwicudroidDB", null, MODE_PRIVATE);
		
		Log.d("FUNCTION", "SAVEUSERSTOCONFIGFILE();");
		
		Log.d("save_config", "Opened DB");
		
		UserDB.execSQL("INSERT INTO LOGINS (USERNAME, OAUTHKEY1, OAUTHKEY2) VALUES(\""+s_user+"\",\""+s_key1+"\",\""+s_key2+"\")");
		Log.d("save_config", "Inserted Users details. User:"+s_user+", key1:"+s_key1+", key2: "+s_key2);
		
		UserDB.close();
	}
*/
    
public String[] getwotd() throws IOException, MalformedURLException{
	
	
	Twitter twitter= new Twitter();
	
	List<Status> statuses = twitter.search("artwiculate \"Today's word is: \" Definition here");
	String[] swotd=new String[2];
	
    // GET THE WORD OF THE DAY FROM THE ARTWICULATE WEBSITE
	HtmlCleaner parser = new HtmlCleaner();		// Initialise the HTML Parser
	URL url = new URL("http://www.artwiculate.com");// Initialise URL Variable & Set Url to Artwiculate Website
	
	URLConnection conn = url.openConnection();			// Open the Connection

	TagNode node = parser.clean(new InputStreamReader(conn.getInputStream()));		// Initalise a Tagnode on the website html	
	TagNode node2 = node.findElementByAttValue("id", "the_word", true, true);		// Look for all DIV Tag with attribute 'id'= 'the_word' where the Word of the Day is
	
	swotd[0]="";
	if (node2==null){
		String twitter_status = statuses.get(0).text;
		int i_start=twitter_status.toLowerCase().indexOf("today's word is: \"");
		int i_end=twitter_status.toLowerCase().indexOf("\"", i_start+18);
		
		swotd[0]=twitter_status.substring(i_start+18,i_end);		
	}
	else{
		swotd[0]=node2.getText().toString();		
	}
	TagNode node5[]=node.getElementsByAttValue("class", "definition", true, true);	// Look for All Tag with Attribute 'Class' = 'definition' to look for definitions of the word
	swotd[1]="";				// Initialise the string to get all the definitions
	String content_string="";			// This will contain only 1 
	int span_tag_position;
			
	swotd[1]="   ";				// Add indentation to the string (for display purposes
	for( int i=0; i <node5.length;i++){		// For all the TagNodes containing tags with attribute 'class' = 'definition'
		TagNode nodeid = node5[i];					// Initialise a TagNode to get the ith Tag from the TagNode Array
		
		content_string=nodeid.getChildren().toString();		// Extract the all children of the Tag to content_string string
		
		span_tag_position=content_string.indexOf("span,");	// We do not need Spans, so look out for their positions in the string
		content_string=content_string.substring(span_tag_position+5);  // Remove some more redundant info by removing part after span +5 char
		content_string=content_string.substring(0, content_string.length()-1); // Remove more info
		content_string=content_string.trim();	// Trim leading and trailing spaces
				
		swotd[1] +=  (i+1)+". "+content_string+"\n   "; // Concat it to the liststring string
	}
	
	return swotd;
    }
}


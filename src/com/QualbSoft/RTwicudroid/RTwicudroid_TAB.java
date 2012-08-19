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

import android.app.TabActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TextView;


public class RTwicudroid_TAB extends TabActivity {
	/** Called when the activity is first created. */
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String s_wotd;
        String s_definition;
       
        String s_user;
        String s_key_token_1;
        String s_key_token_2;
        
        //Bundle b_initial= new Bundle();
        Bundle b_initial = getIntent().getExtras();
		
        s_wotd = b_initial.getString("s_wotd_word");
        s_definition = b_initial.getString("s_wotd_def");
        s_user = b_initial.getString("s_user");
        s_key_token_1 = b_initial.getString("s_key_token_1");
        s_key_token_2 = b_initial.getString("s_key_token_2");	
        
        setContentView(R.layout.main);
        
        TextView tTextView_Wotd = (TextView) findViewById(R.id.TextView_wotd);
        final TextView tTextView_definition = (TextView) findViewById(R.id.TextView_definition);
        tTextView_Wotd.setText("Word of the day: "+s_wotd);
        tTextView_definition.setText("Definition:\n"+s_definition);
        
        final Button definition_button=(Button) findViewById(R.id.ButtonDefinition);
	   

        definition_button.setOnClickListener(new OnClickListener(){

			//@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (definition_button.getText().toString()!="Show Definition"){
					definition_button.setText("Show Definition");
					tTextView_definition.setVisibility(8);//tTextView_definition.setText("");
					
				}else{
					definition_button.setText("Hide Definition");
					tTextView_definition.setVisibility(0);//tTextView_definition.setText("Definition:\n"+s_definition);
				}
			}
	    	
	    });

Intent intent1 = new Intent().setClass(this, RTwicudroid_TimeLine.class);
Intent intent2 = new Intent().setClass(this, RTwicudroid_Indv.class);
Intent intent3 = new Intent().setClass(this, RTwicudroid_Tweetit.class);
intent1.putExtras(b_initial);
intent2.putExtras(b_initial);
intent3.putExtras(b_initial);



TabHost mtabHost = getTabHost();  // The activity TabHost
TabHost.TabSpec spec;  // Resusable TabSpec for each tab

spec = mtabHost.newTabSpec("timeline").setIndicator("Timeline",
                  null).setContent(intent1);
mtabHost.addTab(spec);

// Do the same for the other tabs
spec = mtabHost.newTabSpec("individual").setIndicator("Indv. Tweets",null).setContent(intent2);
mtabHost.addTab(spec);

spec = mtabHost.newTabSpec("tweetit").setIndicator("Tweet it",null).setContent(intent3);
mtabHost.addTab(spec);

mtabHost.setCurrentTab(0);
        
        
    }
    
    
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    	
    	startActivity(new Intent(this, CloseALL.class)
    	 .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
    	 
    	finish();
        
    }
}

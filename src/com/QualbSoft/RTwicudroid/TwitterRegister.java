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

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;

public class TwitterRegister extends Activity {
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.twitter_register);
        WebView webview_twitterregister = (WebView)findViewById(R.id.webview_twitterregister);
        Button btn_TwitterRegister = (Button)findViewById(R.id.btn_TwitterRegister);
        
        webview_twitterregister.loadUrl("https://twitter.com/signup");
        
        webview_twitterregister.setOnTouchListener(new View.OnTouchListener() { 
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
        
        btn_TwitterRegister.setOnClickListener(new OnClickListener(){
			
			public void onClick(View arg0) {
				finish();
			}
		
        });

	}
}

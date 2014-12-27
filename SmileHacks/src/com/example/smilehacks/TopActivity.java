package com.example.smilehacks;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class TopActivity extends Activity {

	Button bt1, bt2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_top);
		
		bt1 = (Button) findViewById(R.id.button1);//カメラへのボタン
		bt2 = (Button) findViewById(R.id.button2);//ギャラリーへのボタン
		
		bt1.setOnClickListener(new SampleClickListener());
		bt2.setOnClickListener(new SampleClickListener());
	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.top, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	class SampleClickListener implements OnClickListener{
    	public void onClick(View v){
    		if(v == bt1){
    			Intent i = new Intent(getApplication(), com.example.smilehacks.CameraActivity.class);
    			//アクティビティの開始
    			startActivity(i);
    		}
    		if(v == bt2){
            	Intent i = new Intent(getApplication(), com.example.smilehacks.GalleryActivity.class);
            	//アクティビティの開始
            	startActivity(i);
        	}
    	}
    }
}
